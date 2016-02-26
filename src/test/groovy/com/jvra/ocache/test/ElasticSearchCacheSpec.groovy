package com.jvra.ocache.test

import com.jvra.ocache.ElasticSearchCache
import com.jvra.ocache.es.CacheIdiom
import com.jvra.ocache.es.CacheIdiomProvider
import com.jvra.ocache.es.CacheOperation
import spock.lang.Specification

/**
 *
 * Created by Jansel Valentin on 8/20/2015.
 *
 **/
class ElasticSearchCacheSpec extends Specification{


    def "ElasticSearchCache deliver correctly the incoming action"(){ //before run this method extract "handleMessage from thread in ElasticSearchCache"

        given:

            BuildConfig.CACHE_FUNCTION_THREAD_ENABLED = false;

            def CacheIdiomProvider provider = Mock()
            def CacheIdiom idiom = Mock()
            def CacheOperation op = Mock()

            def ElasticSearchCache cache = new ElasticSearchCache();

            CacheProviders.registerDefault( provider )

        when: "Some message come to ElasticSearchCache instance"

            cache.work(null,message.getBytes(),null )

        then:
            assert null != CacheProviders.getDefault()

            1 * provider.createIdiom(*_) >> idiom
            1 * idiom.interpret(*_) >> op
            1 * op.perform()


        cleanup:

            BuildConfig.CACHE_FUNCTION_THREAD_ENABLED = true;

        where:
            message << [ """
                            elasticsearch{
                                clean "app-count" where "appId" iss equal to "25"
                            }
                        """ ]
    }


    def "ElasticSearchCache with default configuration respond to clean operation"(){

        given: "a valid ElasticSearchCAcne Instance"

            BuildConfig.CACHE_FUNCTION_THREAD_ENABLED = false

            def ElasticSearchCache cache = new ElasticSearchCache();

        when: "Some message come to ElasticSearchCache instance"

            cache.work(null,message.getBytes(),null )

        then:
            notThrown( OperationExecutionException )
        cleanup:

            BuildConfig.CACHE_FUNCTION_THREAD_ENABLED = true;

        where:
        message << [
                """
                            elasticsearch{
                                clean "app-count" where "appId" iss equal to "255"
                            }
                """,
                """
                            elasticsearch{
                                clean "app-count" where "appId" iss equal to "255"
                            }
                """,
                """
                            elasticsearch{
                                clean "app-count" where  "appId" iss equal to "255"
                            }
                """,
                """
                            elasticsearch{
                                clean "app-count" where "appId" iss equal to "552"
                            }
                """,
                """
                            elasticsearch{
                                clean "app-count" where "appId" iss equal to "552"
                            }
                """,
                """
                            elasticsearch{
                                clean "app-count" where "appId" iss equal to "255"
                            }
                """
        ]
    }

}
