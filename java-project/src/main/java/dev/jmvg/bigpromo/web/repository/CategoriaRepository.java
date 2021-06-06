package dev.jmvg.bigpromo.web.repository;

import dev.jmvg.bigpromo.web.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
