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
	public ResponseEntity<?> createNewZipCodeRange(@RequestBody @Valid NewZipCodeRangeRequest request){
		return ResponseEntity.created(URI.create(format("v1/zip-code-ranges/%s", zipCodeRangeController.saveZipCodeRange(request).getId())))
							 .build();
	}
	
	@GetMapping
	public ResponseEntity<List<ZipCodeRangeDto>> getAllZipCodeRange(){
		return ResponseEntity.ok(zipCodeRangeController.getAllDto());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ZipCodeRangeDto> updateAnZipCodeRange(@RequestBody @Valid NewZipCodeRangeRequest request, @PathVariable("id") Long id){
		return ResponseEntity.ok(zipCodeRangeController.updateZipCodeRange(request, id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ZipCodeRangeDto> getAnZipCodeRangeById(@PathVariable("id") Long id){
		return ResponseEntity.ok(zipCodeRangeController.getById(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAnZipCodeRangeById(@PathVariable("id") Long id){
		zipCodeRangeController.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/store/{zipCode}")
	public ResponseEntity<StoreCodeDto> getStoreCodebyZipCode(@PathVariable("zipCode") Integer zipCode){
		return ResponseEntity.ok(zipCodeRangeController.getStoreCodeByZipCode(zipCode));
	}

}
