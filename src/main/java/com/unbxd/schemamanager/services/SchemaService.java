package com.unbxd.schemamanager.services;

import com.unbxd.schemamanager.models.Field;
import com.unbxd.schemamanager.models.SiteSchema;

public interface SchemaService {

    public void addNewSchema(SiteSchema schema);
    public SiteSchema getSchemaForSite(String siteKey);
    public Field getFieldInSite(String SiteKey, String fieldName);
    public void updateFieldInSite(String SiteKey,String fieldName, Field field);
    public void updateSchemaForSite(String SiteKey, SiteSchema schema);
    public void deleteSchemaForSite(String SiteKey);
}
