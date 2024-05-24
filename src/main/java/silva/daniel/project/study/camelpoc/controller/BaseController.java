package silva.daniel.project.study.camelpoc.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.study.camelpoc.dto.HelloDTO;

@RestController
@RequestMapping("/api")
public class BaseController {

    private final ProducerTemplate producer;

    public BaseController(ProducerTemplate producer) {
        this.producer = producer;
    }

    @GetMapping("/restart")
    public ResponseEntity<String> restart() {
        HelloDTO response = new HelloDTO();
        response.setCod("1234");
        CamelContext context = producer.getCamelContext();
        context.getRegistry().bind("dto", response);
        ProducerTemplate template = context.createProducerTemplate();
        template.send("direct:hello", exchange -> {
            exchange.getMessage().setBody(response);
        });
        return ResponseEntity.ok("Camel Executing...");
    }
}
