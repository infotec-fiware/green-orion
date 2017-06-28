
package mx.infotec.dads.greenorion.integration.orion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "http",
    "httpCustom",
    "attrs",
    "exceptAttrs",
    "attrsFormat"
})
public class Notification {

    @JsonProperty("http")
    private Http http;
    @JsonProperty("httpCustom")
    private HttpCustom httpCustom;
    @JsonProperty("attrs")
    private List<String> attrs = new ArrayList<>();
    @JsonProperty("exceptAttrs")
    private List<String> exceptAttrs = new ArrayList<>();
    @JsonProperty("attrsFormat")
    private String attrsFormat;

    public Notification() {
	this.http = new Http();
	}
    @JsonProperty("http")
    public Http getHttp() {
        return http;
    }

    @JsonProperty("http")
    public void setHttp(Http http) {
        this.http = http;
    }

    @JsonProperty("httpCustom")
    public HttpCustom getHttpCustom() {
        return httpCustom;
    }

    @JsonProperty("httpCustom")
    public void setHttpCustom(HttpCustom httpCustom) {
        this.httpCustom = httpCustom;
    }

    @JsonProperty("attrs")
    public List<String> getAttrs() {
        return attrs;
    }

    @JsonProperty("attrs")
    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }

    @JsonProperty("exceptAttrs")
    public List<String> getExceptAttrs() {
        return exceptAttrs;
    }

    @JsonProperty("exceptAttrs")
    public void setExceptAttrs(List<String> exceptAttrs) {
        this.exceptAttrs = exceptAttrs;
    }

    @JsonProperty("attrsFormat")
    public String getAttrsFormat() {
        return attrsFormat;
    }

    @JsonProperty("attrsFormat")
    public void setAttrsFormat(String attrsFormat) {
        this.attrsFormat = attrsFormat;
    }
}
