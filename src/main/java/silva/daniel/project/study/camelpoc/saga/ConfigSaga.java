package silva.daniel.project.study.camelpoc.saga;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.InMemorySagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import silva.daniel.project.study.camelpoc.service.HelloService;

import java.util.List;
import java.util.Map;

@Component
public class ConfigSaga {

    public static final String DIRECT_HELLO = "direct:hello";
    public static final String DIRECT_EXEC = "direct:exec";
    public static final String DIRECT_HELLO_COMPESATION = "direct:helloCompesation";
    private HelloService helloService;

    @Autowired
    public ConfigSaga(HelloService helloService) {
        this.helloService = helloService;
    }

    public Map<String, RouteBuilder> configure() {

        return Map.of(
                DIRECT_HELLO, new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from(DIRECT_HELLO)
                                .log("Starting saga: Received ${body}")
                                .saga()
                                .to("direct:exec");
                    }
                },
                DIRECT_EXEC, new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from(DIRECT_EXEC)
                                .saga()
                                .log("Starting step 1")
                                .propagation(SagaPropagation.MANDATORY)
                                .setProperty("helloDTO", body())
                                .bean(helloService, "getHello")
                                .compensation("direct:helloCompesation")
                                .log("Terminou !");
                    }
                },
                DIRECT_HELLO_COMPESATION, new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from(DIRECT_HELLO_COMPESATION)
                                .log("Comecou a compensar")
                                .bean(helloService, "compensationHello")
                                .log("Terminou a compesancao");
                    }
                }
        );
    }
}
