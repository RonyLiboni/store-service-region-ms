package br.com.wine.store_service_region.exception_handler;

import lombok.Getter;

@Getter
public enum ProblemType {

	OBJECT_WITH_VALIDATION_ERRORS("/object-with-validation-errors", "Object with validation errors"),
	SYSTEM_ERROR("/system-error", "System error"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
	MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://wine.com.br/problem-documentation" + path;
		this.title = title;
	}
	
}
