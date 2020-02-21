package com.unbxd.schemamanager.exceptions;

public class TimeOutException extends DaoException {
    public TimeOutException(Exception e){
        super(e);
    }

    public TimeOutException(){
        super();
    }
}
