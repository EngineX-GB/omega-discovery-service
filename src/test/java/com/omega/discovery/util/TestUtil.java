package com.omega.discovery.util;

import com.omega.discovery.dto.DiscoveryConfig;
import com.omega.discovery.dto.DiscoveryRequest;
import com.omega.discovery.dto.MapperEntry;

public class TestUtil {


    public static DiscoveryConfig createDiscoveryConfig() {
        final DiscoveryConfig discoveryConfig = new DiscoveryConfig("/path/to/chrome/driver",
                "/path/to/chrome/executable/path", createMapperEntry(), false);
        return discoveryConfig;
    }

    public static MapperEntry createMapperEntry() {
        final MapperEntry mapperEntry = new MapperEntry();
        mapperEntry.setId("10");
        mapperEntry.setName("mapper-test");
        mapperEntry.setRegex(".ts");
        mapperEntry.setStream(true);
        mapperEntry.setAdapter("default");
        return mapperEntry;
    }


}
