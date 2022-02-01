package com.stox.client.kite;

import java.net.HttpCookie;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.javalite.http.Delete;
import org.javalite.http.Get;
import org.javalite.http.Post;
import org.javalite.http.Put;
import org.javalite.http.Request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stox.client.kite.model.Response;
import com.stox.client.kite.model.Twofa;
import com.stox.client.kite.util.KiteConstant;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Data
@RequiredArgsConstructor
public class KiteSession {
	
	@NonNull private final KiteCredentials credentials;
	private final Set<HttpCookie> cookies = new HashSet<>();
	
	private String cookies() {
		return cookies.stream().map(Object::toString).collect(Collectors.joining("; "));
	}
	
	private void cookies(Map<String, List<String>> headers) {
		final List<String> values = new LinkedList<>();
		Optional.ofNullable(headers.get("set-cookie")).ifPresent(cookies -> values.addAll(cookies));
		Optional.ofNullable(headers.get("Set-Cookie")).ifPresent(cookies -> values.addAll(cookies));
		if(!values.isEmpty()) cookies.addAll(HttpCookie.parse(String.join("; ", values)));
	}
	
	@SneakyThrows
	private void login() {
		cookies(new Get(KiteConstant.URL_BASE, KiteConstant.TIMEOUT, KiteConstant.TIMEOUT)
				.header(KiteConstant.USER_AGENT, KiteConstant.USER_AGENT_CHROME)
				.headers());
		final Post login = post(KiteConstant.URL_LOGIN)
				.param("user_id", credentials.getUsername())
				.param("password", credentials.getPassword());
		final Response<Twofa> response = KiteConstant.JSON.readValue(login.text(), new TypeReference<Response<Twofa>>(){});
		final Post twofa = post(KiteConstant.URL_TWOFA)
				.param("user_id", credentials.getUsername())
				.param("request_id", response.getData().getRequestId())
				.param("twofa_value", credentials.getPin());
		cookies(twofa.headers());
		cookies(get(KiteConstant.URL_DASHBOARD).headers());
	}
	
	public Get get(final String url) {
		if(cookies.isEmpty()) login();
		return authorize(new Get(KiteConstant.URL_BASE + url, KiteConstant.TIMEOUT, KiteConstant.TIMEOUT)
				.header(KiteConstant.USER_AGENT, KiteConstant.USER_AGENT_CHROME)
				.header("cookie", cookies()));
	}
	
	public Delete delete(final String url) {
		if(cookies.isEmpty()) login();
		return authorize(new Delete(KiteConstant.URL_BASE + url, KiteConstant.TIMEOUT, KiteConstant.TIMEOUT)
				.header(KiteConstant.USER_AGENT, KiteConstant.USER_AGENT_CHROME)
				.header("cookie", cookies()));
	}
	
	public Post post(final String url) {
		if(cookies.isEmpty()) login();
		return authorize(new Post(KiteConstant.URL_BASE + url, null, KiteConstant.TIMEOUT, KiteConstant.TIMEOUT)
				.header(KiteConstant.USER_AGENT, KiteConstant.USER_AGENT_CHROME)
				.header("cookie", cookies()));
	}
	
	public Put put(final String url) {
		if(cookies.isEmpty()) login();
		return authorize(new Put(KiteConstant.URL_BASE + url, null, KiteConstant.TIMEOUT, KiteConstant.TIMEOUT)
				.header(KiteConstant.USER_AGENT, KiteConstant.USER_AGENT_CHROME)
				.header("cookie", cookies()));
	}
	
	private <T extends Request<T>> T authorize(final T request){
		cookies.stream()
			.filter(cookie -> "enctoken".equalsIgnoreCase(cookie.getName()))
			.findFirst()
			.ifPresent(cookie -> {
				request.header("authorization", "enctoken " + cookie.getValue());
			});
		return request;
	}

}
