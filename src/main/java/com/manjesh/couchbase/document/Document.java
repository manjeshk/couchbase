package com.manjesh.couchbase.document;

public class Document {
    private String id;
    private String content;
    private int expiry;

    public Document(String id, String content, int expiry) {
        this.id = id;
        this.content = content;
        this.expiry = expiry;
    }

    public String id() {
        return id;
    }

    public String content() {
        return content;
    }

    public int expiry() {
        return expiry;
    }

    public static class Builder {
        private String id;
        private String content;
        private int expiry;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder expiry(int expiry) {
            this.expiry = expiry;
            return this;
        }

        public Document build() {
            return new Document(id, content, expiry);
        }
    }
}
