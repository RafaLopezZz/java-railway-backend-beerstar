package com.tfc.beerstar.service;

import java.math.BigDecimal;
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
                    Carrito c = new Carrito();
                    c.setCliente(cliente);
                    return carritoRepo.save(c);
                });
        Articulos articulo = articuloRepo.findById(request.getArticuloId())
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no existe"));
        DetalleCarrito detalleCarrito = carrito.getDetalleList().stream()
                .filter(d -> d.getArticulos().equals(articulo))
                .findFirst()
                .orElseGet(() -> {
                    DetalleCarrito d = new DetalleCarrito();
                    d.setCarrito(carrito);
                    d.setArticulos(articulo);
                    carrito.getDetalleList().add(d);
                    return d;
                });
        detalleCarrito.setCantidad(detalleCarrito.getCantidad() + request.getCantidad());
        carritoRepo.save(carrito);
        return mapeaResponseDTO(carrito);
    }

    public CarritoResponseDTO verCarrito(Cliente cliente) {
        Carrito carrito = carritoRepo.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito vacío"));

        return mapeaResponseDTO(carrito);
    }

    @Transactional
    public void vaciarCarrito(Cliente cliente) {
        carritoRepo.deleteByCliente(cliente);
    }

    private CarritoResponseDTO mapeaResponseDTO(Carrito carrito) {
        CarritoResponseDTO response = new CarritoResponseDTO();
        response.setId(carrito.getIdCarrito());
        response.setFechaCreacion(carrito.getFechaCreacion());

        List<DetalleCarritoResponseDTO> items = carrito.getDetalleList().stream()
                .map(dc -> {
                    BigDecimal precioUnitario = BigDecimal.valueOf(dc.getArticulos().getPrecio());

                    DetalleCarritoResponseDTO dto = new DetalleCarritoResponseDTO();
                    dto.setId(dc.getIdDetalleCarrito());
                    dto.setArticuloId(dc.getArticulos().getIdArticulo());
                    dto.setNombreArticulo(dc.getArticulos().getNombre());
                    dto.setCantidad(dc.getCantidad());
                    dto.setPrecioUnitario(precioUnitario);
                    dto.setCantidad(dc.getCantidad());
                    BigDecimal totalLinea = precioUnitario.multiply(BigDecimal.valueOf(dc.getCantidad()));
                    dto.setPrecioUnitario(totalLinea);
                    return dto;
                })
                .collect(Collectors.toList());

        response.setItems(items);
        return response;
    }
}
