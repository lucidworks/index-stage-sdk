package com.lucidworks.indexing.api;

import java.util.Map;

/**
 * Index pipeline context represented as Java {@link java.util.Map}.
 * Context properties can be retrieved by calling {@link java.util.Map#get(Object)},
 * and set by calling {@link java.util.Map#put(Object, Object)}.
 */
public interface Context extends Map<String, Object> {

  /**
   * Get name of the collection used by current pipeline.
   *
   * @return collection linked to current index pipeline
   */
  String getCollection();

}
