package com.unbxd.SchemaManager.Dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unbxd.SchemaManager.Models.Field;
import com.unbxd.SchemaManager.Models.SiteSchema;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Component
@Qualifier("Redis")
public class RedisDao implements Dao {

    Jedis jedis = new Jedis("localhost");
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addNewSchema(SiteSchema schema) throws JsonProcessingException {
        jedis.set(schema.getSiteKey(),mapper.writeValueAsString(schema));
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) throws IOException {
        JsonNode jsonSchema = mapper.readTree(jedis.get(siteKey));
        SiteSchema schema = mapper.treeToValue(jsonSchema,SiteSchema.class);
        return schema;
    }

    @Override
    public void updateSchemaForSite(String siteKey, SiteSchema schema) throws JsonProcessingException {
        jedis.set(schema.getSiteKey(),mapper.writeValueAsString(schema));
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws IOException {
        SiteSchema schema = getSchemaForSite(siteKey);
        Field[] fields = schema.getFields();
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

    @Override
    public void deleteSchemaForSite(String siteKey) {
        jedis.del(siteKey);
    }
}
