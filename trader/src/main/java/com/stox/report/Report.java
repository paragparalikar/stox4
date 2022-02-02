package com.stox.report;

import org.ta4j.core.num.Num;

import com.stox.util.NumUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Report {
	
	private Num expectancy;
	private Num numberOfBars;
	private Num numberOfPositions;
	private Num numberOfLosingPositions;
	private Num numberOfBreakEvenPositions;
	private Num numberOfWinningPositions;
	private Num losingPositionRatio;
	private Num winningPositionRatio;
	
	private Num grossLoss;
	private Num grossProfit;
	private Num grossReturn;
	private Num netLoss;
	private Num netProfit;
	private Num profitLoss;
	private Num profitLossRatio;
	private Num profitLossPercentage;
	private Num averageLoss;
	private Num averageProfit;
	private Num averageReturnPerBar;
	private Num buyAndHoldReturn;
	private Num maximumDrawdown;
	private Num returnOverMaxDrawdown;
	
	public Report(String text) {
		final String tokens[] = text.split(",");
		expectancy = NumUtils.valueOf(tokens[0]);
		numberOfBars = NumUtils.valueOf(tokens[0]);
		numberOfPositions = NumUtils.valueOf(tokens[0]);
		numberOfLosingPositions = NumUtils.valueOf(tokens[0]);
		numberOfBreakEvenPositions = NumUtils.valueOf(tokens[0]);
		numberOfWinningPositions = NumUtils.valueOf(tokens[0]);
		losingPositionRatio = NumUtils.valueOf(tokens[0]);
		winningPositionRatio = NumUtils.valueOf(tokens[0]);
		grossLoss = NumUtils.valueOf(tokens[0]);
		grossProfit = NumUtils.valueOf(tokens[0]);
		grossReturn = NumUtils.valueOf(tokens[0]);
		netLoss = NumUtils.valueOf(tokens[0]);
		netProfit = NumUtils.valueOf(tokens[0]);
		profitLoss = NumUtils.valueOf(tokens[0]);
		profitLossRatio = NumUtils.valueOf(tokens[0]);
		profitLossPercentage = NumUtils.valueOf(tokens[0]);
		averageLoss = NumUtils.valueOf(tokens[0]);
		averageProfit = NumUtils.valueOf(tokens[0]);
		averageReturnPerBar = NumUtils.valueOf(tokens[0]);
		buyAndHoldReturn = NumUtils.valueOf(tokens[0]);
		maximumDrawdown = NumUtils.valueOf(tokens[0]);
		returnOverMaxDrawdown = NumUtils.valueOf(tokens[0]);
	}
	
	@Override
	public String toString() {
		return String.join(",", 
				String.valueOf(expectancy),
				String.valueOf(numberOfBars),
				String.valueOf(numberOfPositions),
				String.valueOf(numberOfLosingPositions),
				String.valueOf(numberOfBreakEvenPositions),
				String.valueOf(numberOfWinningPositions),
				String.valueOf(losingPositionRatio),
				String.valueOf(winningPositionRatio),
				String.valueOf(grossLoss),
				String.valueOf(grossProfit),
				String.valueOf(grossReturn),
				String.valueOf(netLoss),
				String.valueOf(netProfit),
				String.valueOf(profitLoss),
				String.valueOf(profitLossRatio),
				String.valueOf(profitLossPercentage),
				String.valueOf(averageLoss),
				String.valueOf(averageProfit),
				String.valueOf(averageReturnPerBar),
				String.valueOf(buyAndHoldReturn),
				String.valueOf(maximumDrawdown),
				String.valueOf(returnOverMaxDrawdown));
	}
	
}
