package gui.manufacturing;

/**
 * @author Muhammad
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import dao.DaoBomRoute;
import entities.TblBomRoute;
import extras.AppConstants;
import extras.AppGenerics;
import extras.LoadResource;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.JScrollPane;

public class SetupJob extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel pnlTop;
	private JLabel lblSelectBomRoute;
	private JTree treeBomRoute;
	private JComboBox<ComboBoxIdValuePair> cmboBoxShowBomroute;
	private DefaultMutableTreeNode routeJTreeRootNode;

	static int SANDBOX_GROUP_ID = -1;
	static final String FIRST_CONCAT_PART = " @ ";
	static final String SECOND_CONCAT_PART = " || ";

	/** CLASSES OBJECTS **/
	DaoBomRoute daoBomRouteObject;
	private JButton btnViewDetails;
	private JScrollPane scrollPaneRouteJTree;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_VIEW_DETAILS
	}

	public SetupJob() {

		/** FRAME PROPERTIES **/
		this.setTitle("Setup Job");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(setFrameBannerIcon());
		setBounds(100, 100, 1651, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoBomRouteObject = new DaoBomRoute();

		/** SETUP GUI **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/** CREATE & SETUP GUI **/
	private void createAndShowGUI() {

		pnlTop = new JPanel();
		pnlTop.setBounds(10, 11, 1615, 351);
		getContentPane().add(pnlTop);
		pnlTop.setLayout(null);

		lblSelectBomRoute = new JLabel("Select Job:");
		lblSelectBomRoute.setBounds(38, 41, 63, 14);
		pnlTop.add(lblSelectBomRoute);

		cmboBoxShowBomroute = new JComboBox<>();
		cmboBoxShowBomroute.setBounds(108, 37, 346, 22);
		AutoCompleteDecorator.decorate(cmboBoxShowBomroute);
		pnlTop.add(cmboBoxShowBomroute);
		ComboBoxIdValuePair.bindComboBox(cmboBoxShowBomroute, getAllBomRoutes());

		btnViewDetails = new JButton("View Details");
		btnViewDetails.setBounds(479, 37, 109, 23);
		ActionListener detailListener = new AllUserActionListeners();
		btnViewDetails.addActionListener(detailListener);
		btnViewDetails.setActionCommand(UserActions.BTN_VIEW_DETAILS.name());
		pnlTop.add(btnViewDetails);

		scrollPaneRouteJTree = new JScrollPane();
		scrollPaneRouteJTree.setBounds(38, 79, 550, 188);
		pnlTop.add(scrollPaneRouteJTree);

		treeBomRoute = new JTree();
		treeBomRoute.setFont(new Font("Tahoma", Font.PLAIN, 12));
		treeBomRoute.setRowHeight(25);
		treeBomRoute.setModel(setRouteJTreeModel());
		treeBomRoute.setCellRenderer(new UserRendererJTree());
		AppGenerics.clearAllTreeItems(treeBomRoute);
		scrollPaneRouteJTree.setViewportView(treeBomRoute);

		/** SET ALL TREE ICONS EMPTY **/
		SetupBomRoute.setEmptyTreeIcons();
	}

	/** RETRIEVE ALL ROUTES ID & NAMES **/
	private List<ComboBoxIdValuePair> getAllBomRoutes() {
		List<ComboBoxIdValuePair> listItems = new ArrayList<>();
		List<TblBomRoute> routeItems = null;
		try {
			routeItems = daoBomRouteObject.getAllListOfRoutes(true);
			for (int item = 0; item < routeItems.size(); item++) {
				listItems.add(new ComboBoxIdValuePair(routeItems.get(item).getRouteGroupID(),
						routeItems.get(item).getRouteName()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listItems;
	}

	/** GET ICON FOR FRAME BANNER **/
	private Image setFrameBannerIcon() {
		Image img = null;
		try {
			img = LoadResource.getImageFromResourceAsURL(AppConstants.ENTITIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	/** SET JOB ROUTE JTREE MODEL **/
	private DefaultTreeModel setRouteJTreeModel() {

		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		routeJTreeRootNode = new DefaultMutableTreeNode(AppConstants.ACTIVE_JOB_PATH);
		DefaultTreeModel model = new DefaultTreeModel(routeJTreeRootNode);
		try {
			ArrayList<TblBomRoute> routeArray = daoBomRouteObject.fetchAllBomRoutes(SANDBOX_GROUP_ID, 0, 1);
			int routeTreeDepth = routeArray.size();
			ArrayList<String> customItems = new ArrayList<>(routeArray.size());
			for (int item = 0; item < routeArray.size(); item++) {
				if (item != routeTreeDepth - 1) {
					customItems.add(routeArray.get(item).getInFeedStockCode() + FIRST_CONCAT_PART
							+ routeArray.get(item).getMachineName() + SECOND_CONCAT_PART
							+ routeArray.get(item + 1).getInFeedQuantityInHand() + "("
							+ routeArray.get(item + 1).getRouteID() + ")");
				} else {
					customItems.add(routeArray.get(item).getInFeedStockCode());
				}
			}
			if (routeTreeDepth != 0) {
				rootNodeName = routeArray.get(0).getRouteName() + SECOND_CONCAT_PART
						+ routeArray.get(0).getInFeedQuantityInHand() + "(" + routeArray.get(0).getRouteID() + ")";
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				routeJTreeRootNode.add(rootNode);
				populateNodes(rootNode, customItems, 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** SET RECURSIVE JTREE POPULATE RECORDS **/
	protected void populateNodes(DefaultMutableTreeNode parent, ArrayList<String> items, int index) {
		if (index >= items.size()) {
			return;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(items.get(index));
		parent.add(node);
		populateNodes(node, items, ++index);
	}

	/** LOAD IMAGE ICON FROM RESOURCES **/
	private ImageIcon setImageIcon(String path, int width, int height) {
		Image image = null;
		ImageIcon icon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(path);
			image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}

	/** CHANGE JTREE DEFAULT ICONS **/
	class UserRendererJTree extends DefaultTreeCellRenderer {
		Font boldFont;
		private static final long serialVersionUID = 1L;

		public UserRendererJTree() {
			boldFont = new Font("SansSerif", Font.BOLD, 12);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			String nodeValue = node.getUserObject().toString();
			if (row == 0) {
				setFont(boldFont);
			} else {
				setFont(getFont().deriveFont(Font.PLAIN));
			}
			if (leaf) {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_LEAF, 10, 10));
			} else if (expanded) {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_EXPAND, 10, 10));
			} else {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_RIGHT, 10, 10));
			}
			nodeValue = nodeValue.replaceAll("\\(.*?\\)", "");
			setText(nodeValue.trim());
			return this;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.BLACK);
			g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
		}
	}

	/** MODEL CLASS FOR COMBOX BIND WITH ID & VALUE **/
	class ComboBoxIdValuePair {
		private int id;
		private String value;

		public ComboBoxIdValuePair(int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return id;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}

		public static void bindComboBox(JComboBox<ComboBoxIdValuePair> comboBox, List<ComboBoxIdValuePair> items) {
			DefaultComboBoxModel<ComboBoxIdValuePair> model = new DefaultComboBoxModel<>(
					items.toArray(new ComboBoxIdValuePair[0]));
			comboBox.setModel(model);
		}
	}

	private void getSelectedJobComboBoxID() {
		ComboBoxIdValuePair selectedItem = (ComboBoxIdValuePair) cmboBoxShowBomroute.getSelectedItem();
		if (selectedItem != null) {
			SANDBOX_GROUP_ID = selectedItem.getId();
		}
		AppGenerics.clearAllTreeItems(treeBomRoute);
		treeBomRoute.setModel(setRouteJTreeModel());
		AppGenerics.expandAllNodes(treeBomRoute);
	}

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class AllUserActionListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == UserActions.BTN_VIEW_DETAILS.name()) {
				getSelectedJobComboBoxID();
			}
		}
	}
}
