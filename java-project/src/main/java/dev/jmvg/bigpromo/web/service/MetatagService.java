package dev.jmvg.bigpromo.web.service;

import dev.jmvg.bigpromo.web.domain.Metatag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MetatagService {

    private static final Logger log = LoggerFactory.getLogger(MetatagService.class);

    public Metatag getMetaTagByUrl(String url){
        Metatag twitter = getTwitterCardByUrl(url);
        if(!isEmptyMetatag(twitter)){
            return twitter;
        }
        Metatag openGraph = getOpenGraphByUrl(url);
        if(!isEmptyMetatag(openGraph)){
            return openGraph;
        }
        return null;
    }

    private Metatag getTwitterCardByUrl(String url){
        Metatag tag = new Metatag();
        try {
            Document doc = Jsoup.connect(url).get();
            tag.setTitle(doc.head().select("meta[name=twitter:title]").attr("content"));
            tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content"));
            tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
            tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
        return tag;
    }

    private Metatag getOpenGraphByUrl(String url){
        Metatag tag = new Metatag();
        try {
            Document doc = Jsoup.connect(url).get();
            tag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
            tag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
            tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
            tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
        return tag;
    }
    private boolean isEmptyMetatag(Metatag tag){
        if(tag.getImage().isEmpty()) return true;
        if(tag.getTitle().isEmpty()) return true;
        if(tag.getSite().isEmpty()) return true;
        if(tag.getUrl().isEmpty()) return true;
        return false;
    }
}
