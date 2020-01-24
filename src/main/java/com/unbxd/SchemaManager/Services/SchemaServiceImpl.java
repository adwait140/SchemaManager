package com.unbxd.SchemaManager.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unbxd.SchemaManager.Dao.Dao;
import com.unbxd.SchemaManager.Models.Field;
import com.unbxd.SchemaManager.Models.Response;
import com.unbxd.SchemaManager.Models.SiteSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SchemaServiceImpl implements SchemaService{


    private Dao dao;

    @Autowired
    public SchemaServiceImpl(@Qualifier("Mongo") Dao dao1,@Qualifier("Redis") Dao dao2,String daoType){
        if (daoType=="Redis")
            this.dao = dao2;
        else
            this.dao = dao1;
    }

    @Override
    public Response addNewSchema(SiteSchema schema){
        ObjectMapper mapper = new ObjectMapper();
        try {
            dao.addNewSchema(schema);
            return new Response(mapper.createObjectNode().put("Success","True"),200);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+schema.getSiteKey()+" exists"),400);
        }
    }

    @Override
    public Response getSchemaForSite(String siteKey) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            SiteSchema schema = dao.getSchemaForSite(siteKey);
            return new Response(mapper.readTree(mapper.writeValueAsString(schema)).deepCopy(),200);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+siteKey+" exists"),400);
        }
    }

    @Override
    public Response getFieldInSite(String SiteKey, String fieldName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Field fields[] = dao.getSchemaForSite(SiteKey).getFields();
            for (Field field : fields) {
                if (field.getFieldname() == fieldName)
                    return new Response(mapper.readTree(mapper.writeValueAsString(field)).deepCopy(),200);
            }
            return new Response(mapper.createObjectNode().put("message","No field "+fieldName+" exists"),400);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }

    @Override
    public Response updateFieldInSite(String SiteKey, String fieldName, Field field) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Field fields[] = dao.getSchemaForSite(SiteKey).getFields();
            for (Field f : fields) {
                if (f.getFieldname() == fieldName)
                    try {
                        dao.updateFieldInSite(SiteKey, fieldName, field);
                        return new Response(mapper.createObjectNode().put("Success","True"),200);
                    }
                    catch (Exception e){
                        return new Response(mapper.createObjectNode().put("message","Couldn't Update. (-_-) Something broke"),500);
                    }
            }
            return new Response(mapper.createObjectNode().put("message","No field "+fieldName+" exists"),400);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }

    @Override
    public Response updateSchemaForSite(String siteKey, SiteSchema schema) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dao.updateSchemaForSite(siteKey,schema);
            return new Response(mapper.createObjectNode().put("Success","true"),200);
        }
        catch(Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+siteKey+" exists"),400);
        }
    }

    @Override
    public Response deleteSchemaForSite(String SiteKey) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dao.deleteSchemaForSite(SiteKey);
            return new Response(mapper.createObjectNode().put("Success","true"),200);
        }
        catch(Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }
}
