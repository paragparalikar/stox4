package com.stox.data.downloader.bar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;

import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.util.Strings;

import lombok.NonNull;

public class NseEodBarDownloader implements EodBarDownloader {
	private static final Duration PERIOD = Duration.ofDays(1);
	
	private final ScripService scripService;
	private final DateFormat bhavcopyDateFormat;
	
	public NseEodBarDownloader(@NonNull final ScripService scripService) {
		this.scripService = scripService;
		final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		dateFormatSymbols.setShortMonths(
				new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" });
		bhavcopyDateFormat = new SimpleDateFormat("yyyy/MMM/'cm'ddMMMyyyy", dateFormatSymbols);
	}
	
	@Override
	public Map<Scrip, Bar> download(ZonedDateTime date) throws IOException{
		final String formattedDate = bhavcopyDateFormat.format(new Date(date.toInstant().toEpochMilli()));
		final String url = "https://www1.nseindia.com/content/historical/EQUITIES/" + formattedDate + "bhav.csv.zip";
		final HttpURLConnection connection = init((HttpURLConnection) new URL(url).openConnection());
		final String rawData = Strings.toString(new ZipInputStream(connection.getInputStream()));
		final String[] tokens = rawData.split("\n");
		final Map<Scrip, Bar> data = new HashMap<>();
		for (int index = 1; index < tokens.length; index++) {
			final Map.Entry<Scrip, Bar> entry = parse(tokens[index], date);
			if (null != entry) {
				data.put(entry.getKey(), entry.getValue());
			}
		}
		return data;
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
	
	private Map.Entry<Scrip, Bar> parse(String text, ZonedDateTime date) {
		final String[] values = text.split(",");
		final String code = values[0];
		final Scrip scrip = scripService.findByCode(code);
		if(null != scrip){
			final Bar bar = BaseBar.builder()
					.openPrice(DoubleNum.valueOf(values[2]))
					.highPrice(DoubleNum.valueOf(values[3]))
					.lowPrice(DoubleNum.valueOf(values[4]))
					.closePrice(DoubleNum.valueOf(values[5]))
					.volume(DoubleNum.valueOf(values[8]))
					.endTime(date)
					.timePeriod(PERIOD)
					.build();
			return new SimpleEntry<>(scrip, bar);
		}
		return null;
	}
	
}
