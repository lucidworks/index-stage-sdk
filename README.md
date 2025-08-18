# Index Stage SDK

This public Github repository provides resources to help developers build their own Fusion Index stage plugins. 
The resources include documentation and getting started guides, as well as example plugin implementations.

* [index-stage-plugin-sdk](index-stage-plugin-sdk/README.md) - Index Stage SDK library. It contains 
  classes and interfaces necessary for building new plugins. Documentation can be found both in README and javadocs
* [examples](examples/README.md) - contains example plugin implementations and information about custom plugin 
  development

# Fusion Version

The Index Stage SDK is available for use starting in Fusion 5.1


# Compatibility matrix

Both, Fusion and the implemented plugin need to use the same version of the Index Stage SDK.

The following table shows the compatibility matrix for the Index Stage SDK and Fusion versions:


| Fusion Version     | Index Stage SDK Version | Compile plugin with JDK Version |
|--------------------|-------------------------|---------------------------------|
| 5.4  -  5.9.14     | 1.2.0                   | 8                               |
| 5.9.15             | 2.0.0                   | 11                              |


Make sure to use the correct version of the Index Stage SDK and JDK when building your plugin to ensure compatibility with the Fusion version you are using.