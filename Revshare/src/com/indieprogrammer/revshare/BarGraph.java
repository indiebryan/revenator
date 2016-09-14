package com.indieprogrammer.revshare;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class BarGraph {
	
	CategoryAxis reportName;
	NumberAxis reportAmount;
	private StackedBarChart<String, Number> graph;
	
	public BarGraph(Report report) {
		reportName = new CategoryAxis();
		reportAmount = new NumberAxis();
		graph = new StackedBarChart<String, Number>(reportName, reportAmount);
		
		setupGraph(report);
	}
	
	private void setupGraph(Report report) {
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		
		series1.setName("Bryan");
		series2.setName("Liam");
		for (int i = 0; i < 4; i++) {
			System.out.println(i + " - " + report.getVideos().get(i).getName() + "  rev- " + report.getVideos().get(i).getRevenue());
			Video v = report.getVideos().get(i);
			System.out.println("i: " + i + "  -  video name: " + v.getName() + "  -  video revenue: " + v.getRevenue() + "  -  cut1: " + v.getCuts().get(0).getPercentage() + "  -  cut2: " + v.getCuts().get(1).getPercentage());
			series1.getData().add(new XYChart.Data<String, Number>(v.getName(), v.getRevenue() * (v.getCuts().get(0).getPercentage())));
			System.out.println(v.getRevenue() * (v.getCuts().get(0).getPercentage() / 100));
			series2.getData().add(new XYChart.Data<String, Number>(v.getName(), v.getRevenue() * (v.getCuts().get(1).getPercentage())));
		}
		
		graph.getData().addAll(series1, series2);
	}
	
	public StackedBarChart<String, Number> getGraph() {
		return graph;
	}

}
