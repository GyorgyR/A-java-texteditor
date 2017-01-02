import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*************************************************
Author: Gyorgy Rethy
Date: 29/12/2013
--------------------------------------------------
This is the Options screen in MyTextEditor
*************************************************/
public class Options extends JFrame implements ActionListener
{
	//jmenuitems in options
  	private JCheckBox wrapLine, wrapStyle, displayLineNumbersBox;
  	//the current font size
  	private JTextField fontSize, tabSize;
  	//the current font
  	private JComboBox<Font> fontType;
  	private String fontDir = "Fonts";

  	private JTextArea textArea;

	public Options(JTextArea _textArea)
	{
		textArea = _textArea;

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
		other.setLayout(new GridLayout(1,0));

		//adding JPanels to the tabbedpane as tabs
		tabbedPane.addTab("Font",font);
		tabbedPane.addTab("Wrap",wrap);
		tabbedPane.addTab("Other",other);

		//fontsize
		JLabel fontLabel = new JLabel("Font size:");
		font.add(fontLabel);
		fontSize = new JTextField(""+textArea.getFont().getSize());
		font.add(fontSize);
		fontSize.addActionListener(this);

		//font type
		font.add(new JLabel("Font style:"));
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts();
		fontType = new JComboBox<Font>(fonts);
		fontType.setSelectedItem(textArea.getFont().deriveFont(1.0f));
		fontType.setRenderer(new ComboBoxRendererFont());
		fontType.addActionListener(this);
		font.add(fontType);

		//wrapLine
    	wrapLine = new JCheckBox("Line Wrapping");
    	wrap.add(wrapLine);
    	wrapLine.addActionListener(this);
    	wrapLine.setSelected(textArea.getLineWrap());
    
   		//wrap style
    	wrapStyle = new JCheckBox("Wrap words only.");
    	wrap.add(wrapStyle);
    	wrapStyle.addActionListener(this);
    	wrapStyle.setEnabled(false);

    	//tab size
    	other.add(new JLabel("Tab Size: "));
    	tabSize = new JTextField(""+textArea.getTabSize());
    	tabSize.setSize(new Dimension(1,1));
    	other.add(tabSize);
    	tabSize.addActionListener(this);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	pack();
	} //Options constructor

	//actionPerformed
	public void actionPerformed(ActionEvent event)
	{
		//TODO
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

    	if(event.getSource() == fontType)
    	{
    		Font f = (Font) fontType.getSelectedItem();
    		textArea.setFont(f.deriveFont(Float.parseFloat(fontSize.getText())));
    	}

    	if(event.getSource() == fontSize)
    		textArea.setFont(textArea.getFont().deriveFont(Float.parseFloat(fontSize.getText())));

    	if(event.getSource() == tabSize)
    		textArea.setTabSize(Integer.parseInt(tabSize.getText()));
	} // actionPerformed
} //Options

//font


    
    