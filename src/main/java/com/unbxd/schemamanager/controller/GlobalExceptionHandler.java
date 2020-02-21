package com.unbxd.schemamanager.controller;

import com.unbxd.schemamanager.exceptions.*;
import com.unbxd.schemamanager.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicateSiteKeyException.class, FieldNotFoundException.class, SiteNotFoundException.class, SchemaWriteException.class,
            TimeOutException.class})
    protected final ResponseEntity handleException(Exception ex, WebRequest request){
        if (ex instanceof DuplicateSiteKeyException)
            return handeDuplicateSiteKeyException(ex.getMessage());
        else if (ex instanceof FieldNotFoundException)
            return handleFieldNotFoundException();
        else if (ex instanceof SiteNotFoundException)
            return handleSiteNotFoundException();
        else if (ex instanceof SchemaWriteException)
            return handleSchemaWriteException(ex.getMessage());
        else if (ex instanceof TimeOutException)
            return handleTimeOutException(ex.getMessage());
        else
            return handleUnkownException(ex);
    }

    protected ResponseEntity handeDuplicateSiteKeyException(String error){
        String msg = "Site already exists. Error :"+error;
        String status = "failure";
        return handleInternalServerError(status,msg);
    }

    protected ResponseEntity handleFieldNotFoundException(){
        String msg = "Field not found";
        String status = "failure";
        return handleInternalServerError(status,msg);
    }

    protected ResponseEntity handleSiteNotFoundException(){
        String msg = "Site not found";
        String status = "failure";
        return handleInternalServerError(status,msg);
    }

    protected ResponseEntity handleSchemaWriteException(String error){
        String msg = "Incorrect schema. Error :"+error;
        String status = "failure";
        return handleInternalServerError(status,msg);
    }

    protected ResponseEntity handleTimeOutException(String error){
        String msg = "Mongo timeout. Error :"+error;
        String status = "failure";
        return handleInternalServerError(status,msg);
    }

    protected ResponseEntity handleUnkownException(Exception e){
        String msg = "Unkown exception occured. Error :"+e.getMessage();
        String status = "failure";
        return handleInternalServerError(status,msg);
    }

    protected ResponseEntity handleInternalServerError(String status, String msg){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(status,msg));
    }


}
