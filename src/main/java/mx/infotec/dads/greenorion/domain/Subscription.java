package mx.infotec.dads.greenorion.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Subscription.
 */

@Document(collection = "subscription")
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("entity_id")
    private String entityId;

    @NotNull
    @Field("entity_type")
    private String entityType;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("attr_condition")
    private String attrCondition;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Field("orion_url")
    private String orionUrl;

    @NotNull
    @Field("returned_attr")
    private String returnedAttr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public Subscription entityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Subscription entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getDescription() {
        return description;
    }

    public Subscription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttrCondition() {
        return attrCondition;
    }

    public Subscription attrCondition(String attrCondition) {
        this.attrCondition = attrCondition;
        return this;
    }

    public void setAttrCondition(String attrCondition) {
        this.attrCondition = attrCondition;
    }

    public String getOrionUrl() {
        return orionUrl;
    }

    public Subscription orionUrl(String orionUrl) {
        this.orionUrl = orionUrl;
        return this;
    }

    public void setOrionUrl(String orionUrl) {
        this.orionUrl = orionUrl;
    }

    public String getReturnedAttr() {
        return returnedAttr;
    }

    public Subscription returnedAttr(String returnedAttr) {
        this.returnedAttr = returnedAttr;
        return this;
    }

    public void setReturnedAttr(String returnedAttr) {
        this.returnedAttr = returnedAttr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscription subscription = (Subscription) o;
        if (subscription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subscription{" +
            "id=" + id +
            ", entityId='" + entityId + "'" +
            ", entityType='" + entityType + "'" +
            ", description='" + description + "'" +
            ", attrCondition='" + attrCondition + "'" +
            ", orionUrl='" + orionUrl + "'" +
            ", returnedAttr='" + returnedAttr + "'" +
            '}';
    }
}
