import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This a simple java text editor so I will not forget everything that I learned.
*******************************************************************************/
public class MyTextEditor extends JFrame implements ActionListener
{
  //This is the current file the user is working on
  private File currentFile;

  //the current font
  private JTextField fontSize;

  //just a jmenubar
  private JMenuBar menuBar;

  //jmenus user can choose
  //TODO options help credits
  private JMenu file, edit, options, help, credits;

  //jmenuitems in file
  private JMenuItem newfile, open, save, saveas, exit;

  //jmenuitems in options
  private JCheckBoxMenuItem wrapLine, wrapStyle;

  //the jtextarea where the input is
  private JTextArea textArea;


  //constructor
  public MyTextEditor()
  {
    setTitle("AWESOME text editor - New File");

    Container myContainer = getContentPane();
    myContainer.setLayout(new BorderLayout());

    //initializing the text area 
    textArea = new JTextArea(32, 80);

    //Need a menubar
    menuBar = new JMenuBar();

                     /***********************
                        Menus on the bar
                    ************************/
    //file
    file = new JMenu("File");
    menuBar.add(file);

    //options
    options = new JMenu("Options");
    menuBar.add(options);

                    /***********************
                        Menu items in file
                    ************************/
    //new
    newfile = new JMenuItem("New");
    file.add(newfile);
    newfile.addActionListener(this);
    
    //open
    open = new JMenuItem("Open");
    file.add(open);
    open.addActionListener(this);
    
    //save
    save = new JMenuItem("Save");
    file.add(save);
    save.addActionListener(this);
    
    //save as..
    saveas = new JMenuItem("Save as..");
    file.add(saveas);
    saveas.addActionListener(this);
    
    //exit
    exit = new JMenuItem("Exit");
    file.add(exit);
    exit.addActionListener(this);
    
                    /**************************
                       Menu items in options
                    ***************************/
    //font
    JPanel secondBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    options.add(secondBar);
    //fontsize
    JLabel fontLabel = new JLabel("Font size:");
    secondBar.add(fontLabel);
    fontSize = new JTextField(""+textArea.getFont().getSize(),2);
    fontSize.addActionListener(this);
    secondBar.add(fontSize);
    
    //wrapLine
    wrapLine = new JCheckBoxMenuItem("Line Wrapping");
    options.add(wrapLine);
    wrapLine.addActionListener(this);
    wrapLine.setState(textArea.getLineWrap());
    
    //wrap style
    wrapStyle = new JCheckBoxMenuItem("Wrap Words");
    options.add(wrapStyle);
    wrapStyle.addActionListener(this);
    wrapStyle.setEnabled(false);
    
    //setting the jmenubar
    setJMenuBar(menuBar);
    
    //adding in the scroll pane
    JScrollPane scrollPane = new JScrollPane(textArea);
    myContainer.add(scrollPane,BorderLayout.CENTER);
    
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
  
    if(event.getSource() == open)
      open();
  
    if(event.getSource() == save)
      save();
  
    if(event.getSource() == saveas)
      saveas();
  
    if(event.getSource() == exit)
      this.dispose();
   
            /*************************************
                          OPTIONS
            *************************************/
    if(event.getSource() == wrapLine)
    {
      textArea.setLineWrap(wrapLine.getState());
      if(textArea.getLineWrap())
        wrapStyle.setEnabled(true);
      else
        wrapStyle.setEnabled(false);
    }

    if(event.getSource() == wrapStyle)
      textArea.setWrapStyleWord(wrapStyle.getState());
            
            /************************************
                        SECOND BAR
            ************************************/
    if(event.getSource() == fontSize)
      textArea.setFont(textArea.getFont().deriveFont(Float.parseFloat(fontSize.getText())));

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
  } //saveas
  
  public void open()
  {
    //deleting the things already in the textarea
    textArea.setText("");

    JFileChooser fileBrowser = new JFileChooser();

    //valami változós dolog amit nem értek de asszem emiatt jelenik meg
    //áááh értem 
    //visszatérőérték
    //és ez megmondja milyen értékei vannak
    //mint pl approve button
    int returnValue = fileBrowser.showOpenDialog(this);

    if(returnValue == JFileChooser.APPROVE_OPTION)
    {
       currentFile = fileBrowser.getSelectedFile();

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
      } //try
      catch(Exception e)
      {
        
      } //catch
    } //if
    
  } //open
  
  public static void main(String[] args)
  {
      new MyTextEditor().setVisible(true);
  } //main
} //MyTextEditor
