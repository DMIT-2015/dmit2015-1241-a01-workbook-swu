
package org.openweathermap.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "type",
    "id",
    "country",
    "sunrise",
    "sunset"
})
@Generated("jsonschema2pojo")
public class Sys {

    @JsonbProperty("type")
    private Integer type;
    @JsonbProperty("id")
    private Integer id;
    @JsonbProperty("country")
    private String country;
    @JsonbProperty("sunrise")
    private Integer sunrise;
    @JsonbProperty("sunset")
    private Integer sunset;

    @JsonbProperty("type")
    public Integer getType() {
        return type;
    }

    @JsonbProperty("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonbProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonbProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonbProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonbProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonbProperty("sunrise")
    public Integer getSunrise() {
        return sunrise;
    }

    @JsonbProperty("sunrise")
    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    @JsonbProperty("sunset")
    public Integer getSunset() {
        return sunset;
    }

    @JsonbProperty("sunset")
    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

}
