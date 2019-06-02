package com.manjesh.couchbase.exception;

public class ClientException extends RuntimeException {

	private static final long serialVersionUID = -90287063308349768L;

	public ClientException() {
		super();
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
