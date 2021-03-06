import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This a simple java text editor so I will not forget everything that I learned.
*******************************************************************************/
public class MyTextEditor extends JFrame implements ActionListener
{

  //This will hold the icon set to this JFrame
  private ImageIcon programIcon;

  //just a jmenubar
  private JMenuBar menuBar;

  //jmenus user can choose
  //TODO edit help credits
  private JMenu file, edit, help, credits;

  //jmenuitems in file
  private JMenuItem newfile, open, save, saveas, saveall, exit;

  //jmenuitems in edit
  private JMenuItem options;

  //options variables and default settings
  File settings = new File("Data/Settings");
  private boolean isAutoIndentOn, isNumberingLinesOn, isHighDPIOn = false;
  private boolean isBracketCompletionOn, isSpacesVsTabsOn = false;
  private int fontSizes = 14;
  private float tabFontSize = 14.0f;
  private int tabSize = 8;
  private Font fontUsed = new Font("Monospaced", Font.PLAIN, 16);
  private Font fontUsedB = new Font("Monospaced.plain", Font.PLAIN, 16);
  //private boolean saved = false;

  //the JScrollPane holding the JTabbedPane
  JScrollPane scrollPane;

  //the JTabbedPane holding all the TextTabs
  JTabbedPane tabs;

  //an emptyborder
  EmptyBorder emptyb = new EmptyBorder(0,0,0,0);

  //Colours used in the application
  Color initBackgroundColor = new Color(5,35,65);
  Color initBackgroundColorDarker = new Color(3,25,45);
  Color initForeGroundColor = new Color(236,238,225);
  Color scrollPaneBackground = new Color(4,8,50);

  //constructor
  public MyTextEditor()
  {
  	//the container used in this JFrame
    Container myContainer = getContentPane();
    myContainer.setLayout(new BorderLayout());

    //Need a menubar
    menuBar = new JMenuBar();

  	//set the look and feel
  	try
  	{
  		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  		//System.out.println(UIManager.getSystemLookAndFeelClassName());
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}

  	//loading the setings for the editor
  	loadSettingsForEditor();

  	//calculating the fonts for high dpi
  	if(isHighDPIOn) {
  		fontSizes = 24;
  		tabFontSize = 20.0f;
  	} //if

    //UI settings go here
    //tweak UIManager settings
    UIManager.put("Menu.font", new Font("Arial", Font.PLAIN, fontSizes));
    UIManager.put("MenuItem.font", new Font("Arial", Font.PLAIN, fontSizes));
    UIManager.put("TextArea.background",initBackgroundColorDarker);
    UIManager.put("TextArea.disabledTextColor", Color.GRAY);
    UIManager.put("TextArea.foreground",initForeGroundColor);
    UIManager.put("TextArea.caretForeground",initForeGroundColor);
    UIManager.put("TextPane.background",initBackgroundColor);
    UIManager.put("TextPane.foreground",initForeGroundColor);
    UIManager.put("TextPane.caretForeground",initForeGroundColor);
    UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
    UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);


    setTitle("AWESOME text editor");

    //create icon and set it
    programIcon = new ImageIcon("Data/Icon.gif");
    setIconImage(programIcon.getImage());

                     /***********************
                        Menus on the bar
                    ************************/
    //file
    file = new JMenu("File");
    menuBar.add(file);

    //edit
    edit = new JMenu("Edit");
    menuBar.add(edit);

                    /***********************
                        Menu items in file
                    ************************/
    //new
    newfile = new JMenuItem("New File");
    newfile.setAccelerator(KeyStroke.getKeyStroke(
    	KeyEvent.VK_N,ActionEvent.CTRL_MASK));
    newfile.addActionListener(this);
    file.add(newfile);
    
    //open
    open = new JMenuItem("Open File...");
    open.setAccelerator(KeyStroke.getKeyStroke(
    	KeyEvent.VK_O,ActionEvent.CTRL_MASK));
    open.addActionListener(this);
    file.add(open);
    
    //save
    save = new JMenuItem("Save File");
    save.setAccelerator(KeyStroke.getKeyStroke(
    	KeyEvent.VK_S,ActionEvent.CTRL_MASK));
    save.addActionListener(this);
    file.add(save);

    //saveall
    saveall = new JMenuItem("Save all");
    //saveall.setAccelerator(KeyStroke.getKeyStroke(
      //KeyEvent.VK_S, KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    saveall.addActionListener(this);
    file.add(saveall);
    
    //save as..
    saveas = new JMenuItem("Save File as..");
    saveas.setAccelerator(KeyStroke.getKeyStroke(
    	KeyEvent.VK_S,ActionEvent.ALT_MASK));
    saveas.addActionListener(this);
    file.add(saveas);
    
    //exit
    exit = new JMenuItem("Exit");
    file.add(exit);
    exit.addActionListener(this);

                    /**************************
                       Menu items in edit
                    ***************************/

    //options
    options = new JMenuItem("Options");
    options.addActionListener(this);
    edit.add(options);

