
package com.codedrinker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by codedrinker on 2018/11/16.
 */

@Controller
@SpringBootApplication
@ComponentScan
@ImportResource({"classpath*:applicationContext.xml"})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String index() {
        return "index";
    }
}
