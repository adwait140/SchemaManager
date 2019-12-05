package com.unbxd.SchemaManager.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unbxd.SchemaManager.Models.Field;
import com.unbxd.SchemaManager.Models.Response;
import com.unbxd.SchemaManager.Models.SiteSchema;
import com.unbxd.SchemaManager.Services.SchemaService;
import com.unbxd.SchemaManager.Services.SchemaServiceImpl;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/")
public class SchemaController {

    SchemaService serv = new SchemaServiceImpl();

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.POST)
    public ResponseEntity<ObjectNode> addNewSchema(@PathVariable(value="siteKey") String siteKey, @RequestBody SiteSchema schema  ) {
        Response res = serv.addNewSchema(schema);
        if(res.getStatus()==200){
            return ResponseEntity.ok().body(res.getResponse());
        }
        else if (res.getStatus()==500){
            return ResponseEntity.status(500).body(res.getResponse());
        }
        else{
            return  ResponseEntity.badRequest().body(res.getResponse());
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> getSchemaForSite(@PathVariable(value = "siteKey") String siteKey){
        ObjectMapper mapper = new ObjectMapper();
        Response res = serv.getSchemaForSite(siteKey);
        if(res.getStatus()==200){
            return ResponseEntity.ok().body(res.getResponse());
        }
        else if (res.getStatus()==500){
            return ResponseEntity.status(500).body(res.getResponse());
        }
        else{
            return  ResponseEntity.badRequest().body(res.getResponse());
        }
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> getFieldPropsinSite(@PathVariable(value = "siteKey") String siteKey,@PathVariable(value = "fieldName") String fieldName){
        Response res = serv.getFieldInSite(siteKey,fieldName);
        if(res.getStatus()==200){
            return ResponseEntity.ok().body(res.getResponse());
        }
        else if (res.getStatus()==500){
            return ResponseEntity.status(500).body(res.getResponse());
        }
        else{
            return  ResponseEntity.badRequest().body(res.getResponse());
        }
    }

    @RequestMapping(value ="/site/{siteKey}/field/{fieldName}",method = RequestMethod.PATCH)
    public ResponseEntity<ObjectNode> updateFieldPropsinSite(@PathVariable(value = "siteKey") String siteKey,@PathVariable(value = "fieldName") String fieldName, @RequestBody Field field){
        Response res = serv.updateFieldInSite(siteKey,fieldName,field);
        if(res.getStatus()==200){
            return ResponseEntity.ok().body(res.getResponse());
        }
        else if (res.getStatus()==500){
            return ResponseEntity.status(500).body(res.getResponse());
        }
        else{
            return  ResponseEntity.badRequest().body(res.getResponse());
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.PUT)
    public ResponseEntity<ObjectNode> updateSchemaForSite(@PathVariable(value="siteKey") String siteKey, @RequestBody SiteSchema schema  ) {
        Response res = serv.updateSchemaForSite(siteKey,schema);
        if(res.getStatus()==200){
            return ResponseEntity.ok().body(res.getResponse());
        }
        else{
            return  ResponseEntity.badRequest().body(res.getResponse());
        }
    }

    @RequestMapping(value = "/site/{siteKey}/schema", method = RequestMethod.DELETE)
    public ResponseEntity<ObjectNode> deleteNewSchema(@PathVariable(value="siteKey") String siteKey  ) {
        Response res = serv.deleteSchemaForSite(siteKey);
        if(res.getStatus()==200){
            return ResponseEntity.ok().body(res.getResponse());
        }
        else{
            return  ResponseEntity.badRequest().body(res.getResponse());
        }
    }
}
