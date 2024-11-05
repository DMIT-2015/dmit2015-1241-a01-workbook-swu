package io.geolocation.api;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RequestScoped
@RegisterRestClient(baseUri = "https://api.ipgeolocation.io")
public interface IpGeoLocationApiResponseMpRestClient {

    @GET
    @Path("/ipgeo")
    IpGeoLocationApiResponse getIpGeoLocation(
            @QueryParam("apiKey") String apiKey,
            @QueryParam("ip") String ip
    );
}
