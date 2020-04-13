package com.lucidworks.indexing.api.fusion;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Fusion Blobs API allows to perform operation with files and other objects stored in Fusion blob storage.
 */
public interface Blobs {

  /**
   * Get list of all available blobs.
   * @return list of blob ids
   * @throws IOException if unable to reach Fusion blobs service
   */
  List<String> listBlobs() throws IOException;

  /**
   * Retrieve blob contents from Fusion blob storage.
   *
   * @param blobId id of blob object
   * @return InputStream with blob content
   * @throws IOException if unable to reach Fusion blobs service
   */
  InputStream getBlobContent(String blobId) throws IOException;
}
