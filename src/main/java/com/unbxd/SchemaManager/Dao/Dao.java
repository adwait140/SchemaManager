package com.unbxd.SchemaManager.Dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unbxd.SchemaManager.Models.Field;
import com.unbxd.SchemaManager.Models.SiteSchema;

import java.io.IOException;

public interface Dao {

    public void addNewSchema(SiteSchema schema) throws JsonProcessingException;
    public SiteSchema getSchemaForSite(String siteKey) throws IOException;
    public void updateSchemaForSite(String siteKey,SiteSchema schema) throws IOException;
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws IOException;
    public void deleteSchemaForSite(String siteKey);


    }
