package com.unbxd.SchemaManager.dao;

import com.unbxd.SchemaManager.exceptions.DaoException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;

public interface SchemaDao {

    public void addNewSchema(SiteSchema schema) throws DaoException;
    public SiteSchema getSchemaForSite(String siteKey) throws DaoException;
    public void updateSchemaForSite(String siteKey,SiteSchema schema) throws DaoException;
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws DaoException;
    public void deleteSchemaForSite(String siteKey) throws DaoException;


    }
