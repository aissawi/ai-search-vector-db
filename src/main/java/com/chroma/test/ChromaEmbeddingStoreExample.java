package com.chroma.test;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.v2.ChromaEmbeddingStore;

import java.util.ArrayList;
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

            addEmbedding("F:\\ai-search\\chromadb\\chromadb\\src\\main\\java\\",embeddingModel,embeddingStore);





        Embedding queryEmbedding = embeddingModel.embed("maestro Lothar Matthäus?").content();

            EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                    .queryEmbedding(queryEmbedding)
                    .maxResults(1)
                    .build();
            List<EmbeddingMatch<TextSegment>> matches = embeddingStore.search(embeddingSearchRequest).matches();

            for(final EmbeddingMatch<TextSegment> embeddingMatch : matches) {
                System.out.println(embeddingMatch.score()); // 0.8144288493114709

                System.out.println(embeddingMatch.embedded().text()); // 0.8144288493114709
                 // I like football.

            }






    }

    private  static void addEmbedding(final String directoryPath,final EmbeddingModel embeddingModel,final EmbeddingStore<TextSegment> embeddingStore) {
        final List<Document> documentList=new DocumentLoaderExamples().loadMultipleDocuments(directoryPath);

        documentList.forEach(document -> {
            final TextSegment segment=document.toTextSegment();

            System.out.println(segment.metadata());

            Embedding embedding1 = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding1,segment);
        });

    }
}
