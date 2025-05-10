package dev.langchain4j.store.embedding.chroma.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.langchain4j.internal.Utils;
import java.io.IOException;
import java.time.Duration;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

class ChromaClient {

    private final ChromaApi chromaApi;

    private ChromaClient(Builder builder) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .callTimeout(builder.timeout)
                .connectTimeout(builder.timeout)
                .readTimeout(builder.timeout)
                .writeTimeout(builder.timeout);

        if (builder.logRequests) {
            httpClientBuilder.addInterceptor(new ChromaRequestLoggingInterceptor());
        }
        if (builder.logResponses) {
            httpClientBuilder.addInterceptor(new ChromaResponseLoggingInterceptor());
        }

        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.ensureTrailingForwardSlash(builder.baseUrl))
                .client(httpClientBuilder.build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.chromaApi = retrofit.create(ChromaApi.class);
    }

    public static class Builder {

        private String baseUrl;
        private Duration timeout;
        private boolean logRequests;
        private boolean logResponses;

        private String tenantId;

        private String databaseId;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder tenantId(String logRequests) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder databaseId(String databaseId) {
            this.databaseId = databaseId;
            return this;
        }

        public Builder logRequests(boolean logRequests) {
            this.logRequests = logRequests;
            return this;
        }

        public Builder logResponses(boolean logResponses) {
            this.logResponses = logResponses;
            return this;
        }

        public ChromaClient build() {
            return new ChromaClient(this);
        }
    }

    Collection createCollection(final String tenantId,final String databaseId, CreateCollectionRequest createCollectionRequest) {
        try {
            Response<Collection> response =
                    chromaApi.createCollection(tenantId,databaseId,createCollectionRequest).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw toException(response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    Void createTenant(String tenantId) {
        try {
            Response<Void> response = chromaApi.createTenant(new ChromaTenantRequest(tenantId)).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // if collection is not present, Chroma returns: Status - 500
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Void createDatabase(final String tenantId,final String databaseId) {
        try {
            Response<Void> response = chromaApi.createDatabase(tenantId,new ChromaTenantRequest(databaseId)).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // if collection is not present, Chroma returns: Status - 500
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   Collection collection(final String tenantId,final String databaseId,String collectionName) {
        try {
            Response<Collection> response = chromaApi.collection(tenantId,databaseId,collectionName)
                                            .execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // if collection is not present, Chroma returns: Status - 500
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean addEmbeddings(String tenantId,String databaseId, String collectionId, AddEmbeddingsRequest addEmbeddingsRequest) {
        try {
            Response<Void> retrofitResponse =
                    chromaApi.addEmbeddings(tenantId,databaseId,collectionId, addEmbeddingsRequest).execute();
            if (retrofitResponse.isSuccessful()) {
                return true;
            } else {
                throw toException(retrofitResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    QueryResponse queryCollection(String tenantId,String databaseId, String collectionId, QueryRequest queryRequest) {
        try {
            Response<QueryResponse> retrofitResponse =
                    chromaApi.queryCollection(tenantId,databaseId,collectionId, queryRequest).execute();
            if (retrofitResponse.isSuccessful()) {
                return retrofitResponse.body();
            } else {
                throw toException(retrofitResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    void deleteCollection(String tenantId,String databaseId, String collectionId) {
        try {
            chromaApi.deleteCollection(tenantId,databaseId,collectionId).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static RuntimeException toException(Response<?> response) throws IOException {
        int code = response.code();
        String body = response.errorBody().string();

        String errorMessage = String.format("status code: %s; body: %s", code, body);
        return new RuntimeException(errorMessage);
    }
}
