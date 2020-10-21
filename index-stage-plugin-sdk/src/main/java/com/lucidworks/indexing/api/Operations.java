package com.lucidworks.indexing.api;

/**
 * Atomic update operation modifier hints.
 */
public enum Operations {

  SET("modifier_set"),   // Set or replace the field value(s) with the specified value(s), or remove the values if 'null' or empty list is specified as the new value
  ADD("modifier_add"),   // Adds the specified values to a multiValued field
  ADD_DISTINCT("modifier_add-distinct"), // Adds the specified values to a multiValued field, only if not already present
  REMOVE("modifier_remove"), //Removes (all occurrences of) the specified values from a multiValued field
  REMOVEREGEX("modifier_removeregex"), // Removes all occurrences of the specified regex from a multiValued field
  INC("modifier_inc"); // Increments a numeric value by a specific amount

  private final String hint;

  Operations(String hint) {
    this.hint = hint;
  }

  public String getHint() {
    return hint;
  }
}
