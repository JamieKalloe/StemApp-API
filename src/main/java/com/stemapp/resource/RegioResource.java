package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.model.Regio;
import com.stemapp.service.RegioService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Jamie on 27-3-2016.
 */
@Path("/regios")
@Produces(MediaType.APPLICATION_JSON)
public class RegioResource {

    //Variables
    private final RegioService service;

    public RegioResource(RegioService service) {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Regio> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public Regio retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(Regio regio) {
        service.add(regio);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void update(@PathParam("id") int id, Regio regio) {
        service.update(id, regio);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        service.delete(id);
    }
}
