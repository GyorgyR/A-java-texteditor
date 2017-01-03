import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.ArrayList;

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This is a class that contains everything in a text tab
*******************************************************************************/
public class TextTab extends JPanel implements KeyListener{

	//settings variables
	private boolean isNumberingLines = true;
	private boolean isAutoIndenting = true;

	//the texts in variables
	private String[] oldText;
	private String[] newText;
	private StyledDocument currentDocument;

	//the JTextPane that is going to be the place to edit text
	private JTextPane editorArea;

	//the lineNumbering JTextArea
	private JTextArea lineNumbers;

	//the parent JFrame
	JFrame topFrame;

	//boolean to know if text has changed
	private boolean hasChangeInTextSinceLastSave;


	//contructor
	public TextTab(JFrame frame) {
		setLayout(new BorderLayout());

		//initializing JTextArea holding lineNumbers and adding to this JPanel
		lineNumbers = new JTextArea();
		this.add(lineNumbers, BorderLayout.WEST);

		//init editorArea and add to this JPanel
		editorArea = new JTextPane();
		currentDocument = editorArea.getStyledDocument();
		editorArea.addKeyListener(this);
		this.add(editorArea, BorderLayout.CENTER);

		//getting the JFrame of the parent
		topFrame = frame;
		
	} //TextTab constructor

	//keyPressed
	public void keyPressed(KeyEvent e) {
		if(!topFrame.getTitle().contains("*"))
        	hasChangeInTextSinceLastSave = true;
    } // keyPressed

	public void keyReleased(KeyEvent e) {
		setChangeInText();
	} // keyReleased

	public void keyTyped(KeyEvent e){

		if(newText != null) {
			oldText = newText;
		} else
			hasChangeInTextSinceLastSave = false;
		try{
			newText = currentDocument.getText(0,currentDocument.getLength()).split("\\n", -1);
		} catch (BadLocationException exception) {
			exception.printStackTrace();
		}

		//if enter has been pressed indent
		if(e.getKeyChar() == '\n')
			autoIndent();

		displayLineNumbers();
	} // keyTyped

	//the function that does the has text changed
	public void setChangeInText() {
		if( hasChangeInTextSinceLastSave && !oldText.equals(newText)) {
			topFrame.setTitle(topFrame.getTitle()+"*");
			hasChangeInTextSinceLastSave = false;
  	} //if
  } //setChangeInText

    //the function that does the auto indent
  public void autoIndent() {
  	if(isAutoIndenting) {
        int lineNum = 0;
        try {
   			int offset = editorArea.getCaretPosition(); 
   			while (offset > 0) {
      			offset = Utilities.getRowStart(editorArea, offset) - 1;
      			lineNum++;
   			} //while

			} catch (BadLocationException e) {
    			e.printStackTrace();
			} //catch
  	    //string that holds the previous line
  	    String prevLastLine = newText[lineNum-2];

        //boolean because we only get characters from the beginning of the line
        boolean isGettingWhiteSpaces = true;

        //this array list will hold the whitespace characters
  		ArrayList<Character> whitespaces = new ArrayList<Character>();

        //iterating through last line to and collecting whitespaces until something else is found
  		for(int index = 0; index < prevLastLine.length(); index++) {
  			char c = prevLastLine.charAt(index);
  			if(Character.isWhitespace(c) && isGettingWhiteSpaces)
  				whitespaces.add(c);
        else
          isGettingWhiteSpaces = false;
  		}
        //we build the string representation of the whitespace
        String whitespacesToInsert = "";
  		for(char whitespace : whitespaces)
            whitespacesToInsert += whitespace;
        try {
        	currentDocument.insertString(editorArea.getCaretPosition(), whitespacesToInsert, null);
        } catch(BadLocationException e) {
        	e.printStackTrace();
        }

  	} //if
  } // autoIndent

  public void displayLineNumbers() {
  	if(isNumberingLines) {
  		//set the textarea 
  		lineNumbers.setColumns(3);
  		lineNumbers.setFont(editorArea.getFont());
  		lineNumbers.setText("");

  		//variable that holds the number of lines
  		int noOfLines = newText.length ;

  		//add numbers to the text area
  		if(noOfLines < 100)
    		for(int lineNumber = 1 ; lineNumber <= noOfLines; 
    															lineNumber++)
    			lineNumbers.append(String.format("%3d %n",lineNumber));
    	else if (noOfLines < 1000)
    		for(int lineNumber = 1 ; lineNumber <= noOfLines; 
    															lineNumber++)
    			lineNumbers.append(String.format("%4d %n",lineNumber));
    	else
    		for(int lineNumber = 1 ; lineNumber <= noOfLines; 
    															lineNumber++)
    			lineNumbers.append(String.format("%5d %n",lineNumber));
	}
	else {
		//set text to nothing and shrink it down to zero
		lineNumbers.setText("");
		lineNumbers.setColumns(0);
	}
	
	Rectangle r = topFrame.getBounds();
	topFrame.setPreferredSize(new Dimension(r.width,r.height));
	topFrame.pack();

  } // displayLineNumbers

} //TextTab