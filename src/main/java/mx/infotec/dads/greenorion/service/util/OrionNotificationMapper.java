package mx.infotec.dads.greenorion.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mx.infotec.dads.greenorion.domain.Data;

/**
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class OrionNotificationMapper {

    private OrionNotificationMapper() {

    }

    public static String extractProperty(Data data, String propertyName) {
        if (data == null || propertyName == null) {
            return "DATA NOT PRESENTED";
        }
        Object property = data.getAdditionalProperties().get(propertyName);
        if (property == null) {
            return "PROPERTY NOT PRESENTED :" + propertyName;
        } else {
            return property.toString();
        }

    }

    public static HashMap<String, Object> extractMapProperty(Data data, String propertyName) {
        if (propertyName == null || data == null || data.getAdditionalProperties() == null
                || data.getAdditionalProperties().size() == 0) {
            return new HashMap<String, Object>();
        }
        Object property = data.getAdditionalProperties().get(propertyName);
        if (property == null) {
            return new HashMap<String, Object>();
        } else {
            return (HashMap<String, Object>) property;
        }
    }

    public static String extractFromMapProperty(HashMap<String, Object> data, String propertyName) {
        if (data == null) {
            return "DATA NOT PRESENTED";
        }
        Object property = data.get(propertyName);
        if (property == null) {
            return "PROPERTY NOT PRESENTED";
        } else {
            return property.toString();
        }

    }

    public static Integer extractIntegerProperty(Data data, String propertyName) {
        String property = extractProperty(data, propertyName);
        if (property == null) {
            return -1;
        } else {
            try {
                return Integer.valueOf(property);
            } catch (Exception e) {
                // Add a logger
            }
            return -1;
        }
    }

    public static ArrayList<Double> extractCoordinateProperty(Data data) {
        // (ArrayList<Double>)
        HashMap<String, Object> map = extractMapProperty(data, "location");
        if (map.isEmpty()) {
            return new ArrayList<>(Arrays.asList(-1d, -1d));
        } else {
            return (ArrayList<Double>) map.get("coordinates");
        }
    }
}
