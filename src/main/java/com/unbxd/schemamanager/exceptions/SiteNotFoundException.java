package com.unbxd.schemamanager.exceptions;

public class SiteNotFoundException extends SchemaQueryException {
    public SiteNotFoundException(Exception e){
        super(e);
    }

    public SiteNotFoundException(){
        super();
    }
}
