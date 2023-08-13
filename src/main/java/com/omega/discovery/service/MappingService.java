package com.omega.discovery.service;

import java.util.List;
import java.util.Map;

import com.omega.discovery.dto.Mapper;
import com.omega.discovery.dto.MapperEntry;
import com.omega.discovery.exceptions.DiscoveryServiceException;

public interface MappingService {

	/**
	 * Get the full Mapper of mapper entries
	 * @return - the Mapper
	 */
	Mapper getMapper();
	
	/**
	 * Get a list of mapper entries
	 * @return - list of mapper entries
	 */
	List<MapperEntry> getMappingEntries();
	
	/**
	 * Add a new mapper entry
	 * @param mappingEntry - the mapperEntry to add
	 * @return
	 */
	
	MapperEntry addMapperEntry(final MapperEntry mappingEntry) throws DiscoveryServiceException;
	
	/**
	 * Get a mapper entry by ID
	 * @param id - the UUID of the mapper entry
	 * @return
	 */
	MapperEntry getMapperEntryById(final String id);
	
	/**
	 * Get a mapper entry by it's name
	 * @param name - the name of the mapper entry
	 * @return - returns a list of mapper entries that match the name
	 */
	
	List<MapperEntry> getMapperEntryByName(final String name);
	
	/**
	 * Updates an existing mapper entry
	 * @param mapperEntry - the updated mapper entry
	 * @return - returns the updated mapper entry
	 */
	MapperEntry updateMapperEntry(final MapperEntry mapperEntry) throws DiscoveryServiceException;
	
	/**
	 * Delete an existing mapper entry
	 * @param id - the ID of the mapper entry
	 * @param persist - to persist the change into the file
	 * @return - return's true if deleted, false if not deleted.
	 */
	boolean deleteMapperEntry (final String id, final boolean persist);
	
	
	/**
	 * Get a list of mapper entries
	 * @return - returns a map of mapper entry objects with their corresponding IDs
	 */
	Map<String, MapperEntry> getMapperEntries();
	
}
