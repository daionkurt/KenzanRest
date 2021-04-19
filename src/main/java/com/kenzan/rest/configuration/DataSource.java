package com.kenzan.rest.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DataSource extends BasicDataSource {
    private static final Logger logger = LogManager.getLogger(DataSource.class);

    private String sslConnection;
    private String sslTrustStoreLocation;
    private String sslTrustStorePassword;
    private String url;
    private String username;
    private String password;
    private String driver;

    public static final String CONN_PROP_SSL_CONNECTION = "sslConnection";
    public static final String CONN_PROP_SSL_TRUSTSTORE_LOCATION = "sslTrustStoreLocation";
    public static final String CONN_PROP_SSL_TRUSTSTORE_PDS = "sslTrustStorePassword";

    public DataSource(String sslConnection, String sslTrustStoreLocation, String sslTrustStorePassword) {
        super();
        setSslConnection(sslConnection);
        setSslTrustStoreLocation(sslTrustStoreLocation);
        setSslTrustStorePassword(sslTrustStorePassword);
    }

    public DataSource(String sslConnection, String sslTrustStoreLocation, String sslTrustStorePassword, String url,
                      String username, String password, String driver) {
        super();
        this.sslConnection = sslConnection;
        this.sslTrustStoreLocation = sslTrustStoreLocation;
        this.sslTrustStorePassword = sslTrustStorePassword;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public void setSslConnection(String sslConnection) {
        this.sslConnection = sslConnection;
    }


    public void setSslTrustStoreLocation(String sslTrustStoreLocation) {
        this.sslTrustStoreLocation = sslTrustStoreLocation;
    }

    public void setSslTrustStorePassword(String sslTrustStorePassword) {
        this.sslTrustStorePassword = sslTrustStorePassword;
    }

    public DataSource initDataSourceConfiguration() {
        logger.info("Entering initDataSourceConfiguration()");
        Map<String, String> connProps = new HashMap<>();
        connProps.put(CONN_PROP_SSL_CONNECTION, sslConnection);
        connProps.put(CONN_PROP_SSL_TRUSTSTORE_LOCATION, sslTrustStoreLocation + "trustedStore.jks");
        connProps.put(CONN_PROP_SSL_TRUSTSTORE_PDS, sslTrustStorePassword);
        connProps.keySet().forEach(key -> addConnectionProperty(key, (String) connProps.get(key)));
        logger.debug("Setting DB with [URL=" + url + ",U/P=" + username + "/" + password + "]");
        setUrl(url);
        setUsername(username);
        setPassword(password);
        setDriverClassName(driver);
        setMaxIdle(0);
        setTimeBetweenEvictionRunsMillis(60L * 1000);
        logger.info("Exiting initDataSourceConfiguration()");
        return this;
    }
    @SuppressWarnings("resource")
    public static DataSource getDataSource(String sslConnection, String sslTrustStoreLocation,
                                           String sslTrustStorePassword, String url, String username, String password, String driver) {
        return new DataSource(sslConnection, sslTrustStoreLocation, sslTrustStorePassword, url, username, password,
                driver).initDataSourceConfiguration();
    }
}