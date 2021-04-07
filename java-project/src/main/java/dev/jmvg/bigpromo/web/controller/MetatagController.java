package dev.jmvg.bigpromo.web.controller;

import dev.jmvg.bigpromo.web.domain.Metatag;
import dev.jmvg.bigpromo.web.service.MetatagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.ws.Response;

@Controller
@RequestMapping("/meta")
public class MetatagController {

    private final MetatagService service;

    public MetatagController(MetatagService service) {
        this.service = service;
    }

    @PostMapping("/info")
    public ResponseEntity<Metatag> getDataByUrl(@RequestParam("url") String url){
        Metatag metatag = service.getMetaTagByUrl(url);
        return metatag != null ? ResponseEntity.ok(metatag) : ResponseEntity.notFound().build();
    }

}
