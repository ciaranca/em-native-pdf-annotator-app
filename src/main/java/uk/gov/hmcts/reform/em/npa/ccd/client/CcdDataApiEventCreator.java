package uk.gov.hmcts.reform.em.npa.ccd.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.em.npa.ccd.dto.CcdCallbackDto;
import uk.gov.hmcts.reform.em.npa.ccd.dto.CcdCallbackDtoCreator;
import uk.gov.hmcts.reform.em.npa.ccd.exception.CallbackException;

import java.io.IOException;

@Service
public class CcdDataApiEventCreator {

    private final OkHttpClient http;
    private final AuthTokenGenerator authTokenGenerator;
    private final String ccdDataBaseUrl;
    private final String ccdTriggerPath = "/cases/%s/event-triggers/%s";
    private final CcdCallbackDtoCreator ccdCallbackDtoCreator;

    public CcdDataApiEventCreator(OkHttpClient http, AuthTokenGenerator authTokenGenerator,
                                  @Value("${ccd.data.api.url}") String ccdDataBaseUrl,
                                  CcdCallbackDtoCreator ccdCallbackDtoCreator) {
        this.http = http;
        this.authTokenGenerator = authTokenGenerator;
        this.ccdDataBaseUrl = ccdDataBaseUrl;
        this.ccdCallbackDtoCreator = ccdCallbackDtoCreator;
    }

    /**
     * Call to https://hmcts.github.io/reform-api-docs/swagger.html?url=https://hmcts.github.io/reform-api-docs/specs/ccd-data-store-api.v2.json#/start-trigger-controller/getStartEventTriggerUsingGET.
     * @param caseId - case id
     * @param triggerId - trigger id
     * @param jwt - authentication
     * @return - DTO with CCD case
     */
    public CcdCallbackDto executeTrigger(String caseId, String triggerId, String jwt) {
        final Request request = new Request.Builder()
                .addHeader("Authorization", jwt)
                .addHeader("experimental", "true")
                .addHeader("ServiceAuthorization", authTokenGenerator.generate())
                .url(String.format(ccdDataBaseUrl + ccdTriggerPath,
                        caseId,
                        triggerId))
                .get()
                .build();

        try {
            final Response response = http.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new CallbackException(response.code(), response.body().string(), "Creation of event-trigger failed");
            }

            return ccdCallbackDtoCreator.createDto(
                    "caseBundles",
                    jwt,
                    response.body().charStream());
        } catch (IOException e) {
            throw new CallbackException(500, null, String.format("IOException: %s", e.getMessage()));
        }

    }

}
