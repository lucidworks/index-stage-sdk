package com.lucidworks.sample.copying;


import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.Types;
import com.lucidworks.indexing.api.fusion.Documents;
import com.lucidworks.indexing.sdk.test.IndexStageTestBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class DuplicateDocumentStageTest extends IndexStageTestBase<DuplicateDocumentConfig> {

  @Mock
  protected Documents documents;

  @Before
  public void before() {
    when(fusion.documents()).thenReturn(documents);
    when(documents.newDocument(anyString())).thenAnswer(invocation -> newDocument(invocation.getArgument(0)));
  }

  @Test
  public void process() throws Exception {
    DuplicateDocumentConfig stageConfig = newConfig(DuplicateDocumentConfig.class);

    Document document = newDocument("doc1");
    document.field("stringField").set("test string").type(Types.STRING);
    document.field("_lw_internalField").set("internal string").type(Types.STRING);

    List<Document> result = new ArrayList<>();

    DuplicateDocumentStage stage = createStage(DuplicateDocumentStage.class, stageConfig);
    stage.process(document, null, result::add);

    assertEquals(2, result.size());

    Document copy = result.get(0);
    assertEquals(2, copy.fields().size());
    assertEquals(3, copy.allFields().size());
    assertEquals("doc1-copy", copy.getId());
    assertEquals("test string", copy.field("stringField").getFirst());
    assertEquals("internal string", copy.field("_lw_internalField").getFirst());
    assertEquals(Boolean.TRUE, copy.field("is_copy").getFirst());

    Document original = result.get(1);
    assertEquals(2, original.fields().size());
    assertEquals(3, original.allFields().size());
    assertEquals("doc1", original.getId());
    assertEquals("test string", original.field("stringField").getFirst());
    assertEquals("internal string", original.field("_lw_internalField").getFirst());
    assertEquals(Boolean.FALSE, original.field("is_copy").getFirst());
  }
}