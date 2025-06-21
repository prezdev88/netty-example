package cl.prezdev.config;

import org.springframework.context.annotation.Configuration;

import cl.prezdev.devtour.DevTourInspector;
import cl.prezdev.devtour.DevTourScan;
import jakarta.annotation.PostConstruct;

@Configuration
@DevTourScan(value = "cl.prezdev")
public class DevTourConfig {
    @PostConstruct
    public void init() {
        DevTourInspector.analyzeAndPrint();
    }
}
