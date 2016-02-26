package com.jvra.ocache.es.script

import com.jvra.ocache.es.CacheOperation
import com.jvra.ocache.es.OperationExecutionException
import com.jvra.ocache.es.util.SharedTransport
import com.testpoke.common.module.config.ElasticSearchConfig
import com.testpoke.gm.functions.tso.es.CacheOperation
import com.testpoke.gm.functions.tso.es.OperationExecutionException
import com.testpoke.gm.functions.tso.es.util.SharedTransport
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.apache.log4j.Logger
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.Client
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders

/**
 *
 * Created by Jansel Valentin on 8/20/2015.
 *
 **/

@PackageScope
@CompileStatic
class CleanOperation implements CacheOperation {

    private static final Logger logger = Logger.getLogger(CleanOperation.class)

    private String docType;
    private String condition;
    private String field;
    private String value;

    @Override
    Object perform() throws OperationExecutionException{

        try {
            ElasticSearchConfig config = ElasticSearchConfig.load()
            if( null == config ){
                logger.warn( "No ElasticSearchConfig got.!" );
                return;
            }

            Client client = setupESClient(config);
            if (null == client) {
                logger.warn("Something bad happened, not elastic search get")
                return null;
            }

            logger.info( "Tying to make elastic operation over cacheType="+config.getCacheType()+", docType="+docType+", condition=["+condition+"], field="+field+",value="+value );

            BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must( QueryBuilders.termQuery("type",docType) )
                .must( QueryBuilders.termQuery(field,value) );

            SearchResponse search = client.prepareSearch( config.getTsoIndex() )
                    .setTypes(config.getCacheType())
                    .setQuery(query)
                    .setSize(1)
                    .execute()
                    .actionGet();

            if( 0< search.getHits().getTotalHits() ) {
                client.prepareDelete(config.getTsoIndex(), config.getCacheType(), search.getHits().getAt(0).id()).execute().actionGet();
            }else{
                logger.info( "No got result to delete.!" );
            }
        }catch ( Exception ex ){
            logger.error(ex)
            throw new OperationExecutionException(ex);
        }
        return null;
    }

    private Client setupESClient(ElasticSearchConfig config) {
        return SharedTransport.getSharedClient(config)
    }

    @Override
    void recycle() {

    }
}
