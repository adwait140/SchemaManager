package com.unbxd.SchemaManager.exceptions;

public class ControllerException extends Exception {

    private int status;

    public ControllerException(int status,String message){
        super(message);
        this.status = status;
    }

    public int getStatus(){
        return  status;
    }
}
