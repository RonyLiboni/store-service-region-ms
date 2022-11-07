package br.com.wine.store_service_region.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name= "zip_code_ranges")
@Data
public class ZipCodeRange {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String storeCode;
	@Column(nullable = false, unique = true)
	private Integer startRange;
	@Column(nullable = false, unique = true)
	private Integer endRange;
	
}
