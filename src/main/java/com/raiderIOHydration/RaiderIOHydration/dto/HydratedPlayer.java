package com.raiderIOHydration.RaiderIOHydration.dto;

import java.util.List;
import java.util.Map;

public class HydratedPlayer {
    private String name;
    private String realm;
    private String region;
    private String characterClass;
    private String role;
    private Boolean isMainRole;

    private List<Map<String, Object>> recentRuns;

    private Double currentSeasonScore;
    private Double previousSeasonScore;

    // Constructors
    public HydratedPlayer() {
    }

    public HydratedPlayer(String name, String realm, String region, String characterClass, String role,
            List<Map<String, Object>> recentRuns,
            Double currentSeasonScore, Double previousSeasonScore, Boolean isMainRole) {
        this.name = name;
        this.realm = realm;
        this.region = region;
        this.characterClass = characterClass;
        this.role = role;
        this.recentRuns = recentRuns;
        this.currentSeasonScore = currentSeasonScore;
        this.previousSeasonScore = previousSeasonScore;
        this.isMainRole = isMainRole;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsMainRole() {
        return isMainRole;
    }

    public void setIsMainRole(Boolean isMainRole) {
        this.isMainRole = isMainRole;
    }

    public List<Map<String, Object>> getRecentRuns() {
        return recentRuns;
    }

    public void setRecentRuns(List<Map<String, Object>> recentRuns) {
        this.recentRuns = recentRuns;
    }

    public Double getCurrentSeasonScore() {
        return currentSeasonScore;
    }

    public void setCurrentSeasonScore(Double currentSeasonScore) {
        this.currentSeasonScore = currentSeasonScore;
    }

    public Double getPreviousSeasonScore() {
        return previousSeasonScore;
    }

    public void setPreviousSeasonScore(Double previousSeasonScore) {
        this.previousSeasonScore = previousSeasonScore;
    }
}