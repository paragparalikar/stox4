package com.stox.module.data.downloader.bar.eod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipInputStream;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.Strings;

import lombok.NonNull;

public class NseEodBarDownloader implements EodBarDownloader {
	
	private final DateFormat bhavcopyDateFormat;
	private final ScripRepository scripRepository;
	
	public NseEodBarDownloader(@NonNull final ScripRepository scripRepository) {
		this.scripRepository = scripRepository;
		final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		dateFormatSymbols.setShortMonths(
				new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" });
		bhavcopyDateFormat = new SimpleDateFormat("yyyy/MMM/'cm'ddMMMyyyy", dateFormatSymbols);
	}
	
	@Override
	public List<Bar> download(Date date) throws IOException{
		final String url = "https://www1.nseindia.com/content/historical/EQUITIES/" + bhavcopyDateFormat.format(date) + "bhav.csv.zip";
		final HttpURLConnection connection = init((HttpURLConnection) new URL(url).openConnection());
		final String rawData = Strings.toString(new ZipInputStream(connection.getInputStream()));
		final List<Bar> bars = new ArrayList<Bar>();
		final String[] tokens = rawData.split("\n");
		for (int index = 1; index < tokens.length; index++) {
			final Bar bar = parse(tokens[index], date);
			if (null != bar) {
				bars.add(bar);
			}
		}
		return bars;
	}

	private HttpURLConnection init(HttpURLConnection connection) throws ProtocolException{
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Connection", "keep-alive");
		connection.setRequestProperty("Host", "www.nseindia.com");
		connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		connection.setRequestProperty("Referer", "https://www.nseindia.com/products/content/equities/equities/archieve_eq.htm");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		connection.setRequestProperty("Cookie", "pointer=1; sym1=VEDL; JSESSIONID=FF543E1FC76463AC703391CB6300C2C1; NSE-TEST-1=1826627594.20480.0000");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36");
		return connection;
	}
	
	private Bar parse(String text, Date date) {
		final String[] values = text.split(",");
		final String code = values[0];
		final Scrip scrip = scripRepository.find(Exchange.NSE, code);
		if(null != scrip){
			final Bar bar = new Bar();
			bar.setIsin(scrip.getIsin());
			bar.setDate(date.getTime());
			bar.setOpen(Double.parseDouble(values[2]));
			bar.setHigh(Double.parseDouble(values[3]));
			bar.setLow(Double.parseDouble(values[4]));
			bar.setClose(Double.parseDouble(values[5]));
			bar.setPreviousClose(Double.parseDouble(values[7]));
			bar.setVolume(Double.parseDouble(values[8]));
			return bar;
		}
		return null;
	}
	
}
