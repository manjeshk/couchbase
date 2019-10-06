package com.manjesh.couchbase.factory;

import com.couchbase.client.core.env.NetworkResolution;
import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.manjesh.couchbase.exception.CouchbaseException;
import com.manjesh.couchbase.config.CouchbaseClientConfig;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class CouchbaseClusterFactory {
    private Cluster cluster;

    public Cluster cluster(CouchbaseClientConfig clientConfig) throws CouchbaseException {

        try {
            DefaultCouchbaseEnvironment.Builder builder = DefaultCouchbaseEnvironment.builder();

            if (clientConfig.isSslEnabled())
                builder.sslEnabled(clientConfig.isSslEnabled());

            if (clientConfig.isMutationTokensEnabled()) {
                builder.mutationTokensEnabled(clientConfig.isMutationTokensEnabled())
                        .sslKeystoreFile(clientConfig.getSslKeystoreFile())
                        .sslKeystorePassword(clientConfig.getSslKeystorePassword());
            }

            if (clientConfig.isBootstrapHttpEnabled())
                builder.bootstrapHttpEnabled(clientConfig.isBootstrapHttpEnabled());

            if (clientConfig.getBootstrapHttpDirectPort() != 0)
                builder.bootstrapHttpDirectPort(clientConfig.getBootstrapHttpDirectPort());

            if (clientConfig.getBootstrapHttpSslPort() != 0)
                builder.bootstrapHttpSslPort(clientConfig.getBootstrapHttpSslPort());

            if (clientConfig.getBootstrapCarrierPort() != 0)
                builder.bootstrapCarrierDirectPort(clientConfig.getBootstrapCarrierPort());

            if (clientConfig.getBootstrapCarrierSslPort() != 0)
                builder.bootstrapCarrierSslPort(clientConfig.getBootstrapCarrierSslPort());

            if (clientConfig.isDnsSrvEnabled())
                builder.dnsSrvEnabled(clientConfig.isDnsSrvEnabled());

            if (clientConfig.isMutationTokensEnabled())
                builder.mutationTokensEnabled(clientConfig.isMutationTokensEnabled());

            if (clientConfig.isCertAuthEnabledEnabled())
                builder.certAuthEnabled(clientConfig.isCertAuthEnabledEnabled());

            if (StringUtils.isNotBlank(clientConfig.getNetworkResolution())) {
                switch (clientConfig.getNetworkResolution()) {
                    case "default":
                        builder.networkResolution(NetworkResolution.DEFAULT);
                    case "auto":
                        builder.networkResolution(NetworkResolution.AUTO);
                    case "external":
                        builder.networkResolution(NetworkResolution.EXTERNAL);
                    default:
                        break;
                }
            }

            if (clientConfig.getKvTimeout() != 0)
                builder.kvTimeout(clientConfig.getKvTimeout());

            if (clientConfig.getViewTimeout() != 0)
                builder.viewTimeout(clientConfig.getViewTimeout());

            if (clientConfig.getQueryTimeout() != 0)
                builder.queryTimeout(clientConfig.getQueryTimeout());

            if (clientConfig.getSearchTimeout() != 0)
                builder.searchTimeout(clientConfig.getSearchTimeout());

            if (clientConfig.getAnalyticsTimeout() != 0)
                builder.analyticsTimeout(clientConfig.getAnalyticsTimeout());

            if (clientConfig.getConnectTimeout() != 0)
                builder.connectTimeout(clientConfig.getConnectTimeout());

            if (clientConfig.getDisconnectTimeout() != 0)
                builder.disconnectTimeout(clientConfig.getDisconnectTimeout());

            if (clientConfig.getManagementTimeout() != 0)
                builder.managementTimeout(clientConfig.getManagementTimeout());

            if (clientConfig.getSocketConnectTimeout() != 0)
                builder.socketConnectTimeout(clientConfig.getSocketConnectTimeout());

            if (clientConfig.getReconnectDelay() != 0)
                builder.reconnectDelay(Delay.fixed(clientConfig.getReconnectDelay(), TimeUnit.MILLISECONDS));

            if (clientConfig.getRetryDelay() != 0)
                builder.retryDelay(Delay.fixed(clientConfig.getRetryDelay(), TimeUnit.MILLISECONDS));

            if (clientConfig.getMaxRequestLifetime() != 0)
                builder.maxRequestLifetime(clientConfig.getMaxRequestLifetime());

            if (clientConfig.getKeepAliveInterval() != 0)
                builder.keepAliveInterval(clientConfig.getKeepAliveInterval());

            if (clientConfig.getKeepAliveErrorThreshold() != 0)
                builder.keepAliveErrorThreshold(clientConfig.getKeepAliveErrorThreshold());

            if (clientConfig.getKeepAliveTimeout() != 0)
                builder.keepAliveTimeout(clientConfig.getKeepAliveTimeout());

            if (clientConfig.getConfigPollInterval() != 0)
                builder.configPollInterval(clientConfig.getConfigPollInterval());

            if (clientConfig.getObserveIntervalDelay() != 0)
                builder.observeIntervalDelay(Delay.fixed(clientConfig.getObserveIntervalDelay(), TimeUnit.MILLISECONDS));

            if (clientConfig.getKvEndpoints() != 0)
                builder.kvEndpoints(clientConfig.getKvEndpoints());

            if (clientConfig.getIoPoolSize() != 0)
                builder.ioPoolSize(clientConfig.getIoPoolSize());

            if (clientConfig.getComputationPoolSize() != 0)
                builder.computationPoolSize(clientConfig.getComputationPoolSize());

            if (clientConfig.isTcpNodelayEnabled())
                builder.tcpNodelayEnabled(clientConfig.isTcpNodelayEnabled());

            if (clientConfig.isCallbacks())
                builder.callbacksOnIoPool(clientConfig.isCallbacks());

            if (clientConfig.getRequestBufferSize() != 0)
                builder.requestBufferSize(clientConfig.getRequestBufferSize());

            if (clientConfig.getResponseBufferSize() != 0)
                builder.responseBufferSize(clientConfig.getResponseBufferSize());

            if (clientConfig.isBufferPoolingEnabled())
                builder.bufferPoolingEnabled(clientConfig.isBufferPoolingEnabled());

            CouchbaseEnvironment couchbaseEnvironment = builder.build();

            cluster = CouchbaseCluster.create(couchbaseEnvironment, Arrays.toString(clientConfig.getNodes().toArray()));
            if (StringUtils.isNotBlank(clientConfig.getUserName()) && StringUtils.isNotBlank(clientConfig.getPassword()))
                cluster.authenticate(clientConfig.getUserName(), clientConfig.getPassword());
        } catch (Exception exception) {
            throw new CouchbaseException("Exception occured while creating cluster", exception);
        } catch (Throwable throwable) {
            throw new CouchbaseException(throwable);
        }

        return cluster;
    }

    public void disconnectCluster() throws CouchbaseException {
        try {
            if (cluster != null)
                cluster.disconnect();
        } catch (Exception exception) {
            throw new CouchbaseException("Exception occured while closing all buckets and cluster", exception);
        }
    }


}
