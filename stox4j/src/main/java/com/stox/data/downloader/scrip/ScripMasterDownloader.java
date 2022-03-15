package com.stox.data.downloader.scrip;

import java.io.IOException;
import java.util.List;

import com.stox.common.scrip.Scrip;

public interface ScripMasterDownloader {

	List<Scrip> download() throws IOException;
	
}
