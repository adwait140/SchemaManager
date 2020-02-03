package com.unbxd.SchemaManager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.unbxd.SchemaManager.dao.SchemaDao;
import com.unbxd.SchemaManager.exceptions.SchemaServiceException;
import com.unbxd.SchemaManager.exceptions.DaoException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;

import java.util.List;


public class SchemaServiceImpl implements SchemaService{

    @Inject
    private SchemaDao dao;

//    @Inject
//    public SchemaServiceImpl(Dao dao){
//        this.dao = dao;
//    }

    @Override
    public void addNewSchema(SiteSchema schema) throws SchemaServiceException {
        ObjectMapper mapper = new ObjectMapper();
        try {
        dao.addNewSchema(schema);
        }
        catch (DaoException e){
            throw new SchemaServiceException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) throws SchemaServiceException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return dao.getSchemaForSite(siteKey);
        }
        catch (DaoException e){
            throw new SchemaServiceException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public Field getFieldInSite(String siteKey, String fieldName) throws SchemaServiceException {
        try {
            List<Field> fields = dao.getSchemaForSite(siteKey).getFields();
            Field retVal = fields.stream().filter(field -> fieldName.equals(field.getFieldname())).findFirst().orElse(null);
            if (retVal == null)
                throw new SchemaServiceException(404,"Field "+ fieldName +" not found in site " + siteKey);
            else
                return retVal;
        }
        catch (DaoException e) {
            throw new SchemaServiceException(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws SchemaServiceException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Field> fields = dao.getSchemaForSite(siteKey).getFields();
            for (Field f : fields) {
                if (f.getFieldname() == fieldName)
                    dao.updateFieldInSite(siteKey, fieldName, field);
            }
        }
        catch (DaoException e){
            throw new SchemaServiceException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public void updateSchemaForSite(String siteKey, SiteSchema schema) throws SchemaServiceException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dao.updateSchemaForSite(siteKey,schema);
        }
        catch(DaoException e){
            throw new SchemaServiceException(e.getStatus(),e.getMessage());
        }
    }

    @Override
    public void deleteSchemaForSite(String SiteKey) throws SchemaServiceException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dao.deleteSchemaForSite(SiteKey);
        }
        catch(DaoException e){
            throw new SchemaServiceException(e.getStatus(),e.getMessage());
        }
    }
}
