package com.omega.discovery.adapters;

import com.omega.discovery.adapters.impl.ChromeDevToolsAdapter;
import com.omega.discovery.adapters.impl.HtmlSourceCodeAdapter;
import com.omega.discovery.adapters.impl.NoopAdapter;

public final class NetworkMediaAdapterFactory {

	private NetworkMediaAdapterFactory() {
		//empty constructor
	}
	
	public static final NetworkMediaAdapter getAdapter(final String adapterName) {
		switch(adapterName) {
		case "devtools":
			return new ChromeDevToolsAdapter();
		case "source":
			return new HtmlSourceCodeAdapter();
			default:
			return new NoopAdapter();
		}
	}
	
}
