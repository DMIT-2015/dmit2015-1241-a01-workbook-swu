package dmit2015.restclient;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "complete",
    "id",
    "name",
    "version"
})
@Generated("jsonschema2pojo")
public class TodoApiResponse {

    @JsonbProperty("complete")
    private Boolean complete;
    @JsonbProperty("id")
    private Integer id;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("version")
    private Integer version;

    @JsonbProperty("complete")
    public Boolean getComplete() {
        return complete;
    }

    @JsonbProperty("complete")
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @JsonbProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonbProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonbProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonbProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

}
