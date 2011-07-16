MessagePack Grails Plugin README
======================

This plugin allow you to expose your service class in grails application via Message Pack (<http://msgpack.org/>) RPC. 

Current version is still under experimental development.

Goal
---------------

TODO

Dependencies
---------------

This plugin is based on msgpack-0.5.2-devel and msgpack-rpc-0.6.1-devel (<http://msgpack.org/>).

Build
---------------

    grails package-plugin

Installation
---------------

type this:

    grails install-plugin /path/to/grails-msgpack-0.1.zip

or if you checked out the plugin sources, just add the following line to grails-app/config/BuildConfig.groovy

    grails.plugin.location.msgpack = "/path/to/grails-msgpack"

Getting Started
---------------

You can expose your Grails service class with the declaration of the static property "expose" with 'msgpack' value like:

    static expose=['msgpack']

The plugin evaluates the field option on the basis of the constraints property in Grails Domain which the service class return.

Note
----------------

TODO

Known Issues
---------------

TODO
