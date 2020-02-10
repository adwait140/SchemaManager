package com.unbxd.SchemaManager.controller;

import com.unbxd.SchemaManager.exceptions.SchemaServiceException;
import com.unbxd.SchemaManager.models.Field;
import com.unbxd.SchemaManager.models.Response;
import com.unbxd.SchemaManager.models.SiteSchema;
import com.unbxd.SchemaManager.services.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private SchemaService serv;


    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ResponseEntity checkAlive(){
        return ResponseEntity.status(200).body("I am alive");
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.POST)
    public ResponseEntity addNewSchema(@PathVariable("siteKey") String siteKey, @RequestBody SiteSchema schema  ) {
        try {
            serv.addNewSchema(schema);
            return ResponseEntity.status(200).body(new Response("success","Schema added successfully for site "+siteKey));
        }
        catch (SchemaServiceException e){
            return ResponseEntity.status(e.getStatus()).body(new Response("failure",e.getMessage()));
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.GET)
    public ResponseEntity getSchemaForSite(@PathVariable("siteKey") String siteKey)  {
        try {
            SiteSchema schema = serv.getSchemaForSite(siteKey);
            return ResponseEntity.status(200).body(schema);
        }
        catch (SchemaServiceException e){
            return  ResponseEntity.status(e.getStatus()).body(new Response("failure",e.getMessage()));
        }
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",method = RequestMethod.GET)
    public ResponseEntity getFieldPropsinSite(@PathVariable("siteKey") String siteKey,@PathVariable(value = "fieldName") String fieldName){
        try {
            Field field = serv.getFieldInSite(siteKey, fieldName);
            return ResponseEntity.status(200).body(field);
        }
        catch (SchemaServiceException e){
            return ResponseEntity.status(e.getStatus()).body(new Response("failure",e.getMessage()));
        }
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",method = RequestMethod.PATCH)
    public ResponseEntity updateFieldPropsinSite(@PathVariable("siteKey") String siteKey,@PathVariable("fieldName") String fieldName, @RequestBody Field field){
        try {
            serv.updateFieldInSite(siteKey, fieldName, field);
            return ResponseEntity.status(200).body(new Response("success","Successfully updated field properties"));
        }
        catch (SchemaServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response("failure",e.getMessage()));
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.PUT)
    public ResponseEntity updateSchemaForSite(@PathVariable("siteKey") String siteKey, @RequestBody SiteSchema schema  ) {
        try {
            serv.updateSchemaForSite(siteKey, schema);
            return ResponseEntity.status(200).body(new Response("success","Successfully updated schema"));
        }
        catch (SchemaServiceException e){
            return ResponseEntity.status(e.getStatus()).body(new Response("failure",e.getMessage()));
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.DELETE)
    public ResponseEntity deleteNewSchema(@PathVariable("siteKey") String siteKey  ) {
        try {
            serv.deleteSchemaForSite(siteKey);
            return ResponseEntity.status(200).body(new Response("success","Successfully deleted schema for site "+siteKey));
        }
        catch (SchemaServiceException e){
            return ResponseEntity.status(e.getStatus()).body(new Response("failure",e.getMessage()));
        }

    }
}
