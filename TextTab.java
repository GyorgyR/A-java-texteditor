import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This is a class that contains everything in a text tab
*******************************************************************************/
public class TextTab extends JPanel implements KeyListener{

	//settings variables
	private boolean isNumberingLines;
	private boolean isAutoIndenting;

	//the texts in variables
	private String[] oldText;
	private String[] newText;

	//the JTextPane that is going to be the place to edit text
	private JTextPane editorArea;

	//the lineNumbering textarea
	private JTextArea lineNumbers;


	//contructor
	public TextTab() {
		pack();
	} //TextTab constructor
} //TextTab