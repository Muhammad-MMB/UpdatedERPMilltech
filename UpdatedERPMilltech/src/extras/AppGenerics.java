package extras;

import java.awt.Component;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class AppGenerics {

	/**  SET PANEL STATUS & ITS COMPONENTS  **/
	public static void setPanelStatusForComponents(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelStatusForComponents((JPanel) components[i], isEnabled);
			}
			if (components[i] instanceof JCheckBox) {
				JCheckBox chckbox = (JCheckBox) components[i];
				chckbox.setSelected(false);
			}
			components[i].setEnabled(isEnabled);
		}
	}
	
	/**  RETRIEVE USER MACHINE NAME  **/
	public static String getUserSystemName() throws Exception{
		return InetAddress.getLocalHost().getHostName();
	}
	
	/**  CREATE DYNAMIC COMBOBOX MODEL  **/
	public static JComboBox<String> createComboBox(ArrayList<String> items) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for (String item : items) {
			comboBoxModel.addElement(item);
		}
		JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setEditable(true);
		return comboBox;
	}
	
	/** REMOVEL ALL ITEMS OF JTREE **/
	public static void clearAllTreeItems(JTree tree) {
		if (tree.toString() == null) {
			return;
		}
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		if (root != null) {
			root.removeAllChildren();
			model.reload();
		}
	}
	
	/** EXPAND ALL NODES OF JTREE **/
	public static void expandAllNodes(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root));
	}

	/** SET NODES EXAPND STATUS OF JTREE **/
	static void expandAll(JTree tree, TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
			TreePath childPath = path.pathByAddingChild(childNode);
			expandAll(tree, childPath);
		}
		try {
			tree.expandPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
