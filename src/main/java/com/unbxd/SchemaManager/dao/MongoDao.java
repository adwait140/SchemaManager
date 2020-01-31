package com.unbxd.SchemaManager.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
@Qualifier("Mongo")
public class MongoDao implements Dao {

    private MongoClient mongoClient;

    public MongoDao(MongoClient mc){
        this.mongoClient = mc;
    }

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoDatabase database = mongoClient.getDatabase("SchemaManager").withCodecRegistry(pojoCodecRegistry);

    @Override
    public void addNewSchema(SiteSchema schema){
        database.createCollection(schema.getSiteKey());
        MongoCollection collection= database.getCollection(schema.getSiteKey());
        collection.insertOne(schema);
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey){
        MongoCollection collection = database.getCollection(siteKey);
        SiteSchema res =(SiteSchema) collection.find().first();
        return res;
    }

    @Override
    public void updateSchemaForSite(String siteKey,SiteSchema schema){
        deleteSchemaForSite(siteKey);
        database.createCollection(siteKey);
        MongoCollection collection = database.getCollection(siteKey);
        collection.insertOne(schema);
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) {
        MongoCollection collection = database.getCollection(siteKey);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(Updates.pull("fields", new Document("fieldname", fieldName)));
        updates.add(Updates.push("fields",field));
        collection.updateOne(Filters.eq("siteKey", siteKey),updates);
    }

    @Override
    public void deleteSchemaForSite(String siteKey) {
        MongoCollection collection = database.getCollection(siteKey);
        collection.drop();
    }
}
