package com.stemapp.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import com.stemapp.service.CsvService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by Jamie on 28-3-2016.
 */
@Path("/upload")
@Produces(MediaType.APPLICATION_JSON)
public class CsvResource {

    //Variables
    private final CsvService service;

    public CsvResource(CsvService service) {
        this.service = service;
    }

//    @GET
//    public void importCSV() {
//        service.importMiddleSchool(new File("D:\\School\\Hoofdfase\\Periode 3\\ipsen4\\data\\Data voortgezet onderwijs.csv"));
//    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/middleschool")
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        // TODO: uploadFileLocation should come from config.yml
        String uploadedFileLocation = "D:/IntelliJ_Workspace/" + fileDetail.getFileName();
        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded to : " + uploadedFileLocation;
        return Response.ok(output).build();
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }
}
