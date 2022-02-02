package com.stox.report;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.AverageReturnPerBarCriterion;
import org.ta4j.core.analysis.criteria.BuyAndHoldReturnCriterion;
import org.ta4j.core.analysis.criteria.ExpectancyCriterion;
import org.ta4j.core.analysis.criteria.LosingPositionsRatioCriterion;
import org.ta4j.core.analysis.criteria.MaximumDrawdownCriterion;
import org.ta4j.core.analysis.criteria.NumberOfBarsCriterion;
import org.ta4j.core.analysis.criteria.NumberOfBreakEvenPositionsCriterion;
import org.ta4j.core.analysis.criteria.NumberOfLosingPositionsCriterion;
import org.ta4j.core.analysis.criteria.NumberOfPositionsCriterion;
import org.ta4j.core.analysis.criteria.NumberOfWinningPositionsCriterion;
import org.ta4j.core.analysis.criteria.ReturnOverMaxDrawdownCriterion;
import org.ta4j.core.analysis.criteria.WinningPositionsRatioCriterion;
import org.ta4j.core.analysis.criteria.pnl.AverageLossCriterion;
import org.ta4j.core.analysis.criteria.pnl.AverageProfitCriterion;
import org.ta4j.core.analysis.criteria.pnl.GrossLossCriterion;
import org.ta4j.core.analysis.criteria.pnl.GrossProfitCriterion;
import org.ta4j.core.analysis.criteria.pnl.GrossReturnCriterion;
import org.ta4j.core.analysis.criteria.pnl.NetLossCriterion;
import org.ta4j.core.analysis.criteria.pnl.NetProfitCriterion;
import org.ta4j.core.analysis.criteria.pnl.ProfitLossCriterion;
import org.ta4j.core.analysis.criteria.pnl.ProfitLossPercentageCriterion;
import org.ta4j.core.analysis.criteria.pnl.ProfitLossRatioCriterion;

public class ReportService {

	private final AverageLossCriterion averageLossCriterion = new AverageLossCriterion();
	private final AverageProfitCriterion averageProfitCriterion = new AverageProfitCriterion();
	private final AverageReturnPerBarCriterion averageReturnPerBarCriterion = new AverageReturnPerBarCriterion();
	private final BuyAndHoldReturnCriterion buyAndHoldReturnCriterion = new BuyAndHoldReturnCriterion();
	private final ExpectancyCriterion expectancyCriterion = new ExpectancyCriterion();
	private final GrossLossCriterion grossLossCriterion = new GrossLossCriterion();
	private final GrossProfitCriterion grossProfitCriterion = new GrossProfitCriterion();
	private final GrossReturnCriterion grossReturnCriterion = new GrossReturnCriterion();
	private final LosingPositionsRatioCriterion losingPositionRatioCriterion = new LosingPositionsRatioCriterion();
	private final MaximumDrawdownCriterion maximumDrawdownCriterion = new MaximumDrawdownCriterion();
	private final NetLossCriterion netLossCriterion = new NetLossCriterion();
	private final NetProfitCriterion netProfitCriterion = new NetProfitCriterion();
	private final NumberOfBarsCriterion numberOfBarsCriterion = new NumberOfBarsCriterion();
	private final NumberOfBreakEvenPositionsCriterion numberOfBreakEvenPositionsCriterion = new NumberOfBreakEvenPositionsCriterion();
	private final NumberOfLosingPositionsCriterion numberOfLosingPositionsCriterion = new NumberOfLosingPositionsCriterion();
	private final NumberOfPositionsCriterion numberOfPositionsCriterion = new NumberOfPositionsCriterion();
	private final NumberOfWinningPositionsCriterion numberOfWinningPositionsCriterion = new NumberOfWinningPositionsCriterion();
	private final ProfitLossCriterion profitLossCriterion = new ProfitLossCriterion();
	private final ProfitLossPercentageCriterion profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
	private final ProfitLossRatioCriterion profitLossRatioCriterion = new ProfitLossRatioCriterion();
	private final ReturnOverMaxDrawdownCriterion returnOverMaxDrawdownCriterion = new ReturnOverMaxDrawdownCriterion();
	private final WinningPositionsRatioCriterion winnigPositionsRatioCriterion = new WinningPositionsRatioCriterion();

	public Report generate(BarSeries barSeries, TradingRecord tradingRecord) {
		return Report.builder()
				.expectancy(expectancyCriterion.calculate(barSeries, tradingRecord))
				.numberOfBars(numberOfBarsCriterion.calculate(barSeries, tradingRecord))
				.numberOfPositions(numberOfPositionsCriterion.calculate(barSeries, tradingRecord))
				.numberOfLosingPositions(numberOfLosingPositionsCriterion.calculate(barSeries, tradingRecord))
				.numberOfBreakEvenPositions(numberOfBreakEvenPositionsCriterion.calculate(barSeries, tradingRecord))
				.numberOfWinningPositions(numberOfWinningPositionsCriterion.calculate(barSeries, tradingRecord))
				.losingPositionRatio(losingPositionRatioCriterion.calculate(barSeries, tradingRecord))
				.winningPositionRatio(winnigPositionsRatioCriterion.calculate(barSeries, tradingRecord))
				.grossLoss(grossLossCriterion.calculate(barSeries, tradingRecord))
				.grossProfit(grossProfitCriterion.calculate(barSeries, tradingRecord))
				.grossReturn(grossReturnCriterion.calculate(barSeries, tradingRecord))
				.netLoss(netLossCriterion.calculate(barSeries, tradingRecord))
				.netProfit(netProfitCriterion.calculate(barSeries, tradingRecord))
				.profitLoss(profitLossCriterion.calculate(barSeries, tradingRecord))
				.profitLossRatio(profitLossRatioCriterion.calculate(barSeries, tradingRecord))
				.profitLossPercentage(profitLossPercentageCriterion.calculate(barSeries, tradingRecord))
				.averageLoss(averageLossCriterion.calculate(barSeries, tradingRecord))
				.averageProfit(averageProfitCriterion.calculate(barSeries, tradingRecord))
				.averageReturnPerBar(averageReturnPerBarCriterion.calculate(barSeries, tradingRecord))
				.buyAndHoldReturn(buyAndHoldReturnCriterion.calculate(barSeries, tradingRecord))
				.maximumDrawdown(maximumDrawdownCriterion.calculate(barSeries, tradingRecord))
				.returnOverMaxDrawdown(returnOverMaxDrawdownCriterion.calculate(barSeries, tradingRecord))
				.build();
	}

}
