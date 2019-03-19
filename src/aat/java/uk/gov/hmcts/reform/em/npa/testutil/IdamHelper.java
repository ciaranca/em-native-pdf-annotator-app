package uk.gov.hmcts.reform.em.npa.testutil;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.idam.client.IdamClient;

@Service
public class IdamHelper {

    private static final String USERNAME = "qwertyuiop@qwertyuiop.net";
    private static final String PASSWORD = "4590fgvhbfgbDdffm3lk4j";

    @Autowired
    private IdamClient client;

    @Value("${idam.api.url}")
    private String idamUrl;

    @Value("${idam.client.secret}")
    private String secret;

    @Value("${idam.client.redirect_uri}")
    private String redirect;

    public String getIdamToken() {
        System.out.println("JJJ - printing idamUrl");
        System.out.println(idamUrl);
        System.out.println("JJJ - printing client_secret");
        System.out.println(secret);
        System.out.println("JJJ - printing redirect uri");
        System.out.println(redirect);
        System.out.println("JJJ - printing idam client id, used in Oauth2Config");
        System.out.println(${idam.client.id});
        System.out.println("${idam.client.id}");
        createUser();
        String authClientResponse = client.authenticateUser(USERNAME, PASSWORD);
        System.out.println("JJJ - return from authenticateUser");
        System.out.println(authClientResponse);
        return authClientResponse;
    }

    private void createUser() {
        System.out.println("JJJ");
        System.out.println(idamUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", USERNAME);
        jsonObject.put("password", PASSWORD);
        jsonObject.put("forename", "test");
        jsonObject.put("surname", "test");

        RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(jsonObject.toString())
            .post(idamUrl + "/testing-support/accounts")
            .prettyPrint();
    }

}
