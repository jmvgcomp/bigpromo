package dev.jmvg.bigpromo.web.repository;

import dev.jmvg.bigpromo.web.domain.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
}
