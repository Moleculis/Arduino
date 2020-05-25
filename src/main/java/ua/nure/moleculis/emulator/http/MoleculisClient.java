package ua.nure.moleculis.emulator.http;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Config;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import kong.unirest.json.JSONObject;
import lombok.SneakyThrows;

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
