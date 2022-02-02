package com.stox.report;

import de.vandermeer.asciitable.AsciiTable;

public class TableReportFormatter implements ReportFormatter {

	@Override
	public String format(Report report) {
		final AsciiTable table = new AsciiTable();
		
		table.addRule();
		
		table.addRow("Expectancy", report.getExpectancy());
		table.addRow("Number of bars", report.getNumberOfBars());
		table.addRow("Number of positions", report.getNumberOfPositions());
		table.addRow("Number of losing positions", report.getNumberOfLosingPositions());
		table.addRow("Number of break-even positions", report.getNumberOfBreakEvenPositions());
		table.addRow("Number of winning positions", report.getNumberOfWinningPositions());
		table.addRow("Losing position ratio", report.getLosingPositionRatio());
		table.addRow("Winning position ratio", report.getWinningPositionRatio());
		
		table.addRule();
		
		table.addRow("Gross loss", report.getGrossLoss());
		table.addRow("Gross profit", report.getGrossProfit());
		table.addRow("Gross return", report.getGrossReturn());
		table.addRow("Net loss", report.getNetLoss());
		table.addRow("Net profit", report.getNetProfit());
		table.addRow("Profit / Loss", report.getProfitLoss());
		table.addRow("Profit / Loss ratio", report.getProfitLossRatio());
		table.addRow("Profit / Loss percentage", report.getProfitLossPercentage());
		table.addRow("Average loss", report.getAverageLoss());
		table.addRow("Average profit", report.getAverageProfit());
		table.addRow("Average return per bar", report.getAverageReturnPerBar());
		table.addRow("Buy & Hold return", report.getBuyAndHoldReturn());
		table.addRow("Maximum drawdown", report.getMaximumDrawdown());
		table.addRow("Return over max drawdown", report.getReturnOverMaxDrawdown());
		
		table.addRule();
		
		return table.render();
	}

}
