import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class HelpMenuManager extends JPanel {

	private static final long serialVersionUID = 1L;

	BufferedReader reader;
	FileInputStream input;
	String inputLine;
	JTextArea helpInformationDisplay;
	String[] topicContent;
	String[] topics;

	final String defaultString = "Welcome to the help menu.\nTo begin, click one of the topics on the left";

	public HelpMenuManager() {

		topics = new String[2];

		// Creates an array with the names of the help topics
		topics[0] = "One";
		topics[1] = "Two";

		// Create display panel
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		c.weightx = 10;
		c.weighty = 10;
		c.anchor = GridBagConstraints.NORTH;

		// Reads in the information stored in the help file
		StringBuilder sb = new StringBuilder();

		try {
			input = new FileInputStream("help/helpFile.help");

			reader = new BufferedReader(new InputStreamReader(input));

			while ((inputLine = reader.readLine()) != null) {
				sb.append(inputLine + "\n");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to read in help file",
					"Read in error", JOptionPane.ERROR_MESSAGE);
		}

		topicContent = sb.toString().split("<help>");

		// Creates a tree of the help topics
		DefaultMutableTreeNode root = processHierarchy(getHierarchy());
		JTree tree = new JTree(root);
		tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
				treeValueChanged(evt);
			}
		});

		helpInformationDisplay = new JTextArea(25, 30);
		helpInformationDisplay.setText(defaultString);
		helpInformationDisplay.setLineWrap(true);
		helpInformationDisplay.setEditable(false);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(tree), new JScrollPane(helpInformationDisplay));

		panel.add(splitPane, c);

		JOptionPane.showMessageDialog(null, panel, "Help Menu",
				JOptionPane.PLAIN_MESSAGE);

	}

	public Object[] getHierarchy() {
		/*
		 * Creates an Object[] representing the hierarchy of the help menu
		 */
		Object[] oneHierarchy = { "One" };
		Object[] twoHierarchy = { "Two" };
		Object[] threeHierarchy = { "Three" };
		Object[] fourHierarchy = { "Five" };

		Object[] hierarchy = { "Root", oneHierarchy, twoHierarchy,
				threeHierarchy, fourHierarchy, "Some node" };

		return hierarchy;

	}

	private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		/*
		 * Takes an Object[] hierarchy, and extracts the elements to create the
		 * tree
		 */

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		DefaultMutableTreeNode child;

		for (int i = 1; i < hierarchy.length; i++) {
			Object nodeSpecifier = hierarchy[i];
			if (nodeSpecifier instanceof Object[]) {
				// Parent Nodes
				child = processHierarchy((Object[]) nodeSpecifier);
			} else {
				// Leaf Nodes
				child = new DefaultMutableTreeNode(nodeSpecifier);

			}
			node.add(child);
		}
		return (node);
	}

	public void treeValueChanged(TreeSelectionEvent tse) {
		String node = tse.getNewLeadSelectionPath().getLastPathComponent()
				.toString();

		boolean set = false;

		for (int i = 0; i < topics.length; i++) {
			if (node.equals(topics[i])) {
				helpInformationDisplay.setText(topicContent[i]);
				set = true;
			}
		}

		if (!(set)) {
			helpInformationDisplay.setText(defaultString);
		}
	}
}
