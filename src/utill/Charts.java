package utill;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Charts extends JPanel{

    static XYSeries series = new XYSeries("Positive Patients");
    static XYSeries series2 = new XYSeries("Vaccinated Patients");
    static XYSeries series3 = new XYSeries("Dead Patients");
    static JLabel lbl = new JLabel();

    static Map<String, Integer> data = new HashMap();

    public static XYSeries getSeries(){
        return series;
    }

    public static XYSeries getSeries2(){
        return series2;
    }

    public static XYSeries getSeries3(){
        return series3;
    }

    private XYDataset createDataset(XYSeries s) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s);
        return dataset;
    }

    static void updateLbl(){
        String res = "<html>";
        for (String name: data.keySet()) {
            String key = name;
            String value = data.get(name).toString();
            res+=key + " " + value + "<br/>";
        }
        res+="</html>";
        lbl.setText(res);
    }

    public Charts(){
        XYDataset dataset = createDataset(series);
        XYDataset dataset2 = createDataset(series2);
        XYDataset dataset3 = createDataset(series3);
        ChartPanel chart = createChart(dataset, Color.RED);
        ChartPanel chart2 = createChart(dataset2, Color.GREEN);
        ChartPanel chart3 = createChart(dataset3, Color.BLACK);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel mainPanel2 = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel p1 = new JPanel();
        chart.setBackground(Color.white);
        p1.add(chart);
        JPanel p2 = new JPanel();
        chart2.setBackground(Color.white);
        p2.add(chart2);
        JPanel p3 = new JPanel();
        chart3.setBackground(Color.white);
        p3.add(chart3);
        JPanel p4 = new JPanel();
        lbl.setFont(new Font(lbl.getFont().getName(), Font.PLAIN, 18));
        p4.setSize(300,250);
        p4.setBorder(BorderFactory.createLineBorder(Color.black));
        p4.add(lbl);

        mainPanel.add(p1);
        mainPanel.add(p2);
        mainPanel2.add(p3);
        mainPanel2.add(p4);
        this.add(mainPanel);
        this.add(mainPanel2);
    }
    private ChartPanel createChart(XYDataset dataset, Color c) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "Days",
                "Number of people",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, c);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("",
                        new Font("Serif", Font.BOLD, 15)
                )
        );

        return new ChartPanel(chart) {
            public Dimension getPreferredSize() {
                return new Dimension(250, 250);
            }
        };
    }
}
