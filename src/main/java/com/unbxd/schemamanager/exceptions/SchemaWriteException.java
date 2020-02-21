package com.unbxd.schemamanager.exceptions;

public class SchemaWriteException extends DaoException {
    public SchemaWriteException(Exception e){
        super(e);
    }

    public SchemaWriteException(){
        super();
    }
}
