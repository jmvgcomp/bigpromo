package dev.jmvg.bigpromo.web.controller;

import dev.jmvg.bigpromo.web.domain.Categoria;
import dev.jmvg.bigpromo.web.domain.Promocao;
import dev.jmvg.bigpromo.web.repository.CategoriaRepository;
import dev.jmvg.bigpromo.web.repository.PromocaoRepository;
import dev.jmvg.bigpromo.web.service.PromocaoDataTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/")
public class PromocaoController {

    private static Logger log = LoggerFactory.getLogger(PromocaoController.class);

    private CategoriaRepository categoriaRepository;

    private PromocaoRepository promocaoRepository;

    public PromocaoController(CategoriaRepository categoriaRepository, PromocaoRepository promocaoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.promocaoRepository = promocaoRepository;
    }

    @GetMapping("/tabela")
    public String showTabela(){
        return "promo-datatables";
    }

    @GetMapping("/datatables/server")
    public ResponseEntity<?> dataTables(HttpServletRequest request){
        Map<String, Object> data = new PromocaoDataTableService().execute(promocaoRepository, request);
        return ResponseEntity.ok(data);

    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategoria(){
        return categoriaRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error: result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        log.info("Promocao {}", promocao.toString());
        promocao.setDataCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id){
        promocaoRepository.updateSomarLikes(id);
        int likes = promocaoRepository.findLikesById(id);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> excluirPromocao(@PathVariable("id") Long id){
        promocaoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/index")
    public String listarOfertas(ModelMap model){
        PageRequest pageRequest = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "dataCadastro"));
        PageRequest pageRequest2 = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "likes"));
        model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
        model.addAttribute("promocoes2", promocaoRepository.findAll(pageRequest2));
        return "index";
    }

    @GetMapping("/add")
    public String abrirCadastro(){
        return "add";
    }
}
