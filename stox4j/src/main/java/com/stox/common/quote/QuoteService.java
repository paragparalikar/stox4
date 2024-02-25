package com.stox.common.quote;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class QuoteService {

	@Delegate private final QuoteProvider quoteProvider;
	
}
