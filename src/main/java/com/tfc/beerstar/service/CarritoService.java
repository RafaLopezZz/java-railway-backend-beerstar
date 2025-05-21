package com.tfc.beerstar.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfc.beerstar.dto.request.AddToCarritoRequestDTO;
import com.tfc.beerstar.dto.response.CarritoResponseDTO;
import com.tfc.beerstar.dto.response.DetalleCarritoResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.exception.StockInsuficienteException;
import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Carrito;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.DetalleCarrito;
import com.tfc.beerstar.repository.ArticulosRepository;
import com.tfc.beerstar.repository.CarritoRepository;
import com.tfc.beerstar.repository.DetalleCarritoRepository;

@Service
public class CarritoService {

    private final DetalleCarritoRepository detalleCarritoRepository;
    private final CarritoRepository carritoRepository;
    private final ArticulosRepository articuloRepository;

    public CarritoService(CarritoRepository carritoRepository,
            ArticulosRepository articuloRepository, DetalleCarritoRepository detalleCarritoRepository) {
        this.carritoRepository = carritoRepository;
        this.articuloRepository = articuloRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
    }

    @Transactional
    public CarritoResponseDTO agregarACarrito(Cliente cliente, AddToCarritoRequestDTO request) {
        // Validación de cantidad
        if (request.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Carrito carrito = carritoRepository
                .findByClienteConDetalles(cliente)
                .map(c -> {
                    // Si ya existía pero estaba finalizado, lo reseteamos
                    if (Boolean.TRUE.equals(c.getFinalizado())) {
                        c.setFinalizado(false);
                        c.getDetalleList().clear();
                        c.setSubtotal(BigDecimal.ZERO);
                        c.setImpuestos(BigDecimal.ZERO);
                        c.setGastosEnvio(BigDecimal.ZERO);
                        c.setTotal(BigDecimal.ZERO);
                    }
                    return c;
                })
                .orElseGet(() -> crearCarritoNuevo(cliente));

        // Verificar existencia del artículo
        Articulos articulo = articuloRepository.findById(request.getIdArticulo())
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no existe"));

        // Verificar stock disponible
        if (articulo.getStock() < request.getCantidad()) {
            throw new StockInsuficienteException("Stock insuficiente para el artículo: " + articulo.getNombre());
        }

        // Buscar si ya existe el artículo en el carrito o crear nuevo detalle
        DetalleCarrito detalle = carrito.getDetalleList().stream()
                .filter(d -> d.getArticulos().getIdArticulo()
                .equals(articulo.getIdArticulo()))
                .findFirst()
                .orElseGet(() -> {
                    DetalleCarrito d = new DetalleCarrito();
                    d.setCarrito(carrito);
                    d.setArticulos(articulo);
                    d.setCantidad(0);
                    d.setPrecioUnitario(articulo.getPrecio());
                    // guardamos para forzar ID (cascade=ALL, no haría falta, pero nos aseguramos)
                    DetalleCarrito saved = detalleCarritoRepository.save(d);
                    carrito.getDetalleList().add(saved);
                    return saved;
                });

        // Verificar stock para cantidad total
        int nuevaCantidad = detalle.getCantidad() + request.getCantidad();
        if (articulo.getStock() < nuevaCantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente. Tienes " + detalle.getCantidad()
                    + " unidades en el carrito y solo hay " + articulo.getStock()
                    + " disponibles en total.");
        }

        // Actualizar cantidad y precios
        detalle.setCantidad(nuevaCantidad);
        detalle.setPrecioUnitario(articulo.getPrecio()); // Asegurarse de que el precio esté actualizado

        // Verificar si el método calcularTotalLinea existe
        try {
            detalle.calcularTotalLinea();
        } catch (Exception ex) {
            // Si no existe, calcular manualmente
            detalle.setTotalLinea(detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())));
        }

        // Recalcular totales del carrito
        try {
            carrito.recalcularTotales();
        } catch (Exception ex) {
            // Si el método no existe, implementar cálculo básico
            BigDecimal subtotal = carrito.getDetalleList().stream()
                    .map(DetalleCarrito::getTotalLinea)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            carrito.setSubtotal(subtotal);
            carrito.setImpuestos(subtotal.multiply(new BigDecimal("0.21")));
            carrito.setTotal(subtotal.add(carrito.getImpuestos()).add(carrito.getGastosEnvio()));
        }
        // Actualizar stock del artículo
        articulo.setStock(articulo.getStock() - request.getCantidad());
        articuloRepository.save(articulo);

        carritoRepository.save(carrito);

        return mapearCarritoResponseDTO(carrito);
    }

    private Carrito crearCarritoNuevo(Cliente cliente) {
        Carrito nuevo = new Carrito();
        nuevo.setCliente(cliente);
        nuevo.setFechaCreacion(Instant.now());
        nuevo.setSubtotal(BigDecimal.ZERO);
        nuevo.setImpuestos(BigDecimal.ZERO);
        nuevo.setGastosEnvio(BigDecimal.ZERO);
        nuevo.setTotal(BigDecimal.ZERO);
        // la lista detalleList ya viene inicializada en el constructor de la entidad
        return carritoRepository.save(nuevo);
    }

    @Transactional
    public CarritoResponseDTO decrementarArticulo(Cliente cliente, Long articuloId) {
        // Buscar carrito del cliente
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

        // Buscar el detalle correspondiente al artículo
        DetalleCarrito detalle = carrito.getDetalleList().stream()
                .filter(d -> d.getArticulos().getIdArticulo().equals(articuloId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado en el carrito"));

        // Obtener referencia al artículo
        Articulos articulo = detalle.getArticulos();

        // Si solo hay una unidad, eliminar el detalle
        if (detalle.getCantidad() == 1) {
            carrito.getDetalleList().remove(detalle);
            detalleCarritoRepository.delete(detalle);
        } else {
            // Si hay más unidades, decrementar
            detalle.setCantidad(detalle.getCantidad() - 1);
            detalle.calcularTotalLinea();
        }

        // Recalcular totales del carrito
        carrito.recalcularTotales();
        carritoRepository.save(carrito);
        // Devolver stock
        articulo.setStock(articulo.getStock() + 1);
        articuloRepository.save(articulo);

        // Guardar cambios
        Carrito carritoActualizado = carritoRepository.save(carrito);

        return mapearCarritoResponseDTO(carritoActualizado);
    }

    public CarritoResponseDTO verCarrito(Cliente cliente) {
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setCliente(cliente);
                    nuevoCarrito.setFechaCreacion(Instant.now());
                    nuevoCarrito.setSubtotal(BigDecimal.ZERO);
                    nuevoCarrito.setImpuestos(BigDecimal.ZERO);
                    nuevoCarrito.setGastosEnvio(BigDecimal.ZERO);
                    nuevoCarrito.setTotal(BigDecimal.ZERO);
                    return carritoRepository.save(nuevoCarrito);
                });

        return mapearCarritoResponseDTO(carrito);
    }

    @Transactional
    public void vaciarCarrito(Cliente cliente) {
        // Recuperar carrito junto con sus detalles
        Carrito carrito = carritoRepository
                .findByClienteConDetalles(cliente)
                .orElse(null);

        if (carrito != null && !carrito.getDetalleList().isEmpty()) {
            // Devolver stock de cada artículo
            for (DetalleCarrito detalle : carrito.getDetalleList()) {
                Articulos articulo = detalle.getArticulos();
                articulo.setStock(articulo.getStock() + detalle.getCantidad());
                articuloRepository.save(articulo);
            }

            // Limpiar la lista de detalles (orphanRemoval eliminará en BD)
            carrito.getDetalleList().clear();

            // Recalcular totales (quedarán a cero)
            carrito.recalcularTotales();

            // Persistir el carrito vacío
            carritoRepository.save(carrito);
        }
    }

    private CarritoResponseDTO mapearCarritoResponseDTO(Carrito carrito) {
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setId(carrito.getIdCarrito());
        dto.setFechaCreacion(carrito.getFechaCreacion());

        List<DetalleCarritoResponseDTO> items = carrito.getDetalleList() != null
                ? carrito.getDetalleList().stream()
                        .map(this::mapearDetalleCarritoDTO)
                        .collect(Collectors.toList())
                : new ArrayList<>();

        dto.setItems(items);
        dto.setSubtotal(carrito.getSubtotal() != null ? carrito.getSubtotal() : BigDecimal.ZERO);
        dto.setImpuestos(carrito.getImpuestos() != null ? carrito.getImpuestos() : BigDecimal.ZERO);
        dto.setGastosEnvio(carrito.getGastosEnvio() != null ? carrito.getGastosEnvio() : BigDecimal.ZERO);
        dto.setTotal(carrito.getTotal() != null ? carrito.getTotal() : BigDecimal.ZERO);

        return dto;
    }

    private DetalleCarritoResponseDTO mapearDetalleCarritoDTO(DetalleCarrito detalle) {
        DetalleCarritoResponseDTO dto = new DetalleCarritoResponseDTO();
        dto.setId(detalle.getIdDetalleCarrito());
        dto.setIdArticulo(detalle.getArticulos().getIdArticulo());
        dto.setNombreArticulo(detalle.getArticulos().getNombre());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setTotalLinea(detalle.getTotalLinea());
        return dto;
    }
}
