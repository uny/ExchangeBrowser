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

import panel.ExchangeChartPanelFactory;

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
        dataList_ = loadExchangeData(filename);
        getContentPane().add(ExchangeChartPanelFactory.createChartPanel(dataList_), BorderLayout.CENTER);
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
}
