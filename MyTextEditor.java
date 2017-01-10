import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
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
  ImageIcon programIcon;

  //just a jmenubar
  private JMenuBar menuBar;

  //jmenus user can choose
  //TODO edit help credits
  private JMenu file, edit, help, credits;

  //jmenuitems in file
  private JMenuItem newfile, open, save, saveas, exit;

  //jmenuitems in edit
  private JMenuItem options;

  //options variables
  public boolean isAutoIndentOn;
  private float fontSizes = 16.0f;


  //the JScrollPane holding the JTabbedPane
  JScrollPane scrollPane;

  //the JTabbedPane holding all the TextTabs
  JTabbedPane tabs;

  //Colours used in the application
  Color initBackgroundColor = new Color(5,30,65);
  Color initForeGroundColor = new Color(236,238,225);

  //constructor
  public MyTextEditor()
  {
  	try
  	{
  		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  		System.out.println(UIManager.getSystemLookAndFeelClassName());
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}

    setTitle("AWESOME text editor");

    Container myContainer = getContentPane();
    myContainer.setLayout(new BorderLayout());

    //Need a menubar
    menuBar = new JMenuBar();

    //UI settings go here
    //tweak UIManager settings
    UIManager.put("Menu.font",menuBar.getFont().deriveFont(fontSizes));
    UIManager.put("TextArea.background",initBackgroundColor);
    UIManager.put("TextArea.disabledBackground", Color.GRAY);
    UIManager.put("TextArea.foreground",initForeGroundColor);
    UIManager.put("TextArea.caretForeground",initForeGroundColor);
    UIManager.put("TextPane.background",initBackgroundColor);
    UIManager.put("TextPane.foreground",initForeGroundColor);
    UIManager.put("TextPane.caretForeground",initForeGroundColor);

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
    file.add(newfile);
    newfile.addActionListener(this);
    
    //open
    open = new JMenuItem("Open File...");
    file.add(open);
    open.addActionListener(this);
    
    //save
    save = new JMenuItem("Save File");
    file.add(save);
    save.addActionListener(this);
    
    //save as..
    saveas = new JMenuItem("Save File as..");
    file.add(saveas);
    saveas.addActionListener(this);
    
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

    //adding in the scroll pane
    JScrollPane scrollPane = new JScrollPane(tabs);
    myContainer.add(scrollPane,BorderLayout.CENTER);

    //making adjustments to the widow before opening
    myContainer.setPreferredSize(new Dimension(600,800));
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

  public TextTab getSelectedTab() {
    return (TextTab)tabs.getSelectedComponent();

  } //getSelectedTab

  //function to set the line numbering for all tabs
  public void setLineNumbering(boolean isOn) {
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setLineNumbering(isOn);
    }
  } //setLineNumbering

  public void setAutoIndenting(boolean isOn) {
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setAutoIndent(isOn);
    }
  } //setAutoIndenting

  public void setTabSize(int size) {
    for(int index = 0; index < tabs.getTabCount(); index++) {
        TextTab tab = (TextTab) tabs.getComponentAt(index);
        tab.setTabSize(size);
    }
  } //setTabSize

  public int getTabSize() {
    return getSelectedTab().getTabSize();
  } //getTabSize

  public void setFontOfArea(Font font) {
    if(getSelectedTab() != null)
        getSelectedTab().setFontOfArea(font);
  } //setFont

  public Font getFontOfArea() {
    return getSelectedTab().getFontOfArea();
  } //getFont
    
  //the function that starts a new file
  public void newFile()
  {
    tabs.add(new TextTab(this,null,tabs));
    tabs.setTabComponentAt(tabs.getTabCount()-1, new ButtonTabComponent(tabs));

    //set the active tab to the new one
    tabs.setSelectedIndex(tabs.getTabCount()-1);
  } //newFile
    
  //the function that does save
  public void save()
  {
    //here we will get the save method from the active 
    getSelectedTab().save();
  } //save
  
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
    tabs.add(new TextTab(this,file,tabs));
    tabs.setTabComponentAt(tabs.getTabCount()-1, new ButtonTabComponent(tabs));

    //set the active tab to the new one
    tabs.setSelectedIndex(tabs.getTabCount()-1);
  } //open

  
  
  public static void main(String[] args)
  {
      MyTextEditor textEditor = new MyTextEditor();
      textEditor.setVisible(true);
      if(args.length > 0)
      	for(int index = 0; index < args.length; index++)
      	{
      		File f = new File(args[index]);
      		textEditor.open(f);
      	}
      if(args.length == 0)
        textEditor.newFile();
  } //main
} //MyTextEditor
