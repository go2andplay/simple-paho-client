package com.trvajjala;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        LOGGER.info("Running Spring boot application with profile(external server) :  {}", System.getProperty("spring.profiles.active"));
        return builder.sources(Application.class);
    }

    public static void main(String args[]) {
        final SpringApplication application = new SpringApplication(Application.class);
        application.run();
    }
}
