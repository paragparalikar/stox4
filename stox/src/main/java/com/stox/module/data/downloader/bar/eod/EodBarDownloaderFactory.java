package com.stox.module.data.downloader.bar.eod;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.persistence.ScripRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EodBarDownloaderFactory {
	
	@NonNull 
	private final ScripRepository scripRepository;

	public EodBarDownloader create(Exchange exchange){
		switch(exchange){
		case NSE :
			return new NseEodBarDownloader(scripRepository);
		default :
			throw new UnsupportedOperationException("Exchange \""+exchange.getName()+"\" is not suppoerted yet");
		}
	}
	
}
