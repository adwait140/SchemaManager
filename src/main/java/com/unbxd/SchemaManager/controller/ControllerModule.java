package com.unbxd.SchemaManager.controller;

import com.google.inject.AbstractModule;
import com.unbxd.SchemaManager.services.SchemaService;
import com.unbxd.SchemaManager.services.SchemaServiceImpl;

public class ControllerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SchemaService.class).to(SchemaServiceImpl.class);
    }
}
