/*
Copyright 2019-present, Simon Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at:
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.simondata.pouroversql.clients;

import java.util.Properties;

/**
 * SQLServerSQLParams
 *
 * A set of params that can be sent to the SQLServer client
 */
public class SQLServerSQLParams extends SQLParams {
    private Boolean encrypt = null;
    private Boolean trustServerCertificate = null;
    private String hostNameInCertificate = null;
    private String accessToken = null;
    private String authentication = null;
    private String authenticationScheme = null;
    private String columnEncryptionSetting = null;
    private String failoverPartner = null;
    private Boolean integratedSecurity= null;
    private String trustStore = null;
    private String trustStorePassword = null;
    private String trustStoreType = null;

    /**
     * Params for SQLServer
     * @param host sqlserver host uri. Defaults to localhost
     * @param port sqlserver port. Defaults to 1433.
     * @param user the sqlserver user.
     * @param password the sqlserver user's password
     * @param database the database to use on the SQL Server.
     */
    public SQLServerSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    /**
     * Constructor with custom params
     * @param host sqlserver host uri. Defaults to localhost
     * @param port sqlserver port. Defaults to 1433.
     * @param user the sqlserver user.
     * @param password the sqlserver user's password
     * @param database the database to use on the SQL Server.
     * @param customProperties the custom properties to use
     */
    public SQLServerSQLParams(
            String host, Integer port, String user, String password, String database, Properties customProperties
    ) {
        super(host, port, user, password, database, customProperties);
    }

    public static SQLServerSQLParams initEngineParams(SQLParams sqlParams) {
        return new SQLServerSQLParams(
                sqlParams.getHost(),
                sqlParams.getPort(),
                sqlParams.getUser(),
                sqlParams.getPassword(),
                sqlParams.getDatabase(),
                sqlParams.getCustomProperties()
        );
    }

    /**
     * Set whether or not to encrypt. Boolean.
     * @param encrypt whether or not to encrypt.
     */
    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    /**
     * Get whether or not to encrypt. Boolean
     * @return whether or not to encrypt.
     */
    public Boolean getEncrypt() {
        if (this.encrypt != null) {
            return this.encrypt;
        } else {
            return Boolean.parseBoolean(
                    (String) this.getCustomProperties().getOrDefault("encrypt", "false"));
        }
    }

    /**
     * Set Trust Server Certificate. Boolean.
     * @param trustServerCertificate the trust server certificate.
     */
    public void setTrustServerCertificate(Boolean trustServerCertificate) {
        this.trustServerCertificate = trustServerCertificate;
    }

    /**
     * Get the trust server certificate. Boolean.
     * @return the trust server certificate.
     */
    public Boolean getTrustServerCertificate() {
        if (this.trustServerCertificate != null) {
            return this.trustServerCertificate;
        } else {
            return Boolean.parseBoolean(
                    (String) this.getCustomProperties().getOrDefault(
                            "trustServerCertificate", "false"));
        }
    }

    /**
     * Set host name in certificate. String.
     * @param hostNameInCertificate the host name in certificate.
     */
    public void setHostNameInCertificate(String hostNameInCertificate) {
        this.hostNameInCertificate = hostNameInCertificate;
    }

    /**
     * Get host name in certificate.
     * @return host name in certificate.
     */
    public String getHostNameInCertificate() {
        return this.getCustomStringParameter(this.hostNameInCertificate, "hostNameInCertificate");
    }

    /**
     * Set SQLServer access token
     * @param accessToken the SQLServer access token.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get SQLServer access token
     * @return the SQLServer access token.
     */
    public String getAccessToken() {
        return this.getCustomStringParameter(this.accessToken, "accessToken");
    }

    /**
     * Set SQLServer authentication.
     * @param authentication the SQLServer authentication
     */
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    /**
     * Get SQLServer authentication.
     * @return the SQLServer authentication.
     */
    public String getAuthentication() {
        return this.getCustomStringParameter(this.authentication, "authentication");
    }

    /**
     * Set AuthenticationScheme
     * @param authenticationScheme the SQLServer authentication scheme
     */
    public void setAuthenticationScheme(String authenticationScheme) {
        this.authenticationScheme = authenticationScheme;
    }

    /**
     * Get SQLServer authentication scheme
     * @return the sqlserver authentication scheme.
     */
    public String getAuthenticationScheme() {
        return this.getCustomStringParameter(this.authenticationScheme, "authenticationScheme");
    }

    /**
     * Set Column Encryption Setting
     * @param columnEncryptionSetting the SQLserver column encryption setting.
     */
    public void setColumnEncryptionSetting(String columnEncryptionSetting) {
        this.columnEncryptionSetting = columnEncryptionSetting;
    }

    /**
     * Get Column Encryption Setting. String.
     * @return Column Encryption Setting
     */
    public String getColumnEncryptionSetting() {
        return this.getCustomStringParameter(this.columnEncryptionSetting, "columnEncryptionSetting");
    }

    /**
     * Set Failover partner. String.
     * @param failoverPartner the failover partner
     */
    public void setFailoverPartner(String failoverPartner) {
        this.failoverPartner = failoverPartner;
    }

    /**
     * Get Failover partner. String.
     * @return the failover partner.
     */
    public String getFailoverPartner() {
        return this.getCustomStringParameter(this.failoverPartner, "failoverPartner");
    }

    /**
     * Set use integrated security
     * @param integratedSecurity whether to use integrated security.
     */
    public void setIntegratedSecurity(Boolean integratedSecurity) {
        this.integratedSecurity = integratedSecurity;
    }

    /**
     * Get whether to use integrated security
     * @return whether to use integrated security.
     */
    public Boolean getIntegratedSecurity() {
        return this.getCustomBooleanParameter(this.integratedSecurity, "integratedSecurity");
    }

    /**
     * Set Trust Store. String.
     * @param trustStore The trust store to set.
     */
    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    /**
     * Get Trust Store. String.
     * @return The Trust Store.
     */
    public String getTrustStore() {
        return this.getCustomStringParameter(this.trustStore, "trustStore");
    }

    /**
     * Set the trust store password
     * @param trustStorePassword the trust store password.
     */
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /**
     * Get the trust store password.
     * @return the trust store password.
     */
    public String getTrustStorePassword() {
        return this.getCustomStringParameter(this.trustStorePassword, "trustStorePassword");
    }

    /**
     * Set SQLServer trust store type.
     * @param trustStoreType SQLServer trust store type.
     */
    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    /**
     * Get SQLServer trust store type.
     * @return SQLServer trust store type.
     */
    public String getTrustStoreType() {
        return this.getCustomStringParameter(this.trustStoreType, "trustStoreType");
    }
}
