
package org.openweathermap.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "id",
    "main",
    "description",
    "icon"
})
@Generated("jsonschema2pojo")
public class Weather {

    @JsonbProperty("id")
    private Integer id;
    @JsonbProperty("main")
    private String main;
    @JsonbProperty("description")
    private String description;
    @JsonbProperty("icon")
    private String icon;

    @JsonbProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonbProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonbProperty("main")
    public String getMain() {
        return main;
    }

    @JsonbProperty("main")
    public void setMain(String main) {
        this.main = main;
    }

    @JsonbProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonbProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonbProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonbProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
