package com.unbxd.SchemaManager.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.unbxd.SchemaManager.util.PropertiesLoader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SchemaDao.class).toProvider(DaoProvider.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public Properties getProperties() throws IOException {
        Properties prop = new PropertiesLoader().getProps();
        prop.putAll(System.getProperties());
        return prop;
    }
}
