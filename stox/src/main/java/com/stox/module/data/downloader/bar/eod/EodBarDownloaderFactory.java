package com.stox.module.data.downloader.bar.eod;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.persistence.ScripRepository;

import lombok.NonNull;

public class EodBarDownloaderFactory {
	
	public EodBarDownloader create(@NonNull final Exchange exchange, @NonNull final ScripRepository scripRepository){
		switch(exchange){
		case NSE :
			return new NseEodBarDownloader(scripRepository);
		default :
			throw new UnsupportedOperationException("Exchange \""+exchange.getName()+"\" is not suppoerted yet");
		}
	}
	
}
