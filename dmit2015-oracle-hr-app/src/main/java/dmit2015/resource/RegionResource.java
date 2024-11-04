package dmit2015.resource;

import common.validator.BeanValidator;
import dmit2015.entity.Region;
import dmit2015.repository.RegionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

/**
 * This Jakarta RESTful Web Services root resource class provides common REST API endpoints to
 * perform CRUD operations on Jakarta Persistence entity.
 */
@ApplicationScoped
@Path("Regions")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class RegionResource {

    @Inject
    private RegionRepository _regionRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response findAllRegionsRegions() {
        return Response.ok(_regionRepository.findAll()).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findRegionById(@PathParam("id") Long id) {
        Region existingRegion = _regionRepository.findById(id).orElseThrow(NotFoundException::new);

        return Response.ok(existingRegion).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response createRegionRegion(Region newRegion, @Context UriInfo uriInfo) {

        String errorMessage = BeanValidator.validateBean(newRegion);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new Region into the database
            _regionRepository.add(newRegion);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newRegion.getId()))
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the Region was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateRegionRegion(@PathParam("id") Long id, Region updatedRegion) {
        if (!id.equals(updatedRegion.getId())) {
            throw new BadRequestException();
        }

        String errorMessage = BeanValidator.validateBean(updatedRegion);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        Region existingRegion = _regionRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        existingRegion.setRegionName(updatedRegion.getRegionName());

        try {
            _regionRepository.update(id, existingRegion);
        } catch (OptimisticLockException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The data you are trying to update has changed since your last read request.")
                    .build();
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "200 OK" and include in the body of the response the object that was updated
        return Response.ok(existingRegion).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response deleteRegion(@PathParam("id") Long id) {

        Region existingRegion = _regionRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        try {
            _regionRepository.delete(existingRegion);    // Removes the Region from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" to indicated that the resource was deleted
        return Response.noContent().build();
    }

}