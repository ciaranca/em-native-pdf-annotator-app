package uk.gov.hmcts.reform.em.npa.ccd.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class CcdCallbackDto {

    private Optional<String> propertyName = Optional.empty();

    private JsonNode ccdPayload;

    private JsonNode caseData;

    private JsonNode caseDetails;

    private String jwt;

    public JsonNode getCaseData() {
        return caseData;
    }

    public JsonNode getCaseDetails() {
        return caseDetails;
    }

    public String getJwt() {
        return jwt;
    }

    public void setCaseData(JsonNode caseData) {
        this.caseData = caseData;
    }

    public void setCaseDetails(JsonNode caseDetails) {
        this.caseDetails = caseDetails;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Optional<String> getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(Optional<String> propertyName) {
        this.propertyName = propertyName;
    }

    public JsonNode getCcdPayload() {
        return ccdPayload;
    }

    public void setCcdPayload(JsonNode ccdPayload) {
        this.ccdPayload = ccdPayload;
    }

    public String getCaseId() {
        return ccdPayload != null && ccdPayload.findValue("id") != null
                ? ccdPayload.findValue("id").asText() : null;
    }

    public String getJurisdiction() {
        return ccdPayload != null && ccdPayload.findValue("jurisdiction") != null
                ? ccdPayload.findValue("jurisdiction").asText() : null;
    }

    public String getCaseTypeId() {
        return ccdPayload != null && ccdPayload.findValue("case_type_id") != null
                ? ccdPayload.findValue("case_type_id").asText() : null;
    }

    public String getEventToken() {
        return ccdPayload != null && ccdPayload.findValue("token") != null
                ? ccdPayload.findValue("token").asText() : null;
    }

    public String getEventId() {
        return ccdPayload != null && ccdPayload.findValue("event_id") != null
                ? ccdPayload.findValue("event_id").asText() : null;
    }
}
