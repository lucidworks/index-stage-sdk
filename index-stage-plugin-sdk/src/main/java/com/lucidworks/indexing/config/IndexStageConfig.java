package com.lucidworks.indexing.config;

import com.lucidworks.fusion.schema.Model;

/**
 * Index pipeline stage configuration definition.
 *
 * Extend this interface to define configuration for a custom index stage.
 *
 * Example of index stage configuration:
 * <pre>
 * {@literal @}RootSchema(
 *     title = "Sample",
 *     description = "Sample Index Stage"
 * )
 * public interface MyStageConfig extends IndexStageConfig {
 *
 *   {@literal @}Property(
 *       title = "My String Property",
 *       description = "Example string property..."
 *   )
 *   {@literal @}StringSchema(defaultValue = "example string")
 *   String myStringProperty();
 *
 *   {@literal @}Property(
 *       title = "My Integer Property",
 *       description = "Example integer property...",
 *       required = true)
 *   {@literal @}NumberSchema(minimum = 1, maximum = 100)
 *   Integer myIntegerProperty();
 * }
 * </pre>
 */
public interface IndexStageConfig extends Model {
}
