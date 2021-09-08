package com.example.restservice.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="airtable")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirtableSettings {
    private String endpoint;
    private String apiKey;
}
