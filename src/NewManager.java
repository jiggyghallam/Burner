/**
 * 
 * @author Craig Knott
 *  			
 */


import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;

import javax.swing.JPanel;

public class NewManager extends ChartArea {

	protected JPanel new_Panel;
	
	public NewManager() {
		super();
	}

	public void createNewManager() {
		/*
		 * Create new_panel, located on the right of the screen
		 */

		new_Panel = new JPanel();
		//new_Panel.setMinimumSize(new Dimension(660, 660));

		new_Panel.setLayout(new GridBagLayout());
		new_Panel.setBorder(BorderFactory
				.createTitledBorder("Future Expansion"));

	}
}