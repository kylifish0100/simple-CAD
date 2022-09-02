import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputWindow extends JFrame implements ActionListener {
    MyCAD GUIObj;
    JButton button;
    JTextArea textArea;
    private String text1;
    public InputWindow(MyCAD Obj){
        this.GUIObj = Obj;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        button = new JButton("Done");
        button.addActionListener(this);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(300,200));

        this.add(textArea, BorderLayout.NORTH);
        this.add(button, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            text1 = textArea.getText();
            GUIObj.getInputText(this.text1);
            this.dispose();
        }
    }

    public void changeVisible(){
        this.setVisible(true);
    }

    public String getTextAreaText(){
        return text1;
    }

}
