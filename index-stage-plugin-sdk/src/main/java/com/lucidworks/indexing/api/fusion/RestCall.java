package com.lucidworks.indexing.api.fusion;

import java.io.InputStream;
import java.util.Map;

/**
 * API for making generic REST calls to Fusion services.
 *
 * Example:
 * <pre>
 *   String response = fusion.restCall(String.class)
 *         .get("http://query/query-pipelines/my-pipeline/collections/my-collection/select")
 *         .param("q", "id:example_1")
 *         .execute();
 * </pre>
 *
 * @param <T> type of the response
 */
public interface RestCall<T> {

  /**
   * Perform GET call.
   *
   * @param serviceUrl service URL to make call to
   * @return REST call builder
   */
  RestCallBuilder<T> get(String serviceUrl);

  /**
   * Perform PUT call.
   *
   * @param serviceUrl service URL to make call to
   * @return REST call builder
   */
  RestCallBuilder<T> put(String serviceUrl);

  /**
   * Perform POST call.
   *
   * @param serviceUrl service URL to make call to
   * @return REST call builder
   */
  RestCallBuilder<T> post(String serviceUrl);

  /**
   * Perform DELETE call.
   *
   * @param serviceUrl service URL to make call to
   * @return REST call builder
   */
  RestCallBuilder<T> delete(String serviceUrl);

  interface RestCallBuilder<T> {

    /**
     * Add query parameter.
     *
     * @param name parameter name
     * @param value parameter value
     * @return REST call builder
     */
    RestCallBuilder<T> param(String name, String value);

    /**
     * Add pre-constructed map of query parameters.
     *
     * @param params map of query parameters
     * @return REST call builder
     */
    RestCallBuilder<T> params(Map<String, String> params);

    /**
     * Add HTTP request header.
     *
     * @param name header name
     * @param value header value
     * @return REST call builder
     */
    RestCallBuilder<T> header(String name, String value);

    /**
     * Add pre-constructed map of HTTP request headers.
     *
     * @param headers map of request headers
     * @return REST call builder
     */
    RestCallBuilder<T> headers(Map<String, String> headers);

    /**
     * Set stream request body.
     *
     * @param body InputStream request body
     * @return REST call builder
     */
    RestCallBuilder<T> body(InputStream body);

    /**
     * Set string request body.
     *
     * @param body string request body
     * @return REST call builder
     */
    RestCallBuilder<T> body(String body);

    /**
     * Execute request synchronously and receive response.
     *
     * @return request response
     */
    T execute();
  }
}
