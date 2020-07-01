package com.lucidworks.sample.copying;

import com.lucidworks.indexing.api.Context;
import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.IndexStageBase;
import com.lucidworks.indexing.api.Stage;

import java.util.function.Consumer;

@Stage(type = "copying", configClass = DuplicateDocumentConfig.class)
public class DuplicateDocumentStage extends IndexStageBase<DuplicateDocumentConfig> {

  private static final String COPY_MARK_FIELD_NAME = "is_copy";

  /**
   * Process incoming document.
   *
   * In this case we do not perform simple 1-in-1-out transformation, so we are overriding the
   * {@link #process(Document, Context, Consumer)} method.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(Document original, Context context, Consumer<Document> output) {
    // Create a copy of a document and push it
    Document copy = newDocument(original.getId() + "-copy");
    for (Document.Field originalField : original.allFields()) {
      copy.field(originalField.getName()).set(originalField.get());
    }
    copy.field(COPY_MARK_FIELD_NAME).set(Boolean.TRUE);
    output.accept(copy);

    // Modify and return the original document
    original.field(COPY_MARK_FIELD_NAME).set(Boolean.FALSE);
    output.accept(original);
  }


  @Override
  public Document process(Document document, Context context) {
    throw new UnsupportedOperationException("This method will not be invoked");
  }
}
