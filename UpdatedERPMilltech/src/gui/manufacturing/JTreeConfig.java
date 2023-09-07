/**
 * 
 */
package gui.manufacturing;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.icon.EmptyIcon;

/**
 * @author Muhammad
 *
 */
public class JTreeConfig {
	
	/** SET ALL TREE ICONS EMPTY **/
	public static void setEmptyTreeIcons() {
		EmptyIcon emptyIcon = new EmptyIcon();
		UIManager.put("Tree.closedIcon", emptyIcon);
		UIManager.put("Tree.openIcon", emptyIcon);
		UIManager.put("Tree.collapsedIcon", emptyIcon);
		UIManager.put("Tree.expandedIcon", emptyIcon);
		UIManager.put("Tree.leafIcon", emptyIcon);
	}
	
	/** REMOVEL ALL ITEMS/NODES OF JTREE **/
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
