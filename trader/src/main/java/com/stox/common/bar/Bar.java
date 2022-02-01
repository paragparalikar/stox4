package com.stox.common.bar;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor
@Accessors(fluent = true)
public class Bar implements org.ta4j.core.Bar {
	private static final long serialVersionUID = -3047462532062623094L;
	public static final int BYTES = Double.BYTES * 6 + Long.BYTES * 2;

	private Duration timePeriod;
	private long date, trades;
	private double open, high, low, close, volume, amount;
	private ZonedDateTime beginTime, endTime;
	private Num openPrice, highPrice, lowPrice, closePrice, volumeValue, amountValue;
	
	@Builder
	public Bar(@NonNull Duration timePeriod, long date, long trades, double open, double high, double low, double close,
			double volume, double amount) {
		super();
		this.timePeriod = timePeriod;
		this.date = date;
		this.trades = trades;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.amount = amount;
		this.endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
		this.beginTime = endTime.minus(timePeriod);
		this.openPrice = DoubleNum.valueOf(open);
		this.highPrice = DoubleNum.valueOf(high);
		this.lowPrice = DoubleNum.valueOf(low);
		this.closePrice = DoubleNum.valueOf(close);
		this.volumeValue = DoubleNum.valueOf(volume);
		this.amountValue = DoubleNum.valueOf(amount);
	}

	@Override
	public Num getOpenPrice() {
		return openPrice;
	}

	@Override
	public Num getLowPrice() {
		return lowPrice;
	}

	@Override
	public Num getHighPrice() {
		return highPrice;
	}

	@Override
	public Num getClosePrice() {
		return closePrice;
	}

	@Override
	public Num getVolume() {
		return volumeValue;
	}

	@Override
	public long getTrades() {
		return trades;
	}

	@Override
	public Num getAmount() {
		return amountValue;
	}

	@Override
	public Duration getTimePeriod() {
		return timePeriod;
	}

	@Override
	public ZonedDateTime getBeginTime() {
		return beginTime;
	}

	@Override
	public ZonedDateTime getEndTime() {
		return endTime;
	}

	@Override
	public void addTrade(Num tradeVolume, Num tradePrice) {
		addPrice(tradePrice);

		volume += tradeVolume.doubleValue();
        volumeValue = volumeValue.plus(tradeVolume);
        amount += volume * tradePrice.doubleValue();
        amountValue = amountValue.plus(tradeVolume.multipliedBy(tradePrice));
        trades++;
	}

	@Override
	public void addPrice(Num price) {
		if (openPrice == null) {
            openPrice = price;
            open = price.doubleValue();
        }
        closePrice = price;
        close = price.doubleValue();
        if (highPrice == null || highPrice.isLessThan(price)) {
            highPrice = price;
            high = price.doubleValue();
        }
        if (lowPrice == null || lowPrice.isGreaterThan(price)) {
            lowPrice = price;
            low = price.doubleValue();
        }
	}

}
