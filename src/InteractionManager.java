/**
 * 
 * @author Craig Knott
 *  			
 */


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JPanel;

public class InteractionManager extends ChartArea {

	protected JPanel interaction_Panel;
	
	protected JButton add_task;
	
	public InteractionManager() {
		super();
	}

	public void createInteractionManager() {
		/*
		 * Create new_panel, located on the right of the screen
		 */

		interaction_Panel = new JPanel();

		interaction_Panel.setLayout(new GridBagLayout());
		interaction_Panel.setBorder(BorderFactory
				.createTitledBorder("Interactions"));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		c.weightx = 10;
		c.weighty = 10;
		c.anchor = GridBagConstraints.NORTH;
		
		add_task = new JButton("Add Task");
		
		interaction_Panel.add(add_task,c);
		
	}
}