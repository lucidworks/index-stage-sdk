package com.lucidworks.sample.query;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Wrapper for reading values from nested map structures that for each key can contain either a value or another
 * nested map structure. Provides a convenient way to read data from Fusion/Solr response.
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class NestedMap<T> {

  private Map<String, T> map;

  /**
   * Wrap {@link Map} instance into a {@link NestedMap}.
   *
   * @param map java map instance
   * @param <T> map value type
   * @return wrapped map
   */
  public static <T> NestedMap<T> from(Map<String, T> map) {
    return new NestedMap<>(map);
  }

  /**
   * Wrap raw {@link Map} instance into a {@link NestedMap}.
   *
   * @param map java map instance
   * @return wrapped map
   */
  public static NestedMap<Object> fromRaw(Map map) {
    return new NestedMap<>((Map<String, Object>) map);
  }

  private NestedMap(Map<String, T> map) {
    this.map = map;
  }

  /**
   * Get underlying java {@link Map} instance.
   *
   * @return java map instance
   */
  public Map<String, T> toMap() {
    return map;
  }

  /**
   * Get nested map value by key.
   *
   * @param key map key
   * @return nested map value
   */
  public NestedMap<T> getMap(String key) {
    T value = map.get(key);

    if (value == null) {
      return null;
    }

    if (value instanceof Map) {
      return from((Map<String, T>) value);
    }

    throw new IllegalStateException(String.format("Value for '%s' is not a map.", key));
  }

  /**
   * Get value by nested key sequence.
   *
   * E.g. for the following nested map structure:
   * ["l1_1" :
   *  ["l2_1" :
   *    ["l3_1" : "value_1" ]
   *  ],
   *  ["l2_2" :
   *    ["l3_1" : "value_2" ]
   *  ]
   * ]
   *
   * get(String.class, "l1_1", "l2_1", "l3_1") => "value_1"
   * get(String.class, "l1_1", "l2_2", "l3_1") => "value_2"
   *
   * @param type value type class
   * @param key nested key sequence
   * @param <R> value type
   * @return nested value
   */
  public <R> R get(Class<R> type, String... key) {
    return type.cast(get(key));
  }

  /**
   * Get list of nested maps by nested key sequence.
   *
   * @param key nested key sequence
   * @return nested list value
   */
  public List<NestedMap<T>> getNestedList(String... key) {
    List list = get(List.class, key);

    return new AbstractList<NestedMap<T>>() {
      @Override
      public NestedMap<T> get(int index) {
        return NestedMap.from((Map<String, T>) list.get(index));
      }

      @Override
      public int size() {
        return list.size();
      }
    };
  }

  /**
   * Get value by nested key sequence.
   *
   * E.g. for the following nested map structure:
   * ["l1_1" :
   *  ["l2_1" :
   *    ["l3_1" : "value_1" ]
   *  ],
   *  ["l2_2" :
   *    ["l3_1" : "value_2" ]
   *  ]
   * ]
   *
   * get("l1_1", "l2_1", "l3_1") => "value_1"
   * get("l1_1", "l2_2", "l3_1") => "value_2"
   *
   * @param key nested key sequence
   * @return nested value
   */
  public T get(String... key) {
    Map<String, T> innerMap = map;
    for (int i = 0; i < key.length - 1; i++) {
      Object value = innerMap.get(key[i]);

      if (value == null) {
        return null;
      }

      if (!(value instanceof Map)) {
        throw new IllegalArgumentException("Invalid path. All keys except last must contain maps.");
      }

      innerMap = (Map<String, T>) value;
    }

    return innerMap.get(key[key.length - 1]);
  }

  /**
   * Get {@link Optional} wrapped value by nested key sequence.
   *
   * @param key nested key sequence
   * @return value
   */
  public Optional<T> with(String... key) {
    return Optional.ofNullable(get(key));
  }

  /**
   * Get {@link Optional} wrapped value by nested key sequence.
   *
   * @param type value type class
   * @param key nested key sequence
   * @param <R> value type
   * @return value
   */
  public <R> Optional<R> with(Class<R> type, String... key) {
    return Optional.ofNullable(get(type, key));
  }

  /**
   * Get {@link Optional} wrapped list of nested maps by nested key sequence.
   *
   * @param key nested key sequence
   * @return list
   */
  public Optional<List<NestedMap<T>>> withNestedList(String... key) {
    return Optional.ofNullable(getNestedList(key));
  }
}
