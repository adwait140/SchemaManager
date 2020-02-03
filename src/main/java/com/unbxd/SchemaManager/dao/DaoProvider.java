package com.unbxd.SchemaManager.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import redis.clients.jedis.Jedis;
import com.unbxd.SchemaManager.models.SiteSchema;
import com.unbxd.SchemaManager.models.Field;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DaoProvider implements Provider<SchemaDao> {

    @Inject @Named("dbname")
    private String dBName;

    @Override
    public SchemaDao get(){
        if ("Redis".equals(dBName)){
            Jedis jedis = new Jedis("localhost");
            return new RedisDao(jedis);
        }
        else{
            MongoClient mongoClient = new MongoClient("localhost");
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().register(Field.class).register(SiteSchema.class).automatic(true).build()));
            MongoDatabase database = mongoClient.getDatabase("SchemaManager").withCodecRegistry(pojoCodecRegistry);
            return new MongoDao(mongoClient,database);
        }
    }
}
