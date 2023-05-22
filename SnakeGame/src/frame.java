import javax.swing.*;

public class frame extends JFrame
{
    frame()
    {
        // sets the title of the frame
        this.setTitle("snake");

        //  adding panel to the frame
        this.add(new panel());

        // this property is false by default
        this.setVisible(true);

        // this property is true by default
        this.setResizable(false);

        // pack() will force the panel to be at least 1200 and 600
        this.pack();
    }
}
