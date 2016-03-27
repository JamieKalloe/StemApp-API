package com.stemapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Jamie on 27-3-2016.
 */
public class ApiConfiguration extends Configuration implements AssetsBundleConfiguration {

    @NotEmpty
    @JsonProperty
    private String apiName;

    @NotEmpty
    @JsonProperty
    private String databaseURL;

    @NotEmpty
    @JsonProperty
    private String databaseUser;

    @NotEmpty
    @JsonProperty
    private String databasePwd;

    @NotEmpty
    @JsonProperty
    private String databaseName;

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();

    public String getApiName() {
        return this.apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getDatabaseURL() {
        return this.databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public String getDatabaseUser() {
        return this.databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePwd() {
        return this.databasePwd;
    }

    public void setDatabasePwd(String databasePwd) {
        this.databasePwd = databasePwd;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }


    @Valid
    @NotNull
    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }
}
