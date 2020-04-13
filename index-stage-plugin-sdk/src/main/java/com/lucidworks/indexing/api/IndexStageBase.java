package com.lucidworks.indexing.api;

import com.lucidworks.indexing.api.fusion.Fusion;
import com.lucidworks.indexing.config.IndexStageConfig;

/**
 * Convenient base class for index pipeline stage containing default initialization logic.
 *
 * Extend this class to implement custom index pipeline stage:
 *
 * <pre>
 * {@literal @}Stage(type = "myStage", configClass = MyStageConfig.class)
 * public class MyStage extends IndexStageBase&lt;MyStageConfig&gt; {
 *
 *   {@literal @}Override
 *   public Document process(Document document, Context context) {
 *     // get configuration
 *     String myBlobId = config.myBlobdId();
 *
 *     // call Fusion API methods
 *     fusion.blobs().getBlobContent(myBlobId);
 *
 *     // further document processing logic
 *   }
 * }
 * </pre>
 *
 * @param <T> index stage configuration class
 */
public abstract class IndexStageBase<T extends IndexStageConfig> implements IndexStage<T> {

  protected Fusion fusion;
  protected T config;

  public Document newDocument() {
    return fusion.documents().newDocument();
  }

  public Document newDocument(String id) {
    return fusion.documents().newDocument(id);
  }

  @Override
  public void init(T config, Fusion fusion) {
    this.config = config;
    this.fusion = fusion;
  }
}
