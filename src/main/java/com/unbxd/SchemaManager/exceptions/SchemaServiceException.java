package com.unbxd.SchemaManager.exceptions;

public class SchemaServiceException extends Exception {

    private int status;

    public SchemaServiceException(int status, String message){
        super(message);
        this.status = status;
    }

    public int getStatus(){
        return  status;
    }
}
