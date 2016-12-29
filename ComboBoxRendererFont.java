
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*********************************************************
Author: Gyorgy Rethy
Date: 29/12/2016
----------------------------------------------------------
This is a custom renderer for the font JComboBox 
*********************************************************/
public class ComboBoxRendererFont extends JLabel implements ListCellRenderer
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
        String textOnLabel = fonts.getFontName();
        //labelItem.setText(textOnLabel);
        setText(textOnLabel);
        return this;
    }
		
}