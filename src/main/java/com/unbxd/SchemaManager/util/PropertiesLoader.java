package com.unbxd.SchemaManager.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class PropertiesLoader {

    private Properties prop;

    public PropertiesLoader(){
        Properties prop = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            if (inputStream != null)
                prop.load(inputStream);
            this.prop = prop;
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Properties getProps() throws IOException {
        return prop;
    }
}
