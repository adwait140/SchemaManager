package com.unbxd.SchemaManager.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SchemaDao.class).toProvider(DaoProvider.class).in(Singleton.class);
        String dbname = System.getProperty("dbname");
        bind(String.class).annotatedWith(Names.named("dbname")).toInstance(dbname);
    }
}
