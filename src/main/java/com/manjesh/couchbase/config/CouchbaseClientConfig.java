package com.manjesh.couchbase.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CouchbaseClientConfig {
    private List<String> nodes;
    private String userName;
    private String password;

    //Bootstrap properties
    private boolean sslEnabled;
    private String sslKeystoreFile;
    private String sslKeystorePassword;
    private boolean bootstrapHttpEnabled;
    private int bootstrapHttpDirectPort;
    private int bootstrapHttpSslPort;
    private int bootstrapCarrierPort;
    private int bootstrapCarrierSslPort;
    private boolean dnsSrvEnabled;
    private boolean mutationTokensEnabled;
    private boolean certAuthEnabledEnabled;
    private String networkResolution; //auto or default or external

    //Timeout Properties
    private long kvTimeout;
    private long viewTimeout;
    private long queryTimeout;
    private long searchTimeout;
    private long analyticsTimeout;
    private long connectTimeout;
    private long disconnectTimeout;
    private long managementTimeout;
    private int socketConnectTimeout;

    //Reliability Properties
    private long reconnectDelay;
    private long retryDelay;
    private long maxRequestLifetime;
    private long keepAliveInterval;
    private long keepAliveErrorThreshold;
    private long keepAliveTimeout;
    private long configPollInterval;

    //Performance Properties
    private long observeIntervalDelay;
    private int kvEndpoints;
    private int ioPoolSize;
    private int computationPoolSize;
    private boolean tcpNodelayEnabled;
    private boolean callbacks;

    //Advanced Properties
    private int requestBufferSize;
    private int responseBufferSize;
    private boolean bufferPoolingEnabled;


}
