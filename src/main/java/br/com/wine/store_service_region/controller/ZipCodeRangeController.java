package br.com.wine.store_service_region.controller;

import static java.lang.String.format;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.wine.store_service_region.annotation.documentation.DeleteMappingDocumentation;
import br.com.wine.store_service_region.annotation.documentation.GetMappingDocumentation;
import br.com.wine.store_service_region.annotation.documentation.PostMappingDocumentation;
import br.com.wine.store_service_region.annotation.documentation.PutMappingDocumentation;
import br.com.wine.store_service_region.dto.NewZipCodeRangeRequest;
import br.com.wine.store_service_region.dto.StoreCodeDto;
import br.com.wine.store_service_region.dto.ZipCodeRangeDto;
import br.com.wine.store_service_region.service.ZipCodeRangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/zip-code-ranges")
@Tag(name = "Zip code range Controller", description = "You will be able to create, read, update and delete zip code ranges")
public class ZipCodeRangeController {
	
	private final ZipCodeRangeService zipCodeRangeController;
	
	@PostMapping
	@PostMappingDocumentation(summary= "Creates a new zip code range.")
	public ResponseEntity<?> createNewZipCodeRange(@RequestBody @Valid NewZipCodeRangeRequest request){
		return ResponseEntity.created(URI.create(format("v1/zip-code-ranges/%s", zipCodeRangeController.saveZipCodeRange(request).getId())))
							 .build();
	}
	
	@GetMapping
	@GetMappingDocumentation(summary= "Gets all saved zip code ranges.")
	public ResponseEntity<List<ZipCodeRangeDto>> getAllZipCodeRange(){
		return ResponseEntity.ok(zipCodeRangeController.getAllDto());
	}
	
	@PutMapping("/{id}")
	@PutMappingDocumentation(summary= "Updates a zip code range by its id.")
	public ResponseEntity<ZipCodeRangeDto> updateAnZipCodeRange(@RequestBody @Valid NewZipCodeRangeRequest request, @PathVariable("id") Long id){
		return ResponseEntity.ok(zipCodeRangeController.updateZipCodeRange(request, id));
	}
	
	@GetMapping("/{id}")
	@GetMappingDocumentation(summary= "Gets a zip code range by its id.")
	public ResponseEntity<ZipCodeRangeDto> getAnZipCodeRangeById(@PathVariable("id") Long id){
		return ResponseEntity.ok(zipCodeRangeController.getById(id));
	}
	
	@DeleteMapping("/{id}")
	@DeleteMappingDocumentation(summary= "Deletes a zip code range by its id.")
	public ResponseEntity<Void> deleteAnZipCodeRangeById(@PathVariable("id") Long id){
		zipCodeRangeController.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/store/{storeCode}")
	@DeleteMappingDocumentation(summary= "Deletes all zip code ranges with same store code.")
	public ResponseEntity<Void> deleteAnZipCodeRangeById(@PathVariable("storeCode") String storeCode){
		zipCodeRangeController.deleteStoreCode(storeCode);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/store/{zipCode}")
	@GetMappingDocumentation(summary= "Gets a store by the zip code.")
	public ResponseEntity<StoreCodeDto> getStoreCodebyZipCode(@PathVariable("zipCode") Integer zipCode){
		return ResponseEntity.ok(zipCodeRangeController.getStoreCodeByZipCode(zipCode));
	}

}
