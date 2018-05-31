import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Histogram {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Double[] data = new Double[256];
                for (int r = 0; r < data.length; r++) {
                    data[r] = (256 * Math.random());
                }
                
                new Histogram(data, 30);
            }
        });
    }
    
    private double step;
    private double max;
    
     public Histogram(Double[] data, int columns) {
        max = 0;
        for (Double data1 : data) {
            if (data1 > max) {
                max = data1;
            }
        }
         step = max / columns;
         
        Map<Integer, Integer> mapHistory = new TreeMap<>();
        for (Double data1 : data) {
            int index = (int) (data1 / step);
            int amount = 0;
            if (mapHistory.containsKey(index)) {
                amount = mapHistory.get(index);
                amount++;
            } else {
                amount = 1;
            }
            mapHistory.put(index, amount);
        }         
        setupMapHistory(mapHistory);         
     }
    
    private void setupMapHistory(Map<Integer, Integer> mapHistory){
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(new Graph(mapHistory)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }

    protected class Graph extends JPanel {

        protected static final int MIN_BAR_WIDTH = 4;
        private Map<Integer, Integer> mapHistory;

        public Graph(Map<Integer, Integer> mapHistory) {
            this.mapHistory = mapHistory;
            int width = (mapHistory.size() * MIN_BAR_WIDTH) + 11;
            Dimension minSize = new Dimension(width, 128);
            Dimension prefSize = new Dimension(width, 256);
            setMinimumSize(minSize);
            setPreferredSize(prefSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            AffineTransform oldXForm;
            if (mapHistory != null) {
                int xOffset = 5;
                int yOffset = 5;
                int width = getWidth() - 1 - (xOffset * 2);
                int height = getHeight() - 1 - (yOffset * 2);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setFont(new Font("default", Font.BOLD, 16));
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(xOffset, yOffset, width, height);
                int barWidth = Math.max(MIN_BAR_WIDTH,  (int) Math.floor((float) width / (float) mapHistory.size()));
                int maxValue = 0;
                for (Integer key : mapHistory.keySet()) {
                    int value = mapHistory.get(key);
                    maxValue = Math.max(maxValue, value);
                }
                int xPos = xOffset;
                for (Integer key : mapHistory.keySet()) {
                    int value = mapHistory.get(key);
                    int barHeight = Math.round(((float) value / (float) maxValue) * height);
                    g2d.setColor(Color.BLACK);
                    int yPos = height + yOffset - barHeight;
                    //Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
                    Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
                    g2d.fill(bar);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.draw(bar);
                    g2d.setColor(Color.GREEN);
                    oldXForm = g2d.getTransform();
                    g2d.setTransform(AffineTransform.getRotateInstance(Math.toRadians(270), xPos+15, height));
                    g2d.drawString(">"+String.format( "%.2f",step*key), xPos+15, height);
                    g2d.setTransform(oldXForm);
                    xPos += barWidth;
                }
                g2d.dispose();
            }
        }
    }
}