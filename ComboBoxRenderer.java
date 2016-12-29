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
	public Component getListCellRendererComponent(JList list,
	                                              Object value,
	                                              int index,
	                                              boolean isSelected,
	                                              boolean cellHasFocus)
	{

		//int selectedItem = ((Integer)value).intValue();

		String fontType = value.getFontName();
		setText(fontType);
		return this;
	}
		
}