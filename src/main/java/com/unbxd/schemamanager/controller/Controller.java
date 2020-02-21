package com.unbxd.schemamanager.controller;

import com.unbxd.schemamanager.models.Field;
import com.unbxd.schemamanager.models.Response;
import com.unbxd.schemamanager.models.SiteSchema;
import com.unbxd.schemamanager.services.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private SchemaService serv;

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ResponseEntity checkAlive(){
        return ResponseEntity.status(200).body("I am alive");
    }

    @RequestMapping(value = "/site/{siteKey}/schema",
                    method = RequestMethod.POST)
    public ResponseEntity addNewSchema(@PathVariable("siteKey") String siteKey,
                                       @RequestBody SiteSchema schema  ) {
        serv.addNewSchema(schema);
        return ResponseEntity.status(200).body(new Response("success","Schema added successfully for site "+siteKey));
    }

    @RequestMapping(value = "/site/{siteKey}/schema",
                    method = RequestMethod.GET)
    public ResponseEntity getSchemaForSite(@PathVariable("siteKey") String siteKey)  {
        SiteSchema schema = serv.getSchemaForSite(siteKey);
        return ResponseEntity.status(200).body(schema);
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",
                    method = RequestMethod.GET)
    public ResponseEntity getFieldPropsinSite(@PathVariable("siteKey") String siteKey,
                                              @PathVariable(value = "fieldName") String fieldName){
        Field field = serv.getFieldInSite(siteKey, fieldName);
        return ResponseEntity.status(200).body(field);
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",
                    method = RequestMethod.PATCH)
    public ResponseEntity updateFieldPropsinSite(@PathVariable("siteKey") String siteKey,
                                                 @PathVariable("fieldName") String fieldName,
                                                 @RequestBody Field field){
        serv.updateFieldInSite(siteKey, fieldName, field);
        return ResponseEntity.status(200).body(new Response("success","Successfully updated field properties"));
       }

    @RequestMapping(value = "/site/{siteKey}/schema",
                    method = RequestMethod.PUT)
    public ResponseEntity updateSchemaForSite(
            @PathVariable("siteKey") String siteKey,
            @RequestBody SiteSchema schema  ) {
        serv.updateSchemaForSite(siteKey, schema);
        return ResponseEntity.status(200).body(new Response("success","Successfully updated schema"));
       }

    @RequestMapping(value = "/site/{siteKey}/schema",
                    method = RequestMethod.DELETE)
    public ResponseEntity deleteNewSchema(@PathVariable("siteKey") String siteKey ) {
        serv.deleteSchemaForSite(siteKey);
        return ResponseEntity.status(200).body(new Response("success","Successfully deleted schema for site "+siteKey));
    }
}
