package br.com.wine.store_service_region.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.wine.store_service_region.dto.NewZipCodeRangeRequest;
import br.com.wine.store_service_region.dto.StoreCodeDto;
import br.com.wine.store_service_region.dto.ZipCodeRangeDto;
import br.com.wine.store_service_region.entity.ZipCodeRange;
import br.com.wine.store_service_region.exception.ZipCodeNotRegisteredException;
import br.com.wine.store_service_region.repository.ZipCodeRangeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZipCodeRangeService {
	
	private final ZipCodeRangeRepository zipCodeRangeRepository;
	private final ModelMapper mapper;
	
	@Transactional
	public ZipCodeRange saveZipCodeRange(NewZipCodeRangeRequest request) {						
		return save(mapper.map(request, ZipCodeRange.class));
	}

	private ZipCodeRange save(ZipCodeRange zipCodeRange) {
		return zipCodeRangeRepository.save(zipCodeRange);
	}
	
	public List<ZipCodeRangeDto> getAllDto(){
		return getAll().stream()
					.map(zipCodeRange -> mapper.map(zipCodeRange, ZipCodeRangeDto.class))
					.collect(Collectors.toList());
	}
	
	private List<ZipCodeRange> getAll() {
		return zipCodeRangeRepository.findAll();
	}
	
	@Transactional
	public ZipCodeRangeDto updateZipCodeRange(NewZipCodeRangeRequest request, Long id) {
		var zipCodeRange = findById(id);
		mapper.map(request, zipCodeRange);
		return mapper.map(zipCodeRange, ZipCodeRangeDto.class);
	}

	private ZipCodeRange findById(Long id) {
		return zipCodeRangeRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException(String.format("The ZipCodeRange with id '%s' was not found!", id)));
	}
	
	@Transactional
	public ZipCodeRangeDto getById(Long id) {
		return mapper.map(findById(id), ZipCodeRangeDto.class);
	}
	
	@Transactional
	public void deleteById(Long id) {
		var zipCodeRangeToBeDeleted = findById(id);
		zipCodeRangeRepository.delete(zipCodeRangeToBeDeleted);
	}

	public StoreCodeDto getStoreCodeByZipCode(Integer zipCode) {
		return mapper.map(getByZipCode(zipCode), StoreCodeDto.class);
	}
	
	private ZipCodeRange getByZipCode(Integer zipCode) {
		return zipCodeRangeRepository.getByZipCode(zipCode)
				.orElseThrow(()-> new ZipCodeNotRegisteredException(String.format("Any store serving in zip code '%s' was found! ", zipCode)));
	}
	
}
