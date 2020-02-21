package com.unbxd.schemamanager.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoQueryException;
import com.unbxd.schemamanager.exceptions.*;
import com.unbxd.schemamanager.models.Field;
import com.unbxd.schemamanager.models.SiteSchema;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.List;

public class RedisDao implements SchemaDao {

    private Jedis jedis;

    public RedisDao(String host, int port){
        try {
            Jedis jedis = new Jedis(host, port);
            this.jedis = jedis;
        }
        catch (JedisConnectionException e){
            throw new TimeOutException(e);
        }
    }

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addNewSchema(SiteSchema schema)  {
        if (jedis.exists(schema.getSiteKey()))
                throw new DuplicateSiteKeyException();
        try {
            jedis.set(schema.getSiteKey(),mapper.writeValueAsString(schema));
        }
        catch (JsonProcessingException e){
            throw new SchemaWriteException(e);
        }
        catch (Exception e){
            throw new DaoException(e);
        }
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey)  {
        try {
            JsonNode jsonSchema = mapper.readTree(jedis.get(siteKey));
            SiteSchema schema = mapper.treeToValue(jsonSchema, SiteSchema.class);
            return schema;
        }
        catch (IOException e){
            throw new SiteNotFoundException(e);
        }
    }

    @Override
    public void updateSchemaForSite(String siteKey, SiteSchema schema)  {
        if (jedis.exists(siteKey)) {
            try {
                jedis.set(schema.getSiteKey(), mapper.writeValueAsString(schema));
            }
            catch (JsonProcessingException e){
                throw new SchemaWriteException(e);
            }
        }
        else
            throw new SiteNotFoundException();
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field)  {
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
        catch (JsonProcessingException e){
            throw new SchemaWriteException(e);
        }
    }

    @Override
    public void deleteSchemaForSite(String siteKey) {
        if (jedis.exists(siteKey)){
            jedis.del(siteKey);
        }
        else
            throw new SiteNotFoundException();
    }
}
