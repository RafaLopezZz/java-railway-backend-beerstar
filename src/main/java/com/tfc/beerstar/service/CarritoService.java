package com.tfc.beerstar.service;

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

    public CarritoService(CarritoRepository carritoRepo,
            ArticulosRepository articuloRepo, DetalleCarritoRepository detalleCarritoRepository) {
        this.carritoRepository = carritoRepo;
        this.articuloRepository = articuloRepo;
        this.detalleCarritoRepository = detalleCarritoRepository;
    }

    @Transactional
    public CarritoResponseDTO agregarACarrito(Cliente cliente, AddToCarritoRequestDTO request) {
        // Validación de cantidad
        if (request.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        // Buscar o crear carrito para el cliente
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setCliente(cliente);
                    return carritoRepository.save(nuevoCarrito);
                });

        // Verificar existencia del artículo
        Articulos articulo = articuloRepository.findById(request.getArticuloId())
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no existe"));

        // Verificar stock disponible
        if (articulo.getStock() < request.getCantidad()) {
            throw new StockInsuficienteException("Stock insuficiente para el artículo: " + articulo.getNombre());
        }

        // Buscar si ya existe el artículo en el carrito o crear nuevo detalle
        DetalleCarrito detalle = carrito.getDetalleList().stream()
                .filter(d -> d.getArticulos().getIdArticulo().equals(articulo.getIdArticulo()))
                .findFirst()
                .orElseGet(() -> {
                    DetalleCarrito nuevoDetalle = new DetalleCarrito();
                    nuevoDetalle.setCarrito(carrito);
                    nuevoDetalle.setArticulos(articulo);
                    nuevoDetalle.setCantidad(0); // Inicializar en cero
                    carrito.getDetalleList().add(nuevoDetalle);
                    return nuevoDetalle;
                });

        // Verificar stock para cantidad total
        int nuevaCantidad = detalle.getCantidad() + request.getCantidad();
        if (articulo.getStock() < nuevaCantidad) {
            throw new StockInsuficienteException("Stock insuficiente para la cantidad total solicitada");
        }

        // Actualizar cantidad
        detalle.setCantidad(nuevaCantidad);

        // Actualizar stock (opcional: implementar reserva de stock)
        articulo.setStock(articulo.getStock() - request.getCantidad());
        articuloRepository.save(articulo);

        // Guardar carrito actualizado
        carritoRepository.save(carrito);

        return mapearCarritoResponseDTO(carrito);

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
        }

        // Devolver stock
        articulo.setStock(articulo.getStock() + 1);
        articuloRepository.save(articulo);

        // Guardar cambios
        Carrito carritoActualizado = carritoRepository.save(carrito);

        return mapearCarritoResponseDTO(carritoActualizado);
    }

    public CarritoResponseDTO verCarrito(Cliente cliente) {
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito vacío"));

        return mapearCarritoResponseDTO(carrito);
    }

    @Transactional
    public void vaciarCarrito(Cliente cliente) {
        carritoRepository.deleteByCliente(cliente);
    }

    private CarritoResponseDTO mapearCarritoResponseDTO(Carrito carrito) {
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setId(carrito.getIdCarrito());
        dto.setFechaCreacion(carrito.getFechaCreacion());

        List<DetalleCarritoResponseDTO> items = carrito.getDetalleList().stream()
                .map(this::mapearDetalleCarritoDTO)
                .collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }

    private DetalleCarritoResponseDTO mapearDetalleCarritoDTO(DetalleCarrito detalle) {
        DetalleCarritoResponseDTO dto = new DetalleCarritoResponseDTO();
        dto.setId(detalle.getIdDetalleCarrito());
        dto.setIdArticulo(detalle.getArticulos().getIdArticulo());
        dto.setNombreArticulo(detalle.getArticulos().getNombre());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getArticulos().getPrecio());
        return dto;
    }
}
