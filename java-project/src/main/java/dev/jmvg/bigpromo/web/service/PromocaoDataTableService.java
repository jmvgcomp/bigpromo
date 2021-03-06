package dev.jmvg.bigpromo.web.service;

import dev.jmvg.bigpromo.web.domain.Promocao;
import dev.jmvg.bigpromo.web.repository.PromocaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class PromocaoDataTableService {
    private String[] cols = {
            "id", "titulo", "linkPromocao",
            "descricao", "preco",
            "likes", "dataCadastro", "categoria"
    };



    public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request){
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int draw = Integer.parseInt(request.getParameter("draw"));

        int current = currentPage(start, length);
        
        String column = columnName(request);

        Sort.Direction direction = orderBy(request);
        String search = searchBy(request);
        
        Pageable pageable = PageRequest.of(current, length, direction, column);

        Page<Promocao> page = queryBy(search, repository, pageable);

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());

        return json;
    }

    private String searchBy(HttpServletRequest request) {
        return request.getParameter("search[value]").isEmpty() ? "" : request.getParameter("search[value]");
    }

    private Page<Promocao> queryBy(String search, PromocaoRepository repository, Pageable pageable) {
        if(search.isEmpty()){
            return repository.findAll(pageable);

        }
        return repository.findByTituloOrSiteOrCategoria(search, pageable);
    }

    private Sort.Direction orderBy(HttpServletRequest request) {
        String order = request.getParameter("order[0][dir]");
        Sort.Direction sort = Sort.Direction.ASC;
        if(order.equalsIgnoreCase("desc")){
            sort = Sort.Direction.DESC;
        }
        return sort;
    }

    private String columnName(HttpServletRequest request) {
        int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
        return cols[iCol];
    }

    private int currentPage(int start, int length) {
        return start / length;
    }
}
