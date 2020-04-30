package com.stox.module.data.downloader.scrip;

import com.stox.module.core.model.Exchange;

public class ScripMasterDownloaderFactory {

	public ScripMasterDownloader create(Exchange exchange) {
		switch (exchange) {
		case NSE:
			return new NseScripMasterDownloader();
		default:
			throw new UnsupportedOperationException("Exchange \"" + exchange.name() + "\" is not suppoerted yet");
		}
	}

}
