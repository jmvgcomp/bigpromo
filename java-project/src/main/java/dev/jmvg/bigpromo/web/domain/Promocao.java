package dev.jmvg.bigpromo.web.domain;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "promocoes")
public class Promocao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "link_promocao",nullable = false)
    private String linkPromocao;

    @Column(name = "site_promocao")
    private String site;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "link_imagem", nullable = false, length = 1000)
    private String linkImagem;

    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    @Column(name = "preco_promocao",nullable = false)
    private BigDecimal preco;

    @Column(name = "total_likes")
    private int likes;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "categoria_fk")
    private Categoria categoria;
}
