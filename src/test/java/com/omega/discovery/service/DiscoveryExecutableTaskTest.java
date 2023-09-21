package com.omega.discovery.service;

import com.omega.discovery.dto.AdapterResponse;
import com.omega.discovery.dto.DiscoveryRequest;
import com.omega.discovery.service.impl.DiscoveryExecutableTask;
import com.omega.discovery.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscoveryExecutableTaskTest {

    @Test
    public void testDiscoveryProcess() {
        final DiscoveryRequest request = new DiscoveryRequest();
        request.setUrl("http://request-link.com");
        request.setAdapterName("no-op");
        final DiscoveryExecutableTask discoveryExecutableTask = new DiscoveryExecutableTask(request, TestUtil.createDiscoveryConfig());
        assertEquals("http://mock", discoveryExecutableTask.retrieveLink());
    }

}
