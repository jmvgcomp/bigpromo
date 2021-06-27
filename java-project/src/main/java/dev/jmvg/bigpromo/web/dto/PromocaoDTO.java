package dev.jmvg.bigpromo.web.dto;

import dev.jmvg.bigpromo.web.domain.Categoria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PromocaoDTO {

    @NotNull
    private Long id;

    @NotBlank(message = "Um título é requerido")
    private String titulo;

    private String descricao;

    @NotBlank(message = "Uma imagem é requerida")
    private String linkImagem;

    @NotNull(message = "O preço é requerido")
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    private BigDecimal preco;

    @NotNull(message = "Uma categoria é requerida")
    private Categoria categoria;
}
