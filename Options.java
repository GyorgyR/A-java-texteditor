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
  	private JCheckBox wrapLine, wrapStyle, lineNumbering, autoIndenting, highdpi;
  	private JCheckBox bracketCompletion;
  	//the current font size
  	private JTextField fontSize, tabSize;
  	//the current font
  	private JComboBox<Font> fontType;

  	//a button to save the settings
  	private JButton saveSettings;

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

		//the main class 
		mainEditor = editor;

		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());

		//the tabbed pane which holds the tabs for options
		JTabbedPane tabbedPane = new JTabbedPane();
		cont.add(tabbedPane, BorderLayout.CENTER);

		//creating the jpanel where the save settings button will be
		JPanel strip = new JPanel();
		cont.add(strip, BorderLayout.SOUTH);

		//the saveSettings button
		saveSettings = new JButton("Save Settings");
		saveSettings.addActionListener(this);
		strip.add(saveSettings);


		//tab for everything regards fonts
		JPanel font = new JPanel();
		font.setLayout(new GridLayout(0,2));

		//tab for everything regards wrapping
		JPanel wrap = new JPanel();
		wrap.setLayout(new GridLayout(0,1));

		//tab for other options
		JPanel other = new JPanel();
		other.setLayout(new GridLayout(0,1));

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
    	//a jpanel holds the label and the textfield
    	JPanel tabPanel = new JPanel();
    	tabPanel.setLayout(new GridLayout(0,2));
    	tabPanel.add(new JLabel("Tab Size: "));
    	tabSize = new JTextField(""+mainEditor.getTabSize());
    	tabSize.setSize(new Dimension(1,1));
    	tabPanel.add(tabSize);
    	tabSize.addActionListener(this);
    	other.add(tabPanel);

    	//lineNumbering
    	lineNumbering = new JCheckBox("Numbering Lines");
    	other.add(lineNumbering);
    	lineNumbering.addActionListener(this);
    	lineNumbering.setSelected(mainEditor.getLineNumbering());

    	//autoindent
    	autoIndenting = new JCheckBox("Auto Indenting");
    	other.add(autoIndenting);
    	autoIndenting.addActionListener(this);
    	autoIndenting.setSelected(mainEditor.getAutoIndent());

    	//bracketCompletion
    	bracketCompletion = new JCheckBox("Bracket Completion");
    	other.add(bracketCompletion);
    	bracketCompletion.addActionListener(this);
    	bracketCompletion.setSelected(mainEditor.getBracketCompletion());

    	//highdpi
    	highdpi = new JCheckBox("High-DPI Monitor");
    	highdpi.addActionListener(this);
    	highdpi.setSelected(mainEditor.getHighDPI());
    	other.add(highdpi);


		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	pack();
	} //Options constructor

	//actionPerformed
	public void actionPerformed(ActionEvent event)
	{
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

    	if(event.getSource() == bracketCompletion)
    		mainEditor.setBracketCompletion(bracketCompletion.isSelected());

    	if(event.getSource() == highdpi)
    		mainEditor.setHighDPI(highdpi.isSelected(),true);

    	if(event.getSource() == saveSettings)
    		mainEditor.saveSettings();
	} // actionPerformed
} //Options