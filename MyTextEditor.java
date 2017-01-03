import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.util.ArrayList;

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This a simple java text editor so I will not forget everything that I learned.
*******************************************************************************/
public class MyTextEditor extends JFrame implements ActionListener, KeyListener
{
  //This is the current file the user is working on
  private static File currentFile;

  //This will hold the icon set to this JFrame
  ImageIcon programIcon;

  //variables about the text
  private boolean hasChangeInTextSinceLastSave;
  

  //just a jmenubar
  private JMenuBar menuBar;

  //jmenus user can choose
  //TODO edit help credits
  private JMenu file, edit, help, credits;

  //jmenuitems in file
  private JMenuItem newfile, open, save, saveas, exit;

  //jmenuitems in edit
  private JMenuItem options;
  private JCheckBoxMenuItem displayLineNumbersBox;
  private JCheckBoxMenuItem autoIndent;

  //options variables
  public boolean isAutoIndentOn;
  private float fontSizes = 14.0f;


  //the jtextarea where the input is
  private JTextArea textArea;

  //JPanle to hold labels for line numbers
  private JTextArea lineNumbers;

  //Colours used in the application
  Color initBackgroundColor = new Color(5,30,65);
  Color initForeGroundColor = new Color(236,238,225);

  //constructor
  public MyTextEditor()
  {
  	try
  	{
  		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}

    setTitle("AWESOME text editor - New File");

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
    //line numbering
    // a JPanel for linenumber labels
    lineNumbers = new JTextArea();
    lineNumbers.setEnabled(false);
    lineNumbers.setFont(lineNumbers.getFont().deriveFont(fontSizes));
    displayLineNumbersBox = new JCheckBoxMenuItem("Display Line Numbers");
    displayLineNumbersBox.addActionListener(this);
    edit.add(displayLineNumbersBox);

    //auto indentation
    autoIndent = new JCheckBoxMenuItem("Auto Indent");
    autoIndent.addActionListener(this);
    edit.add(autoIndent);

    //options
    options = new JMenuItem("Options");
    options.addActionListener(this);
    edit.add(options);

    //setting the jmenubar
    setJMenuBar(menuBar);
    
    //initializing the text area 
    textArea = new JTextArea(30,20);
    textArea.addKeyListener(this);

    //Jpanel to hold the line numbers and the textarea
    JPanel scrollabletextAreas = new JPanel();
    scrollabletextAreas.setLayout(new BorderLayout());
    scrollabletextAreas.add(lineNumbers, BorderLayout.WEST);
    scrollabletextAreas.add(textArea, BorderLayout.CENTER);

    //adding in the scroll pane
    JScrollPane scrollPane = new JScrollPane(scrollabletextAreas);
    myContainer.add(scrollPane,BorderLayout.CENTER);

    //making adjustments to the widow before opening
    textArea.setFont(textArea.getFont().deriveFont(fontSizes));
    //textArea.setBackground(initBackgroundColor);
    //textArea.setForeground(initForeGroundColor);
    myContainer.setPreferredSize(new Dimension(600,800));
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();

    //this is where I will load in the settings
    //now I just initialize variables
    isAutoIndentOn = false;
    autoIndent.setState(isAutoIndentOn);
    hasChangeInTextSinceLastSave = false;
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
       		currentFile = fileBrowser.getSelectedFile();
      	open();
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

    if(event.getSource() == displayLineNumbersBox)
    	displayLineNumbers();

    if(event.getSource() == autoIndent)
    	isAutoIndentOn = autoIndent.getState();

    if(event.getSource() == options)
    	new Options(textArea).setVisible(true);
  } //actionPerformed
    
  //the function that starts a new file
  public void newFile()
  {
    currentFile = null;
    setTitle("AWESOME text editor - New File");
    textArea.setText("");
  } //newFile
    
  //the function that does save
  public void save()
  {
    //if this is the first save need to know where to save
    if (currentFile == null)
      saveas();
    else
    {
      try(FileWriter writer = new FileWriter(currentFile))
      {
        textArea.write(writer);
      } //try
      catch (Exception e)
      {
       
      } //catch
    } //else
    hasChangeInTextSinceLastSave = false;
    setTitle("AWESOME text editor - "+currentFile.getName());
  } //save
  
  //the function that does save as
  public void saveas()
  {
    JFileChooser fileBrowser = new JFileChooser();

    //the return value of the jfilechooser
    int returnValue = fileBrowser.showSaveDialog(this);

    if(returnValue == JFileChooser.APPROVE_OPTION)
    {
      try(FileWriter writer = new FileWriter(fileBrowser.getSelectedFile()))
      { 
        textArea.write(writer);
        currentFile = fileBrowser.getSelectedFile();
        setTitle("AWESOME text editor - "+currentFile.getName());
      } //try
      catch (Exception e)
      {
      
      } //catch
    } //if
    hasChangeInTextSinceLastSave = false;
  } //saveas
  
  //this method opens the curently selected file
  public void open()
  {
    //deleting the things already in the textarea
    textArea.setText("");

      //reading the file
      try
      {
        BufferedReader reader = new BufferedReader(new FileReader(currentFile));
        
        //reading a string then appending it to t5he text area
        String line = reader.readLine();
        textArea.append(line);

        //while there is a next line append it to the textarea
        //with a lineseparator
        while(line != null)
        {
          //add lineseparator
          textArea.append(System.lineSeparator()); 
          //append the line
          textArea.append(line);
          //read the next line
          line = reader.readLine();
        } //while
        
        reader.close();
        
        setTitle("AWESOME text editor - "+currentFile.getName());
        hasChangeInTextSinceLastSave = false;
      } //try
      catch(Exception e)
      {
        
      } //catch
    
  } //open

  
  
  public static void main(String[] args)
  {
      MyTextEditor textEditor = new MyTextEditor();
      textEditor.setVisible(true);
      if(args.length > 0)
      	for(int index = 0; index < args.length; index++)
      	{
      		currentFile = new File(args[index]);
      		textEditor.open();
      	}
  } //main
} //MyTextEditor