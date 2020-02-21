package com.unbxd.schemamanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class SchemaConstants {

    public static final Properties props = loadConfig();

    public static final String MONGO_DATABASE = props.getProperty("mongo.dbname");

    public static final int MONGO_PORT = Integer.parseInt(props.getProperty("mongo.port"));

    public static final String MONGO_HOST = props.getProperty("mongo.host");

    public static final int REDIS_PORT = Integer.parseInt(props.getProperty("redis.port"));

    public static final String REDIS_HOST = props.getProperty("redis.host");

    public static final String DATABASE = props.getProperty("dbname");

    private static Properties loadConfig(){
        Properties prop = new Properties();
        try {
            InputStream inputStream = SchemaConstants.class.getClassLoader().getResourceAsStream("application.properties");
            if (inputStream != null)
                prop.load(inputStream);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        prop.putAll(System.getProperties());
        return prop ;
    }
}
