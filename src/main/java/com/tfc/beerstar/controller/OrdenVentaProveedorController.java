package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.model.OrdenVentaProveedor;
import com.tfc.beerstar.repository.OrdenVentaProveedorRepository;

@RestController
@RequestMapping("/beerstar/proveedores/{idProveedor}/ordenes")
@CrossOrigin(origins = "*")
public class OrdenVentaProveedorController {

    @Autowired
    private OrdenVentaProveedorRepository ordenVentaProveedorRepository;

    @GetMapping
    public List<OrdenVentaProveedor> listarPorProveedor(@PathVariable Long idProveedor) {
        return ordenVentaProveedorRepository.findByProveedor_IdProveedor(idProveedor);
    }
}
