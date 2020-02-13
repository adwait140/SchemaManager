package com.unbxd.SchemaManager.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Properties;

public class DaoProvider implements Provider<SchemaDao> {

    @Inject
    private Properties props ;

    @Override
    public SchemaDao get(){
        String dBName = props.getProperty("dbname");
        System.out.println(dBName);
        props.list(System.out);
        if ("Redis".equals(dBName)){
            return new RedisDao(props.getProperty("redis.host"),Integer.parseInt(props.getProperty("redis.port")));
        }
        else{
            return new MongoDao(props.getProperty("mongo.host"),Integer.parseInt(props.getProperty("mongo.port")),props.getProperty("mongo.dbname"));
        }
    }
}
