package com.unbxd.SchemaManager.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

@Data
public class Response {
    ObjectNode response;
    int status;

}
