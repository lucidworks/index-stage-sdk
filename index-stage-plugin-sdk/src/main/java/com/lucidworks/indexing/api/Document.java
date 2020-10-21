package com.lucidworks.indexing.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * Fusion document. {@link Document} instance is an input of an index pipeline stage. One or more {@link Document}
 * instances are emitted by an index pipeline as the result of processing.
 */
public interface Document {

  /**
   * Get document unique identifier.
   *
   * @return Identifier assigned to the document or null if identifier hasn't been assigned yet.
   */
  String getId();

  /**
   * Get document fields as map.
   *
   * @return Map of fields
   */
  Map<String, List<Object>> getFieldsAsMap();

  /**
   * Get all field values.
   *
   * @param fieldName Name of the field
   * @return List of assigned values
   */
  List<Object> getFieldValues(String fieldName);

  /**
   * Sets list of values to the field, removing any previously existing values.
   *
   * @param fieldName Name of the field
   * @param values List of values to set
   */
  void setFieldValues(String fieldName, List<?> values);

  /**
   * Add new value to the list of existing field values.
   *
   * @param fieldName Name of the field
   * @param value Value to add
   */
  void addFieldValue(String fieldName, Object value);

  /**
   * Mark document for deletion. Calling this method will instruct Fusion to remove current document from index.
   */
  void delete();

  /**
   * Check if document is marked for deletion.
   *
   * @return <code>true</code> if document was marked for deletion by the preceding pipeline stages.
   */
  boolean isDelete();

  /**
   * Get or create document field by name.
   *
   * @param fieldName Name of the field
   * @return Document field
   */
  Field<Object> field(String fieldName);

  /**
   * Get or create document of specific type field by name.
   *
   * @param fieldName Name of the field
   * @param fieldClass Type of the field
   * @param <T> The type of field
   * @return Document field
   */
  <T> Field<T> field(String fieldName, Class<T> fieldClass);

  /**
   * Get all fields document contains, excluding those that are reserved for internal use
   *
   * @return Set of document fields
   */
  Set<Field<Object>> fields();

  /**
   * Get all fields document contains, including those that are reserved for internal use.
   *
   * @return Set of document fields
   */
  Set<Field<Object>> allFields();

  /**
   * Representation of single field in document. Fields can be multivalued
   *
   * @param <T> Type of field values
   */
  interface Field<T> {

    /**
     * Get field name.
     *
     * @return Field name
     */
    String getName();

    /**
     * Set new field values removing any previously existing values.
     *
     * @param values Collection of values to set
     * @return This field, with modified list of values
     */
    Field<T> set(Collection<T> values);

    /**
     * Add more values to the field.
     *
     * @param values List of values to add
     * @return This field, with modified list of values
     */
    Field<T> add(Collection<T> values);

    /**
     * Get all field values as immutable collection.
     *
     * @return Field values
     */
    Collection<T> get();

    /**
     * Set new field value removing any previously existing values.
     *
     * @param value Value to set
     * @return This field, with modified list of values
     */
    Field<T> set(T value);

    /**
     * Add value to the field preserving any previously existing values.
     *
     * @param value Value to add
     * @return This field, with modified list of values
     */
    Field<T> add(T value);

    /**
     * Set type hint. This will help Fusion determine Solr field type when saving document to collection.
     *
     * @param type Value to be set on type hint
     * @return This field
     */
    Field<T> type(Types type);

    /**
     * Set atomic update modifier hint. This will tell Fusion what atomic update operation to apply to the field, if any.
     *
     * @param operation Operation modifier value to be set for the field
     * @return This field
     */
    Field<T> operation(Operations operation);

    /**
     * Set multivalued field hint. This will tell Fusion to use multivalued Solr field type for this field
     * when saving document to collection.
     *
     * @return This field
     */
    Field<T> multivalued();

    /**
     * Get first value from the field. This is useful for doing manipulations with single valued fields.
     *
     * @return First value from the field.
     */
    T getFirst();

    /**
     * Apply mapping function to each field value.
     *
     * @param mapper Mapping function
     * @return This field
     */
    Field<T> map(UnaryOperator<T> mapper);

    /**
     * Add hint value to the field.
     *
     * @param hint Hint value
     * @return This field
     */
    Field<T> hint(String... hint);

    /**
     * Get all hints for this field.
     *
     * @return Field hints
     */
    Set<String> getHints();
  }
}
