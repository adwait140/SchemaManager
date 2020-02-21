package com.unbxd.schemamanager.exceptions;

public class DuplicateSiteKeyException extends DaoException {
    public DuplicateSiteKeyException(Exception e){
        super(e);
    }

    public DuplicateSiteKeyException(){
        super();
    }
}
