package com.tfc.beerstar.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.response.CarritoResponseDTO;
import com.tfc.beerstar.dto.response.DetalleCarritoResponseDTO;
import com.tfc.beerstar.dto.response.DetallePedidoResponseDTO;
import com.tfc.beerstar.dto.response.PedidoResponseDTO;
import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.DetallePedido;
import com.tfc.beerstar.model.Pedido;
import com.tfc.beerstar.repository.ArticulosRepository;
import com.tfc.beerstar.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ArticulosRepository articulosRepo;
    private final CarritoService carritoService;

    public PedidoService(PedidoRepository pedidoRepo, ArticulosRepository articulosRepo, CarritoService carritoService) {
        this.pedidoRepo = pedidoRepo;
        this.articulosRepo = articulosRepo;
        this.carritoService = carritoService;
    }

    public PedidoResponseDTO crearPedido(Cliente cliente) {
        CarritoResponseDTO carrito = carritoService.verCarrito(cliente);
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado("PENDIENTE");
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleCarritoResponseDTO detalle : carrito.getItems()) {

        Articulos articulo = articulosRepo.getReferenceById(detalle.getIdArticulo());

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        detallePedido.setArticulo(articulo);
        detallePedido.setCantidad(detalle.getCantidad());
        detallePedido.setPrecioUnitario(detalle.getPrecioUnitario());

        pedido.getDetallePedido().add(detallePedido);

        total = total.add(detalle.getPrecioUnitario()
                         .multiply(BigDecimal.valueOf(detalle.getCantidad())));
    }

        pedido.setTotal(total.doubleValue());
        pedidoRepo.save(pedido);
        carritoService.vaciarCarrito(cliente);
        return mapearResponseDTO(pedido);

    }

    public List<PedidoResponseDTO> listarPorCliente(Cliente cliente) {
        List<Pedido> pedidos = pedidoRepo.findByCliente(cliente);
        return pedidos.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO mapearResponseDTO(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        BigDecimal total = BigDecimal.valueOf(pedido.getTotal());

        response.setId(pedido.getIdPedido());
        response.setClienteId(pedido.getCliente().getIdCliente());
        response.setFechaPedido(pedido.getFechaPedido());
        response.setEstado(pedido.getEstado());
        response.setTotal(total);

        List<DetallePedidoResponseDTO> detalles = pedido.getDetallePedido().stream()
                .map(dp -> {
                    DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();
                    dto.setId(dp.getIdDetallePedido());

                    Articulos art = dp.getArticulo();
                    dto.setArticuloId(art.getIdArticulo());
                    dto.setNombreArticulo(art.getNombre());

                    dto.setCantidad(dp.getCantidad());
                    dto.setPrecioUnitario(dp.getPrecioUnitario());

                    BigDecimal subtotal = dp.getPrecioUnitario()
                            .multiply(BigDecimal.valueOf(dp.getCantidad()));
                    dto.setTotalLinea(subtotal);

                    return dto;
                })
                .collect(Collectors.toList());

        response.setDetalles(detalles);
        return response;
    }

}
