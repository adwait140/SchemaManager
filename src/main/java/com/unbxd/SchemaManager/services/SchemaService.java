package com.unbxd.SchemaManager.services;

import com.unbxd.SchemaManager.exceptions.SchemaServiceException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;

public interface SchemaService {

    public void addNewSchema(SiteSchema schema) throws SchemaServiceException;
    public SiteSchema getSchemaForSite(String siteKey) throws SchemaServiceException;
    public Field getFieldInSite(String SiteKey, String fieldName) throws SchemaServiceException;
    public void updateFieldInSite(String SiteKey,String fieldName, Field field) throws SchemaServiceException;
    public void updateSchemaForSite(String SiteKey, SiteSchema schema) throws SchemaServiceException;
    public void deleteSchemaForSite(String SiteKey) throws SchemaServiceException;
}
