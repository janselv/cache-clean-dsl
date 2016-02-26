# ElasticSearch Cache DSL

Simple Clean operation DSL for docs based cache saved in ElasticSearch that expose a
human readable way to clean dirty cache docs in background.

This library is intended to work as [Gearman Job Server](http://gearman.org/) function, but you can use it as you
want. 



Installation:
----

Before compiling the code you might need to change ElasticSearch connection settings,
please modify do this via the utility class [SharedTransport]()

``` java

    gradle install
    
```



Usage:
-----

Once connected to your Gearman Server, call this function with the name you've registered it
passing the following as parameter. You can dig into the test, for more details.

``` java 

    elasticsearch{
        clean "<cache-doc>" where "<some-key>" iss equal to "<some-value>"
    }

```
