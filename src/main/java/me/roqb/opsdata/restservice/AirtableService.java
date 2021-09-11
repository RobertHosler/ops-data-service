package me.roqb.opsdata.restservice;

import me.roqb.opsdata.restservice.entity.Root;
import me.roqb.opsdata.restservice.entity.TypeRecord;
import me.roqb.opsdata.restservice.settings.AirtableSettings;
import me.roqb.opsdata.restservice.settings.CommunityInclusions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AirtableService {

    private final RestTemplate restTemplate;
    private final AirtableSettings airTableSettings;
    private final CommunityInclusions communityInclusions;

    private static final String AUTH_URL = "https://api.airtable.com/v0/appudq0aG1uwqIFX5/Officially%20Typed%20People?api_key={apiKey}";
    private static final String LIST_URL = "https://api.airtable.com/v0/appudq0aG1uwqIFX5/{table}?maxRecords={maxRecords}&pageSize=100&view={view}";
    private static final String DEFAULT_API_KEY = "defaultKey"; // defaultKey won't work

    private static final String TABLE = "Officially Typed People";
    private static final String VIEW = "Gallery by MBTI Type";

    private static final String MINIMUM_FIELDS_PARAMS = "&fields=Name&fields=Type&fields=Tags";
    private static final String PICTURE_FIELDS_PARAMS = MINIMUM_FIELDS_PARAMS + "&fields=Picture";

    @Autowired
    public AirtableService(RestTemplate restTemplate, AirtableSettings airtableSettings, CommunityInclusions communityInclusions) {
        this.restTemplate = restTemplate;
        this.airTableSettings = airtableSettings;
        this.communityInclusions = communityInclusions;
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
        FormulaBuilder builder = new FormulaBuilder();
        builder.append("First Savior Function", saviorOne);
        builder.append("Second Savior Function", saviorTwo);
        builder.append("Animal Stack", animalStack);
        Map<String, String> variables = builder.getVariables();
        variables.put("table", TABLE);
        variables.put("view", VIEW);
        variables.put("maxRecords", maxRecords);
        String url = LIST_URL + PICTURE_FIELDS_PARAMS + "&filterByFormula=" + builder.buildFormula();
        Root result = getRoot(includeCommunity, variables, url);
        return result;
    }

    public Root getAirtableRecordsByName(String maxRecords, String name, boolean includeCommunity) {
        Map<String, String> variables = new HashMap<>();
        variables.put("table", TABLE);
        variables.put("view", VIEW);
        variables.put("maxRecords", maxRecords);
        variables.put("nameLabel", "{Name}");
        variables.put("nameValue", name != null ? name.toUpperCase() : "");
        String formula = "FIND('{nameValue}', UPPER({nameLabel}))";
        String url = LIST_URL + PICTURE_FIELDS_PARAMS + "&filterByFormula=" + formula;
        Root result = getRoot(includeCommunity, variables, url);
        return result;
    }

    private Root getRoot(boolean includeCommunity, Map<String, String> variables, String url) {
        return getRoot(includeCommunity, variables, url, null);
    }

    private Root getRoot(boolean includeCommunity, Map<String, String> variables, String url, String offset) {
        String offsetUrl = StringUtils.hasLength(offset) ? url + "&offset={offsetValue}" : url;
        variables.put("offsetValue", offset);
        System.out.println("offset: " + offset + " - " + variables.get("offsetValue"));
        ResponseEntity<Root> response = restTemplate.exchange(
                offsetUrl,
                HttpMethod.GET,
                new HttpEntity(createHeaders()),
                Root.class,
                variables);
        Root root = processResponse(includeCommunity, response);
        if (root.offset != null) {
            Root nextPage = getRoot(includeCommunity, variables, url, root.offset);
            nextPage.records.addAll(root.records);
            root = nextPage;
        }
        return root;
    }

    public Root getAirtableRecordsByType(String maxRecords, String type, boolean includeCommunity) {
        Map<String, String> variables = new HashMap<>();
        variables.put("table", TABLE);
        variables.put("view", VIEW);
        variables.put("maxRecords", maxRecords);
        variables.put("typeLabel", "{Type}");
        variables.put("typeValue", type != null ? type.toUpperCase() : "");
        String formula = "FIND('{typeValue}', UPPER({typeLabel}))";
        String url = LIST_URL + PICTURE_FIELDS_PARAMS + "&filterByFormula=" + formula;
        Root result = getRoot(includeCommunity, variables, url);
        return result;
    }

    private Root processResponse(boolean includeCommunity, ResponseEntity<Root> response) {
        Root result;
        if (!HttpStatus.OK.equals(response.getStatusCode()) || response.getBody() == null) {
            result = new Root();//TODO: handle failure
            System.out.println(response);
        } else {
            result = new Root();
            result.records = new ArrayList<>();
            for (TypeRecord record : response.getBody().records) {
                if (record.fields.tags == null || includeCommunity || !isCommunity(record)) {
                    /*
                     * Only add records if:
                     * 1) there are no tags
                     * 2) or if has tags, but no community tag when includeCommunity=false
                     */
                    result.records.add(record);
                }
            }
            result.offset = response.getBody().offset;
        }
        return result;
    }

    public Root getAirtableCoins(String maxRecords,
                                 TenCoins tenCoins,
                                 boolean includeCommunity) {
        FormulaBuilder builder = new FormulaBuilder();
        builder.append("Single Observer vs Decider", tenCoins.getPrimaryHumanNeed());
        builder.append("Decider Human Need", tenCoins.getDeciderHumanNeed());
        builder.append("Observer Human Need", tenCoins.getObserverHumanNeed());
        builder.append("Savior Decider", tenCoins.getDeciderLetter());
        builder.append("Savior Observer", tenCoins.getObserverLetter());
        builder.append("Blast vs Consume", tenCoins.getInfoAnimal());
        builder.append("Play vs Sleep", tenCoins.getEnergyAnimal());
        builder.append("Energy vs Info Dom", tenCoins.getAnimalDominance());
        builder.append("De Sexual", tenCoins.getDeModality());
        builder.append("Sensory Sexual", tenCoins.getSensoryModality());
        builder.append("Biological Sex", tenCoins.getSex());
        String url = LIST_URL + PICTURE_FIELDS_PARAMS + "&filterByFormula=" + builder.buildFormula();
        Map<String, String> variables = builder.getVariables();
        variables.put("table", TABLE);
        variables.put("view", VIEW);
        variables.put("maxRecords", maxRecords);
        System.out.println(url);
        System.out.println(variables);
        Root result = getRoot(includeCommunity, variables, url);
        return result;
    }

    private boolean isCommunity(TypeRecord record) {
        return record.fields.tags.contains("Community Member") && !isIncludedCommunity(record);
    }

    /*
     * Overrides the isCommunityMember exclusion and reincludes the member
     */
    private boolean isIncludedCommunity(TypeRecord record) {
        boolean isIncluded = this.communityInclusions.getFileContents().contains(record.fields.name);
        if (isIncluded) {
            record.fields.tags.add("Inclusions List");
        }
        return isIncluded;
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
