package com.stox.common.quote;

import com.stox.common.scrip.Scrip;

public interface QuoteProvider {

	Quote get(Scrip scrip);
}
