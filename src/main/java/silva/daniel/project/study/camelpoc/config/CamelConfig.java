package silva.daniel.project.study.camelpoc.config;

import org.apache.camel.CamelContext;
import org.apache.camel.saga.InMemorySagaService;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import silva.daniel.project.study.camelpoc.saga.ConfigSaga;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class CamelConfig {

    private final ConfigSaga configSaga;
    public static final Set<String> LOAD_ROUTE = new HashSet<>();
    private static int FLAG = 0;

    public CamelConfig(ConfigSaga configSaga) {
        this.configSaga = configSaga;
    }

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                try {
                    camelContext.addService(new InMemorySagaService());
                    loadRoutes(camelContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                if (camelContext.getRoutes().size() != LOAD_ROUTE.size()) {
                    System.out.println("Camel Context Restarted with total routes : " + camelContext.getRoutes().size() + "/" + LOAD_ROUTE.size());

                    loadRoutes(camelContext);
                }
                System.out.println("Camel Context Started with total routes : " + camelContext.getRoutes().size());
            }
        };
    }

    private void loadRoutes(CamelContext camelContext) {
        configSaga.configure().forEach( (routes, builder) -> {
            try {
                if (FLAG > 0) {
                    if (!LOAD_ROUTE.contains(routes)) {
                        camelContext.addRoutes(builder);
                    }
                } else {
                    FLAG = 1;
                }
                LOAD_ROUTE.add(routes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
