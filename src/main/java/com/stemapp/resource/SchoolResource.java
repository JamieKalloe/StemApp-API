package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.model.School;
import com.stemapp.service.SchoolService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Jamie on 28-3-2016.
 */
@Path("schools")
@Produces(MediaType.APPLICATION_JSON)
public class SchoolResource {

    //Variables
    private final SchoolService service;

    public SchoolResource(SchoolService service) {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<School> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public School retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(School school) {
        service.add(school);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void update(@PathParam("id") int id, School school) {
        service.update(id, school);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        service.delete(id);
    }


}
