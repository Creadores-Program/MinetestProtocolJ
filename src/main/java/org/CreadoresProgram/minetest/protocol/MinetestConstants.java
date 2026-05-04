package org.CreadoresProgram.minetest.protocol;
/**
 * Contains various protocol constants.
 */
public class MinetestConstants {
  //supported network protocol range
  public static final int MIN_PROTOCOL_VERSION = 37;
  //max supported network protocol
  public static final int MAX_PROTOCOL_VERSION = 52;
  //majorVer
  public static final byte MAJOR_VERSION = 5;
  //minorVer
  public static final byte MINOR_VERSION = 15;
  //patchVer
  public static final byte PATCH_VERSION = 2;
  //version full String
  public static final String FULL_VERSION = MAJOR_VERSION + "." MINOR_VERSION + "." + PATCH_VERSION + "-Lua";
}
