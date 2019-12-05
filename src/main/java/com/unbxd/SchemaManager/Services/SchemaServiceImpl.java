package com.unbxd.SchemaManager.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unbxd.SchemaManager.Dao.databaseManager;
import com.unbxd.SchemaManager.Models.Field;
import com.unbxd.SchemaManager.Models.Response;
import com.unbxd.SchemaManager.Models.SiteSchema;

public class SchemaServiceImpl implements SchemaService{

    @Override
    public Response addNewSchema(SiteSchema schema){
        ObjectMapper mapper = new ObjectMapper();
        try {
            dgetSchemaForSite(siteKey);
            return new Response(mapper.createObjectNode().put("Success","True"),200);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }

    @Override
    public Response getSchemaForSite(String siteKey) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            SiteSchema schema = dgetSchemaForSite(siteKey);
            return new Response(mapper.readTree(mapper.writeValueAsString(schema)).deepCopy(),200);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }

    @Override
    public Response getFieldInSite(String SiteKey, String fieldName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Field fields[] = dgetSchemaForSite(SiteKey).getFields();
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
            Field fields[] = getSchemaForSite(SiteKey).getFields();
            for (Field field : fields) {
                if (field.getFieldname() == fieldName)
                    try {
                        dupdateFieldInSite(SiteKey, fieldName, field);
                        return new Response(mapper.createObjectNode().put("Success","True"),200)
                    }
                    catch (Exception e){
                        return new Response(mapper.createObjectNode().put("message","Couldn't Update. (-_-) Something broke"),500)
                    }
            }
            return new Response(mapper.createObjectNode().put("message","No field "+fieldName+" exists"),400);
        }
        catch (Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }

    @Override
    public Response updateSchemaForSite(String SiteKey, SiteSchema schema) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            dupdateSchemaForSite(SiteKey, schema);
            return new Response(mapper.createObjectNode().put("Success","true"),200);
        }
        catch(Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }

    @Override
    public Response deleteSchemaForSite(String SiteKey) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            ddeleteSchemaForSite(SiteKey);
            return new Response(mapper.createObjectNode().put("Success","true"),200);
        }
        catch(Exception e){
            return new Response(mapper.createObjectNode().put("message","No Site "+SiteKey+" exists"),400);
        }
    }
}
