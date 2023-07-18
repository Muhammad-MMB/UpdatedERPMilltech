package businessLogic;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ImportUserEntities
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  ArrayList<String> allTablesNameData = null;
  
  public ImportUserEntities()
  {
    setTitle("Import User Entities");
    setIconImage(Toolkit.getDefaultToolkit().getImage(ImportUserEntities.class.getResource("/resources/images/import.png")));
    
    JPanel EntityPnll = new JPanel();
    EntityPnll.setBackground(UIManager.getColor("Button.background"));
    EntityPnll.setBorder(new TitledBorder(new EtchedBorder(1, new Color(255, 255, 255), new Color(160, 160, 160)), "Import Entities", 4, 2, null, new Color(0, 0, 0)));
    
    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(groupLayout.createSequentialGroup()
      .addContainerGap()
      .addComponent(EntityPnll, -1, 964, 32767)
      .addContainerGap()));
    
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(groupLayout.createSequentialGroup()
      .addContainerGap()
      .addComponent(EntityPnll, -2, 69, -2)
      .addContainerGap(581, 32767)));
    
    EntityPnll.setLayout(null);
    
    JLabel lblSlctEntity = new JLabel("Select Entity to Import");
    lblSlctEntity.setBounds(228, 26, 131, 18);
    EntityPnll.add(lblSlctEntity);
    
    JComboBox<String> allTablesNameComboBox = new JComboBox<String>();
    allTablesNameComboBox.setBounds(388, 24, 289, 22);
    EntityPnll.add(allTablesNameComboBox);
   
    allTablesNameComboBox.setModel(new DefaultComboBoxModel<String>((String[])allTablesNameData.toArray(new String[0])));
    
    getContentPane().setLayout(groupLayout);
    getContentPane().setBackground(Color.WHITE);
    setBounds(100, 100, 1000, 700);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(2);
    setVisible(true);
  }
}