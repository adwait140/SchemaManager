package com.unbxd.schemamanager.services;

import com.unbxd.schemamanager.dao.SchemaDao;
import com.unbxd.schemamanager.dao.SchemaFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaFactoryBeanConfig {

    public SchemaFactoryBean getSchemaFactory() {
        return new SchemaFactoryBean();
    }

    @Bean
    public SchemaDao getSchemaDao() throws Exception {
        return getSchemaFactory().getObject();
    }
}