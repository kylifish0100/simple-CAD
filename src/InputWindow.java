import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputWindow extends JFrame implements ActionListener {
    JButton button;
    JTextArea textArea;
    LabelElement label;
    public InputWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        button = new JButton("Done");

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
            label.getInputText(textArea.getText());
        }
    }

    public void changeVisible(){
        this.setVisible(true);
    }

}
