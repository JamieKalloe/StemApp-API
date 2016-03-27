package com.stemapp;

import com.stemapp.persistence.RegioDAO;
import com.stemapp.resource.RegioResource;
import com.stemapp.service.RegioService;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.FilterHolder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.DispatcherType;
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

        logger.info(String.format("Set API name to %s", this.name));

        //Create and register DAOs, Services and Resources.
        RegioDAO regioDAO = new RegioDAO();

        RegioService regioService = new RegioService(regioDAO);

        RegioResource regioResource = new RegioResource(regioService);

        //Register
        configureClientFilter(environment);

        environment.jersey().register(regioResource);
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
