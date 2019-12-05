package com.unbxd.SchemaManager.Dao;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.unbxd.SchemaManager.Models.SiteSchema;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDao implements databaseManager {

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase("SchemaManager").withCodecRegistry(pojoCodecRegistry);

    @Override
    public void addNewSchema(SiteSchema schema){
        database.createCollection(schema.getSiteKey());
        MongoCollection collection= database.getCollection(schema.getSiteKey());
        collection.insertOne(schema);
    }
}
