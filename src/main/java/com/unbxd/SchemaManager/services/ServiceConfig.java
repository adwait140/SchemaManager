package com.unbxd.SchemaManager.services;

import com.google.inject.Guice;
//import com.unbxd.SchemaManager.controller.ControllerModule;
import com.unbxd.SchemaManager.dao.DaoModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    SchemaService getSchemaService() {
        return Guice.createInjector(new DaoModule())
                .getInstance(SchemaServiceImpl.class);
    }
}