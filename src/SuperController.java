/**
 * 
 * @author Craig Knott
 * 			
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class SuperController {

	JFrame frame;
	private JTabbedPane systemTabs;
	private int counter = 1;

	private ArrayList<SubController> closedTabs;

	private ArrayList<SubController> subControllers;

	public SuperController() {

		subControllers = new ArrayList<SubController>();
		closedTabs = new ArrayList<SubController>();

		// Creates system frame

		frame = new JFrame();
		frame.setTitle("Burn Down Chart Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1005, 760);
		frame.setResizable(false);

		// Create the tabbed pane
		systemTabs = new JTabbedPane();
		systemTabs.addContainerListener(new assignContainerListener());

		// Assign listeners
		systemTabs.addMouseListener(new PopClickListener());

		// Add initial tab
		SubController sc = new SubController(this, counter, "Tab " + counter);
		systemTabs.add(sc.getMainPanel());
		systemTabs.getComponents()[0].setName(sc.getTabName());
		systemTabs.setTitleAt(0, sc.getTabName());
		subControllers.add(sc);
		counter++;
		updatePositions();

		// Display tabbed pane and frame
		frame.add(systemTabs);
		frame.setVisible(true);

	}

	/*
	 * Tab Methods
	 */

	public void addNewTab() {
		// Add new new to system
		SubController sc = new SubController(this, counter, "Tab " + counter);

		systemTabs.add(sc.getToolBar());
		try {
			systemTabs.getComponentAt(subControllers.size()).setName(
					sc.getTabName());
			systemTabs.setTitleAt(subControllers.size(), sc.getTabName());
		} catch (IndexOutOfBoundsException e) {
			/* Silently deal with this exception */
		}
		subControllers.add(sc);

		counter++;
		updatePositions();

		SwingUtilities.updateComponentTreeUI(frame);
		systemTabs.setSelectedIndex(systemTabs.getTabCount() - 1);

		/*
		 * For some reason, when pressing "CTRL+T" to get a new tab, the table
		 * will /ONLY/ be displayed if this is run (twice).
		 */
		SwingUtilities.updateComponentTreeUI(frame);
		System.out.println("why");
	}

	public void removeTab(int i) {
		// Close tab at index
		systemTabs.removeTabAt(i);

		closedTabs.add(subControllers.get(i));
		subControllers.remove(i);

		for (SubController sub : subControllers) {
			sub.getView().enableRecallTab(true);
		}

		updatePositions();

		if (systemTabs.getTabCount() == 0) {
			System.exit(0);
		}
	}

	public void recallTab() {
		/*
		 * Reopen the last closed tab. Works like a stack
		 */
		if (!(closedTabs.size() == 0)) {
			SubController sc = closedTabs.get(closedTabs.size() - 1);
			closedTabs.remove(closedTabs.size() - 1);
			systemTabs.addTab(sc.getTabName(), sc.getToolBar());
			subControllers.add(sc);

			for (int i = 0; i < systemTabs.getComponents().length; i++) {
				systemTabs.setTitleAt(i,
						systemTabs.getComponents()[i].getName());

			}
			counter++;
			updatePositions();

			SwingUtilities.updateComponentTreeUI(frame);
			systemTabs.setSelectedIndex(systemTabs.getTabCount() - 1);

			// For some reason, when pressing "CTRL+T" to get a new tab, the
			// table
			// will /ONLY/ be displayed if this is run (twice).
			SwingUtilities.updateComponentTreeUI(frame);

			if (closedTabs.size() == 0) {
				for (SubController sub : subControllers) {
					sub.getView().enableRecallTab(false);
				}
			}
		}
	}

	/*
	 * Other methods
	 */

	public void updatePositions() {
		/*
		 * Updates the positioning and indexing of items in the array. This is
		 * so, when an item is deleted from the tabbed pane, no index out of
		 * bounds errors occur
		 */
		for (int i = 0; i < subControllers.size(); i++) {
			subControllers.get(i).setTabIndex(i);
		}
	}

	/*
	 * Sub class used to correctly rename docked and undocked windows
	 */

	class assignContainerListener implements ContainerListener {

		public void componentAdded(ContainerEvent arg0) {
			try {
				for (int i = 0; i < systemTabs.getComponents().length; i++) {
					systemTabs.setTitleAt(i,
							systemTabs.getComponents()[i].getName());

				}
			} catch (ArrayIndexOutOfBoundsException e) {
				/*
				 * By catching this error, Java suddenly twigs that a new
				 * component has been added and that it's not out of bounds any
				 * more. How does this witch craft work..?
				 */
			}
		}

		public void componentRemoved(ContainerEvent arg0) {
		}

	}

	/*
	 * Sub class used to add functionality to the pop up menu
	 */

	class assignAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("newTab_al")) {
				addNewTab();
				SwingUtilities.updateComponentTreeUI(frame);
			} else if (e.getActionCommand().equals("renTab_al")) {
				String newTitle = JOptionPane.showInputDialog(null,
						"Please enter a new name for this tab",
						systemTabs.getTitleAt(systemTabs.getSelectedIndex()));

				systemTabs.setTitleAt(systemTabs.getSelectedIndex(), newTitle);

				subControllers.get(systemTabs.getSelectedIndex()).setTabName(
						newTitle);

				systemTabs.getComponentAt(systemTabs.getSelectedIndex())
						.setName(newTitle);

			} else if (e.getActionCommand().equals("closeTab_al")) {
				removeTab(systemTabs.getSelectedIndex());
			} else if (e.getActionCommand().equals("reopenTab_al")) {
				recallTab();
			}

		}
	}

	/*
	 * Sub class used to create a pop up menu on the tabbed pane
	 */

	@SuppressWarnings("serial")
	class PopUpMenu extends JPopupMenu {

		private JMenuItem newTab;
		private JMenuItem reopenTab;
		private JMenuItem renameTab;
		private JMenuItem closeTab;

		public PopUpMenu() {

			newTab = new JMenuItem("New Tab");
			newTab.addActionListener(new assignAction());
			newTab.setActionCommand("newTab_al");

			reopenTab = new JMenuItem("Reopen Last Tab");
			reopenTab.addActionListener(new assignAction());
			reopenTab.setActionCommand("reopenTab_al");

			closeTab = new JMenuItem("Close Tab");
			closeTab.addActionListener(new assignAction());
			closeTab.setActionCommand("closeTab_al");

			renameTab = new JMenuItem("Rename Tab");
			renameTab.addActionListener(new assignAction());
			renameTab.setActionCommand("renTab_al");

			add(newTab);
			add(reopenTab);
			add(renameTab);
			add(closeTab);

		}

		public void enableReopen(boolean b) {
			reopenTab.setEnabled(b);
		}
	}

	/*
	 * Sub class used to check for mouse presses on the tabbed panes
	 */

	class PopClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				createMenu(e);
			}

		}

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				createMenu(e);
			}
		}

		private void createMenu(MouseEvent e) {
			PopUpMenu menu = new PopUpMenu();

			menu.enableReopen(closedTabs.size() >= 1);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	/*
	 * Main Method - attempts to use Nimbus theme, and creates super controller
	 * object
	 */

	public static void main(String[] args) {

		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (Exception e) {
					System.err
							.println("Nimbus GUI Cannot be found, resorting to default :(");
				}
				break;
			}
		}

		@SuppressWarnings("unused")
		SuperController superController = new SuperController();

	}

}
