MessagePack Grails Plugin README
======================

This plugin allows you to expose your service class in the Grails application via MessagePack (<http://msgpack.org/>) RPC.

Note
---------------

The current version is still under experimental development.
Do not use this plugin in a production environment.

What Is MessagePack?
--------------

Visit the MessagePack website <http://msgpack.org> for details.

> MessagePack is a binary-based efficient object serialization library. Like JSON, it enables the exchange of structured objects among many languages. But unlike JSON, it is very fast and small.

Goal
---------------

The goal of this plugin is to enable all of Grailsﾕ service classes to be exposed as MessagePack RPC with minimal configuration.
If you have designed your service class to be exposed as a web service, rpc, a kind of RESTful or SOAP, you can expose it as MessagePack RPC as it is.

Dependencies
---------------

This plugin is based on msgpack-0.5.2-devel and msgpack-rpc-0.6.1-devel (<http://msgpack.org/>).

Build
---------------

As this plugin has not been deployed to the plugin repository, you need to build the plugin by yourself at first to use it.
To build the plugin package, just type:

    grails package-plugin

Installation
---------------

Type this:

    grails install-plugin /path/to/grails-msgpack-0.1.zip

Alternatively, if you checked out the plugin sources, just add the following line to your grails-app/config/BuildConfig.groovy

    grails.plugin.location.msgpack = "/path/to/grails-msgpack"

Getting Started
---------------

Like any other 'service exposure' plugin, you can declare the static property "expose" with a 'msgpack' value like:

    static expose=['msgpack']

Then all the methods of your service class will be exposed as MessagePack RPC.

The plugin evaluates each field option on the basis of the constraints property in Grails Domain, which the method of the service class returns.
For example, if the domain has 'nullable' constraint for the 'name' field, then the 'name' field will be treated as optional like with the 'Optionl' annotation.

Configuration
----------------

You can set properties for this plugin in grails-app/config/Config.groovy like this:

    msgpack{
        rpc.expose = true
        rpc.port = 1985
    }

Here is a list of the supported properties:

  - rpc.expose - if true, the plugin will expose your service class. (default: false)
  - rpc.port - The port on which the messagepack rpc will be available. (default: 1985)

Demo
----------------

You can checkout a demo application and sample client from the following repository.

demo: <https://github.com/ohneda/grails-msgpack-demo>

democlient: <https://github.com/ohneda/grails-msgpack-demo-client>

Known Issues
---------------

  - **Does not support a cyclic reference in domain classes** except the 'belongsTo' relationship. If you need a bidirectional relationship, you should use the 'belongsTo' property in domain.
  - MessagePack RPC server doesn't restart in a development environment. You should stop your application first and start it again when you need some modifications during development.
  - Just one service class is allowed to be exposed for now because MessagePack RPC doesn't support namespace. If you defined the expose property with 'msgpack' value in more than one service classes, it will be ignored.
  - Does not support 'errors' property in domain classes.If a domain class has 'errors' property, it will be ignored.

These issues will be fixed as soon as possible.

Roadmap
----------------

  - Generate MessagePack IDL automatically
  - Support msgpack-java-0.6
  - MessagePack RPC functional enhancement 

