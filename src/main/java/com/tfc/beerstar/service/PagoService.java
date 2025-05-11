package com.tfc.beerstar.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfc.beerstar.dto.request.PagoRequestDTO;
import com.tfc.beerstar.dto.response.PagoResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Pago;
import com.tfc.beerstar.model.Pedido;
import com.tfc.beerstar.repository.PagoRepository;
import com.tfc.beerstar.repository.PedidoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepo;
    private final PedidoRepository pedidoRepo;

    public PagoService(PagoRepository pagoRepo, PedidoRepository pedidoRepo) {
        this.pagoRepo = pagoRepo;
        this.pedidoRepo = pedidoRepo;
    }

    @Transactional
    public PagoResponseDTO registrar(PagoRequestDTO request) {
        Pedido pedido = pedidoRepo.findById(request.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no existe"));

        BigDecimal totalPedido = BigDecimal.valueOf(pedido.getTotal());
        if (totalPedido.compareTo(request.getMonto()) != 0) {
            throw new IllegalArgumentException("El monto pagado no coincide con el total del pedido.");
        }
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setEstadoPago("COMPLETADO");
        pagoRepo.save(pago);
        pedido.setEstado("PAGADO");
        pedidoRepo.save(pedido); // Actualizas el estado del pedido
        return mapearResponseDto(pago);
    }

    private PagoResponseDTO mapearResponseDto(Pago pago) {
        PagoResponseDTO response = new PagoResponseDTO();
        response.setId(pago.getId());
        response.setIdPedido(pago.getPedido().getIdPedido());
        response.setMonto(pago.getMonto());
        response.setMetodoPago(pago.getMetodoPago());
        response.setEstadoPago(pago.getEstadoPago());
        return response;
    }
}
