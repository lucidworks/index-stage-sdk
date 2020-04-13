package com.lucidworks.sample.simple;

import com.lucidworks.indexing.api.Context;
import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.IndexStageBase;
import com.lucidworks.indexing.api.Stage;
import com.lucidworks.indexing.api.Types;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stage(type = "simple", configClass = SimpleStageConfig.class)
public class SimpleStage extends IndexStageBase<SimpleStageConfig> {

  private static final Logger logger = LoggerFactory.getLogger(SimpleStage.class);

  @Override
  public Document process(Document document, Context context) {
    String text = StringUtils.trim(config.text());

    document.field(config.newField(), String.class).set(text).type(Types.STRING);

    logger.info("Sample Stage emitting document {} => {}", document.getId(), document.getFieldsAsMap());

    return document;
  }
}
