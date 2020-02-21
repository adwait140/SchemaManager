package com.unbxd.schemamanager.dao;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.unbxd.schemamanager.SchemaConstants;
import com.unbxd.schemamanager.exceptions.*;
import com.unbxd.schemamanager.models.Field;
import com.unbxd.schemamanager.models.SiteSchema;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDao implements SchemaDao {

    private MongoClient client;

    public MongoDao(String host,int port){
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://"+host+":"+port);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register(Field.class).register(SiteSchema.class).automatic(true).build()));
        ServerAddress address = new ServerAddress(host,port);
        try {
            this.client = new MongoClient(address,MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        }
        catch (MongoTimeoutException e){
            throw new TimeOutException(e);
        }
    }

    public MongoDatabase getDatabase(){
        try {
            String db = SchemaConstants.MONGO_DATABASE;
            return client.getDatabase(db);
        }
        catch (MongoCommandException e){
            throw new SchemaQueryException(e);
        }
    }

    @Override
    public void addNewSchema(SiteSchema schema) {
        MongoDatabase database = getDatabase();
        try {
            database.createCollection(schema.getSiteKey());
            MongoCollection<SiteSchema> collection = database.getCollection(schema.getSiteKey(), SiteSchema.class);
            collection.insertOne(schema);
        }
        catch (MongoCommandException e){
            throw new DuplicateSiteKeyException(e);
        }
        catch (MongoWriteException e) {
            throw new SchemaWriteException(e);
        }
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) {
        MongoDatabase database = getDatabase();
        try {
            MongoCollection<SiteSchema> collection = database.getCollection(siteKey,SiteSchema.class);
            SiteSchema res = collection.find().first();
            return res;
        }
        catch (MongoCommandException e){
            throw new SiteNotFoundException(e);
        }
        catch (MongoException e){
            throw new DaoException(e);
        }
    }

    @Override
    public void updateSchemaForSite(String siteKey,SiteSchema schema) {
        MongoDatabase database = getDatabase();
        try {
            deleteSchemaForSite(siteKey);
            database.createCollection(siteKey);
            MongoCollection<SiteSchema> collection = database.getCollection(siteKey,SiteSchema.class);
            collection.insertOne(schema);
        }
        catch (MongoCommandException e){
            throw new SiteNotFoundException(e);
        }
        catch (MongoWriteException e){
            throw new SchemaWriteException(e);
        }
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) {
        MongoDatabase database = getDatabase();
        try {
            MongoCollection<SiteSchema> collection = database.getCollection(siteKey,SiteSchema.class);
            List<Bson> updates = new ArrayList();
            updates.add(Updates.pull("fields", new Document("fieldname", fieldName)));
            updates.add(Updates.push("fields", field));
            collection.updateOne(Filters.eq("siteKey", siteKey), updates);
        }
        catch (MongoWriteException e) {
            throw new FieldNotFoundException(e);
        }
    }

    @Override
    public void deleteSchemaForSite(String siteKey) {
        MongoDatabase database = getDatabase();
        try {
            MongoCollection<Document> collection = database.getCollection(siteKey);
            collection.drop();
        }
        catch (MongoCommandException e) {
            throw new SiteNotFoundException(e);
        }
    }
}
