/**
 * 
 * @author Craig Knott
 *  			
 */


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TaskListManager extends MenuManager {

	protected JPanel taskListPanel;
	protected JTable taskListTable;
	protected Dimension table_Dimensions;

	public TaskListManager() {
		super();
	}

	public void createTaskListManager(DefaultTableModel tableModel) {
		taskListPanel = new JPanel();
		taskListPanel.setLayout(new GridBagLayout());
		taskListPanel.setBorder(BorderFactory
				.createTitledBorder("Task List"));
		taskListPanel.setMinimumSize(new Dimension(150, 150));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

		taskListTable = new JTable();
		taskListTable.setModel(tableModel);
		taskListTable.setEnabled(false);
		taskListTable.setAutoResizeMode(JTable.HEIGHT);
		table_Dimensions = new Dimension(taskListTable.getWidth(), 0);
		taskListTable
				.setPreferredScrollableViewportSize(table_Dimensions);
		c.weightx = 10;
		c.weighty = 10;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		taskListPanel.add(new JScrollPane(taskListTable), c);
	}

}