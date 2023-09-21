package com.omega.discovery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.discovery.dto.Mapper;
import com.omega.discovery.dto.MapperEntry;
import com.omega.discovery.exceptions.DiscoveryServiceException;
import com.omega.discovery.service.impl.MappingServiceImpl;
import com.omega.discovery.util.ReflectionUtil;
import com.omega.discovery.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class MappingServiceTest {

    MappingService mappingService;

    @BeforeEach
    public void before() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Mapper mapper = objectMapper.readValue(new File("src/test/resources/test-mapper.json"), Mapper.class);
        mappingService = new MappingServiceImpl();
        ReflectionUtil.setFieldValue(mappingService, "objectMapper", objectMapper);
        ReflectionUtil.setFieldValue(mappingService, "mapper", mapper);
        ReflectionUtil.setFieldValue(mappingService, "mappingFilePath", "src/test/resources/test-mapper.json");

    }

    @Test
    public void testGetMappingEntries() {
        assertNotNull(mappingService.getMappingEntries());
    }

    @Test
    public void testGetMappingById() {
        assertNotNull(mappingService.getMapperEntryById("1"));
        assertNull(mappingService.getMapperEntryById("x"));
    }

    @Test
    public void testAddAndDeleteMappingEntry() throws Exception{
        final MapperEntry mapperEntry = TestUtil.createMapperEntry();
        final MapperEntry updatedMapperEntry = mappingService.addMapperEntry(mapperEntry);
        assertNotNull(updatedMapperEntry);
        assertEquals(3, mappingService.getMapperEntries().size());
        boolean deleteEntry = mappingService.deleteMapperEntry("10", true);
        assertTrue(deleteEntry);
        assertEquals(2, mappingService.getMapperEntries().size());
    }

    @Test
    public void testAddingEntryWithDuplicateMapperId() throws Exception {
        Exception exception = assertThrows(DiscoveryServiceException.class, () -> {
            final MapperEntry mapperEntry = new MapperEntry();
            mapperEntry.setId("1");
            mappingService.addMapperEntry(mapperEntry);
        });

        assertTrue(exception.getMessage().contains("Duplicate mapping id found"));
    }

    @Test
    public void testGetMappersByName() {
        assertFalse(mappingService.getMapperEntryByName("default").isEmpty());
        assertTrue(mappingService.getMapperEntryByName("x").isEmpty());
    }

}
