package com.unbxd.SchemaManager.exceptions;

public class DaoException extends Exception {

    private int status;

    public DaoException(int status,String msg){
        super(msg);
        this.status = status;
    }

    public int getStatus(){
        return  status;
    }
}