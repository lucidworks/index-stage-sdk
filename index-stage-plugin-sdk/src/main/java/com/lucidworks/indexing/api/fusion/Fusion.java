package com.lucidworks.indexing.api.fusion;

import com.lucidworks.indexing.api.Document;

/**
 * Fusion APIs available to use from a index pipeline stage.
 */
public interface Fusion {

  /**
   * REST call API for making generic HTTP calls to Fusion services.
   *
   * @param responseType expected response type
   * @param <T> expected response type
   * @return REST call API instance
   */
  <T> RestCall<T> restCall(Class<T> responseType);

  /**
   * Fusion Blobs API for operations with the blob objects stored in Fusion.
   *
   * @return Blobs API instance
   */
  Blobs blobs();

  /**
   * Fusion Documents API for operation with {@link Document} objects.
   *
   * @return Documents API instance
   */
  Documents documents();
}
