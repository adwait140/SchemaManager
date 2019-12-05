package com.unbxd.SchemaManager.Services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unbxd.SchemaManager.Models.Field;
import com.unbxd.SchemaManager.Models.Response;
import com.unbxd.SchemaManager.Models.SiteSchema;

public interface SchemaService {

    public Response addNewSchema(SiteSchema schema);
    public Response getSchemaForSite(String siteKey);
    public Response getFieldInSite(String SiteKey, String fieldName);
    public Response updateFieldInSite(String SiteKey,String fieldName, Field field);
    public Response updateSchemaForSite(String SiteKey, SiteSchema schema);
    public Response deleteSchemaForSite(String SiteKey);
}
