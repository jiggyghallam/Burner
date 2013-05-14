/**
 * 
 * @author Craig Knott
 * 			
 */

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class Model {

	/*
	 * Data storage object of the system interacts with the controller to add,
	 * and pass back data to be further passed to the view
	 */

	private ArrayList<Task> TaskList;
	private DefaultTableModel table_dataModel;

	// Used to also store table data, for updating purposes
	private ArrayList<Object[]> tableData;

	public Model() {
		TaskList = new ArrayList<Task>();

		// Create table model
		String[] columnNames = { "Task", "Time" };
		table_dataModel = new DefaultTableModel(columnNames, 0);
		tableData = new ArrayList<Object[]>();
	}

	/*
	 * Information getting methods
	 */

	public int getArraySize() {
		return TaskList.size();
	}

	public Task getGPSPoint(int i) {
		return TaskList.get(i);
	}

	public DefaultTableModel getTableModel() {
		return table_dataModel;
	}
	
	/*
	 * Data Manipulation method
	 */

	public void setTask(Task t, int i) {
		TaskList.set(i, t);
	}

	public void addNewTask(String name, int time) {
		Task t = new Task(name, time);
		TaskList.add(t);
	}

	public void addNewTask(Task t){
		TaskList.add(t);
	}
	
	public void updateTable() {
		// Updates all values in the table after an edit to information
		for (int i = table_dataModel.getRowCount() - 1; i >= 0; i--) {
			table_dataModel.removeRow(i);
		}

		for (Object[] data : tableData) {
			table_dataModel.addRow(data);
		}
	}

	public void addTableRow(Object[] data) {
		// Adds a new row to the table model
		table_dataModel.addRow(data);
	}

	public void truncateArray() {
		// Delete all way points
		TaskList.clear();
	}

	public void truncateTable() {
		// Deletes all values in the table
		for (int i = table_dataModel.getRowCount() - 1; i >= 0; i--) {
			table_dataModel.removeRow(i);
		}
		tableData.clear();
	}

	public void fireTableStructureChanged() {
		// Updates the size of the JTable
		table_dataModel.fireTableStructureChanged();
	}

	public void removeTaskAtIndex(int i) {
		TaskList.remove(i);
		table_dataModel.removeRow(i);
	}

	/*
	 * Other methods
	 */

	public void taskEditted() {
		/*
		 * Recreates and redisplays the JTable when a task is changed to
		 * reflect these changes
		 */
		tableData.clear();

		for (Task t : TaskList) {
			Object[] temp = { t.getName(), t.getTime() };
			tableData.add(temp);
		}

	}
}
