package com.lucidworks.indexing.api;

/**
 * Field type hints.
 */
public enum Types {

  STRING("type_string"),
  INTEGER("type_integer"),
  LONG("type_long"),
  FLOAT("type_float"),
  DOUBLE("type_double"),
  BOOLEAN("type_boolean"),
  DATE("type_date"),
  TRIE("type_trie"),
  BINARY("type_binary"),
  LOCATION("type_location");

  private final String hint;

  Types(String hint) {
    this.hint = hint;
  }

  public String getHint() {
    return hint;
  }
}
