package com.unbxd.SchemaManager.dao;

import com.google.inject.Provider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import redis.clients.jedis.Jedis;

public class DaoProvider implements Provider<Dao> {

    public Dao get(){
        String dBName = System.getProperty("DBName");
        if (dBName == "Redis"){
            Jedis jedis = new Jedis("localhost");
            RedisDao dao = new RedisDao(jedis);
            return dao;
        }
        else{
            MongoClient mongoClient = MongoClients.create();
            MongoDao dao = new MongoDao(mongoClient);
            return dao;
        }
    }
}
