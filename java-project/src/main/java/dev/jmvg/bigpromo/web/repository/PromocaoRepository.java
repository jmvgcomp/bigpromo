package dev.jmvg.bigpromo.web.repository;

import dev.jmvg.bigpromo.web.domain.Promocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {

    @Query("select count(p.id) as count, max(p.dataCadastro) as lastDate " +
            "from Promocao p where p.dataCadastro > :data")
    Map<String, Object> totalAndUltimaPromocaoByDataCadastro(@Param("data") LocalDateTime data);

    @Query("select p.dataCadastro from Promocao p")
    Page<LocalDateTime> findUltimaDataDePromocao(Pageable pageable);

    @Query("select p from Promocao p where p.titulo like %:search% or " +
            "p.site like %:search% or " +
            "p.categoria.nome like %:search%")
    Page<Promocao> findByTituloOrSiteOrCategoria(@Param("search") String search, Pageable pageable);

    @Transactional(readOnly = false)
    @Modifying
    @Query("update Promocao p set p.likes = p.likes + 1 where p.id = :id")
    void updateSomarLikes(@Param("id") Long id);

    @Query("select p.likes from Promocao p where p.id = :id")
    int findLikesById(@Param("id") Long id);
}
