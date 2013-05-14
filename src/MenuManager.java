/**
 * 
 * @author Craig Knott
 * 			
 */


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuManager {

	protected JMenuBar menuBar;

	private JMenu menu_File;
	private JMenu menu_Edit;
	private JMenu menu_View;
	private JMenu menu_Help;

	protected JMenuItem file_New;
	protected JMenuItem file_Tab;
	protected JMenuItem file_LastTab;
	protected JMenuItem file_Open;
	protected JMenuItem file_Save;
	protected JMenuItem file_Save_As;
	protected JMenuItem file_Close;
	
	protected JMenuItem help_help;

	public MenuManager() {
		super();
	}

	public void createMenu() {

		/*
		 * Create the menu bar and all the menu bar items
		 */

		menuBar = new JMenuBar();

		// Creates each menu and menu item with a Mnemonic and/or an accelerator
		menu_File = new JMenu("File");
		menu_File.setMnemonic('F');

		file_New = new JMenuItem("New");
		file_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		file_New.setMnemonic('N');

		file_Tab = new JMenuItem("New Tab");
		file_Tab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		file_Tab.setMnemonic('T');

		file_LastTab = new JMenuItem("Reopen Last Tab");
		file_LastTab.setAccelerator(javax.swing.KeyStroke.getKeyStroke('T',
				java.awt.event.KeyEvent.CTRL_MASK
						| java.awt.event.KeyEvent.SHIFT_MASK, false));

		file_Open = new JMenuItem("Open");
		file_Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		file_Open.setMnemonic('O');

		file_Save = new JMenuItem("Save");
		file_Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		file_Save.setMnemonic('S');

		file_Save_As = new JMenuItem("Save As");
		file_Save_As.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		file_Save_As.setMnemonic('A');

		file_Close = new JMenuItem("Close");
		file_Close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		file_Close.setMnemonic('W');

		menu_File.add(file_New);
		menu_File.add(file_Tab);
		menu_File.add(file_LastTab);
		menu_File.add(file_Open);
		menu_File.add(file_Save);
		menu_File.add(file_Save_As);
		menu_File.add(file_Close);

		menu_Edit = new JMenu("Edit");
		menu_Edit.setMnemonic('E');

		menu_View = new JMenu("View");
		menu_View.setMnemonic('V');
	
		menu_Help = new JMenu("Help");
		menu_Help.setMnemonic('H');
		
		help_help = new JMenuItem("Help Manual");
		help_help.setMnemonic('P');
		help_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));

		menu_Help.add(help_help);
		
		menuBar.add(menu_File);
		menuBar.add(menu_Edit);
		menuBar.add(menu_View);
		menuBar.add(menu_Help);
	}
}