package com.chroma.test;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;


import  static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.*;

public class DocumentLoaderExamples {

    private static final Logger log = LoggerFactory.getLogger(DocumentLoaderExamples.class);

    public static void main(String[] args) {
       /* loadSingleDocument("");
        loadMultipleDocuments();
        loadMultipleDocumentsWithGlob();
        loadMultipleDocumentsRecursively();
        loadUsingParserFromSPI();*/
    }

    public   Document loadSingleDocument(final String pdfFilePath) {
        Path documentPath = toPath(pdfFilePath);
        log.info("Loading single document: {}", documentPath);
        final ApacheTikaDocumentParser parser = new ApacheTikaDocumentParser(PDFParser::new,null,null,
                null,true);


        Document document = loadDocument(documentPath, parser);

        log(document);
        log.info("");
        return document;
    }

    public List<Document> loadMultipleDocuments(final String pdfFilePath) {
        Path directoryPath = toPath(pdfFilePath);
        log.info("Loading multiple documents from: {}", directoryPath);

        final ApacheTikaDocumentParser parser = new ApacheTikaDocumentParser(PDFParser::new,null,null,
                null,true);
        final List<Document> documents = loadDocuments(directoryPath, parser);

        documents.forEach(DocumentLoaderExamples::log);

        log.info("");

        return documents;
    }

    private static void loadMultipleDocumentsWithGlob() {
        Path directoryPath = toPath("example-files/");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        log.info("Loading *.txt documents from: {}", directoryPath);
        List<Document> documents = loadDocuments(directoryPath, pathMatcher, new ApacheTikaDocumentParser());
        documents.forEach(DocumentLoaderExamples::log);
        log.info("");
    }

    private static void loadMultipleDocumentsRecursively() {
        Path directoryPath = toPath("example-files/");
        log.info("Loading multiple documents recursively from: {}", directoryPath);
        List<Document> documents = loadDocumentsRecursively(directoryPath, new ApacheTikaDocumentParser());
        documents.forEach(DocumentLoaderExamples::log);
        log.info("");
    }

    private static void loadUsingParserFromSPI() {
        Path documentPath = toPath("example-files/story-about-happy-carrot.pdf");
        log.info("Loading using parser imported through SPI: {}", documentPath);
        Document document = loadDocument(documentPath); // we are not specifying a parser here, it is imported through SPI
        log(document);
        log.info("");
    }

    private static void log(Document document) {
        log.info("{}: {} ...", document.metadata().getString("file_name"), document.text().trim().substring(0, 50));
    }

    private static Path toPath(String fileName) {

        return Paths.get(fileName);
    }
}