    //setting the jmenubar
    setJMenuBar(menuBar);

    //initializing tabs
    tabs = new JTabbedPane();
    //set border for this tabbedpane
    tabs.setBorder(emptyb);

    //adding in the scroll pane
    JScrollPane scrollPane = new JScrollPane(tabs);
    //change background color
    scrollPane.getViewport().setBackground(scrollPaneBackground);

    myContainer.add(scrollPane,BorderLayout.CENTER);

    //making adjustments to the widow before opening
    myContainer.setPreferredSize(new Dimension(600,800));
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new AreYouSure(this));
    pack();
  } //MyTextEditor constructor
  
  public void actionPerformed(ActionEvent event)
  {
            /*************************************
                           FILE
            *************************************/
    if(event.getSource() == newfile)
      newFile();
  
    if(event.getSource() == open) {
    	JFileChooser fileBrowser = new JFileChooser();

    	//valami változós dolog amit nem értek de asszem emiatt jelenik meg
    	//áááh értem 
    	//visszatérőérték
    	//és ez megmondja milyen értékei vannak
    	//mint pl approve button
    	int returnValue = fileBrowser.showOpenDialog(this);

    	if(returnValue == JFileChooser.APPROVE_OPTION)
       		open(fileBrowser.getSelectedFile());
       	else {}
    }
  
    if(event.getSource() == save)
      save();

    if(event.getSource() == saveall)
      saveAll();
  
    if(event.getSource() == saveas)
      saveas();
  
    if(event.getSource() == exit)
      this.dispose();
   
            /*************************************
                          EDIT
            *************************************/

    if(event.getSource() == options)
    	new Options(this).setVisible(true);
  } //actionPerformed
