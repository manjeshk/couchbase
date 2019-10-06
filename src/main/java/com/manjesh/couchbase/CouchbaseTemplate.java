package com.manjesh.couchbase;

import com.manjesh.couchbase.document.Document;
import com.manjesh.couchbase.exception.CouchbaseException;

import java.util.List;

public interface CouchbaseTemplate {
    void save(Document envelope) throws CouchbaseException;

    void saveOrUpdate(Document document) throws CouchbaseException;

    void replace(Document document) throws CouchbaseException;

    void delete(Document document) throws CouchbaseException;

    String retrieve(Document document) throws CouchbaseException;

    List<String> search(String query) throws CouchbaseException;

    List<String> parameterizedSearch(String query, Object ... parameters) throws CouchbaseException;

}
