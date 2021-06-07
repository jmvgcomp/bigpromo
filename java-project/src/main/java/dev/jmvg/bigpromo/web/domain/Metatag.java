package dev.jmvg.bigpromo.web.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Metatag implements Serializable {
    private String site;
    private String title;
    private String url;
    private String image;

}
