package dev.jmvg.bigpromo.web.controller;

import dev.jmvg.bigpromo.web.domain.Categoria;
import dev.jmvg.bigpromo.web.domain.Promocao;
import dev.jmvg.bigpromo.web.repository.CategoriaRepository;
import dev.jmvg.bigpromo.web.repository.PromocaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {

    private static Logger log = LoggerFactory.getLogger(PromocaoController.class);

    private CategoriaRepository categoriaRepository;

    private PromocaoRepository promocaoRepository;

    public PromocaoController(CategoriaRepository categoriaRepository, PromocaoRepository promocaoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.promocaoRepository = promocaoRepository;
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategoria(){
        return categoriaRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Promocao> salvarPromocao(Promocao promocao){
        log.info("Promocao {}", promocao.toString());
        promocao.setDataCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/add")
    public String abrirCadastro(){
        return "add";
    }
}
