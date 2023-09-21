package com.omega.discovery.adapters;

import com.omega.discovery.adapters.impl.ChromeDevToolsAdapter;
import com.omega.discovery.adapters.impl.HtmlSourceCodeAdapter;
import com.omega.discovery.adapters.impl.NoopAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NetworkMediaAdapterFactoryTest {

    @Test
    public void testAdapterFactory() {
       NetworkMediaAdapter adapter = NetworkMediaAdapterFactory.getAdapter("devtools");
       assertTrue(adapter instanceof ChromeDevToolsAdapter);
       adapter = NetworkMediaAdapterFactory.getAdapter("source");
       assertTrue(adapter instanceof HtmlSourceCodeAdapter);
       adapter = NetworkMediaAdapterFactory.getAdapter("random");
       assertTrue(adapter instanceof NoopAdapter);
    }

}
