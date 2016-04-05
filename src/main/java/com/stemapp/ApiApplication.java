package com.stemapp;

import com.stemapp.database.Database;
import com.stemapp.persistence.*;
import com.stemapp.resource.*;
import com.stemapp.service.*;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
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

        //cors
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        logger.info(String.format("Set API name to %s", this.name));

        //Create and register DAOs, Services and Resources.
        RegioDAO regioDAO = new RegioDAO();
        SchoolDAO schoolDAO = new SchoolDAO(regioDAO);
        CsvDAO csvDAO = new CsvDAO(regioDAO, schoolDAO);
        StellingDAO stellingDAO = new StellingDAO();
        CategorieDAO categorieDAO = new CategorieDAO();
        VragenlijstDAO vragenlijstDAO = new VragenlijstDAO();

        RegioService regioService = new RegioService(regioDAO);
        SchoolService schoolService = new SchoolService(schoolDAO);
        CsvService csvService = new CsvService(csvDAO);
        CategorieService categorieService = new CategorieService(categorieDAO);
        StellingService stellingService = new StellingService(stellingDAO);
        VragenlijstService vragenlijstService = new VragenlijstService(vragenlijstDAO, categorieDAO, stellingDAO);

        RegioResource regioResource = new RegioResource(regioService);
        SchoolResource schoolResource = new SchoolResource(schoolService);
        CsvResource csvResource = new CsvResource(csvService);
        CategorieResource categorieResource = new CategorieResource(categorieService);
        StellingResource stellingResource = new StellingResource(stellingService);
        VragenlijstResource vragenlijstResource = new VragenlijstResource(vragenlijstService);

        //Register
        configureClientFilter(environment);

        environment.jersey().register(regioResource);
        environment.jersey().register(schoolResource);
        environment.jersey().register(csvResource);
        environment.jersey().register(categorieResource);
        environment.jersey().register(stellingResource);
        environment.jersey().register(vragenlijstResource);
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
