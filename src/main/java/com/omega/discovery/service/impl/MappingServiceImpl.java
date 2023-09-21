package com.omega.discovery.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.omega.discovery.dto.Mapper;
import com.omega.discovery.dto.MapperEntry;
import com.omega.discovery.exceptions.DiscoveryServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.discovery.service.MappingService;

@Service
public class MappingServiceImpl implements MappingService{

	private static final Logger LOGGER = LoggerFactory.getLogger(MappingServiceImpl.class);
	
	@Value("${application.mapping.file.path}")
	private String mappingFilePath;
	
	private Mapper mapper;
	
	private ObjectMapper objectMapper;
	
	@PostConstruct
	private void generateMapper() {
		try {
			objectMapper = new ObjectMapper();
			mapper = objectMapper.readValue(new File(mappingFilePath), Mapper.class);
			LOGGER.info("Loaded {} mappings from {}", mapper.getEntries().size(), mappingFilePath);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public List<MapperEntry> getMappingEntries() {
		if (mapper != null) {
			return mapper.getEntries();
		}
		return null;
	}

	@Override
	public Mapper getMapper() {
		return mapper;
	}

	@Override
	public MapperEntry addMapperEntry(final MapperEntry mappingEntry) throws DiscoveryServiceException {
		if (mappingEntry.getId() == null) {
			mappingEntry.setId(UUID.randomUUID().toString());
		}
		long duplicateId = this.getMapperEntries().values().stream().filter(s -> s.getId().equals(mappingEntry.getId())).count();
		if (duplicateId > 0) {
			throw new DiscoveryServiceException("Duplicate mapping id found : " + mappingEntry.getId());
		}
		boolean addMapperEntry = this.mapper.getEntries().add(mappingEntry);
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(mappingFilePath), this.mapper);
		} catch(IOException e) {
			LOGGER.error(e.getMessage(), e);
			addMapperEntry = false;
		}
		if (!addMapperEntry) {
			throw new DiscoveryServiceException("An error occured when adding the new mapping entry [" + mappingEntry.getAdapter()+ "]");
		}
		return mappingEntry;
	}

	@Override
	public MapperEntry getMapperEntryById(final String id) {
		for (final MapperEntry mapperEntry : this.mapper.getEntries()) {
			if (mapperEntry.getId().equals(id)) {
				return mapperEntry;
			}
		}
		return null;
	}

	@Override
	public List<MapperEntry> getMapperEntryByName(String name) {
		return this.mapper.getEntries().stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public MapperEntry updateMapperEntry(final MapperEntry mapperEntry) throws DiscoveryServiceException {
		boolean exists = this.mapper.getEntries().stream().filter(s -> s.getId().equals(mapperEntry.getId())).count() == 1;
		if(exists) {
			final boolean deleteOldMapperEntry = deleteMapperEntry(mapperEntry.getId(), false);
			if (deleteOldMapperEntry) {
				//re-write the file
				this.mapper.getEntries().add(mapperEntry);
				try {
					objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(mappingFilePath), this.mapper);
				} catch(IOException e) {
					LOGGER.error(e.getMessage(), e);
					throw new DiscoveryServiceException("Unable to update mapper entry [" + e.getMessage()+ "]");
				}
			} else {
				throw new DiscoveryServiceException("Unable to update mapper entry");
			}
			return mapperEntry;
		}
		throw new DiscoveryServiceException("Mapper entry cannot be updated because the it does not exist!");
		
	}

	@Override
	public boolean deleteMapperEntry(final String id, boolean persist) {
		boolean deleted = false;
		final Iterator<MapperEntry> mapperEntryIterator = this.mapper.getEntries().iterator();
		while(mapperEntryIterator.hasNext()) {
			final MapperEntry entry = mapperEntryIterator.next();
			if (entry.getId().equals(id)) {
				mapperEntryIterator.remove();
				deleted = true;
			}
		}
		if (persist) {
			if (deleted) {
				try {
					objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(mappingFilePath), this.mapper);
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					deleted = false;
				}
			}
		}
		return deleted;
	}

	@Override
	public Map<String, MapperEntry> getMapperEntries() {
		return this.mapper.getEntries().stream().collect(Collectors.toMap(MapperEntry::getId, m -> m));
	}

}
