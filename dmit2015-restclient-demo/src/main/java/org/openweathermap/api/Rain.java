
package org.openweathermap.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "1h"
})
@Generated("jsonschema2pojo")
public class Rain {

    @JsonbProperty("1h")
    private Double _1h;

    @JsonbProperty("1h")
    public Double get1h() {
        return _1h;
    }

    @JsonbProperty("1h")
    public void set1h(Double _1h) {
        this._1h = _1h;
    }

}
