package com.unbxd.SchemaManager.Models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

@Getter
public class Response {
    ObjectNode response;
    int status;

    public Response(ObjectNode node,int status){
        this.response=node;
        this.status=status;
    }
}
