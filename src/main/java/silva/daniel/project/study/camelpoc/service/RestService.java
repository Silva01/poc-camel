package silva.daniel.project.study.camelpoc.service;

import org.springframework.stereotype.Component;

@Component
public class RestService {

    public void getMessage() {
        System.out.println("Opa, cheguei aqui no Rest 2");
//        double d = 3/0;
    }

    public void compensation() {
        System.out.println("Opa, cheguei aqui no Rest compensation");
    }
}