//------------------------------------------------------------------------------

  			/***********************************
  		   			Settings Save-Load
  			************************************/

  public void saveSettings() {
  	//try catch block for the fileWriter
  	try {
  		FileWriter fileWriter = new FileWriter(settings);

  		//font
  		fileWriter.write("Font: ");
  		fileWriter.write(settingsFontToString(fontUsed));
  		fileWriter.write(System.lineSeparator());

  		//tab size
  		fileWriter.write("TabSize: ");
  		fileWriter.write(""+tabSize);
  		fileWriter.write(System.lineSeparator());

  		//using space instead of tabulator
  		fileWriter.write("SpaceVsTab: ");
  		fileWriter.write(""+isSpacesVsTabsOn);
  		fileWriter.write(System.lineSeparator());

  		//numbering lines
  		fileWriter.write("LineNumbering: ");
  		fileWriter.write(""+isNumberingLinesOn);
  		fileWriter.write(System.lineSeparator());

  		//auto indent
  		fileWriter.write("AutoIndent: ");
  		fileWriter.write(""+isAutoIndentOn);
  		fileWriter.write(System.lineSeparator());

  		//bracket completion
  		fileWriter.write("BracketCompletion: ");
  		fileWriter.write(""+isBracketCompletionOn);
  		fileWriter.write(System.lineSeparator());

  		//high dpi
  		fileWriter.write("HighDPI: ");
  		fileWriter.write(""+isHighDPIOn);
  		fileWriter.write(System.lineSeparator());


  		fileWriter.close();
  	} catch(Exception e) {
  		e.printStackTrace();
  	}

  } //saveSettings

  public void loadSettingsForTextTab() {

    //setting everything to default
    try {setFontOfArea(fontUsedB);}
    catch(Exception e) {setFontOfArea(fontUsed);}

    setLineNumbering(isNumberingLinesOn);
    setAutoIndenting(isAutoIndentOn);
    setHighDPI(isHighDPIOn,false);
    setTabSize(tabSize);

    //now change to what settings has
  	//try catch block for FileReader
  	try {
  		BufferedReader reader = new BufferedReader(new FileReader(settings));
  		String line = reader.readLine();

  		while(line != null) {
  			String[] settings = line.split(" ");

  			//a big if statement collection
  			if(settings[0].equals("Font:")) 
  				setFontOfArea(settingsReadFont(settings[1]));

  			if(settings[0].equals("TabSize:"))
  				setTabSize(Integer.parseInt(settings[1]));

  			if(settings[0].equals("SpaceVsTab:"))
  				setSpaceVsTabs(Boolean.parseBoolean(settings[1]));

  			if(settings[0].equals("LineNumbering:"))
  				setLineNumbering(Boolean.parseBoolean(settings[1]));

  			if(settings[0].equals("AutoIndent:"))
  				setAutoIndenting(Boolean.parseBoolean(settings[1]));

  			if(settings[0].equals("BracketCompletion:"))
  				setBracketCompletion(Boolean.parseBoolean(settings[1]));

  			line = reader.readLine();
  		} //while
 
  	} catch(Exception e) {
  		e.printStackTrace();
  	}
  } //loadSettingsForTextTabs

  public void loadSettingsForEditor() {
  	//try catch block for reading the settings file
  	try {
  		BufferedReader reader = new BufferedReader(new FileReader(settings));
  		String line = reader.readLine();

  		while(line != null) {
  			//splitting into setting names and values
  			String[] settings = line.split(" ");

  			//just a bunch of if statements
  			if(settings[0].equals("HighDPI:"))
  				isHighDPIOn = Boolean.parseBoolean(settings[1]);
  			line = reader.readLine();
  		} //while

  	} catch(Exception e) {
  		e.printStackTrace();
  	}
  } //loadSettingsForEditor

  public Font settingsReadFont(String line) {
  	String[] font = line.split(",");
  	int style = Integer.parseInt(font[1]);
  	int size = Integer.parseInt(font[2]);
  	return new Font(font[0],style,size);
  } //settingsReadFont\

  public String settingsFontToString(Font f) {
  	return f.getName()+","+f.getStyle()+","+f.getSize(); 
  } //settingFontToString

  			/***********************************
  							Getters
  			************************************/

  public boolean getLineNumbering() {
  	return isNumberingLinesOn;
  } //getLineNumbering

  public boolean getAutoIndent() {
  	return isAutoIndentOn;
  } //getAutoIndent

  public boolean getHighDPI() {
  	return isHighDPIOn;
  } //getHighDPI

  public boolean getBracketCompletion() {
  	return isBracketCompletionOn;
  }

  public TextTab getSelectedTab() {
    return (TextTab)tabs.getSelectedComponent();
  } //getSelectedTab

  public int getTabSize() {
    return getSelectedTab().getTabSize();
  } //getTabSize

  public boolean getSpaceVsTab() {
  	return isSpacesVsTabsOn;
  } //getSpaceVsTab

  public Font getFontOfArea() {
    return getSelectedTab().getFontOfArea();
  } //getFont

  public boolean getActiveTabSaved() {

    boolean isAllSaved = true;

    //going through all the tabs and if there is at least one which was not saved 
    //then we will return false 
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        if(!tab.getIsSaved())
          isAllSaved = false;
    } //for

    return isAllSaved;
  }

  			/***********************************
  							Setters
  			************************************/

  public void setHighDPI(boolean isOn, boolean save) {
  	isHighDPIOn = isOn;
    if(save)
  		saveSettings();
  } //setHighDPI

  //function to set the line numbering for all tabs
  public void setLineNumbering(boolean isOn) {
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setLineNumbering(isOn);
    } //for

    //storing for the setting file
    isNumberingLinesOn = isOn;
  } //setLineNumbering

  public void setAutoIndenting(boolean isOn) {
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setAutoIndent(isOn);
    } //for

    //storing for the setting file
    isAutoIndentOn = isOn;
  } //setAutoIndenting

  public void setBracketCompletion(boolean isOn) {
  	for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setBracketCompletion(isOn);
    } //for

    //storing for the setting file
    isBracketCompletionOn = isOn;
  }

  public void setTabSize(int size) {
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setTabSize(size);
    } //for

    //storing for the setting file
    tabSize = size;
  } //setTabSize

  public void setSpaceVsTabs(boolean isOn) {
  	for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setSpaceVsTabs(isOn);
    } //for

    //storing for the setting file
    isSpacesVsTabsOn = isOn;
  } //setSpaceVsTabs

  public void setFontOfArea(Font font) {
    if(getSelectedTab() != null)
        getSelectedTab().setFontOfArea(font);

    //storing for the setting file
    fontUsed = font;
  } //setFont

  public void setColorTheme(ColorTheme colours) {
    

  } //setColorTheme

  			/***********************************
  			  			File Actions
  			************************************/
    
  //the function that starts a new file
  public void newFile()
  {
    tabs.add(new TextTab(this,null,tabs,tabs.getTabCount()));
    tabs.setTabComponentAt(tabs.getTabCount()-1, 
    								new ButtonTabComponent(tabs, tabFontSize));

    //set the active tab to the new one
    tabs.setSelectedIndex(tabs.getTabCount()-1);

    //load the settings for the new text tab also
    loadSettingsForTextTab();
  } //newFile
    
  //the function that does save
  public void save()
  {
    //here we will get the save method from the active 
    getSelectedTab().save();
  } //save

  public void saveAll() {
    for(int index = 0; index < tabs.getTabCount(); index++) {
      TextTab tab = (TextTab)tabs.getComponentAt(index);
      tab.saveThisTab(index);
    } //for
  } //saveAll
  
  //the function that does save as
  public void saveas()
  {
    //getting the active tab component and call saveas in there
    getSelectedTab().saveas();
  } //saveas
  
  //this method opens the curently selected file
  public void open(File file)
  {
    //add a new tab with the file opened
    tabs.add(new TextTab(this,file,tabs,tabs.getTabCount()));
    tabs.setTabComponentAt(tabs.getTabCount()-1, 
    								new ButtonTabComponent(tabs, tabFontSize));

    //set the active tab to the new one
    tabs.setSelectedIndex(tabs.getTabCount()-1);

    //load the settings for the new text tab also
    loadSettingsForTextTab();
  } //open
} //MyTextEditor
