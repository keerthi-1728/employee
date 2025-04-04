package in.codifi.ExternalApi;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;



import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.databind.JsonNode;

@RegisterRestClient  
@ApplicationScoped
public interface ExternalApi {

    @GET
    @Path("/api/CompanyMaster")  // path of the external api
    @Produces(MediaType.APPLICATION_JSON)
//    @ClientHeaderParam(name = "Authorization", value = "{getAuthToken}")
    JsonNode fetchExternalData(@HeaderParam("Authorization") String token);
}