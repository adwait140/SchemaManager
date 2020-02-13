package com.unbxd.SchemaManager.dao;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.unbxd.SchemaManager.exceptions.DaoException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Log4j2
public class MongoDao implements SchemaDao {

    private MongoDatabase database;

    public MongoDao(String host,int port,String dbname){
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://"+host+":"+port);
        log.info("Connecting to mongo with with using host: "+host+" and port: "+port);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register(Field.class).register(SiteSchema.class).automatic(true).build()));
        MongoClient mongoClient  = new MongoClient(mongoClientURI);
        this.database = mongoClient.getDatabase(dbname).withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public void addNewSchema(SiteSchema schema) throws DaoException{
        try {
            database.createCollection(schema.getSiteKey());
            MongoCollection<SiteSchema> collection = database.getCollection(schema.getSiteKey(), SiteSchema.class);
            collection.insertOne(schema);
        }
        catch (Exception e){
            throw new DaoException(500,"Site already present, "+e.getLocalizedMessage());
        }
    }

    @Override
    public SiteSchema getSchemaForSite(String siteKey) throws DaoException{
        try {
            MongoCollection<SiteSchema> collection = database.getCollection(siteKey,SiteSchema.class);
            SiteSchema res = collection.find().first();
            return res;
        }
        catch (Exception e){
            throw new DaoException(500,e.getLocalizedMessage());
        }
    }

    @Override
    public void updateSchemaForSite(String siteKey,SiteSchema schema) throws DaoException{
        try {
            deleteSchemaForSite(siteKey);
            database.createCollection(siteKey);
            MongoCollection<SiteSchema> collection = database.getCollection(siteKey,SiteSchema.class);
            collection.insertOne(schema);
        }
        catch (Exception e){
            throw new DaoException(500,e.getLocalizedMessage());
        }
    }

    @Override
    public void updateFieldInSite(String siteKey, String fieldName, Field field) throws DaoException {
        try {
            MongoCollection<SiteSchema> collection = database.getCollection(siteKey,SiteSchema.class);
            ArrayList<Bson> updates = new ArrayList<Bson>();
            updates.add(Updates.pull("fields", new Document("fieldname", fieldName)));
            updates.add(Updates.push("fields", field));
            collection.updateOne(Filters.eq("siteKey", siteKey), updates);
        }
        catch (Exception e) {
            throw new DaoException(500,e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteSchemaForSite(String siteKey) throws DaoException {
        try {
            MongoCollection<Document> collection = database.getCollection(siteKey);
            collection.drop();
        }
        catch (Exception e){
            throw new DaoException(500,e.getLocalizedMessage());
        }
    }
}
