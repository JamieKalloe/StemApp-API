package com.stemapp;

import com.stemapp.database.Database;
import com.stemapp.persistence.CsvDAO;
import com.stemapp.persistence.RegioDAO;
import com.stemapp.persistence.SchoolDAO;
import com.stemapp.resource.CsvResource;
import com.stemapp.resource.RegioResource;
import com.stemapp.resource.SchoolResource;
import com.stemapp.service.CsvService;
import com.stemapp.service.RegioService;
import com.stemapp.service.SchoolService;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.FilterHolder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.DispatcherType;
import java.io.File;
import java.util.EnumSet;

/**
 * Created by Jamie on 27-3-2016.
 */
public class ApiApplication extends Application<ApiConfiguration> {

    //Variables
    private final Logger logger = LoggerFactory.getLogger(ApiApplication.class);
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
        bootstrap.addBundle((ConfiguredBundle) new ConfiguredAssetsBundle("/assets/", "/client", "index.html"));
    }

    @Override
    public void run(ApiConfiguration configuration, Environment environment) throws Exception {
        this.name = configuration.getApiName();
        Database.getInstance(configuration);

        logger.info(String.format("Set API name to %s", this.name));

        //Create and register DAOs, Services and Resources.
        RegioDAO regioDAO = new RegioDAO();
        SchoolDAO schoolDAO = new SchoolDAO(regioDAO);
        CsvDAO csvDAO = new CsvDAO(regioDAO, schoolDAO);

        RegioService regioService = new RegioService(regioDAO);
        SchoolService schoolService = new SchoolService(schoolDAO);
        CsvService csvService = new CsvService(csvDAO);

        RegioResource regioResource = new RegioResource(regioService);
        SchoolResource schoolResource = new SchoolResource(schoolService);
//        CsvResource csvResource = new CsvResource(csvService);
        csvService.importMiddleSchool(new File("D:\\School\\Hoofdfase\\Periode 3\\ipsen4\\data\\Data voortgezet onderwijs.csv"));

        //Register
        configureClientFilter(environment);

        environment.jersey().register(regioResource);
        environment.jersey().register(schoolResource);
//        environment.jersey().register(csvResource);
    }

    private void configureClientFilter(Environment environment) {
        environment.getApplicationContext().addFilter(
                new FilterHolder(new ClientFilter()), "/*",
                EnumSet.allOf(DispatcherType.class)
        );
    }

    public static void main(String[] args) throws Exception {
        new ApiApplication().run(args);
    }
}
