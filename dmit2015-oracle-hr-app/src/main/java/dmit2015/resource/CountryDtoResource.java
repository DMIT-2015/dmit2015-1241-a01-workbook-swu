package dmit2015.resource;

import common.validator.BeanValidator;
import dmit2015.entity.Country;
import dmit2015.dto.CountryDto;
import dmit2015.mapper.CountryMapper;
import dmit2015.repository.CountryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.stream.Collectors;

/**
 * This Jakarta RESTful Web Services root resource class provides common REST API endpoints to
 * perform CRUD operations on the DTO (Data Transfer Object) for a Jakarta Persistence entity.
 */
@ApplicationScoped
@Path("CountryDtos")                // All methods in this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)
// All methods in this class expects method parameters to contain data in JSON format
@Produces(MediaType.APPLICATION_JSON)    // All methods in this class returns data in JSON format
public class CountryDtoResource {

    @Inject
    private CountryRepository _countryRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response findAllCountrysCountrys() {
        return Response.ok(
                _countryRepository
                        .findAll()
                        .stream()
                        .map(CountryMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
        ).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findCountryByIdCountryById(@PathParam("id") String countryId) {
        Country existingCountry = _countryRepository.findById(countryId).orElseThrow(NotFoundException::new);

        CountryDto dto = CountryMapper.INSTANCE.toDto(existingCountry);

        return Response.ok(dto).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response createCountryCountry(CountryDto dto, @Context UriInfo uriInfo) {
        Country newCountry = CountryMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(newCountry);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new Country into the database
            _countryRepository.add(newCountry);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // uriInfo is injected via @Context parameter to this method
        URI location = UriBuilder
                .fromPath(uriInfo.getPath())
                .path("{id}")
                .build(newCountry.getCountryId());

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the Country was created.
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateCountryCountry(@PathParam("id") String id, CountryDto dto) {
        if (!id.equals(dto.getId())) {
            throw new BadRequestException();
        }

        Country existingCountry = _countryRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        Country updatedCountry = CountryMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(updatedCountry);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        existingCountry.setCountryName(updatedCountry.getCountryName());

        try {
            _countryRepository.update(id, existingCountry);
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
        dto = CountryMapper.INSTANCE.toDto(existingCountry);
        return Response.ok(dto).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response deleteCountryCountry(@PathParam("id") String countryId) {

        Country existingCountry = _countryRepository
                .findById(countryId)
                .orElseThrow(NotFoundException::new);

        try {
            _countryRepository.delete(existingCountry);    // Removes the Country from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" to indicate the resource was deleted
        return Response.noContent().build();

    }

}