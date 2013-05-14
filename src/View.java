/**
 * 
 * @author Craig Knott
 * 			
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class View extends InteractionManager {

	private JSplitPane left_splitPane;
	private JSplitPane main_splitPane;

	private JPanel mainViewPanel;
	private JToolBar toolbar;

	public View(DefaultTableModel dtm) {
		/*
		 * Creation of the visual elements of the system Split into sub-methods
		 * for ease of reading
		 */

		mainViewPanel = new JPanel();
		toolbar = new JToolBar("derp");

		createMenu();
		createTaskListManager(dtm);
		createInteractionManager();
		createChartArea();

		left_splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				taskListPanel, interaction_Panel);
		left_splitPane.setDividerLocation(600);
		left_splitPane.setOneTouchExpandable(true);

		main_splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				left_splitPane, map_Viewer_Panel);
		main_splitPane.setDividerLocation(300);
		main_splitPane.setOneTouchExpandable(true);

		mainViewPanel.setLayout(new BorderLayout());
		mainViewPanel.add(main_splitPane);

		toolbar.add(mainViewPanel);
		toolbar.setOrientation(0);
		toolbar.setMaximumSize(new Dimension(1000, 1000));
		toolbar.setMinimumSize(new Dimension(1000, 1000));

		mainViewPanel.add(menuBar, BorderLayout.NORTH);

		enableSaving(false);
		enableRecallTab(false);
	}

	/*
	 * Add listener methods
	 */

	public void addFocusListeners(FocusListener fl){

	}
	
	public void addMenuListeners(ActionListener al) {
		/*
		 * Assigning action listeners to the menu items, each is assigned an
		 * actionCommand so they can be differentiated
		 */

		file_New.addActionListener(al);
		file_New.setActionCommand("file_New_ml");

		file_Tab.addActionListener(al);
		file_Tab.setActionCommand("file_Tab_ml");

		file_LastTab.addActionListener(al);
		file_LastTab.setActionCommand("file_LastTab_ml");

		file_Open.addActionListener(al);
		file_Open.setActionCommand("file_Open_ml");

		file_Save.addActionListener(al);
		file_Save.setActionCommand("file_Save_ml");

		file_Save_As.addActionListener(al);
		file_Save_As.setActionCommand("file_Save_As_ml");

		file_Close.addActionListener(al);
		file_Close.setActionCommand("file_Close_ml");

		help_help.addActionListener(al);
		help_help.setActionCommand("help_help_ml");
	}

	public void addPropertyChangeListeners(PropertyChangeListener pcl) {
		left_splitPane.addPropertyChangeListener(pcl);
	}

	public void addActionListeners(ActionListener al) {
		/*
		 * Assigning action listeners to the buttons, each is assigned an
		 * actionCommand so they can be differentiated
		 */
		add_task.setActionCommand("add_task_al");
		add_task.addActionListener(al);
	}

	/*
	 * Data Retrieval Methods
	 */
	public JToolBar getToolBar() {
		return toolbar;
	}

	public JPanel getMainViewPanel() {
		return mainViewPanel;
	}

	public void drawMapViewer() {
		map_Viewer_Panel.removeAll();

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 640;
		c.ipady = 640;
		c.gridwidth = 2;

		map_Viewer_Panel.add(new JPanel(), c);
	}

	public void enableSaving(boolean b) {
		// Toggles the enabled status of saving
		file_Save.setEnabled(b);
		file_Save_As.setEnabled(b);
	}

	public void enableRecallTab(boolean b) {
		file_LastTab.setEnabled(b);
	}

	/*
	 * Setter Methods
	 */

	public void setJToolBarName(String name) {
		// sets name of toolbar
		toolbar.setName(name);
	}

	public void setFloatable(boolean b) {
		// Sets the toolbar to be floatable
		toolbar.setFloatable(b);
	}

	public void setFileName(String string) {
		// TODO Auto-generated method stub
		
	}


}
