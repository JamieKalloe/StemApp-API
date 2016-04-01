package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.model.Categorie;
import com.stemapp.service.CategorieService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Jamie on 1-4-2016.
 */
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategorieResource {

    //Variables
    private final CategorieService service;

    public CategorieResource(CategorieService service) {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Categorie> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public Categorie retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(Categorie categorie) {
        service.add(categorie);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void update(@PathParam("id") int id, Categorie categorie) {
        service.update(id, categorie);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        service.delete(id);
    }
}
