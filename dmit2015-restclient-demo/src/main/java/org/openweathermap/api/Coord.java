
package org.openweathermap.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "lon",
    "lat"
})
@Generated("jsonschema2pojo")
public class Coord {

    @JsonbProperty("lon")
    private Double lon;
    @JsonbProperty("lat")
    private Double lat;

    @JsonbProperty("lon")
    public Double getLon() {
        return lon;
    }

    @JsonbProperty("lon")
    public void setLon(Double lon) {
        this.lon = lon;
    }

    @JsonbProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonbProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

}
