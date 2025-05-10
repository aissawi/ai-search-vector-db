
# 🔍 AI-Powered Semantic Search Engine

A cross-language semantic search engine built with **Python** and **Java**, leveraging **vector databases** to deliver context-aware search over unstructured content such as documents, articles, or user queries.

## 🧠 Overview

This project demonstrates a modular AI Search system where:

- Python handles **text embedding** and vector storage.
- Java (Spring Boot) serves **REST APIs** for querying and result retrieval.
- A vector database (e.g., **Pinecone**, **Milvus**, **Weaviate**, or **Qdrant**) stores high-dimensional embeddings.

## 📐 Architecture

```
+--------------+        +-----------------+        +-------------------+
|  User / UI   +------->+  Java Spring API+------->+  Vector Database  |
+--------------+        +-----------------+        +-------------------+
                              ^   |
                              |   v
                  +---------------------------+
                  | Python Embedding Pipeline |
                  | (HuggingFace / OpenAI     |
                  | + LangChain / Faiss)      |
                  +---------------------------+
```

## 🚀 Features

- 🧾 **Document Ingestion** with metadata tagging
- 🔢 **Text Embedding** using Transformer models
- 📦 **Storage in Vector DB** with fast similarity search
- 🌐 **REST API in Java** for query handling
- 🖥️ **Optional UI** to demo search results (React or Thymeleaf)

## 🧰 Tech Stack

| Layer       | Technology                     |
|-------------|---------------------------------|
| Language    | Python, Java (Spring Boot)     |
| Embeddings  | Hugging Face, OpenAI, LangChain|
| DB          | Qdrant / Milvus / Pinecone     |
| API         | REST (Spring Boot)             |
| UI (optional)| React / Thymeleaf              |

## 📄 Folder Structure

```
/ai-search
│
├── /python-embedding/         # Embedding pipeline
│   ├── ingest.py
│   └── embedder.py
│
├── /java-api/                 # Spring Boot API
│   ├── src/main/java/
│   └── pom.xml
│
├── /ui/                       # Optional frontend
│   └── ...
│
├── .env                       # API keys & configs
├── README.md
```

## ⚙️ Setup Instructions

### 1. Clone the Repo

```bash
git clone https://github.com/yourusername/ai-semantic-search.git
cd ai-semantic-search
```

### 2. Python (Embedding Pipeline)

```bash
cd python-embedding
pip install -r requirements.txt
python ingest.py --source=data/
```

### 3. Java (Search API)

```bash
cd java-api
./mvnw spring-boot:run
```

### 4. (Optional) UI

```bash
cd ui
npm install && npm start
```

## 🧪 Sample API Request

```bash
curl -X POST http://localhost:8080/search \
     -H "Content-Type: application/json" \
     -d '{"query": "How to handle customer complaints?"}'
```

## 📝 To-Do

- [ ] Support multi-language content
- [ ] Add OpenAI / Azure embedding fallback
- [ ] Deploy using Docker & Kubernetes

## 🙌 Acknowledgements

- [LangChain](https://github.com/langchain-ai/langchain)
- [Milvus](https://milvus.io/)
- [Qdrant](https://qdrant.tech/)
- [HuggingFace Transformers](https://huggingface.co/transformers/)
