package br.com.wine.store_service_region.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZipCodeRangeDto {
	
	private String storeCode;
	private Integer startRange;
	private Integer endRange;
	
}
