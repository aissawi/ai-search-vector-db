package com.chroma.test;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.v2.AddEmbeddingsRequest;
import dev.langchain4j.store.embedding.chroma.v2.ChromaEmbeddingStore;

import java.util.List;

public class ChromaEmbeddingStoreExample {

    public static void main(String[] args) {

        String chromaBaseUrl = "http://localhost:8000"; // or your remote Chroma DB URL
        String collectionName = "collection1";

        String tenantId="tenant1";

        String databaseId="database1";

        // Create the Chroma client


            EmbeddingStore<TextSegment> embeddingStore = ChromaEmbeddingStore
                .builder()
                .baseUrl(chromaBaseUrl)
                .collectionName(collectionName)
                .logRequests(true)
                .logResponses(true)
                 .tenantId(tenantId)
                 .databaseId(databaseId)
                .build();

            EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

            TextSegment segment1 = TextSegment.from("I like football.");
            Embedding embedding1 = embeddingModel.embed(segment1).content();
            embeddingStore.add(embedding1, segment1);

        TextSegment segment3 = TextSegment.from("FC Barcelona Supports Messi");
        Embedding embedding3 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding3, segment3);

            TextSegment segment2 = TextSegment.from("The weather is good today.");
            Embedding embedding2 = embeddingModel.embed(segment2).content();
            embeddingStore.add(embedding2, segment2);

            Embedding queryEmbedding = embeddingModel.embed("FC Bayern Munich?").content();
            EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                    .queryEmbedding(queryEmbedding)
                    .maxResults(1)
                    .build();
            List<EmbeddingMatch<TextSegment>> matches = embeddingStore.search(embeddingSearchRequest).matches();
            EmbeddingMatch<TextSegment> embeddingMatch = matches.getFirst();

            System.out.println(embeddingMatch.score()); // 0.8144288493114709
            System.out.println(embeddingMatch.embedded().text()); // I like football.



    }
}
