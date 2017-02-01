import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*********************************************************
Author: Gyorgy Rethy
Date: 29/12/2016
----------------------------------------------------------
This is a custom renderer for the font JComboBox 
*********************************************************/
public class ComboBoxRendererFont extends JLabel implements ListCellRenderer<Object>
{
	private JLabel labelItem = new JLabel();
	public ComboBoxRendererFont() 
	{
    	setOpaque(true);
    }

	@Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus)
    {
    	Font fonts = (Font) value;

    	//the selected items index is -1
    	if (index == -1)
    		setFont(fonts.deriveFont(16.0f));
        
        String textOnLabel = fonts.getFontName();
        setText(textOnLabel);
        setFont(fonts.deriveFont(16.0f));
        return this;
    }
		
}