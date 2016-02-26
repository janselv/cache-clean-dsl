package com.jvra.ocache.test.scripts

//clean "app-count",{ where "appId", equal: "25" }

elasticsearch{
    clean "app-count" where "appId" iss equal to "25"
}



