package silva.daniel.project.study.camelpoc.config;

import org.apache.camel.CamelContext;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final CamelContext camelContext;

    public ApplicationStartupListener(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            camelContext.start();
        } catch (Exception e) {
            throw new RuntimeException("Failed to start Camel context", e);
        }

        while (!camelContext.getStatus().isStarted()) {
            try {
                Thread.sleep(100); // Wait for Camel to be fully started
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
