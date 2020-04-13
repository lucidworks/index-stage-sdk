package com.lucidworks.sample.query;

import com.lucidworks.indexing.api.Context;
import com.lucidworks.indexing.api.Document;
import com.lucidworks.indexing.api.IndexStageBase;
import com.lucidworks.indexing.api.Stage;
import io.prometheus.client.Histogram;

import java.util.Map;

@Stage(type = "external-query", configClass = ExternalQueryStageConfig.class)
public class ExternalQueryStage extends IndexStageBase<ExternalQueryStageConfig> {

  private static final Histogram EXTERNAL_REQUEST_TIME = Histogram.build()
      .help("Time to execute external query request.")
      .name("external_query_request_time")
      .labelNames("request_url")
      .register();

  @Override
  public Document process(Document document, Context context) {
    String query = document.field(config.fromField(), String.class).getFirst();

    String requestUrl = String.format(
        "http://query/query-pipelines/%s/collections/%s/select", config.queryPipeline(), config.collection());

    Map resp;

    Histogram.Timer externalQueryTimer = EXTERNAL_REQUEST_TIME
        .labels(requestUrl)
        .startTimer();
    try {
      resp = fusion.restCall(Map.class)
          .get(String.format("http://query/query-pipelines/%s/collections/%s/select", config.queryPipeline(), config.collection()))
          .param("fq", String.format("%s:%s", config.toField(), query))
          .param("q", "*:*")
          .execute();
    } finally {
      externalQueryTimer.observeDuration();
    }

    NestedMap.fromRaw(resp).withNestedList("response", "docs").ifPresent(list -> {
      if (!list.isEmpty()) {
        NestedMap<Object> externalDocument = list.get(0);
        config.externalFields().forEach(
            field -> externalDocument.with(field).ifPresent(
                value -> document.field(field).add(value)));
      }
    });

    return document;
  }
}
