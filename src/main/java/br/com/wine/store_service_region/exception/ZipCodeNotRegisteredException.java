package br.com.wine.store_service_region.exception;

public class ZipCodeNotRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ZipCodeNotRegisteredException(String msg) {
		super(msg);
	}
}
