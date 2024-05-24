package silva.daniel.project.study.camelpoc.saga;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.InMemorySagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import silva.daniel.project.study.camelpoc.service.RestService;

//@Component
public class RestProducer{

//    @Autowired
//    private RestService resteService;
//
//
//    @Override
//    public void configure() throws Exception {
//        getContext().addService(new InMemorySagaService());
//
//        from("timer:first-timer")
//                .log("Starting saga: Received ${body}")
//                .saga()
//                .to("direct:exec");
//
//        from("direct:exec")
//                .saga()
//                    .log("Starting step 1")
//                    .propagation(SagaPropagation.MANDATORY)
//                    .bean(resteService, "getMessage")
//                    .log("Terminou !");
//    }
   
}
