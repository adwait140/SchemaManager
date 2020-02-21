package com.unbxd.schemamanager.dao;

import com.unbxd.schemamanager.SchemaConstants;
import org.springframework.beans.factory.FactoryBean;

public class SchemaFactoryBean implements FactoryBean<SchemaDao> {

    String db = SchemaConstants.DATABASE;
    String mongohost = SchemaConstants.MONGO_HOST;
    int mongoport = SchemaConstants.MONGO_PORT;
    String redishost = SchemaConstants.REDIS_HOST;
    int redisport = SchemaConstants.REDIS_PORT;


    @Override
    public SchemaDao getObject() throws Exception {
        if ("Redis".equals(db))
            return new RedisDao(redishost,redisport);
        else
            return new MongoDao(mongohost,mongoport);
    }

    @Override
    public Class<?> getObjectType() {
        return SchemaDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
