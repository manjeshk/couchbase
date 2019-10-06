package com.manjesh.couchbase.service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.transcoder.JsonTranscoder;
import com.manjesh.couchbase.CouchbaseTemplate;
import com.manjesh.couchbase.document.Document;
import com.manjesh.couchbase.exception.CouchbaseException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CouchbaseTemplateImpl implements CouchbaseTemplate {

    private Cluster cluster;
    private String bucketName;
    private String bucketPassword;

    public CouchbaseTemplateImpl(Cluster cluster, String bucketName, String bucketPassword) {
        this.cluster = cluster;
        this.bucketName = bucketName;
        this.bucketPassword = bucketPassword;
    }

    @Override
    public void save(Document document) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            JsonTranscoder jsonTranscoder = new JsonTranscoder();
            JsonObject jsonObject = jsonTranscoder.stringToJsonObject(document.content());
            JsonDocument jsonDocument = JsonDocument.create(document.id(), document.expiry(), jsonObject);
            bucket.insert(jsonDocument);
        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to save document due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }
    }

    @Override
    public void saveOrUpdate(Document document) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            JsonTranscoder jsonTranscoder = new JsonTranscoder();
            JsonObject jsonObject = jsonTranscoder.stringToJsonObject(document.content());
            JsonDocument jsonDocument = JsonDocument.create(document.id(), document.expiry(), jsonObject);
            bucket.upsert(jsonDocument);
        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to save/update document due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }
    }

    @Override
    public void replace(Document document) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            JsonTranscoder jsonTranscoder = new JsonTranscoder();
            JsonObject jsonObject = jsonTranscoder.stringToJsonObject(document.content());
            JsonDocument jsonDocument = JsonDocument.create(document.id(), document.expiry(), jsonObject);
            bucket.replace(jsonDocument);
        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to replace document due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }
    }

    @Override
    public void delete(Document document) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            bucket.remove(document.id());
        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to delete document for doc id: " + document.id() + " due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }
    }

    @Override
    public String retrieve(Document document) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            JsonObject jsonObject = bucket.get(document.id()).content();
            if (jsonObject != null)
                return jsonObject.toString();

        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to retrieve document for doc id: " + document.id() + " due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }
        return null;
    }

    @Override
    public List<String> search(String query) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            // Create a N1QL Primary Index (but ignore if it exists)
            bucket.bucketManager().createN1qlPrimaryIndex(true, false);

            // Perform a N1QL Query
            N1qlQueryResult result = bucket.query(
                    N1qlQuery.simple(query)
            );

            // Collect documents
            if (result.finalSuccess()) {
                List<String> rows = new ArrayList<>();
                for (N1qlQueryRow row : result) {
                    rows.add(row.value().toString());
                }
                return rows;
            }
        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to search documents due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }

        return null;
    }

    @Override
    public List<String> parameterizedSearch(String query, Object... parameters) throws CouchbaseException {
        Bucket bucket = null;
        try {
            bucket = cluster.openBucket(bucketName, bucketPassword);
            // Create a N1QL Primary Index (but ignore if it exists)
            bucket.bucketManager().createN1qlPrimaryIndex(true, false);

            // Perform a N1QL Query
            N1qlQueryResult result = bucket.query(
                    N1qlQuery.parameterized(query, JsonArray.from(parameters))
            );

            // Collect documents
            if (result.finalSuccess()) {
                List<String> rows = new ArrayList<>();
                for (N1qlQueryRow row : result) {
                    rows.add(row.value().toString());
                }
                return rows;
            }
        } catch (Exception exception) {
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            throw new CouchbaseException("Failed to search documents due to " + errors.toString());
        } finally {
            if (bucket != null)
                bucket.close();
        }
        return null;
    }

}
