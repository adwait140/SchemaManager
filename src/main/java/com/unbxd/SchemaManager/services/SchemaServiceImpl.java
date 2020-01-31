package com.unbxd.SchemaManager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unbxd.SchemaManager.dao.Dao;
import com.unbxd.SchemaManager.exceptions.ControllerException;
import com.unbxd.SchemaManager.exceptions.DaoException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;

import javax.inject.Inject;

public class SchemaServiceImpl implements SchemaService{


    private Dao dao;

    @Inject
    public SchemaServiceImpl(Dao dao){
        this.dao = dao;
    }

    @Override
    public void addNewSchema(SiteSchema schema) throws ControllerException {
        ObjectMapper mapper = new ObjectMapper();
        try {
        dao.addNewSchema(schema);
        }
        catch (DaoException e){
            throw new ControllerException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) throws ControllerException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return dao.getSchemaForSite(siteKey);
        }
        catch (DaoException e){
            throw new ControllerException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public Field getFieldInSite(String siteKey, String fieldName) throws ControllerException {
        try {
            Field fields[] = dao.getSchemaForSite(siteKey).getFields();
            for (Field field : fields) {
                if (field.getFieldname() == fieldName)
                    return field;
            }
            throw new ControllerException(404,"Field "+ fieldName +" not found in site" + siteKey);
        }
        catch (DaoException e) {
            throw new ControllerException(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws ControllerException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Field fields[] = dao.getSchemaForSite(siteKey).getFields();
            for (Field f : fields) {
                if (f.getFieldname() == fieldName)
                    dao.updateFieldInSite(siteKey, fieldName, field);
            }
        }
        catch (DaoException e){
            throw new ControllerException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public void updateSchemaForSite(String siteKey, SiteSchema schema) throws ControllerException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dao.updateSchemaForSite(siteKey,schema);
        }
        catch(DaoException e){
            throw new ControllerException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public void deleteSchemaForSite(String SiteKey) throws ControllerException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dao.deleteSchemaForSite(SiteKey);
        }
        catch(DaoException e){
            throw new ControllerException(e.getStatus(),e.getMessage());
        }
    }
}
