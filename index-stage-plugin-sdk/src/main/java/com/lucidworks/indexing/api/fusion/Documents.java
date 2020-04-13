package com.lucidworks.indexing.api.fusion;

import com.lucidworks.indexing.api.Document;

public interface Documents {

  /**
   * Create new document.
   *
   * @return document instance
   */
  Document newDocument();

  /**
   * Create new document.
   *
   * @param id document id
   * @return document instance
   */
  Document newDocument(String id);
}
