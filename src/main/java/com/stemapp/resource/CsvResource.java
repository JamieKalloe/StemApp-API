package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.service.CsvService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Created by Jamie on 28-3-2016.
 */
@Path("/regios")
@Produces(MediaType.APPLICATION_JSON)
public class CsvResource {

    //Variables
    private final CsvService service;

    public CsvResource(CsvService service) {
        this.service = service;
    }

    @GET
    public void importCSV() {
        service.importMiddleSchool(new File("D:\\School\\Hoofdfase\\Periode 3\\ipsen4\\data\\Data voortgezet onderwijs.csv"));
    }

}
