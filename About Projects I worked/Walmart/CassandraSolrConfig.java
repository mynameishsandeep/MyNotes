package com.walmart.services.partnerquery.domain.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.walmart.services.nosql.config.ClusterBuilder;
import com.walmart.services.nosql.config.ClusterConfig;
import com.walmart.services.nosql.config.CqlConfig;
import com.walmart.services.nosql.config.CqlDriverConfig;
import com.walmart.services.nosql.data.CqlDAO;
import com.walmart.services.nosql.data.impl.CqlDaoImpl;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import com.walmart.partnerapi.common.security.util.EncryptionUtils;


@Configuration
public class CassandraSolrConfig<Key,T> {

	
	//@Value("${partnerQuery.keyspace.name}")
	@Value("${partner-query-service-app.cassandra[keyspace.name]}")
	private String orderKeySpace;

	//@Value("${partnerQuery.keyspace.name}")
	@Value("${partner-query-service-app.cassandra[userName]}")
	private String userName;

	//@Value("${partnerQuery.keyspace.name}")
	@Value("${partner-query-service-app.cassandra[password]}")
	private String password;	

	//@Value("${partnerQuery.orderRelease.table.name}")
	@Value("${partner-query-service-app.cassandra[orderRelease.table.name]}")
	private String orderReleaseTableName;

	@Value("${partner-query-service-app.cassandra[orderAudit.table.name]}")
	private String orderAuditTableName;

	
    //@Value("${partnerQuery.cassandra.entity.packages}")
    @Value("${partner-query-service-app.cassandra[partnerQuery.cassandra.entity.packages]}")
    private String entityPackages;
    
    
    //@Value("${dataCentre}")
    @Value("${partner-query-service-app.cassandra[dataCentre]}")
    private String dataCentre;

    //@Value("${clustername}")
    @Value("${partner-query-service-app.cassandra[clustername]}")
    private String clusterName;

    //@Value("${clusterIpAddressList}")
    @Value("${partner-query-service-app.cassandra[clusterIpAddressList]}")
    private String clusterIpAddressList;
    
    //@Value("${cassandraPort}")
    @Value("${partner-query-service-app.cassandra[cassandraPort]}")
    private Integer cassandraPort;

    //@Value("${cassandra.thrift.Port}")
    @Value("${partner-query-service-app.cassandra[cassandra.thrift.Port]}")
    private Integer cassandraThriftPort;

    //@Value("${readConsistency}")
    @Value("${partner-query-service-app.cassandra[readConsistency]}")
    private String readConsistency;

    //@Value("${writeConsistency}")
    @Value("${partner-query-service-app.cassandra[writeConsistency]}")
    private String writeConsistency;

   //@Value("${initConnectionsPerHost}")
    @Value("${partner-query-service-app.cassandra[initConnectionsPerHost]}")
    private Integer initConnectionsPerHost;

    //@Value("${maxConnectionsPerHost}")
    @Value("${partner-query-service-app.cassandra[maxConnectionsPerHost]}")
    private Integer maxConnectionsPerHost;
    
    //@Value("${initConnectionsPerHostStorage}")
    @Value("${partner-query-service-app.cassandra[initConnectionsPerHostStorage]}")
    private Integer initConnectionsPerHostStorage;

    //@Value("${maxConnectionsPerHostStorage}")
    @Value("${partner-query-service-app.cassandra[maxConnectionsPerHostStorage]}")
    private Integer maxConnectionsPerHostStorage;
    
    //@Value("${cfsClusterIpAddressList}")
    @Value("${partner-query-service-app.cassandra[cfsClusterIpAddressList]}")
    private String cfsClusterIpAddressList;
    
    //@Value("${cfsPort}")
    @Value("${partner-query-service-app.cassandra[cfsPort]}")
    private Integer cfsPort;

    //@Value("${connectionPoolName}")
    @Value("${partner-query-service-app.cassandra[connectionPoolName]}")
    private String connectionPoolName;
    
    //@Value("${connectionPoolType}")
    @Value("${partner-query-service-app.cassandra[connectionPoolType]}")
    private String connectionPoolType;
    
    //@Value("${nodeDiscoveryType}")
    @Value("${partner-query-service-app.cassandra[nodeDiscoveryType]}")
    private String	nodeDiscoveryType;
    
    //@Value("${maxSolrConnections}")
    @Value("${partner-query-service-app.cassandra[maxSolrConnections]}")
    private Integer maxSolrConnections;
    
    //@Value("${solrConnectionsPerNode}")
    @Value("${partner-query-service-app.cassandra[solrConnectionsPerNode]}")
    private Integer solrConnectionsPerNode;
    
    //@Value("${solrConnectionRequestTimeOut}")
    @Value("${partner-query-service-app.cassandra[solrConnectionRequestTimeOut]}")
    private Integer solrConnectionRequestTimeOut;
    
