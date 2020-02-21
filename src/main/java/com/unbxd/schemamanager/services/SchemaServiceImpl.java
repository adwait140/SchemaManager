package com.unbxd.schemamanager.services;

import com.unbxd.schemamanager.dao.SchemaDao;
import com.unbxd.schemamanager.exceptions.FieldNotFoundException;
import com.unbxd.schemamanager.models.Field;
import com.unbxd.schemamanager.models.SiteSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Provider;
import java.util.List;

@Component
@ContextConfiguration(classes = SchemaFactoryBeanConfig.class)
public class SchemaServiceImpl implements SchemaService{

    @Autowired
    private SchemaDao dao;

    @Override
    public void addNewSchema(SiteSchema schema) {
        dao.addNewSchema(schema);
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) {
        return dao.getSchemaForSite(siteKey);
    }

    @Override
    public Field getFieldInSite(String siteKey, String fieldName) {
            List<Field> fields = dao.getSchemaForSite(siteKey).getFields();
            Field retVal = fields.stream().filter(field -> fieldName.equals(field.getFieldname())).findFirst().orElse(null);
            if (retVal == null)
                throw new FieldNotFoundException();
            else
                return retVal;
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) {
        List<Field> fields = dao.getSchemaForSite(siteKey).getFields();
        for (Field f : fields) {
            if (f.getFieldname() == fieldName) {
                dao.updateFieldInSite(siteKey, fieldName, field);
                return;
            }
        }
        throw new FieldNotFoundException();
    }

    @Override
    public void updateSchemaForSite(String siteKey, SiteSchema schema) {
        dao.updateSchemaForSite(siteKey,schema);
    }

    @Override
    public void deleteSchemaForSite(String SiteKey) {
        dao.deleteSchemaForSite(SiteKey);
    }
}
