package com.lucidworks.sample.simple;

import com.lucidworks.indexing.config.IndexStageConfig;

import static com.lucidworks.fusion.schema.SchemaAnnotations.Property;
import static com.lucidworks.fusion.schema.SchemaAnnotations.RootSchema;
import static com.lucidworks.fusion.schema.SchemaAnnotations.StringSchema;

@RootSchema(
    title = "Simple",
    description = "Simple Index Stage"
)
public interface SimpleStageConfig extends IndexStageConfig {

  @Property(
      title = "Field",
      description = "Name of field to add to document.",
      required = true
  )
  @StringSchema(minLength = 1)
  String newField();

  @Property(
      title = "Text",
      description = "Sample text to put into the field."
  )
  @StringSchema
  String text();
}
