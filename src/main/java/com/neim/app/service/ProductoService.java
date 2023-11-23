package com.neim.app.service;

import com.neim.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductoService {

    public Iterable<Producto> findAll();

    public Page<Producto> findAll(Pageable pageable);

    public Optional<Producto> findById(Long id);

    public Producto save(Producto producto);

    public void deleteById(Long id);
}
