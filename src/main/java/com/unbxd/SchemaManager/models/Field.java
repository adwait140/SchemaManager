package com.unbxd.SchemaManager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    private String dataType;
    private boolean multiValue;
    private boolean autoSuggest;
    private String fieldname;
}
