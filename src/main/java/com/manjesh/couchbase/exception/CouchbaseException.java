package com.manjesh.couchbase.exception;

public class CouchbaseException extends RuntimeException {

	private static final long serialVersionUID = -90287063308349768L;

	public CouchbaseException() {
		super();
	}

	public CouchbaseException(String message) {
		super(message);
	}

	public CouchbaseException(Throwable cause) {
		super(cause);
	}

	public CouchbaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
