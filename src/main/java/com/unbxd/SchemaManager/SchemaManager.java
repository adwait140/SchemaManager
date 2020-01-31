package com.unbxd.SchemaManager;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SchemaManager extends SpringBootServletInitializer {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SchemaModule());
        injector.getInstance(SchemaManager.class);
        SpringApplication.run(SchemaManager.class);

    }
}
