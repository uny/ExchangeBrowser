/**
 * 
 */
package panel;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * @author ynagai
 *
 */
public class ExchangeChartPanelFactory {
    // ----------------
    // Private Variable
    // ----------------
    // -----------
    // Constructor
    // -----------
    public ExchangeChartPanelFactory() {
    }
    // -------------
    // Public Method
    // -------------
    public static ChartPanel createChartPanel(ArrayList<String[]> dataList) {
        TimeSeriesCollection collection = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("為替");
        // datalist -> series
        addDataToSeries(dataList, series);
        collection.addSeries(series);
        ChartPanel panel = new ChartPanel(createChart(collection));
        // Zoom by mouse wheel
        panel.setMouseWheelEnabled(true);
        return panel;
    }
    // --------------
    // Private Method
    // --------------
    private static void addDataToSeries(ArrayList<String[]> dataList, TimeSeries series) {
        for (String[] strings : dataList) {
            // Get day, month, year
            String[] date = strings[0].split("/");
            try {
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                series.add(new Day(day, month, year), Double.parseDouble(strings[1]));
            } catch (NumberFormatException e) {
                // Do nothing
            }
        }
        return;
    }
    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "為替相場", // Title
                "日付", // x-axis Label
                "為替相場", // y-axis Label
                dataset, // Dataset
                true, // Create Legend?
                true, // Generate tooltips?
                false); // Generate URLs?
        XYPlot plot = (XYPlot) chart.getPlot();
        // Show the point near click
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
//        XYItemRenderer r = plot.getRenderer();
//        if (r instanceof XYLineAndShapeRenderer) {
//            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
//            renderer.setBaseShapesVisible(true);
//            renderer.setBaseShapesFilled(true);
//            renderer.setDrawSeriesLineAsPath(false);
//        }
        chart.removeLegend();
        return chart;
    }
}
