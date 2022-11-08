package br.com.wine.store_service_region.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wine.store_service_region.entity.ZipCodeRange;

public interface ZipCodeRangeRepository extends JpaRepository<ZipCodeRange, Long> {
	
	@Query("from ZipCodeRange z where :zipCode between z.startRange and z.endRange")
	Optional<ZipCodeRange> getByZipCode(@Param("zipCode") Integer zipCode);

	Optional<List<ZipCodeRange>> findByStoreCode(String storeCode);
	
}
