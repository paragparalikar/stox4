package com.stox.alert;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class AlertService {

	@Delegate private final AlertRepository alertRepository;

}
