package dev.jmvg.bigpromo;

import dev.jmvg.bigpromo.web.domain.Metatag;
import dev.jmvg.bigpromo.web.service.MetatagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BigpromoApplication{
    public static void main(String[] args) {
        SpringApplication.run(BigpromoApplication.class, args);
    }
}
