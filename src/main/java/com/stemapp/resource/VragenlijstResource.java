package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.model.Categorie;
import com.stemapp.model.Vragenlijst;
import com.stemapp.service.VragenlijstService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Jamie on 4-4-2016.
 */
@Path("/vragenlijsten")
@Produces(MediaType.APPLICATION_JSON)
public class VragenlijstResource {

    //Variables
    private final VragenlijstService service;

    public VragenlijstResource(VragenlijstService service) {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Vragenlijst> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public Vragenlijst retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(Vragenlijst vragenlijst) {
        service.add(vragenlijst);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void update(@PathParam("id") int id, Vragenlijst vragenlijst) {
        service.update(id, vragenlijst);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        service.delete(id);
    }
}
