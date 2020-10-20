package com.lucidworks.indexing.sdk.test;

import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class TestDocument implements Document {

  private String id;
  private Map<String, Field> fields = new HashMap<>();

  private boolean delete = false;

  public TestDocument(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Map<String, List<Object>> getFieldsAsMap() {
    return fields.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<Object>(e.getValue().get())));
  }

  @Override
  public List<Object> getFieldValues(String fieldName) {
    return new ArrayList<>(fields.getOrDefault(fieldName, new TestField<>(fieldName)).get());
  }

  @Override
  public void setFieldValues(String fieldName, List<?> values) {
    fields.computeIfAbsent(fieldName, TestField::new).set(values);
  }

  @Override
  public void addFieldValue(String fieldName, Object value) {
    fields.computeIfAbsent(fieldName, TestField::new).add(value);
  }

  @Override
  public void delete() {
    this.delete = true;
  }

  @Override
  public boolean isDelete() {
    return delete;
  }

  @Override
  public Field<Object> field(String fieldName) {
    return fields.computeIfAbsent(fieldName, TestField::new);
  }

  @Override
  public <T> Field<T> field(String fieldName, Class<T> fieldClass) {
    return fields.computeIfAbsent(fieldName, TestField::new);
  }

  @Override
  public Set<Field<Object>> fields() {
    return new HashSet(fields.entrySet().stream()
        .filter(e -> !e.getKey().startsWith("_lw_"))
        .map(Map.Entry::getValue)
        .collect(Collectors.toSet()));
  }

  @Override
  public Set<Field<Object>> allFields() {
    return new HashSet(fields.values());
  }

  static class TestField<T> implements Field<T> {

    private String name;
    private List<T> values = new ArrayList<>();
    private Set<String> hints = new HashSet<>();
    private boolean multivalued = false;

    public TestField(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public Field<T> set(Collection<T> values) {
      this.values = new ArrayList<>(values);
      return this;
    }

    @Override
    public Field<T> add(Collection<T> values) {
      values.addAll(values);
      return this;
    }

    @Override
    public Collection<T> get() {
      return values;
    }

    @Override
    public Field<T> set(T value) {
      List<T> newValues = new ArrayList<>();
      newValues.add(value);
      this.values = newValues;

      return this;
    }

    @Override
    public Field<T> add(T value) {
      values.add(value);
      return this;
    }

    @Override
    public Field<T> type(Types type) {
      hints.add(type.getHint());
      return this;
    }

    @Override
    public Field<T> multivalued() {
      this.multivalued = true;
      return this;
    }

    @Override
    public T getFirst() {
      return values.isEmpty() ? null : values.get(0);
    }

    @Override
    public Field<T> map(UnaryOperator<T> mapper) {
      this.values = values.stream().map(mapper).collect(Collectors.toList());
      return this;
    }

    @Override
    public Field<T> hint(String... hint) {
      hints.addAll(Arrays.asList(hint));
      return this;
    }

    @Override
    public Set<String> getHints() {
      return hints;
    }

    public boolean isMultivalued() {
      return multivalued;
    }
  }
}
