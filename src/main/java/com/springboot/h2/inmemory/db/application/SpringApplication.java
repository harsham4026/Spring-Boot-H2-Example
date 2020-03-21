package com.springboot.h2.inmemory.db.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Harsha Mandadi
 */
@SpringBootApplication
public class SpringApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringApplication.class);

    public static void main(String[] args) {
        LOG.debug("main method started");
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        LOG.debug("main method ended");
    }
}
