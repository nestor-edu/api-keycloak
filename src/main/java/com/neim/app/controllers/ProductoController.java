package com.neim.app.controllers;

import com.neim.app.models.entity.Producto;
import com.neim.app.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }

    @GetMapping("/productos")
    public String productos(Principal principal, Model model) {
        model.addAttribute("productos", service.findAll());
        model.addAttribute("username", principal.getName());
        return "productos";
    }

    @GetMapping("/productos/pagina")
    public ResponseEntity<?> productos(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Producto> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }

    @PostMapping("/productos")
    @RolesAllowed("BO_ADMIN")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        Producto productoDB = service.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoDB);
    }

    @PutMapping("/productos/{id}")
    @RolesAllowed("BO_ADMIN")
    public ResponseEntity<?> editar(@RequestBody Producto producto, @PathVariable Long id) {
        Optional<Producto> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto productoDB = o.get();
        productoDB.setCodigo(producto.getCodigo());
        productoDB.setNombre(producto.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productoDB));
    }

    @DeleteMapping("/productos/{id}")
    @RolesAllowed("BO_ADMIN")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
