/*
 * package extras;
 * 
 * import java.awt.Color; import java.awt.Component; import java.awt.Font;
 * import java.awt.SystemColor;
 * 
 * import javax.swing.ImageIcon; import javax.swing.JComponent; import
 * javax.swing.JLabel; import javax.swing.JScrollPane; import
 * javax.swing.JTable; import javax.swing.ListSelectionModel; import
 * javax.swing.SwingConstants; import javax.swing.table.DefaultTableModel;
 * import javax.swing.table.TableCellRenderer;
 * 
 * import gui.manufacturing.SetupJob.HorizontalAlignmentHeaderRenderer;
 * 
 * public class testCase {
 * 
 * 
 *//** SET TABLE MODEL & STRUCTURE **//*
										 * private void createJobsTable() { scrollPaneShowRecords = new JScrollPane();
										 * scrollPaneShowRecords.setBounds(10, 21, 1276, 600);
										 * pnlBottom.add(scrollPaneShowRecords);
										 * 
										 * tblShowRecords = new JTable() { private static final long serialVersionUID =
										 * 1L;
										 * 
										 * public Class<?> getColumnClass(int column) { if (column == 8) { return
										 * ImageIcon.class; } else { return Object.class; } }
										 * 
										 * @Override public boolean isCellEditable(int row, int column) { return false;
										 * }
										 * 
										 * public Component prepareRenderer(TableCellRenderer renderer, int row, int
										 * column) { Component c = super.prepareRenderer(renderer, row, column);
										 * JComponent jc = (JComponent) c; if (isRowSelected(row)) jc.setBorder(null);
										 * return c; } };
										 * 
										 * scrollPaneShowRecords.setViewportView(tblShowRecords);
										 * tblShowRecords.setBackground(SystemColor.inactiveCaptionBorder);
										 * tblShowRecords.setFont(new Font("Calibri", Font.PLAIN, 12));
										 * tblShowRecords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										 * ShowRecordsTableModel = (DefaultTableModel) tblShowRecords.getModel();
										 * tblShowRecords.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
										 * tblShowRecords.getTableHeader().setReorderingAllowed(false);
										 * ShowRecordsTableModel.setColumnIdentifiers(showRecordsTblColNames);
										 * tblShowRecords.setGridColor(Color.BLACK);
										 * tblShowRecords.setShowHorizontalLines(true);
										 * tblShowRecords.setShowVerticalLines(false);
										 * 
										 * this.getLastFewRecords();
										 * 
										 * tblShowRecords.getColumnModel().getColumn(0) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
										 * tblShowRecords.getColumnModel().getColumn(1) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
										 * tblShowRecords.getColumnModel().getColumn(2) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
										 * tblShowRecords.getColumnModel().getColumn(3) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
										 * tblShowRecords.getColumnModel().getColumn(4) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
										 * tblShowRecords.getColumnModel().getColumn(5) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
										 * tblShowRecords.getColumnModel().getColumn(6) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
										 * tblShowRecords.getColumnModel().getColumn(7) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
										 * tblShowRecords.getColumnModel().getColumn(8) .setHeaderRenderer(new
										 * HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
										 * 
										 * tblShowRecords.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
										 * setColumnWidth(tblShowRecords, 0, 50, JLabel.CENTER, 50, 50);
										 * setColumnWidth(tblShowRecords, 1, 60, JLabel.CENTER, 60, 60);
										 * setColumnWidth(tblShowRecords, 2, 140, JLabel.LEFT, 140, 200);
										 * setColumnWidth(tblShowRecords, 3, 140, JLabel.LEFT, 140, 200);
										 * setColumnWidth(tblShowRecords, 4, 170, JLabel.LEFT, 170, 200);
										 * setColumnWidth(tblShowRecords, 5, 60, JLabel.CENTER, 60, 60);
										 * setColumnWidth(tblShowRecords, 6, 270, JLabel.LEFT, 270, 270);
										 * setColumnWidth(tblShowRecords, 7, 120, JLabel.CENTER, 120, 120);
										 * setColumnWidth(tblShowRecords, 9, 90, JLabel.CENTER, 90, 90);
										 * setColumnWidth(tblShowRecords, 10, 90, JLabel.CENTER, 90, 90);
										 * 
										 * tblShowRecords.setRowHeight(30); }
										 * 
										 * 
										 * }
										 */