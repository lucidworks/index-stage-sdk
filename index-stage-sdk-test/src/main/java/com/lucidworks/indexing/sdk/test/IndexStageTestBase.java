package com.lucidworks.indexing.sdk.test;

import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.IndexStage;
import com.lucidworks.indexing.api.fusion.Documents;
import com.lucidworks.indexing.api.fusion.Fusion;
import com.lucidworks.indexing.config.IndexStageConfig;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;

/**
 * Base class for index stage unit tests.
 * @param <C> index stage config class
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class IndexStageTestBase<C extends IndexStageConfig> {

  @Mock
  protected Fusion fusion;

  /**
   * Create index stage instance using provided config.
   *
   * @param stageClass index stage class
   * @param config index stage config instance
   * @param <S> index stage class
   * @return initialized index stage instance
   * @throws ReflectiveOperationException when unable to instantiate stage class using default no param constructor
   */
  public <S extends IndexStage<C>> S createStage(Class<S> stageClass, C config) throws ReflectiveOperationException {
    mockDocumentsInFusion();

    S stage = stageClass.getDeclaredConstructor().newInstance();
    stage.init(config, fusion);

    return stage;
  }

  private void mockDocumentsInFusion() {
    Documents documents = Mockito.mock(Documents.class);
    Mockito.when(documents.newDocument()).thenReturn(new TestDocument(null));
    Mockito.when(documents.newDocument(any())).then(invocation -> new TestDocument(invocation.getArgument(0)));
    Mockito.when(fusion.documents()).thenReturn(documents);
  }

  /**
   * Create new document without id.
   *
   * @return document instance
   */
  public Document newDocument() {
    return newDocument(null);
  }

  /**
   * Create new document with given id.
   *
   * @param id document id
   * @return document instance
   */
  public Document newDocument(String id) {
    return new TestDocument(id);
  }

  /**
   * Create new index stage config instance.
   *
   * @param configClass index stage config class
   * @return index stage config instance
   */
  public C newConfig(Class<C> configClass) {
    return newConfig(configClass, config -> {});
  }

  /**
   * Create new index stage config instance with mock setup callback.
   * The callback can be used to setup return values for the config mock, e.g.
   *
   * <pre>
   *   SimpleStageConfig stageConfig = newConfig(SimpleStageConfig.class, config -> {
   *     when(config.field()).thenReturn("field_name");
   *     when(config.text()).thenReturn("text_value");
   *   });
   * </pre>
   *
   * @param configClass index stage config class
   * @param mockConfiguration mock setup callback
   * @return index stage config instance
   */
  public C newConfig(Class<C> configClass, Consumer<C> mockConfiguration) {
    C config = Mockito.mock(configClass, Mockito.RETURNS_DEEP_STUBS);
    mockConfiguration.accept(config);
    return config;
  }
}
