
package org.openweathermap.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "speed",
    "deg",
    "gust"
})
@Generated("jsonschema2pojo")
public class Wind {

    @JsonbProperty("speed")
    private Double speed;
    @JsonbProperty("deg")
    private Integer deg;
    @JsonbProperty("gust")
    private Double gust;

    @JsonbProperty("speed")
    public Double getSpeed() {
        return speed;
    }

    @JsonbProperty("speed")
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @JsonbProperty("deg")
    public Integer getDeg() {
        return deg;
    }

    @JsonbProperty("deg")
    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    @JsonbProperty("gust")
    public Double getGust() {
        return gust;
    }

    @JsonbProperty("gust")
    public void setGust(Double gust) {
        this.gust = gust;
    }

}
