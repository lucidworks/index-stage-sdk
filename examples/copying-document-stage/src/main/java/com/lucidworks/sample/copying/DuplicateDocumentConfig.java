package com.lucidworks.sample.copying;

import com.lucidworks.fusion.schema.SchemaAnnotations;
import com.lucidworks.indexing.config.IndexStageConfig;

/**
 * This is a minimal required configuration, no plugin-specific fields will be added
 */
@SchemaAnnotations.RootSchema(
    title = "Duplicate documents",
    description = "Index Stage for duplicating every document"
)
public interface DuplicateDocumentConfig extends IndexStageConfig {
}
