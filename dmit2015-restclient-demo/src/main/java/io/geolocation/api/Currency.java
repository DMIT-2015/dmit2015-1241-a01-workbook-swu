
package io.geolocation.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "code",
    "name",
    "symbol"
})
@Generated("jsonschema2pojo")
public class Currency {

    @JsonbProperty("code")
    private String code;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("symbol")
    private String symbol;

    @JsonbProperty("code")
    public String getCode() {
        return code;
    }

    @JsonbProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonbProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonbProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
