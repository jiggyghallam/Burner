/**
 * 
 * @author Craig Knott
 *  			
 */

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

import javax.swing.JPanel;

public class ChartArea extends TaskListManager {

	protected JPanel map_Viewer_Panel;

	public ChartArea() {
		super();
	}

	public void createChartArea() {
		/*
		 * Create map_viewer_panel, located on the right of the screen
		 */

		map_Viewer_Panel = new JPanel();
		map_Viewer_Panel.setMinimumSize(new Dimension(75, 660));

		map_Viewer_Panel.setLayout(new GridBagLayout());
		map_Viewer_Panel.setBorder(BorderFactory
				.createTitledBorder("Chart Area"));

	}
}