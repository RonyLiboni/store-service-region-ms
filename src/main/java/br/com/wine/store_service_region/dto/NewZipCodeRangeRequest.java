package br.com.wine.store_service_region.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NewZipCodeRangeRequest {
		
	@NotBlank
	@Length(max = 50)
	@Schema(example = "LOJA_MOEMA", description = "Here you have to add the name of the store.")
	private String storeCode;
	
	@NotNull
	@Min(1)
	@Max(99999999)
	@Schema(example = "10000000", description = "Here you have to add zip code start range.")
	private Integer startRange;
	
	@NotNull
	@Min(1)
	@Max(99999999)
	@Schema(example = "20000000", description = "Here you have to add zip code end range.")
	private Integer endRange;
}
