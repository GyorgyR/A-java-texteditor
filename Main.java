import java.io.File;

//This is the class containing the main method
public class Main {
	public static void main(String[] args)
	{
    	MyTextEditor textEditor = new MyTextEditor();
    	textEditor.setVisible(true);

    	//if there are some arguments given open those files
    	if(args.length > 0)
     		for(int index = 0; index < args.length; index++)
      		{
      			File f = new File(args[index]);
      			textEditor.open(f);
      		}

      	//else just open up a new file
      	else
        	textEditor.newFile();

    	//reading the settings and setting them
    	textEditor.loadSettingsForTextTab();
	} //main
}