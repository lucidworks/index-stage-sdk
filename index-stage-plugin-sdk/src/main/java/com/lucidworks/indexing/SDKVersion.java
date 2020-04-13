package com.lucidworks.indexing;

public class SDKVersion {
  private static final String VERSION;

  static {
    Package packageClass = SDKVersion.class.getPackage();
    // Get the implementation version of Index Stage SDK
    VERSION = packageClass.getImplementationVersion();
  }

  public static String getVersion() {
    return VERSION;
  }
}
