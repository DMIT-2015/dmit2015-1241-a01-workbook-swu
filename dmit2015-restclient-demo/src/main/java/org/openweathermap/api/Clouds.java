
package org.openweathermap.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "all"
})
@Generated("jsonschema2pojo")
public class Clouds {

    @JsonbProperty("all")
    private Integer all;

    @JsonbProperty("all")
    public Integer getAll() {
        return all;
    }

    @JsonbProperty("all")
    public void setAll(Integer all) {
        this.all = all;
    }

}
