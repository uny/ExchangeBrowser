/**
 * 
 */
package main;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * @author ynagai
 *
 */
public class ExchangeFrame extends JFrame {
    private static final long serialVersionUID = -21104437397340170L;
    // -----------------
    // Private Variables
    // -----------------
    private ArrayList<String[]> dataList_;
    // -----------
    // Constructor
    // -----------
    public ExchangeFrame() {
        System.err.println("Error: No input filename.");
        System.exit(-1);
    }
    public ExchangeFrame(String filename) {
        dataList_ = new ArrayList<String[]>();
        TimeSeriesCollection collection = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("為替");
        dataList_ = loadExchangeData(filename);
        addDataToSeries(dataList_, series);
        collection.addSeries(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("為替相場", "日付", "為替相場", collection, true, false, false);
        chart.removeLegend();
        ChartPanel chartPanel = new ChartPanel(chart);
        getContentPane().add(chartPanel, BorderLayout.CENTER);
    }
    // --------------
    // Private Method
    // --------------
    private ArrayList<String[]> loadExchangeData(String filename) {
        ArrayList<String[]> dataList = new ArrayList<String[]>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] date_rate = line.split(",");
                if (date_rate.length == 2) {
                    Pattern pattern = Pattern.compile("^[0-9.]*$");
                    Matcher matcher = pattern.matcher(date_rate[1]);
                    if (matcher.find()) {
                        dataList.add(date_rate);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    private void addDataToSeries(ArrayList<String[]> dataList, TimeSeries series) {
        for (String[] strings : dataList) {
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
}
