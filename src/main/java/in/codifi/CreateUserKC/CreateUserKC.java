package in.codifi.CreateUserKC;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.CreateUserKCSpec.CreateUserKCSpec;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.InputStream;
import in.codifi.DTO.*;
import in.codifi.EmployeService.PrepareResponse;
import in.codifi.Responce.GenericResponse;



@ApplicationScoped
public class CreateUserKC implements CreateUserKCSpec {

    private static final String KEYCLOAK_URL = "http://localhost:9090";
    private static final String REALM = "myrealm";
    private static final String ADMIN_USERNAME = "keerthika N";
    private static final String ADMIN_PASSWORD = "Pk@2003!";
    
    @Inject
   	PrepareResponse prepareresponse;  // Injecting the PrepareResponse class

    private String getAdminAccessToken() throws Exception {
        WebTarget target = ClientBuilder.newClient()
            .target(KEYCLOAK_URL + "/realms/master/protocol/openid-connect/token");

        Form form = new Form()
            .param("grant_type", "password")
            .param("client_id", "admin-cli")
            .param("username", ADMIN_USERNAME)
            .param("password", ADMIN_PASSWORD);

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.form(form));

        if (response.getStatus() == 200) {
            JsonObject json = Json.createReader(response.readEntity(InputStream.class)).readObject();
            return json.getString("access_token");
        } else {
            throw new RuntimeException("Failed to get admin token: " + response.readEntity(String.class));
        }
    }

    @Override
    public RestResponse<GenericResponse> createUser(UserDTO userDTO) {
        try {
            String token = getAdminAccessToken();

            JsonObject userJson = Json.createObjectBuilder()
                .add("username", userDTO.getUsername())
                .add("email", userDTO.getEmail())
                .add("firstName",userDTO.getFirstname() )
                .add("lastName",userDTO.getLastname() )
                .add("enabled", true)
                .add("emailVerified", true) // Automatically mark email as verified
                .add("credentials", Json.createArrayBuilder()
                    .add(Json.createObjectBuilder()
                        .add("type", "password")
                        .add("value", userDTO.getPassword())
                        .add("temporary", false)
                    )
                ).build();

            WebTarget target = ClientBuilder.newClient()
                .target(KEYCLOAK_URL + "/admin/realms/" + REALM + "/users");

            Response response = target.request()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .post(Entity.json(userJson.toString()));

            if (response.getStatus() == 201) {
                return prepareresponse.prepareSuccessResponseObject("User created successfully.");
            } else {
                return prepareresponse.prepareFailedResponse("Failed to create user: " + response.readEntity(String.class));
            }
        } catch (Exception e) {
            return prepareresponse.prepareFailedResponse("Error: " + e.getMessage());
        }
    }

    @Override
    public RestResponse<GenericResponse> loginUser(UserLoginDTO loginDTO) {
        try {
            WebTarget target = ClientBuilder.newClient()
                .target(KEYCLOAK_URL + "/realms/" + REALM + "/protocol/openid-connect/token");

            Form form = new Form()
                .param("grant_type", "password")
                .param("client_id", "rmoney")
                .param("username", loginDTO.getUsername())
                .param("password", loginDTO.getPassword());

            Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.form(form));

            if (response.getStatus() == 200) {
                JsonObject json = Json.createReader(response.readEntity(InputStream.class)).readObject();
                String token = json.getString("access_token");
                return prepareresponse.prepareSuccessResponseObject("Token generated successfully: " + token);
            } else {
                return prepareresponse.prepareFailedResponse("Login failed: " + response.readEntity(String.class));
            }
        } catch (Exception e) {
            return prepareresponse.prepareFailedResponse("Login failed: " + e.getMessage());
        }
    }
}