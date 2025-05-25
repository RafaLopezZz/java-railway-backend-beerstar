package com.rlp.ecommerce.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlp.ecommerce.dto.response.DetallePedidoResponseDTO;
import com.rlp.ecommerce.dto.response.PedidoResponseDTO;
import com.rlp.ecommerce.exception.ResourceNotFoundException;
import com.rlp.ecommerce.model.Articulos;
import com.rlp.ecommerce.model.Carrito;
import com.rlp.ecommerce.model.Cliente;
import com.rlp.ecommerce.model.DetalleCarrito;
import com.rlp.ecommerce.model.DetallePedido;
import com.rlp.ecommerce.model.Pedido;
import com.rlp.ecommerce.repository.CarritoRepository;
import com.rlp.ecommerce.repository.ClienteRepository;
import com.rlp.ecommerce.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final CarritoRepository carritoRepository;
    private final CarritoService carritoService;

    public PedidoService(PedidoRepository pedidoRepository, CarritoRepository carritoRepository,
     CarritoService carritoService, ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.carritoRepository = carritoRepository;
        this.carritoService = carritoService;
    }

    @Transactional
    public PedidoResponseDTO crearPedido(Long idUsuario, String metodoPago) {
        Cliente cliente = clienteRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        // Obtener el carrito del cliente
        Carrito carrito = carritoRepository.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

        // Verificar que el carrito no esté vacío
        if (carrito.getDetalleList().isEmpty()) {
            throw new IllegalStateException("No se puede crear un pedido con un carrito vacío");
        }

        // Asegurar que los totales estén actualizados
        carrito.recalcularTotales();

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstadoPedido("PENDIENTE");
        pedido.setFechaPedido(Instant.now());
        pedido.setSubtotal(carrito.getSubtotal());
        pedido.setIva(carrito.getImpuestos());
        pedido.setGastosEnvio(carrito.getGastosEnvio());
        pedido.setTotal(carrito.getTotal());
        pedido.setMetodoPago(metodoPago);

        // Generar ID de transacción único (simulado)
        pedido.setIdTransaccion("TX-" + System.currentTimeMillis());

        // Crear detalles del pedido a partir del carrito
        for (DetalleCarrito detalleCarrito : carrito.getDetalleList()) {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setArticulo(detalleCarrito.getArticulos());
            detallePedido.setCantidad(detalleCarrito.getCantidad());
            detallePedido.setPrecioUnitario(detalleCarrito.getPrecioUnitario());
            detallePedido.setTotalLinea(detalleCarrito.getTotalLinea());
            pedido.getDetallePedido().add(detallePedido);
        }

        // Guardar el pedido
        pedidoRepository.save(pedido);

        // Vaciar el carrito
        carritoService.vaciarCarrito(idUsuario);

        return mapearResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarPorCliente(Long idUsuario) {
        Cliente cliente = clienteRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);
        return pedidos.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO mapearResponseDTO(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        //BigDecimal total = BigDecimal.valueOf(pedido.getTotal());

        response.setIdPedido(pedido.getIdPedido());
        response.setIdUsuario(pedido.getCliente().getUsuario().getIdUsuario());
        response.setIdCliente(pedido.getCliente().getIdCliente());
        response.setEmailUsuario(pedido.getCliente().getUsuario().getEmail());
        response.setNombreCliente(pedido.getCliente().getNombre());
        response.setDireccionCliente(pedido.getCliente().getDireccion());
        response.setFechaPedido(pedido.getFechaPedido());
        response.setEstadoPedido(pedido.getEstadoPedido());
        response.setSubTotal(pedido.getSubtotal());
        response.setIva(pedido.getIva());
        response.setGastosEnvio(pedido.getGastosEnvio());
        response.setTotal(pedido.getTotal());
        response.setMetodoPago(pedido.getMetodoPago());
        response.setIdTransaccion(pedido.getIdTransaccion());

        List<DetallePedidoResponseDTO> detalles = pedido.getDetallePedido().stream()
                .map(dp -> {
                    DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();
                    dto.setIdPedido(dp.getIdDetallePedido());

                    Articulos art = dp.getArticulo();
                    dto.setIdArticulo(art.getIdArticulo());
                    dto.setNombreArticulo(art.getNombre());
                    dto.setCantidad(dp.getCantidad());
                    dto.setPrecioUnitario(dp.getPrecioUnitario());
                    dto.setTotalLinea(dp.getTotalLinea());

                    return dto;
                })
                .collect(Collectors.toList());

        response.setDetalles(detalles);
        return response;
    }
}
