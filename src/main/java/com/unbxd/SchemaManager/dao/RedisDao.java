package com.unbxd.SchemaManager.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unbxd.SchemaManager.exceptions.DaoException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

public class RedisDao implements SchemaDao {

    private Jedis jedis;

    public RedisDao(Jedis jedis){
        this.jedis = jedis;
    }

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addNewSchema(SiteSchema schema) throws DaoException {
        try {
            jedis.set(schema.getSiteKey(),mapper.writeValueAsString(schema));
        }
        catch (JsonProcessingException e){
            throw new DaoException(500,"Unable to add schema due to error e :"+ e.getMessage());
        }
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) throws DaoException {
        try {
            JsonNode jsonSchema = mapper.readTree(jedis.get(siteKey));
            SiteSchema schema = mapper.treeToValue(jsonSchema, SiteSchema.class);
            return schema;
        }
        catch (IOException e){
            throw new DaoException(404,"Site "+ siteKey +" not found");
        }
    }

    @Override
    public void updateSchemaForSite(String siteKey, SiteSchema schema) throws DaoException {
        if (jedis.exists(siteKey)) {
            try {
                jedis.set(schema.getSiteKey(), mapper.writeValueAsString(schema));
            }
            catch (JsonProcessingException e){
                throw new DaoException(500,"Unable to update schema due to error e : "+ e.getMessage());
            }
        }
        else
            throw new DaoException(404,"Site "+ siteKey +" not found");
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws DaoException {
        try {
            SiteSchema schema = getSchemaForSite(siteKey);
            List<Field> fields = schema.getFields();
            for (Field f : fields) {
                if (f.getFieldname() == fieldName) {
                    f.setFieldname(fieldName);
                    f.setAutoSuggest(field.isAutoSuggest());
                    f.setMultiValue(field.isMultiValue());
                    f.setDataType(field.getDataType());
                }
            }
            schema.setFields(fields);
            jedis.set(siteKey,mapper.writeValueAsString(schema));
        }
        catch (DaoException e){
            throw new DaoException(e.getStatus(),e.getMessage());
        }
        catch (JsonProcessingException e){
            throw new DaoException(500,"Unable to update Site due to error e : " + e.getMessage());
        }
    }

    @Override
    public void deleteSchemaForSite(String siteKey) throws DaoException{
        if (jedis.exists(siteKey)){
            jedis.del(siteKey);
        }
        else
            throw new DaoException(404,"Site "+ siteKey +" not found");
    }
}
