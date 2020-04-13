package com.lucidworks.sample.simple;

import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.sdk.test.IndexStageTestBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SimpleStageTest extends IndexStageTestBase<SimpleStageConfig> {

  @Test
  public void process() throws Exception {
    SimpleStageConfig stageConfig = newConfig(SimpleStageConfig.class, config -> {
      when(config.newField()).thenReturn("field");
      when(config.text()).thenReturn("value");
    });

    Document document = newDocument(null);

    SimpleStage stage = createStage(SimpleStage.class, stageConfig);
    stage.process(document, null);

    assertEquals("value", document.field("field").getFirst());
  }
}