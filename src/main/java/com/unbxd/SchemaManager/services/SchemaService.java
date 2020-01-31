package com.unbxd.SchemaManager.services;

import com.unbxd.SchemaManager.exceptions.ControllerException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;

public interface SchemaService {

    public void addNewSchema(SiteSchema schema) throws ControllerException;
    public SiteSchema getSchemaForSite(String siteKey) throws ControllerException;
    public Field getFieldInSite(String SiteKey, String fieldName) throws ControllerException;
    public void updateFieldInSite(String SiteKey,String fieldName, Field field) throws ControllerException;
    public void updateSchemaForSite(String SiteKey, SiteSchema schema) throws ControllerException;
    public void deleteSchemaForSite(String SiteKey) throws ControllerException;
}
