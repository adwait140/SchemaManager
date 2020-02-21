package com.unbxd.schemamanager.exceptions;

public class SchemaQueryException extends DaoException {
    public SchemaQueryException(Exception e){
        super(e);
    }

    public SchemaQueryException(){
        super();
    }
}
