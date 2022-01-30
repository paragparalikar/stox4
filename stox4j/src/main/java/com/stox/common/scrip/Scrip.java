package com.stox.common.scrip;

import java.io.Serializable;
import java.util.Objects;

import com.stox.common.intf.HasScrip;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Scrip implements Comparable<Scrip>, Serializable, HasScrip {
	private static final long serialVersionUID = -5267152623133884519L;

	@NonNull private final String isin;
	@NonNull private final String code;
	@NonNull private final String name;
	
	public int compareTo(Scrip scrip) {
		return Objects.compare(name, scrip.name, (one, two) -> one.compareToIgnoreCase(two));
	}
	
	@Override
	public Scrip getScrip() {
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