    //@Value("${solrConnectionTimeOut}")
    @Value("${partner-query-service-app.cassandra[solrConnectionTimeOut]}")
    private Integer solrConnectionTimeOut;
    
    //@Value("${solrSocketTimeOut}")
    @Value("${partner-query-service-app.cassandra[solrSocketTimeOut]}")
    private Integer solrSocketTimeOut;
    
    //@Value("${solrBaseUrl}")
    @Value("${partner-query-service-app.cassandra[solrBaseUrl]}")
    private String solrBaseUrl;
    
    @Value("${partner-query-service-app.solr[order.url]}")
	public String orderSolr; 
	

   @Bean
    public CqlDAO<String, T> cqlDao(){
        CqlDriverConfig cqlDriverConfig = new CqlDriverConfig(getProductCqlConfig(),clusterBuilder());
        CqlDAO<String, T> cqlDAO = new CqlDaoImpl(cqlDriverConfig,null);   //new HttpSolrServer(solrBaseUrl,httpClient())
        return cqlDAO;
    }

    public ClusterBuilder clusterBuilder(){
    	
        ClusterConfig clusterConfig=new ClusterConfig();
        clusterConfig.setUsername(EncryptionUtils.decrypt(userName));
        clusterConfig.setPassword(EncryptionUtils.decrypt(password));
        clusterConfig.setClusterName(clusterName);
        clusterConfig.setCfsClusterIpAddressList(cfsClusterIpAddressList);
        clusterConfig.setCfsPort(cfsPort);
        clusterConfig.setClusterIpAddressList(clusterIpAddressList);
        clusterConfig.setDataCentre(dataCentre);
        clusterConfig.setPort(cassandraPort);
        clusterConfig.setInitConnectionsPerHost(initConnectionsPerHost);
        clusterConfig.setMaxHostsPerConnection(maxConnectionsPerHost);
        clusterConfig.setSimultaneousRequestsPerConnection(100);
        clusterConfig.setCompression("LZ4");
     
        ClusterBuilder clusterBuilder=new ClusterBuilder(clusterConfig);
        return clusterBuilder;

    }

    private CqlConfig getProductCqlConfig() {

        CqlConfig cqlConfig = new CqlConfig();
        cqlConfig.setReadConsistency(readConsistency);
        cqlConfig.setWriteConsistency(writeConsistency);
        cqlConfig.setKeySpace(orderKeySpace);
        cqlConfig.setEntityPackages(entityPackages);

        return cqlConfig;
    }
    
    /**
    *
    */
   private CloseableHttpClient httpClient(){
       CloseableHttpClient httpClient= HttpClients.custom()
               .setConnectionManager(poolingConnectionManager()).setDefaultRequestConfig(requestConfig())
                 .setConnectionManager(poolingConnectionManager())
                 .setDefaultRequestConfig(requestConfig())
                 .setDefaultCredentialsProvider(credentialProvider())
                 .build();
       return httpClient;
   }

   private CredentialsProvider credentialProvider() {
	           CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	           if(userName!=null&&password!=null){
	        	   credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, EncryptionUtils.decrypt(password)));  //TODO : Relaxed it to ANY, change this later
	           }
	           return credentialsProvider;
	       }
   
   /**
    *
    */
   private PoolingHttpClientConnectionManager poolingConnectionManager(){
       PoolingHttpClientConnectionManager poolingHttpClientConnectionManager=new PoolingHttpClientConnectionManager();
       poolingHttpClientConnectionManager.setMaxTotal(maxSolrConnections);
       poolingHttpClientConnectionManager.setDefaultMaxPerRoute(solrConnectionsPerNode);
       return poolingHttpClientConnectionManager;
   }

   /**
    *
    */
   private RequestConfig requestConfig(){
       RequestConfig requestConfig= RequestConfig.custom()
               .setConnectionRequestTimeout(solrConnectionRequestTimeOut)
               .setConnectTimeout(solrConnectionTimeOut)
               .setSocketTimeout(solrSocketTimeOut).setStaleConnectionCheckEnabled(true).build();
       return requestConfig;
   }
   
   @Bean
   public SolrServer orderReleaseSolrServer() {      
	   return new HttpSolrServer(  solrBaseUrl + "/" + orderKeySpace + "." + orderReleaseTableName, httpClient());
   }

   @Bean
   public SolrServer orderReleaseSolrServerWithLB() {
       return new LBHttpSolrServer(httpClient(),orderSolr.split(",",-1));
   }

   
   @Bean
   public SolrServer orderAuditSolrServer() {
       return new HttpSolrServer(  solrBaseUrl + "/" + orderKeySpace + "." + orderAuditTableName, httpClient());
   }
  
}
