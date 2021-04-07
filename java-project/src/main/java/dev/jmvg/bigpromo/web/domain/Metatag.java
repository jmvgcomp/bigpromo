package dev.jmvg.bigpromo.web.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Metatag implements Serializable {
    private String site;
    private String title;
    private String url;
    private String image;

}
