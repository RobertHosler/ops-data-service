package me.roqb.opsdata.restservice;

import me.roqb.opsdata.restservice.entity.Root;
import me.roqb.opsdata.restservice.entity.TypeRecord;
import me.roqb.opsdata.restservice.settings.AirtableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AirtableService {

    private final RestTemplate restTemplate;
    private final AirtableSettings airTableSettings;

    private static final String AUTH_URL = "https://api.airtable.com/v0/appudq0aG1uwqIFX5/Officially%20Typed%20People?api_key={apiKey}";
    private static final String LIST_URL = "https://api.airtable.com/v0/appudq0aG1uwqIFX5/{table}?maxRecords={maxRecords}&view={view}";
    private static final String DEFAULT_API_KEY = "defaultKey"; // defaultKey won't work

    private static final String TABLE = "Officially Typed People";
    private static final String VIEW = "Gallery by MBTI Type";

    private static final String MINIMUM_FIELDS_PARAMS = "&fields=Name&fields=Type&fields=Tags";
    private static final String PICTURE_FIELDS_PARAMS = MINIMUM_FIELDS_PARAMS + "&fields=Picture";

    @Autowired
    public AirtableService(RestTemplate restTemplate, AirtableSettings airtableSettings) {
        this.restTemplate = restTemplate;
        this.airTableSettings = airtableSettings;
    }

    public Object getAirtable() {
        Object result = null;
        ResponseEntity<Object> response = restTemplate.exchange(LIST_URL, HttpMethod.GET, new HttpEntity(createHeaders()), Object.class, TABLE, 3, VIEW);
        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            result = response.getStatusCode();
        } else {
            result = response.getBody();
        }
        return result;
    }

    public Root getAirtableRecords(String maxRecords, String saviorOne, String saviorTwo, String animalStack, boolean includeCommunity) {
        Root result = null;
        String formula = "AND({s1Label}='{s1}',{s2Label}='{s2}',{animalStackLabel}='{animalStack}')";
        String url = LIST_URL + PICTURE_FIELDS_PARAMS + "&filterByFormula=" + formula;
        System.out.println(url);
        Map<String, String> variables = new HashMap<>();
        variables.put("table", TABLE);
        variables.put("view", VIEW);
        variables.put("maxRecords", maxRecords);
        variables.put("s1Label", "{First Savior Function}");
        variables.put("s1", saviorOne);
        variables.put("s2Label", "{Second Savior Function}");
        variables.put("s2", saviorTwo);
        variables.put("animalStackLabel", "{Animal Stack}");
        variables.put("animalStack", animalStack);
        ResponseEntity<Root> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity(createHeaders()),
                Root.class,
                variables);
        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            result = null;//TODO: handle failure
        } else {
            result = new Root();
            result.records = new ArrayList<>();
            for (TypeRecord record : response.getBody().records) {
                if (record.fields.tags == null || (!isSpeculation(record) && (!includeCommunity && isCommunity(record)))) {
                    // Only add records if there are no tags, or the tags are no
                    result.records.add(record);
                }
            }
        }
        return result;
    }

    private boolean isCommunity(TypeRecord record) {
        return record.fields.tags.contains("Community Member");
    }

    private boolean isSpeculation(TypeRecord record) {
        return record.fields.tags.contains("Speculation");
    }

    private HttpHeaders createHeaders() {
        return new HttpHeaders() {{
            String authHeader = "Bearer " + getApiKey();
            set( "Authorization", authHeader );
        }};
    }

    private String getApiKey() {
        String key = System.getenv("OP_DATABASE_KEY");
        if (key == null) {
            key = airTableSettings.getApiKey();
        }
        if (key == null) {
            key = DEFAULT_API_KEY;
        }
        return key;
    }
}
