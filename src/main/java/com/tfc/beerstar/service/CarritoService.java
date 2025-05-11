package com.tfc.beerstar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfc.beerstar.dto.request.AddToCarritoRequestDTO;
import com.tfc.beerstar.dto.response.CarritoResponseDTO;
import com.tfc.beerstar.dto.response.DetalleCarritoResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Carrito;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.DetalleCarrito;
import com.tfc.beerstar.repository.ArticulosRepository;
import com.tfc.beerstar.repository.CarritoRepository;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final ArticulosRepository articuloRepo;

    public CarritoService(CarritoRepository carritoRepo,
            ArticulosRepository articuloRepo) {
        this.carritoRepo = carritoRepo;
        this.articuloRepo = articuloRepo;
    }

    @Transactional
    public CarritoResponseDTO agregarACarrito(Cliente cliente, AddToCarritoRequestDTO request) {
        if (request.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Carrito carrito = carritoRepo.findByCliente(cliente)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setCliente(cliente);
                    return carritoRepo.save(nuevoCarrito);
                });

        Articulos articulo = articuloRepo.findById(request.getArticuloId())
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no existe"));

        DetalleCarrito detalle = carrito.getDetalleList().stream()
                .filter(d -> d.getArticulos().equals(articulo))
                .findFirst()
                .orElseGet(() -> {
                    DetalleCarrito nuevoDetalle = new DetalleCarrito();
                    nuevoDetalle.setCarrito(carrito);
                    nuevoDetalle.setArticulos(articulo);
                    carrito.getDetalleList().add(nuevoDetalle);
                    return nuevoDetalle;
                });

        detalle.setCantidad(detalle.getCantidad() + request.getCantidad());
        carritoRepo.save(carrito);
        return mapearCarritoResponseDTO(carrito);
    }

    public CarritoResponseDTO verCarrito(Cliente cliente) {
        Carrito carrito = carritoRepo.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito vacío"));

        return mapearCarritoResponseDTO(carrito);
    }

    @Transactional
    public void vaciarCarrito(Cliente cliente) {
        carritoRepo.deleteByCliente(cliente);
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
