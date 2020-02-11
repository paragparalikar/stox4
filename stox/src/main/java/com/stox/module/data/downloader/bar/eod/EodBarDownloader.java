package com.stox.module.data.downloader.bar.eod;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.stox.module.core.model.Bar;

public interface EodBarDownloader {

	List<Bar> download(Date date) throws IOException;
	
}
