package mx.infotec.dads.greenorion.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import mx.infotec.dads.greenorion.service.util.OrionNotificationMapper;

/**
 * A Notification.
 */

@Document(collection = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    public Notification() {

    }

    public Notification(OrionAlert orionAlert) {
        this.setOrionAlert(orionAlert);
        Data data = orionAlert.getData().get(0);
        this.orionId = orionAlert.getSubscriptionId();
        this.type = data.getType();
        ArrayList<Double> coordinates = OrionNotificationMapper.extractCoordinateProperty(data);
        this.latitud = Double.toString(coordinates.get(0));
        this.longitud = Double.toString(coordinates.get(1));
        this.o3 = OrionNotificationMapper.extractIntegerProperty(data, "O3");
        this.name = data.getId();
    }

    @Id
    private String id;

    @Field("orion_id")
    private String orionId;

    @Field("type")
    private String type;

    @Field("latitud")
    private String latitud;

    @Field("longitud")
    private String longitud;

    @NotNull
    @Min(value = 1)
    @Field("o_3")
    private Integer o3;

    @Field("name")
    private String name;

    private OrionAlert orionAlert;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrionId() {
        return orionId;
    }

    public Notification orionId(String orionId) {
        this.orionId = orionId;
        return this;
    }

    public void setOrionId(String orionId) {
        this.orionId = orionId;
    }

    public String getType() {
        return type;
    }

    public Notification type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitud() {
        return latitud;
    }

    public Notification latitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public Notification longitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Integer geto3() {
        return o3;
    }

    public Notification o3(Integer o3) {
        this.o3 = o3;
        return this;
    }

    public void seto3(Integer o3) {
        this.o3 = o3;
    }

    public String getName() {
        return name;
    }

    public Notification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrionAlert getOrionAlert() {
        return orionAlert;
    }

    public void setOrionAlert(OrionAlert orionAlert) {
        this.orionAlert = orionAlert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        if (notification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, notification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", orionId='" + orionId + "'" + ", type='" + type + "'" + ", latitud='"
                + latitud + "'" + ", longitud='" + longitud + "'" + ", o3='" + o3 + "'" + ", name='" + name + "'" + '}';
    }

}
