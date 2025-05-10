package dev.langchain4j.store.embedding.chroma.v2;


public class ChromaTenantRequest {


    private final String name;


    /**
     * Currently, cosine distance is always used as the distance method for chroma implementation
     */
    ChromaTenantRequest(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

}
