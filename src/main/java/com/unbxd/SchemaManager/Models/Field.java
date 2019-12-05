package com.unbxd.SchemaManager.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    public String dataType;
    public boolean multiValue;
    public boolean autoSuggest;
    public String fieldname;
}
