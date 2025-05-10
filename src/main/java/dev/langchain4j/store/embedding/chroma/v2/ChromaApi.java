package dev.langchain4j.store.embedding.chroma.v2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface ChromaApi {




    @POST("api/v2/tenants")
    @Headers({"Content-Type: application/json"})
    Call<Void> createTenant(@Body ChromaTenantRequest createTenantRequest);


    @POST("api/v2/tenants/{tenant_id}/databases")
    @Headers({"Content-Type: application/json"})
    Call<Void> createDatabase(@Path("tenant_id") String tenantId,
                                    @Body ChromaTenantRequest createTenantRequest);

    @GET("api/v2/tenants/{tenant_id}/databases/{database_id}/collections/{collection_id}")
    @Headers({"Content-Type: application/json"})
    Call<Collection> collection(@Path("tenant_id") String tenantId
                                ,@Path("database_id") String databaseId
                                , @Path("collection_id") String collectionId);





    @POST("api/v2/tenants/{tenant_id}/databases/{database_id}/collections")
    @Headers({"Content-Type: application/json"})
    Call<Collection> createCollection(@Path("tenant_id") String tenantId
            ,@Path("database_id") String databaseId
            , @Body final CreateCollectionRequest createCollectionRequest);

    @POST("api/v2/tenants/{tenant_id}/databases/{database_id}/collections/{collection_id}/add")
    @Headers({"Content-Type: application/json"})
    Call<Void> addEmbeddings(
                                 @Path("tenant_id") String tenantId
                                ,@Path("database_id") String databaseId,
                                @Path("collection_id") String collectionId,

                                @Body AddEmbeddingsRequest embedding);

    @POST("api/v2/tenants/{tenant_id}/databases/{database_id}/collections/{collection_id}/query")
    @Headers({"Content-Type: application/json"})
    Call<QueryResponse> queryCollection( @Path("tenant_id") String tenantId
            ,@Path("database_id") String databaseId,
                                         @Path("collection_id") String collectionId, @Body QueryRequest queryRequest);


    @DELETE("api/v2/tenants/{tenant_id}/databases/{database_id}/collections/{collection_id}")
    @Headers({"Content-Type: application/json"})
    Call<Collection> deleteCollection(@Path("tenant_id") String tenantId
                                    ,@Path("database_id") String databaseId,
                                      @Path("collection_id") String collectionId);
}
