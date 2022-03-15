package com.stox.common.event;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageEvent {

	private String icon;
	private String text;
	private String style;
	
}
