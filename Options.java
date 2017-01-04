import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*************************************************
Author: Gyorgy Rethy
Date: 29/12/2016
--------------------------------------------------
This is the Options screen in MyTextEditor
*************************************************/
public class Options extends JFrame implements ActionListener
{
	//jmenuitems in options
  	private JCheckBox wrapLine, wrapStyle, lineNumbering, autoIndenting;
  	//the current font size
  	private JTextField fontSize, tabSize;
  	//the current font
  	private JComboBox<Font> fontType;

  	//the MyTextEditor class
  	private MyTextEditor mainEditor;

  	//This will hold the icon set to this JFrame
  	ImageIcon programIcon;

	public Options(MyTextEditor editor)
	{
		//set the title
		setTitle("Options");
		//create icon and set it
    	programIcon = new ImageIcon("Data/Icon.gif");
    	setIconImage(programIcon.getImage());
		mainEditor = editor;

		JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane);

		//tab for everything regards fonts
		JPanel font = new JPanel();
		font.setLayout(new GridLayout(0,2));

		//tab for everything regards wrapping
		JPanel wrap = new JPanel();
		wrap.setLayout(new GridLayout(0,1));

		//tab for other options
		JPanel other = new JPanel();
		other.setLayout(new GridLayout(3,2));

		//adding JPanels to the tabbedpane as tabs
		tabbedPane.addTab("Font",font);
		//tabbedPane.addTab("Wrap",wrap);
		tabbedPane.addTab("Other",other);

		//fontsize
		JLabel fontLabel = new JLabel("Font size:");
		font.add(fontLabel);
		fontSize = new JTextField(""+mainEditor.getFontOfArea().getSize());
		font.add(fontSize);
		fontSize.addActionListener(this);

		//font type
		font.add(new JLabel("Font style:"));
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts();
		fontType = new JComboBox<Font>(fonts);
		fontType.setSelectedItem(mainEditor.getFontOfArea().deriveFont(1.0f));
		fontType.setRenderer(new ComboBoxRendererFont());
		fontType.addActionListener(this);
		font.add(fontType);

		//wrapLine
    	wrapLine = new JCheckBox("Line Wrapping");
    	wrap.add(wrapLine);
    	wrapLine.addActionListener(this);
    	//wrapLine.setSelected(textArea.getLineWrap());
    
   		//wrap style
    	wrapStyle = new JCheckBox("Wrap words only.");
    	wrap.add(wrapStyle);
    	wrapStyle.addActionListener(this);
    	wrapStyle.setEnabled(false);

    	//tab size
    	other.add(new JLabel("Tab Size: "));
    	tabSize = new JTextField(""+mainEditor.getTabSize());
    	tabSize.setSize(new Dimension(1,1));
    	other.add(tabSize);
    	tabSize.addActionListener(this);

    	//lineNumbering
    	lineNumbering = new JCheckBox("Numbering Lines");
    	other.add(lineNumbering);
    	lineNumbering.addActionListener(this);

    	//autoindent
    	autoIndenting = new JCheckBox("Auto Indenting");
    	other.add(autoIndenting);
    	autoIndenting.addActionListener(this);


		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	pack();
	} //Options constructor

	//actionPerformed
	public void actionPerformed(ActionEvent event)
	{
		//TODO
		/*
		if(event.getSource() == wrapLine)
    	{
    		textArea.setLineWrap(wrapLine.isSelected());
    		if(textArea.getLineWrap())
    			wrapStyle.setEnabled(true);
    		else
        		wrapStyle.setEnabled(false);
    	}


    	if(event.getSource() == wrapStyle)
    		textArea.setWrapStyleWord(wrapStyle.isSelected());
    	*/

    	if(event.getSource() == fontType)
    	{
    		Font f = (Font) fontType.getSelectedItem();
    		mainEditor.setFontOfArea(f.deriveFont(Float.parseFloat(fontSize.getText())));
    	}

    	if(event.getSource() == fontSize)
    		mainEditor.setFontOfArea(mainEditor.getFontOfArea().deriveFont(Float.parseFloat(fontSize.getText())));

    	if(event.getSource() == tabSize)
    		mainEditor.setTabSize(Integer.parseInt(tabSize.getText()));

    	if(event.getSource() == lineNumbering)
    		mainEditor.setLineNumbering(lineNumbering.isSelected());

    	if(event.getSource() == autoIndenting)
    		mainEditor.setAutoIndenting(autoIndenting.isSelected());
	} // actionPerformed
} //Options

//font


    
    