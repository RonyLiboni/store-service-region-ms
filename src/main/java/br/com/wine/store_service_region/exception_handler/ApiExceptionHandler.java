package br.com.wine.store_service_region.exception_handler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import br.com.wine.store_service_region.exception.ZipCodeNotRegisteredException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String GENERIC_MESSAGE_FOR_USER
		= "An unexpected error happened in the system. Try again and if the problem persist "
				+ "please, contact a system administrator.";
	
	private final MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleZipCodeNotRegisteredException(EntityNotFoundException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ZipCodeNotRegisteredException.class)
	public ResponseEntity<?> handleZipCodeNotRegisteredException(ZipCodeNotRegisteredException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemType problemType = ProblemType.SYSTEM_ERROR;
		String detail = GENERIC_MESSAGE_FOR_USER;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.title(status.getReasonPhrase())
				.status(status.value())
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.title((String) body)
				.status(status.value())
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.OBJECT_WITH_VALIDATION_ERRORS;
	    String detail = "One or more fields are invalid. Please fill the form with valid data and try again.";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
	    		.map(objectError -> {
	    			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	    			
	    			String name = objectError.getObjectName();
	    			
	    			if (objectError instanceof FieldError) {
	    				name = ((FieldError) objectError).getField();
	    			}
	    			
	    			return Problem.Object.builder()
	    				.name(name)
	    				.userMessage(message)
	    				.build();
	    		})
	    		.collect(Collectors.toList());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .objects(problemObjects)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
		
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format("The resource %s, that you tried to access doesn't exist.", 
				ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_PARAMETER;

		String detail = String.format("The URL parameter got the value '%s', "
				+ "that is an invalid type. Please inform a value compatible with the type '%s'.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = "Request body is invalid. Check for syntax error.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = String.format("The property '%s' does not exist. "
				+ "Correct or remove that property and try again.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = String.format("The property '%s' got the value '%s', "
				+ "that is an invalid type. Inform a compatible value with the type '%s'.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_MESSAGE_FOR_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
		
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail) {
		
		return Problem.builder()
			.timestamp(OffsetDateTime.now())
			.status(status.value())
			.type(problemType.getUri())
			.title(problemType.getTitle())
			.detail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
}
