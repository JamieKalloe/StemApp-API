package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.model.Stelling;
import com.stemapp.service.StellingService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Jamie on 5-4-2016.
 */
@Path("/stellingen")
@Produces(MediaType.APPLICATION_JSON)
public class StellingResource {

    //Variables
    private final StellingService service;

    public StellingResource(StellingService service) {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Stelling> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public Stelling retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(Stelling stelling) {
        service.add(stelling);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void update(@PathParam("id") int id, Stelling stelling) {
        service.update(id, stelling);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        service.delete(id);
    }
}
