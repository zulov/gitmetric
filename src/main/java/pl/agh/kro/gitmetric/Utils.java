package pl.agh.kro.gitmetric;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * @author Tomek
 */
public class Utils {

    public static void fillList(javax.swing.JList<String> list, Set<String> set) {
        SwingUtilities.invokeLater(() -> {
            list.removeAll();
            DefaultListModel listModel = new DefaultListModel();
            for (String user : set) {
                listModel.addElement(user);
            }
            list.setModel(listModel);
        });

    }

    public static String getExt(String name) {
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return null;
        }
        String ext = name.substring(index);
        return ext;
    }

    public static void addToMap(Map<String, Integer> map, String key, Integer value) {
        if (map.containsKey(key)) {
            Integer a = map.get(key);
            a += value;
            map.put(key, a);
        } else {
            map.put(key, value);
        }
    }

    public static void paintPieChart(javax.swing.JPanel panel, Map<String, Integer> datas) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (String key : datas.keySet()) {
            Integer value = datas.get(key);
            if (value <= 0) {
                continue;
            }
            dataset.setValue(key + " - " + value, value);
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Rozkład rozszerzeń", // chart title                   
                dataset, // data 
                true, // include legend                   
                true,
                false);

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(0);
        plot.setForegroundAlpha(0.75f);
        plot.setInteriorGap(0.05);

//        for (int i = 0; i < datas.size(); i++) {
//            plot.setSectionPaint(datas.get(i).name, partie[i].color);
//        }
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(panel.getSize()));
        chartPanel.setVisible(true);
        panel.setLayout(new java.awt.BorderLayout());
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
    }

    public static void paintTimeSeries(javax.swing.JPanel panel, Map<String, TimeSeries> data) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries ts : data.values()) {
            dataset.addSeries(ts);
        }
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Historia commitów w czasie",
                "Data",
                "Liczba linii",
                dataset,
                true,
                true,
                false);

        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.setForegroundAlpha(0.75f);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(panel.getSize()));
        chartPanel.setVisible(true);
        panel.setLayout(new java.awt.BorderLayout());
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
    }
}
