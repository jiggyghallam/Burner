/**
 * 
 * @author Craig Knott
 * 			
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

public class SubController {

	// Set up system counters
	private int currentIndex;

	// Set up system flags
	private boolean unsavedChanges = false;
	public boolean fileOpen = false;

	// File chooser for file I/O
	private JFileChooser jfc;
	private String filePath;

	// Set up the model, view and frame to be used by the controller
	private Model model;
	private View view;

	private SuperController sc;
	protected int tabIndex;
	protected String tabName;

	public SubController(SuperController sc, int i, String tabName) {

		this.sc = sc;
		this.tabIndex = i;
		this.tabName = tabName;

		/*
		 * Create the model and the view. Passes the table model to the view to
		 * be displayed
		 */

		model = new Model();
		view = new View(model.getTableModel());
		view.setJToolBarName(tabName);

		/*
		 * Assign action listeners, menu listeners, and property change
		 * listeners
		 */
		view.addMenuListeners(new assignMenuListener());
		view.addFocusListeners(new assignFocusListener());
		view.addActionListeners(new buttonListeners());
		
		currentIndex = 0;

	}

	/*
	 * Data retrieval Methods
	 */

	public JToolBar getToolBar() {
		return view.getToolBar();
	}

	public JPanel getMainPanel() {
		return view.getMainViewPanel();
	}

	public View getView() {
		return this.view;
	}

	public String getTabName() {
		return this.tabName;
	}

	/*
	 * Data allocation methods
	 */

	public void setTabIndex(int i) {
		this.tabIndex = i;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	/*
	 * Sub classes used to assign listeners to the elements of the system
	 */
	
	class buttonListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getActionCommand().equals("add_task_al")){
				
				String[] params = { "Task Name", "Task Length" };
				
				InputDialogue id = new InputDialogue("New Task Input", params);
				
				String[] out = id.getFields();
				
				try {
					int time = Integer.valueOf(out[1]);
					Task t = new Task(out[0], time);
					
					model.addNewTask(t);
					
					Object[] newRow = new Object[2];
					newRow[0] = t.getName();
					newRow[1] = t.getTime();
					model.addTableRow(newRow);

				//	view.resizeTable();
					
				} catch (NumberFormatException nfe){
					JOptionPane
					.showMessageDialog(
							null,
							"Please make sure you are entering a number for the time field",
							"Number Required", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
			
		}
		
	}

	class assignFocusListener implements FocusListener {

		/*
		 * Selecting all text in an input box when selecting it
		 */

		public void focusGained(FocusEvent arg0) {
			((JTextComponent) arg0.getComponent()).selectAll();
		}

		public void focusLost(FocusEvent arg0) {
		}

	}

	class assignMenuListener implements ActionListener {
		/*
		 * Sub class used to assign action listeners to menu items
		 */

		public void actionPerformed(ActionEvent m) {
			if (m.getActionCommand().equals("file_New_ml")) {
				// file -> New
				newFile();
			} else if (m.getActionCommand().equals("file_Tab_ml")) {
				// file -> New Tab
				sc.addNewTab();
				SwingUtilities.updateComponentTreeUI(sc.frame);
			} else if (m.getActionCommand().equals("file_LastTab_ml")) {
				// file -> Reopen last tab
				sc.recallTab();
			} else if (m.getActionCommand().equals("file_Open_ml")) {
				// File -> Open
				openFile();
			} else if (m.getActionCommand().equals("file_Save_ml")) {
				// File -> Save
				if (fileOpen) {
					saveFile();
				} else {
					saveNewFile();
				}
			} else if (m.getActionCommand().equals("file_Save_As_ml")) {
				// File -> Save As
				saveNewFile();

			} else if (m.getActionCommand().equals("file_Close_ml")) {
				// File -> Close
				closeTab();

			} else if (m.getActionCommand().equals("help_help_ml")) {
				// Help -> Help Manual
				launchHelpMenu();
			} 
			}

		/*
		 * Sub methods used to split up the action listener
		 */

		public void newFile() {
		}

		public void openFile() {
		}

		public void saveFile() {
		}

		public void saveNewFile() {
		}

		public void closeTab() {
			if (unsavedChanges) {
				// Checks whether the user is about to lose unsaved data
				int unsavedChangesWarning = JOptionPane
						.showConfirmDialog(
								null,
								"You have unsaved changes, are you sure you wish to exit?",
								"Unsaved warning", JOptionPane.WARNING_MESSAGE);
				if (!(unsavedChangesWarning == JOptionPane.CANCEL_OPTION)) {
					sc.removeTab(tabIndex);
				}
			} else {
				sc.removeTab(tabIndex);
			}
		}

		public void launchHelpMenu() {
			@SuppressWarnings("unused")
			HelpMenuManager hm = new HelpMenuManager();
		}

	}

}
