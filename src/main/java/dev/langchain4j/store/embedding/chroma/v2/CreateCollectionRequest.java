package dev.langchain4j.store.embedding.chroma.v2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

class CreateCollectionRequest {

    private final String name;

    private final Map<String, Object> metadata;

    @JsonProperty("get_or_create")
    private final boolean getOrCreate;
    /**
     * Currently, cosine distance is always used as the distance method for chroma implementation
     */
    CreateCollectionRequest(String name) {
        this.name = name;
        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put("hnsw:space", "cosine");
        this.metadata = metadata;
        this.getOrCreate = true;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public boolean isGetOrCreate() {
        return getOrCreate;
    }
}
