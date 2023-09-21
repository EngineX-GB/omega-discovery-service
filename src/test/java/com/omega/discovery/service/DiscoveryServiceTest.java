package com.omega.discovery.service;

import com.omega.discovery.dto.DiscoveryRequest;
import com.omega.discovery.dto.DiscoveryResponse;
import com.omega.discovery.service.impl.DiscoveryServiceImpl;
import com.omega.discovery.service.impl.ExecutionEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DiscoveryServiceTest {

    @Mock
    ExecutionEngine executionEngine;

    @InjectMocks
    DiscoveryServiceImpl discoveryService;

    @Test
    public void testGetDiscoveryLink() {
        DiscoveryRequest request = new DiscoveryRequest();
        Mockito.when(executionEngine.executeDiscoveryAndRetrieveLink(request)).thenReturn("https://www.discoverylink.com/api/1.ts");
        DiscoveryResponse response = discoveryService.getDiscoveryLink(request);
        assertNotNull(response);
        assertEquals("https://www.discoverylink.com/api/1.ts", response.getLink());
    }

    @Test
    public void testGetNoLinkFromDiscoveryService() {
        final DiscoveryRequest request = new DiscoveryRequest();
        Mockito.when(executionEngine.executeDiscoveryAndRetrieveLink(request)).thenReturn(null);
        final DiscoveryResponse response = discoveryService.getDiscoveryLink(request);
        assertNotNull(response);
        assertEquals("NULL_LINK_RETURNED", response.getStatus());
    }

}
