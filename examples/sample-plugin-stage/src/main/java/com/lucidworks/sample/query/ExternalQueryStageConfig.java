package com.lucidworks.sample.query;

import com.lucidworks.fusion.schema.SchemaAnnotations.ArraySchema;
import com.lucidworks.fusion.schema.SchemaAnnotations.Property;
import com.lucidworks.fusion.schema.SchemaAnnotations.RootSchema;
import com.lucidworks.fusion.schema.SchemaAnnotations.StringSchema;
import com.lucidworks.indexing.config.IndexStageConfig;

import java.util.List;

@RootSchema(
    title = "External collection query",
    description = "Adds fields to the document from external Fusion collection."
)
public interface ExternalQueryStageConfig extends IndexStageConfig {

  @Property(
      title = "Collection",
      description = "Name of external collection.",
      required = true
  )
  @StringSchema
  String collection();

  @Property(
      title = "Query pipeline",
      description = "Name of query pipeline.",
      required = true
  )
  @StringSchema
  String queryPipeline();

  @Property(
      title = "From ID field",
      description = "From ID field to use for look up."
  )
  @StringSchema
  String fromField();

  @Property(
      title = "To ID field",
      description = "To ID field to use for look up."
  )
  @StringSchema
  String toField();

  @Property(
      title = "External fields",
      description = "External fields to include"
  )
  @ArraySchema
  List<String> externalFields();
}
