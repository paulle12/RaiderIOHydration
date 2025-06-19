package com.raiderIOHydration.RaiderIOHydration.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.raiderIOHydration.RaiderIOHydration.dto.HydratedPlayer;

import java.util.List;
import java.util.Map;

@Service
public class RaiderIOService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getTopRole(Map<String, Object> scores) {
        String[] roles = { "dps", "healer", "tank" };
        String topRole = null;
        double maxScore = Double.NEGATIVE_INFINITY;

        for (String role : roles) {
            Object val = scores.get(role);
            if (val instanceof Number) {
                double score = ((Number) val).doubleValue();
                if (score > maxScore) {
                    maxScore = score;
                    topRole = role;
                }
            }
        }

        return topRole;
    }

    public HydratedPlayer getRecentRuns(String region, String realm, String name) {
        String baseUrl = "https://raider.io/api/v1/characters/profile";
        String fields = "mythic_plus_recent_runs,mythic_plus_scores_by_season:current:previous";
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("region", region)
                .queryParam("realm", realm)
                .queryParam("name", name)
                .queryParam("fields", fields)
                .build()
                .toUriString();

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null) {
            return null;
        }
        // Extract relevant fields
        String characterClass = (String) responseBody.get("class");
        String role = (String) responseBody.get("active_spec_role");
        // TODO: probably need to see how to remove suppress warning
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> recentRuns = (List<Map<String, Object>>) responseBody.get("mythic_plus_recent_runs");

        // Parse seasonal scores
        // TODO: probably need to see how to remove suppress warning
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> seasonalScores = (List<Map<String, Object>>) responseBody
                .get("mythic_plus_scores_by_season");
        Double current = null;
        Double previous = null;
        Boolean isMainRole = true;
        if (seasonalScores != null) {
            for (Map<String, Object> seasonData : seasonalScores) {
                String season = (String) seasonData.get("season");
                // TODO: probably need to see how to remove suppress warning
                @SuppressWarnings("unchecked")
                Map<String, Object> scores = (Map<String, Object>) seasonData.get("scores");
                Double score = scores != null ? ((Number) scores.get("all")).doubleValue() : null;
                if ("season-tww-2".equals(season)) {
                    String topRole = getTopRole(scores);
                    switch (topRole) {
                        case "dps":
                            isMainRole = role.equals("DPS");
                            break;
                        case "healer":
                            isMainRole = role.equals("HEALING");

                            break;
                        case "tank":
                            isMainRole = role.equals("TANK");
                            break;
                        default:
                            System.out.println("Unknown role");
                    }
                    current = score;
                } else {
                    previous = score;
                }
            }
        }

        return new HydratedPlayer(
                name,
                realm,
                region,
                characterClass,
                role,
                recentRuns,
                current,
                previous,
                isMainRole);
    }

}
// After RaiderIO hydration we need to think about our own hydration service
// should or will i need to create a separate service for our own personal
// hydration
// what are some benefits from decoupling this
// some pros would be if we needed to reuse stuff we could and allows for more
// scalable things in the future, but with microservice architect would enable
// us to further think about how to batch hydration processes
// Testing and observability become more focused—e.g., failures in hydration
// don’t necessarily imply Service A is broken.
// this service can focus strictly on collecting raw data, while the hydration
// service can be responsible for enrichment (e.g. external API calls,
// calculations). allows for easier maintainability
// cons could over complicate things as now separate services are needed
