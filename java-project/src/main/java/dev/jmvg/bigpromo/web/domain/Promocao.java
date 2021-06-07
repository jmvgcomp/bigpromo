package dev.jmvg.bigpromo.web.domain;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Um título é requerido")
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank(message = "O link da promoção é requerido")
    @Column(name = "link_promocao",nullable = false)
    private String linkPromocao;

    @Column(name = "site_promocao")
    private String site;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "link_imagem", nullable = false, length = 1000)
    private String linkImagem;

    @NotNull(message = "O preço é requerido")
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    @Column(name = "preco_promocao",nullable = false)
    private BigDecimal preco;

    @Column(name = "total_likes")
    private int likes;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @NotNull(message = "Uma categoria é requerida")
    @ManyToOne
    @JoinColumn(name = "categoria_fk")
    private Categoria categoria;
}
