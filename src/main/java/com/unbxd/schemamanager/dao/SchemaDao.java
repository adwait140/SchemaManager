package com.unbxd.schemamanager.dao;

import com.unbxd.schemamanager.models.Field;
import com.unbxd.schemamanager.models.SiteSchema;

public interface SchemaDao {

    public void addNewSchema(SiteSchema schema) ;
    public SiteSchema getSchemaForSite(String siteKey) ;
    public void updateSchemaForSite(String siteKey,SiteSchema schema) ;
    public void updateFieldInSite(String siteKey, String fieldName, Field field) ;
    public void deleteSchemaForSite(String siteKey) ;


    }
