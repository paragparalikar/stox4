package com.stox.common.util;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.http.HttpRequest;

public interface Http {
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36";

	public static HttpRequest.Builder addHeaders(HttpRequest.Builder buidler) {
		buidler.header("Upgrade-Insecure-Requests", "1");
		buidler.header("Accept-Language", "en-US,en;q=0.8");
		buidler.header("Accept-Encoding", "gzip, deflate, br");
		buidler.header("Sec-Ch-Ua", "\"Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24\"");
		buidler.header("Sec-Ch-Ua-Mobile", "?0");
		buidler.header("Sec-Ch-Ua-Platform", "\"Windows\"");
		buidler.header("Sec-Fetch-Dest", "document");
		buidler.header("Sec-Fetch-Mode", "navigate");
		buidler.header("Sec-Fetch-Site", "none");
		buidler.header("Sec-Fetch-User", "?1");
		buidler.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
		buidler.header("User-Agent", USER_AGENT);
		return buidler;
	}
	
	public static HttpURLConnection addNSEHeaders(HttpURLConnection connection) throws ProtocolException {
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		connection.setRequestProperty("Sec-Ch-Ua", "\"Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24\"");
		connection.setRequestProperty("Sec-Ch-Ua-Mobile", "?0");
		connection.setRequestProperty("Sec-Ch-Ua-Platform", "\"Windows\"");
		connection.setRequestProperty("Sec-Fetch-Dest", "document");
		connection.setRequestProperty("Sec-Fetch-Mode", "navigate");
		connection.setRequestProperty("Sec-Fetch-Site", "none");
		connection.setRequestProperty("Sec-Fetch-User", "?1");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
		connection.setRequestProperty("User-Agent", USER_AGENT);
		return connection;
	}
	
}
