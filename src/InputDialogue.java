import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InputDialogue {
	
	protected JTextField[] fields;
	
	public InputDialogue(String title, String[] params){
		
		fields = new JTextField[params.length];
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		c.weightx = 10;
		c.anchor = GridBagConstraints.NORTH;

		JPanel panel = new JPanel(new GridBagLayout());
		
		for(int i = 0; i < params.length; i++){
			fields[i] = new JTextField("", 20);
			c.gridy = i;
			
			c.gridx = 0;
			panel.add(new JLabel(params[i]), c);
			
			c.gridx = 1;
			
			panel.add(fields[i], c);
		}
		
		JOptionPane.showMessageDialog(null, panel,
				title, JOptionPane.PLAIN_MESSAGE);
	}
	
	public String[] getFields(){
		
		String[] out = new String[fields.length];
		for(int i = 0; i < fields.length; i++){
			out[i] = fields[i].getText();
		}
		
		return out;
	}
}
