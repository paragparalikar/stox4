package com.stox.data.downloader.bar;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Map;

import org.ta4j.core.Bar;

import com.stox.common.scrip.Scrip;

public interface EodBarDownloader {

	Map<Scrip, Bar> download(ZonedDateTime date) throws IOException;
	
}
