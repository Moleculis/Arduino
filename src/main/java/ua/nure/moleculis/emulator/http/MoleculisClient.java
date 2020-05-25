package ua.nure.moleculis.emulator.http;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Config;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import kong.unirest.json.JSONObject;
import lombok.SneakyThrows;
import ua.nure.moleculis.emulator.http.models.responses.LoginResponse;
import ua.nure.moleculis.emulator.http.models.responses.MessageDTO;
import ua.nure.moleculis.emulator.http.models.responses.PeopleResponse;

import java.util.List;

public class MoleculisClient {
    private final UnirestInstance unirest;
    private final String baseUrl = "http://localhost:8080";

    public MoleculisClient() {
        this.unirest = createUnirestInstance();
    }

    public LoginResponse login(String login, String password) {
        var body = new JSONObject()
                .put("username", login)
                .put("password", password);

        final HttpResponse<LoginResponse> response = unirest
                .post(baseUrl + "/users/login")
                .body(body)
                .header("Content-Type", "application/json")
                .asObject(LoginResponse.class);

        return response.getBody();
    }

    public PeopleResponse finPeople(String accessToken) {

        final HttpResponse<PeopleResponse> response = unirest
                .get(baseUrl + "/users/nearby")
                .header("Authorization", "Bearer " + accessToken)
                .asObject(PeopleResponse.class);

        return response.getBody();
    }

    public MessageDTO sendContactRequests(List<String> usernames, String accessToken) {
        var body = new JSONObject()
                .put("usernames", usernames);

        final HttpResponse<MessageDTO> response = unirest
                .post(baseUrl + "/users/send_contact_requests")
                .body(body)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .asObject(MessageDTO.class);

        return response.getBody();
    }

    private UnirestInstance createUnirestInstance() {
        var config = new Config();
        config.setObjectMapper(new kong.unirest.ObjectMapper() {
            private ObjectMapper objectMapper = new ObjectMapper()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            @Override
            @SneakyThrows
            public <T> T readValue(String value, Class<T> valueType) {
                return objectMapper.readValue(value, valueType);
            }

            @Override
            @SneakyThrows
            public String writeValue(Object value) {
                return objectMapper.writeValueAsString(value);
            }
        });

        return new UnirestInstance(config);
    }

}
