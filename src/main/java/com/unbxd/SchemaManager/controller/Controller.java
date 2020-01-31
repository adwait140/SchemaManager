package com.unbxd.SchemaManager.controller;

import com.unbxd.SchemaManager.exceptions.ControllerException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.SiteSchema;
import com.unbxd.SchemaManager.services.SchemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@RequestMapping("/")
public class Controller {

    private SchemaService serv;

    @Inject
    public Controller(SchemaService ss){
        this.serv = ss;
    }


    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.POST)
    public ResponseEntity addNewSchema(@PathVariable("siteKey") String siteKey, @RequestBody SiteSchema schema  ) throws ControllerException {
        try {
            serv.addNewSchema(schema);
            return ResponseEntity.status(200).body("success");
        }
        catch (ControllerException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.GET)
    public ResponseEntity getSchemaForSite(@PathVariable("siteKey") String siteKey)  {
        try {
            SiteSchema schema = serv.getSchemaForSite(siteKey);
            return ResponseEntity.status(200).body(schema);
        }
        catch (ControllerException e){
            return  ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",method = RequestMethod.GET)
    public ResponseEntity getFieldPropsinSite(@PathVariable("siteKey") String siteKey,@PathVariable(value = "fieldName") String fieldName){
        try {
            Field field = serv.getFieldInSite(siteKey, fieldName);
            return ResponseEntity.status(200).body(field);
        }
        catch (ControllerException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",method = RequestMethod.PATCH)
    public ResponseEntity updateFieldPropsinSite(@PathVariable("siteKey") String siteKey,@PathVariable("fieldName") String fieldName, @RequestBody Field field){
        try {
            serv.updateFieldInSite(siteKey, fieldName, field);
            return ResponseEntity.status(200).body("Success");
        }
        catch (ControllerException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.PUT)
    public ResponseEntity updateSchemaForSite(@PathVariable("siteKey") String siteKey, @RequestBody SiteSchema schema  ) {
        try {
            serv.updateSchemaForSite(siteKey, schema);
            return ResponseEntity.status(200).body("success");
        }
        catch (ControllerException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.DELETE)
    public ResponseEntity deleteNewSchema(@PathVariable("siteKey") String siteKey  ) {
        try {
            serv.deleteSchemaForSite(siteKey);
            return ResponseEntity.status(200).body("success");
        }
        catch (ControllerException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }

    }
}
