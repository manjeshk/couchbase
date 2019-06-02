package com.manjesh.couchbase;

import com.manjesh.couchbase.document.Document;
import com.manjesh.couchbase.exception.ClientException;

import java.util.List;

public interface CouchbaseTemplate {
    void save(Document envelope) throws ClientException;

    void saveOrUpdate(Document document) throws ClientException;

    void replace(Document document) throws ClientException;

    void delete(Document document) throws ClientException;

    String retrieve(Document document) throws ClientException;

    List<String> search(String query) throws ClientException;

    List<String> parameterizedSearch(String query, Object ... parameters) throws ClientException;

}
