package com.unbxd.SchemaManager.services;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.unbxd.SchemaManager.dao.Dao;
import com.unbxd.SchemaManager.dao.DaoProvider;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dao.class).toProvider(DaoProvider.class).in(Singleton.class); ;
    }
}
