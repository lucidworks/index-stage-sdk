package com.lucidworks.indexing.api;

import com.lucidworks.indexing.api.fusion.Fusion;
import com.lucidworks.indexing.config.IndexStageConfig;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Fusion index pipeline stage. Custom index stages must implement this interface to be discovered and called
 * by Fusion.
 *
 * Example of custom index stage class:
 * <pre>
 * {@literal @}Stage(type = "myStage", configClass = MyStageConfig.class)
 * public class MyStage implements IndexStage&lt;MyStageConfig&gt; {
 *
 *   {@literal @}Override
 *   public void init(MyStageConfig config, Fusion fusion) {
 *     // stage initialization logic
 *   }
 *
 *   {@literal @}Override
 *   public Document process(Document document, Context context) {
 *     // document processing logic
 *   }
 * </pre>
 *
 * Implementations of this class must be stateless. Fusion can create and use multiple index stage instances
 * at the same time.
 *
 * @param <T> index stage configuration class
 */
public interface IndexStage<T extends IndexStageConfig> {

  /**
   * Stage initialization callback. This method will be called by Fusion when index stage instance is created
   * and before 'process' method is called.
   *
   * Stage configuration will be passed by Fusion as {@link IndexStageConfig} instance. Additionally {@link Fusion}
   * interface instance will be passed to allow calling Fusion API from the index stage.
   *
   * @param config index pipeline stage configuration
   * @param fusion Fusion API instance
   */
  void init(T config, Fusion fusion);

  /**
   * Process single document. This method is called for each document passing through index pipeline.
   *
   * Implement this method to perform processing of single {@link Document} instance that results in either 1 or 0
   * documents being emitted. Return null to drop document from the pipeline.
   *
   * This method is a convenience and will not be called if {@link #process(Document, Context, Consumer)}
   * implementation is overridden. Default implementation is NOOP if not implemented.
   *
   * Note that this method implementation must be thread-safe as it can be invoked concurrently by multiple threads.
   *
   * @param document document going through index pipeline
   * @param context index pipeline context
   * @return processed document or null to drop document from the pipeline
   */
  default Document process(Document document, Context context) {
    return document;
  }

  /**
   * Process single document. This method is called for each document passing through index pipeline.
   *
   * Implement this method to perform processing of single {@link Document} instance that results in arbitrary
   * number of documents being emitted. Call <code>output.accept(document)</code> for each document you want to
   * emit as a result of processing. Note that after sending a document instance to the output, its state
   * may be changed by subsequent stages, therefore it is strongly advised to discard the document instance
   * immediately after emitting it. Passing <code>null</code> to <code>output</code> consumer will cause
   * {@link IllegalArgumentException}.
   *
   * Overriding the default implementation of this method will result in {@link #process(Document, Context)} to
   * not be called. Default implementation is to call {@link #process(Document, Context)} method.
   *
   * Note that this method implementation must be thread-safe as it can be invoked concurrently by multiple threads.
   *
   * @param document document going through index pipeline
   * @param context index pipeline context
   * @param output consumer for documents emitted as the result of processing
   */
  default void process(Document document, Context context, Consumer<Document> output) {
    Optional.ofNullable(process(document, context)).ifPresent(output);
  }
}
