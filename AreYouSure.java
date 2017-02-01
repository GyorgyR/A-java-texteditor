import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import javax.swing.JFrame;  
import javax.swing.JOptionPane; 

public class AreYouSure extends WindowAdapter {
    MyTextEditor textEditor;

    public AreYouSure(MyTextEditor _textEditor) {
        textEditor = _textEditor;

    } //constructor
        public void windowClosing( WindowEvent e ) {  
            int option = JOptionPane.showOptionDialog(  
                    textEditor,  
                    "Are you sure you want to quit?",  
                    "Exit Dialog", JOptionPane.YES_NO_OPTION,  
                    JOptionPane.WARNING_MESSAGE, null, null,  
                    null );  
            if( option == JOptionPane.YES_OPTION ) {  
                System.exit( 0 );  
            }  
        }  
    }