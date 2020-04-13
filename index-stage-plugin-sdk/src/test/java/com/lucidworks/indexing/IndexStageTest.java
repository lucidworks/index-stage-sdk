package com.lucidworks.indexing;

import com.lucidworks.fusion.schema.SchemaAnnotations;
import com.lucidworks.indexing.api.Context;
import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.IndexStageBase;
import com.lucidworks.indexing.api.Stage;
import com.lucidworks.indexing.api.Types;
import com.lucidworks.indexing.api.fusion.Fusion;
import com.lucidworks.indexing.config.IndexStageConfig;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IndexStageTest {

  @Test
  public void testSampleIndexStage() {
    TestStageConfig config = mock(TestStageConfig.class);
    Fusion fusion = mock(Fusion.class);

    Document document = mock(Document.class);
    Document.Field<Object> field = mock(Document.Field.class, RETURNS_SELF);
    when(document.field(any())).thenReturn(field);

    Context context = mock(Context.class);

    String fieldName = "testField";
    List<String> values = Arrays.asList("test1", "test2");

    when(config.field()).thenReturn(fieldName);
    when(config.values()).thenReturn(values);

    TestStage stage = new TestStage();
    stage.init(config, fusion);

    stage.process(document, context);

    verify(document, times(1)).field(eq(fieldName));
    verify(field, times(1)).add(eq(values));
    verify(field, times(1)).type(eq(Types.STRING));
    verify(field, times(1)).multivalued();
  }

  @SchemaAnnotations.RootSchema(title = "Test", description = "Test stage.")
  static interface TestStageConfig extends IndexStageConfig {

    @SchemaAnnotations.Property
    @SchemaAnnotations.StringSchema
    String field();

    @SchemaAnnotations.Property
    @SchemaAnnotations.ArraySchema
    List<String> values();
  }

  @Stage(type = "test-stage", configClass = TestStageConfig.class)
  static class TestStage extends IndexStageBase<TestStageConfig> {

    @Override
    public Document process(Document document, Context context) {
      document.field(config.field()).add(config.values()).type(Types.STRING).multivalued();
      return document;
    }
  }
}
