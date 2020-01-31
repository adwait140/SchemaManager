package com.unbxd.SchemaManager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    public String dataType;
    public boolean multiValue;
    public boolean autoSuggest;
    public String fieldname;
}
