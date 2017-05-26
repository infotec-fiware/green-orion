package mx.infotec.dads.greenorion.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see AccountResource
 */

public class RestTemplateTest {

    public static void main(String[] args) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        // create request body
        JSONObject request = new JSONObject();
        // description
        request.put("description", "A subscription to get info about Station");
        // subject
        HashMap<String, Object> subject = new LinkedHashMap<>();
        HashMap<String, Object> entityElement = new LinkedHashMap<>();
        entityElement.put("id", "CDMX-AmbientObserved-3");
        entityElement.put("type", "AirQualityObserveds");
        ArrayList<HashMap<String, Object>> entities = new ArrayList<>();
        entities.add(entityElement);
        subject.put("entities", entities);
        HashMap<String, Object> condition = new LinkedHashMap<>();
        condition.put("attrs", new String[] { "O3" });
        subject.put("condition", condition);
        request.put("subject", subject);
        // notification
        HashMap<String, Object> notification = new LinkedHashMap<>();
        HashMap<String, Object> http = new LinkedHashMap<>();
        http.put("url", "http://172.17.0.1:8080/notifications");
        http.put("method", "POST");
        notification.put("http", http);
        notification.put("attrsFormat", "keyValues");
        notification.put("attrs", new String[] { "id", "type", "CO", "NO2", "O3" });
        request.put("notification", notification);
        // expires
        request.put("expires", "2020-04-05T14:00:00.00Z");
        // throttling
        request.put("throttling", 1);
        // System.out.println(request.toString());
        // // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
        //
        // send request and parse result
        ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:1026/v2/subscriptions",
                HttpMethod.POST, entity, String.class);
        System.out.println("Todo va bien");
        if (loginResponse.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("respuesta bien");
            List<String> hedersReponse = loginResponse.getHeaders().get("location");
            for (String string : hedersReponse) {
                System.out.println(string);
            }
        } else {
            System.out.println("algo salio mal");
            System.out.println(loginResponse.getStatusCodeValue());
            
        }

    }
}
