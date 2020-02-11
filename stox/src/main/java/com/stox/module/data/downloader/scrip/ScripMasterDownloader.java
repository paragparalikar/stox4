package com.stox.module.data.downloader.scrip;

import java.io.IOException;
import java.util.List;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;

public interface ScripMasterDownloader {

	List<Scrip> download(Exchange exchange) throws IOException;
	
}
