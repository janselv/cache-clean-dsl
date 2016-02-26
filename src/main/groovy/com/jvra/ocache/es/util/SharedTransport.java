package com.jvra.ocache.es.util;

import com.testpoke.common.module.config.ElasticSearchConfig;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * Created by Jansel Valentin on 8/24/2015.
 */
public final class SharedTransport {
    private static final Logger logger = Logger.getLogger(SharedTransport.class);

    private static TransportClient client;

    public static Client getSharedClient(ElasticSearchConfig config) {
        synchronized (SharedTransport.class) {
            if (null != client)
                return client;
        }

        synchronized (SharedTransport.class) {
            if (null == client) {
                logger.info("Trying to configure Elastic Transport with " + config);
                if (null == config.getClusterName() || "".equals(config.getClusterName())) {
                    logger.warn("No empty cluster is allowed.");
                    return null;
                }
                Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", config.getClusterName()).build();
                client = new TransportClient(settings);

                client.addTransportAddress( new InetSocketTransportAddress(config.getMasterHost(),Integer.parseInt(config.getMasterControlPort())));
                addClientSlavesTransport(config, client);
                return client;
            }
        }

        return client;
    }


    private static void addClientSlavesTransport(ElasticSearchConfig config, TransportClient client) {
        int slavesCount = Integer.parseInt(config.getSlavesCount());
        if( 0>=slavesCount ){
            logger.info( "The config does not specify slaves nodes" );
            return;
        }

        for( int i=1;i<slavesCount+1;++i ){
            final String slaveHost = config.getSlaveHostAt(i);
            final String slavePort = config.getSlavePortAt(i);

            logger.info( "Configuring Slave "+i+" host="+slaveHost+" and port="+slavePort+"..." );
            client.addTransportAddress( new InetSocketTransportAddress(slaveHost,Integer.parseInt(slavePort)));
        }
    }
}
