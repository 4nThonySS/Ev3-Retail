package cl.duocuc.EvaRetail.repository;

import cl.duocuc.EvaRetail.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
