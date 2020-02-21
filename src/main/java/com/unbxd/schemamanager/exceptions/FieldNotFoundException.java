package com.unbxd.schemamanager.exceptions;

import com.unbxd.schemamanager.models.Field;

public class FieldNotFoundException extends SchemaQueryException {
    public FieldNotFoundException(Exception e){
        super(e);
    }

    public FieldNotFoundException(){
        super();
    }
}
