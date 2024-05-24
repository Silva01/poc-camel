package silva.daniel.project.study.camelpoc.service;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import silva.daniel.project.study.camelpoc.dto.HelloDTO;

@Component
public class HelloService {

    public HelloDTO getHello(Exchange exchange) {
        System.out.println("Opa, cheguei aqui");
        CamelContext context = exchange.getContext();
        HelloDTO dto = context.getRegistry().lookupByNameAndType("dto", HelloDTO.class);
        System.out.println(dto.getCod());
//        double d = 3/0;
        dto.setCod("1235");
        dto.setMessage("Olá Mundo, tudo com sucesso kkkkkk agora sim carai");
        return dto;
    }

    public HelloDTO compensationHello(Exchange exchange) {
        System.out.println("Começando a compensacao");
        CamelContext context = exchange.getContext();
        HelloDTO sharedDTO = context.getRegistry().lookupByNameAndType("dto", HelloDTO.class);
        if (sharedDTO != null) {
            sharedDTO.setCod("500");
            sharedDTO.setMessage("Opa, Compensacao executada por saga, sucesso");
            exchange.getIn().setBody(sharedDTO);
        }
        
        return sharedDTO;
    }
}
