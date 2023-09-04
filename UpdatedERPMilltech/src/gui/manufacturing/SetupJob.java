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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import dao.DaoBomRoute;
import dao.DaoJob;
import dao.DaoJobState;
import entities.TblBomRoute;
import entities.TblJobState;
import extras.AppConstants;
import extras.AppGenerics;
import extras.LoadResource;
import extras.MessageWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class SetupJob extends JFrame {

	private static final long serialVersionUID = 1L;

	/** COMPONENTS / CONTROLS **/
	private JPanel pnlTop;
	private JLabel lblSelectBomRoute, lblQuantityToMake, lblJobNotes, lblmax;
	private JTree treeBomRoute;
	private JTextPane textPaneJobNotes;
	private JFormattedTextField textFieldQuantity;
	private NumberFormatter _quantityFormatter;
	private DecimalFormat _numberFormat;
	private JButton btnViewDetails, btnCreateNewJob;
	private JScrollPane scrollPaneRouteJTree, scrollPaneJobNotes;
	private DefaultMutableTreeNode routeJTreeRootNode;
	private JComboBox<TblBomRoute> cmboBoxShowBomroute;

	/** VARIABLES **/
	private final int maxNumberOfCharacters = 100;
	private int sandboxGroupID = -1, selectedRouteID = -1;
	static final String FIRST_CONCAT_PART = " @ ";
	static final String SECOND_CONCAT_PART = " || ";

	/** CLASSES OBJECTS **/
	DaoJob daoJobObject;
	DaoJobState daoJobStateObject;
	DaoBomRoute daoBomRouteObject;
	TblJobState tblJobStateObject;

	/** USER ALERTS MESSAGES **/
	private final String OK_NEW_RECORD_SAVE_ALERT = " New record successfully inserted! ";
	private final String CONFIRM_CREATE_NEW_JOB_ALERT = " Are you sure you want to add this new job? ";

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_VIEW_DETAILS, CREATE_NEW_JOB
	}

	public SetupJob() {

		/** FRAME PROPERTIES **/
		this.setTitle("Setup Job");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(setFrameBannerIcon());
		setBounds(100, 100, 1332, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoJobObject = new DaoJob();
		daoJobStateObject = new DaoJobState();
		daoBomRouteObject = new DaoBomRoute();
		tblJobStateObject = new TblJobState();

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
		pnlTop.setBorder(new TitledBorder(null, "Setup Job", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlTop.setBounds(10, 11, 1296, 296);
		getContentPane().add(pnlTop);
		pnlTop.setLayout(null);

		lblSelectBomRoute = new JLabel("Select Job:");
		lblSelectBomRoute.setBounds(35, 45, 63, 14);
		pnlTop.add(lblSelectBomRoute);

		cmboBoxShowBomroute = new JComboBox<>();
		cmboBoxShowBomroute.setBounds(108, 37, 346, 28);
		AutoCompleteDecorator.decorate(cmboBoxShowBomroute);
		pnlTop.add(cmboBoxShowBomroute);
		bindComboBox(cmboBoxShowBomroute, getAllBomRoutes());

		btnViewDetails = new JButton("View Details");
		btnViewDetails.setBounds(479, 37, 109, 28);
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
		JTreeConfig.clearAllTreeItems(treeBomRoute);
		scrollPaneRouteJTree.setViewportView(treeBomRoute);

		lblQuantityToMake = new JLabel("Quantity (tons):");
		lblQuantityToMake.setBounds(669, 41, 103, 14);
		pnlTop.add(lblQuantityToMake);

		_numberFormat = new DecimalFormat("#0.0");
		_quantityFormatter = new NumberFormatter(_numberFormat);
		_quantityFormatter.setValueClass(Float.class);
		_quantityFormatter.setAllowsInvalid(false);
		_quantityFormatter.setMinimum(0.0f);
		textFieldQuantity = new JFormattedTextField(_quantityFormatter);
		textFieldQuantity.setText("0.0");
		textFieldQuantity.setColumns(3);
		textFieldQuantity.setBounds(773, 38, 266, 28);
		pnlTop.add(textFieldQuantity);

		lblJobNotes = new JLabel("Job Notes:");
		lblJobNotes.setBounds(669, 79, 94, 14);
		pnlTop.add(lblJobNotes);

		scrollPaneJobNotes = new JScrollPane();
		scrollPaneJobNotes.setBounds(773, 79, 266, 188);
		pnlTop.add(scrollPaneJobNotes);

		textPaneJobNotes = new CharacterLimitTextPane(maxNumberOfCharacters);
		scrollPaneJobNotes.setViewportView(textPaneJobNotes);

		btnCreateNewJob = new JButton("Create New Job");
		ActionListener setupJobListener = new AllUserActionListeners();
		btnCreateNewJob.addActionListener(setupJobListener);
		btnCreateNewJob.setActionCommand(UserActions.CREATE_NEW_JOB.name());
		btnCreateNewJob.setBounds(1084, 235, 149, 32);
		pnlTop.add(btnCreateNewJob);

		lblmax = new JLabel("(max 100)");
		lblmax.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblmax.setBounds(672, 95, 49, 14);
		pnlTop.add(lblmax);

		/** SET ALL TREE ICONS EMPTY **/
		JTreeConfig.setEmptyTreeIcons();
	}

	/** RETRIEVE ALL ROUTES ID & NAMES **/
	private List<TblBomRoute> getAllBomRoutes() {
		List<TblBomRoute> listItems = null;
		try {
			listItems = daoBomRouteObject.getAllListOfRoutes(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listItems;
	}

	/** GET ICON FOR FRAME BANNER **/
	private Image setFrameBannerIcon() {
		Image img = null;
		try {
			img = LoadResource.getImageFromResourceAsURL(AppConstants.IMPORT_TABLES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	/** SET JOB ROUTE JTREE MODEL **/
	private DefaultTreeModel setRouteJTreeModel() {

		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		routeJTreeRootNode = new DefaultMutableTreeNode(AppConstants.JOB_PATH_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(routeJTreeRootNode);
		try {
			ArrayList<TblBomRoute> routeArray = daoBomRouteObject.fetchAllBomRoutes(sandboxGroupID, 0, 1);
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

	/** BIND COMBO BOX WITH ROUTE ID, GROUP ID & ROUTE NAME **/
	private void bindComboBox(JComboBox<TblBomRoute> comboBox, List<TblBomRoute> items) {
		DefaultComboBoxModel<TblBomRoute> model = new DefaultComboBoxModel<>(items.toArray(new TblBomRoute[0]));
		comboBox.setModel(model);
	}

	/** FETCH & SETUP JTREE **/
	private void getBomRouteDetails() {
		TblBomRoute selectedItem = (TblBomRoute) cmboBoxShowBomroute.getSelectedItem();
		if (selectedItem != null) {
			selectedRouteID = selectedItem.getRouteID();
			sandboxGroupID = selectedItem.getRouteGroupID();
		}
		JTreeConfig.clearAllTreeItems(treeBomRoute);
		treeBomRoute.setModel(setRouteJTreeModel());
		JTreeConfig.expandAllNodes(treeBomRoute);
	}

	private void createNewJob() {
		try {
			int userResponse = MessageWindow.createConfirmDialogueWindow(CONFIRM_CREATE_NEW_JOB_ALERT,
					"Confirm action");
			if (userResponse == 0) {
				daoJobStateObject.setDefaultJobState(tblJobStateObject);
				daoJobObject.createNewJob(selectedRouteID, Double.parseDouble(textFieldQuantity.getText()),
						textPaneJobNotes.getText(), tblJobStateObject, true);
				AppGenerics.setMessageAlert(OK_NEW_RECORD_SAVE_ALERT);
				setComponentsDefaulState();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setComponentsDefaulState() {
		JTreeConfig.clearAllTreeItems(treeBomRoute);
		textFieldQuantity.setText("0.0");
		textPaneJobNotes.setText("");
	}

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class AllUserActionListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == UserActions.BTN_VIEW_DETAILS.name()) {
				getBomRouteDetails();
			} else if (e.getActionCommand() == UserActions.CREATE_NEW_JOB.name()) {
				createNewJob();
			}
		}
	}

	/** CLASS TO RESTRICT USER FOR 100 CHARACTERS IN JTEXTPANE **/
	public class CharacterLimitTextPane extends JTextPane {

		private static final long serialVersionUID = 1L;

		public CharacterLimitTextPane(int maxLength) {
			((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
				@Override
				public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
						throws BadLocationException {
					int currentLength = fb.getDocument().getLength();
					if (currentLength + text.length() - length <= maxLength) {
						super.replace(fb, offset, length, text, attrs);
					} else {
						Toolkit.getDefaultToolkit().beep();
					}
				}
			});
		}
	}

	/** CLASS FOR CHANGE JTREE DEFAULT ICONS **/
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

}