package com.jvra.ocache.test

import com.jvra.ocache.es.CacheIdiomProvider
import spock.lang.Specification

/**
 *
 * Created by Jansel Valentin on 08/21/15.
 *
 **/
class CacheProviderSpec extends Specification{
    def "Provider receive custom Cache Idiom"(){

        given: "Some mock provider"

            def CacheIdiomProvider provider = Mock()

        when:
            CacheProviders.registerDefault(provider)

        then:
            assert CacheProviders.getDefault() != null
    }



    def "Provider receive just one Cache Idiom"(){

        given: "Some mock provider"

        BuildConfig.ALLOW_MANY_PROVIDERS = false

            def CacheIdiomProvider p1 = Mock()
            def CacheIdiomProvider p2 = Mock()

        when:

            CacheProviders.registerDefault(p1)
            CacheProviders.registerDefault(p2)

        then:
            thrown( IllegalStateException )

        cleanup:

            BuildConfig.ALLOW_MANY_PROVIDERS = true;
    }



    def "Provider receive many debug Cache Idiom"(){

        given: "Some mock provider"

            def CacheIdiomProvider p1 = Mock()
            def CacheIdiomProvider p2 = Mock()

        when:

            CacheProviders.registerDefault(p1)
            CacheProviders.registerDefault(p2)

        then:

            notThrown( IllegalStateException)

    }
}
