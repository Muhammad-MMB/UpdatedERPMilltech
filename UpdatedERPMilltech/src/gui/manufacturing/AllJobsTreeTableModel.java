package gui.manufacturing;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class AllJobsTreeTableModel extends AbstractTreeTableModel implements TreeTableModel {
	private String[] columnNames;

	public AllJobsTreeTableModel(DefaultMutableTreeTableNode root, String[] columnNames) {
		super(root);
		this.columnNames = columnNames;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof DefaultMutableTreeTableNode) {
			DefaultMutableTreeTableNode treeNode = (DefaultMutableTreeTableNode) node;
			if (column == 0) {
				return ((AllJobsDataModel) treeNode.getUserObject()).getName();
			} else if (column == 1) {
				return ((AllJobsDataModel) treeNode.getUserObject()).getDescription();
			}
		}
		return null;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((DefaultMutableTreeTableNode) parent).getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((DefaultMutableTreeTableNode) parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((DefaultMutableTreeTableNode) node).isLeaf();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) parent;
		DefaultMutableTreeTableNode childNode = (DefaultMutableTreeTableNode) child;
		return parentNode.getIndex(childNode);
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return false;
	}
}
