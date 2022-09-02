import DrawElements.DrawElement;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MeasurementDisplay extends JFrame {

    MyCAD GUIObj;
    JTextArea textArea;
    private String text1;

    public MeasurementDisplay(MyCAD Obj){
        this.GUIObj = Obj;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(300,200));

        this.add(textArea, BorderLayout.NORTH);
        this.pack();
        this.setVisible(false);
    }

    public void display(DrawElement d){
        if(d==null)
            return;
        System.out.println(d);
        Map<String,Double> measure_map = d.getMeasurement();
        for (Map.Entry<String,Double> entry : measure_map.entrySet())
            this.textArea.append(entry.getKey() + " = " + entry.getValue() + "\n");
        this.setVisible(true);
    }
}
