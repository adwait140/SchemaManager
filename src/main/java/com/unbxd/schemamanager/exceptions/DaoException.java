package com.unbxd.schemamanager.exceptions;

public class DaoException extends RuntimeException {

    public DaoException(Exception e){
        super(e);
    }

    public DaoException(){
        super();
    }
}