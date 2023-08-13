package com.omega.discovery.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.omega.discovery.dto.MapperEntry;
import com.omega.discovery.exceptions.DiscoveryServiceException;
import com.omega.discovery.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/discovery")
@RestController
public class AdapterController {

	@Autowired
	private MappingService mappingService;
	
	@GetMapping("/adapter/names")
	public List<String> getAdapterMappingNames() {
		return mappingService.getMappingEntries().stream().map(s -> s.getName()).collect(Collectors.toList());
	}
	
	@GetMapping("/adapter")
	public Map<String, MapperEntry> getAdapterMapperEntries() {
		return mappingService.getMapperEntries();
	}
	
	@PutMapping("/adapter")
	public MapperEntry updateMappingEntry(@RequestBody MapperEntry mapperEntry) throws DiscoveryServiceException {
		return mappingService.updateMapperEntry(mapperEntry);
	}
	
	@PostMapping("/adapter")
	public MapperEntry addMappingEntry(@RequestBody MapperEntry mapperEntry) throws DiscoveryServiceException {
		return mappingService.addMapperEntry(mapperEntry);
	}
	
	@GetMapping("/adapter/{id}")
	public MapperEntry getMapperEntryById(@PathVariable String id) {
		return mappingService.getMapperEntryById(id);
	}
	
	@GetMapping("/adapter/{name}")
	public List<MapperEntry> getMapperEntryByName(@PathVariable String name) {
		return mappingService.getMapperEntryByName(name);
	}
	
	
	@DeleteMapping("/adapter/{id}")
	public boolean deleteMapperEntryById(@PathVariable String id) {
		return mappingService.deleteMapperEntry(id,  true);
	}
	
}
