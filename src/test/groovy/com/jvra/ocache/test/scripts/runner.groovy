package com.jvra.ocache.test.scripts
//
//
//b = new Binding()
//b.clean = { type, closure ->
//    println "working over ${type}"
//    closure()
//}
//
//b.where = { conditions, param ->
//    println "${param} == ${conditions.equal}"
//}
//
//
//
//shell = new GroovyShell(b)
//shell.evaluate( new File( "tso-dsl.groovy" ) )


class Executor {
    private Script script;

    String what, field, condition, value;

    def equal = {
        "="
    }

    def clean(what) {
        [where: { field ->
            [iss: { condition ->
                [to: { value ->
//                    return "clean from ${what} where ${field} ${condition()} ${value}"
                    this.what = what;
                    this.field = field
                    this.condition = condition()
                    this.value = value
                }]
            }]
        }]
    }


    Object perform() {

        def binding = new Binding()
        binding.elasticsearch = { closure ->
            closure.delegate = this
            closure()
        }

        script.binding = binding;
        script.run()
    }


    @Override
    public String toString() {
        return "clean from ${what} where ${field} ${condition} ${value}"
    }
}


GroovyShell shell = new GroovyShell()
Script script = shell.parse( """
elasticsearch{
    clean "app-count" where "appId" iss equal to "25"
}
"""
)

ex = new Executor( script:script )
ex.perform()

println ex


