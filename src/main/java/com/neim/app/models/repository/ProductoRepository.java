package com.neim.app.models.repository;

import com.neim.app.models.entity.Producto;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long> {
}
