package dbproject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import javax.swing.JTextField;

public class Dbvehicle {

	private JFrame frame;
	private JTable table;
	private JComboBox txtCSTATE;
	private JComboBox txtID;
	private JComboBox txtCPLACE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dbvehicle window = new Dbvehicle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Dbvehicle() {
		initialize();
		Connect();
		
		table_load3();
		LoadCode3();
		table_load();
		LoadCode();

	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtVID;
	private JTextField txtMODEL;
	private JTextField txtDEFPLACE;
	private JTextField txtVPLACE;
	private JTextField txtBATT;
	private JTextField txtSTATE;
	private JTextField txtRESERVE;
	private JTextField txtUSING;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root","1234");
        }
        catch (ClassNotFoundException ex)
        {
          ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
 
    }
	public void table_load()
    {
     try
     {
    pst = con.prepareStatement("SELECT * FROM vehicletbl");
    rs = pst.executeQuery();
    table.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void LoadCode()
    {
        try
        {
        pst = con.prepareStatement("SELECT VEH_ID FROM vehicletbl");
        rs = pst.executeQuery();
         
        txtID.removeAllItems();

        while (rs.next())
          {
        txtID.addItem(rs.getString("VEH_ID"));
          }
        }
        catch ( Exception e)
        {
           e.printStackTrace();
        }
    }
	
	public void table_load2()
    {
     try
     {
    pst = con.prepareStatement("SELECT * FROM vehicletbl");
    rs = pst.executeQuery();
    table.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     }
    }
	
	
	
	
	
	public void LoadCode2()
    {
        try
        {
        pst = con.prepareStatement("SELECT VEH_STATE FROM vehicletbl");
        rs = pst.executeQuery();}
         
       catch(SQLException c)
       {
    	   c.printStackTrace();
       }
    }
	
	public void table_load3()
    {
     try
     {
    pst = con.prepareStatement("SELECT * FROM vehicletbl");
    rs = pst.executeQuery();
    table.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     }
    }
	
	public void LoadCode3()
    {
		 
        try
        {
        pst = con.prepareStatement("SELECT VEH_PLACE FROM vehicletbl");
        rs = pst.executeQuery();
         
        txtID.removeAllItems();

        while (rs.next())
          {
        txtCPLACE.addItem(rs.getString("VEH_PLACE"));
          }
        }
        catch ( Exception e)
        {
           e.printStackTrace();
        }
    }
    
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		JLabel lblNewLabel = new JLabel("Vehicle Management");
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 26));
		lblNewLabel.setBounds(374, 34, 341, 47);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(823, 587, 101, 39);
		frame.getContentPane().add(btnNewButton);
		
		txtCSTATE = new JComboBox();
		txtCSTATE.setModel(new DefaultComboBoxModel(new String[] {"\uC815\uC0C1", "\uBC29\uCE58"}));
		txtCSTATE.setBounds(806, 158, 101, 29);
		frame.getContentPane().add(txtCSTATE);
		
		JLabel lblNewLabel_1 = new JLabel("state");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(806, 121, 146, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(147, 273, 738, 291);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		txtVID = new JTextField();
		txtVID.setBounds(105, 135, 101, 21);
		frame.getContentPane().add(txtVID);
		txtVID.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("VEH_ID");
		lblNewLabel_2.setBounds(58, 128, 48, 34);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String VEH_ID,VEH_MODEL,VEH_DEFPLACE,VEH_PLACE,VEH_BATT,VEH_STATE,VEH_RESERVE,VEH_USING;
				
				VEH_ID= txtVID.getText();
			
				VEH_MODEL= txtMODEL.getText();
				VEH_DEFPLACE= txtDEFPLACE.getText();
				VEH_PLACE= txtVPLACE.getText();
				VEH_BATT= txtBATT.getText();
				VEH_STATE= txtSTATE.getText();
				VEH_RESERVE= txtRESERVE.getText();
				VEH_USING= txtUSING.getText();
				
				
				try {
					pst = con.prepareStatement("insert into vehicletbl(VEH_ID,VEH_MODEL,VEH_DEFPLACE,VEH_PLACE,VEH_BATT,VEH_STATE,VEH_RESERVE,VEH_USING)values(?,?,?,?,?,?,?,?)");
					pst.setString(1, VEH_ID);
				
					pst.setString(2, VEH_MODEL);
					pst.setString(3, VEH_DEFPLACE);
					pst.setString(4, VEH_PLACE);
					pst.setString(5, VEH_BATT);
					pst.setString(6, VEH_STATE);
					pst.setString(7, VEH_RESERVE);
					pst.setString(8, VEH_USING);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "ADD COMPLETED");
					
					table_load();
					
					txtVID.setText("");
					
					txtMODEL.setText("");
					txtDEFPLACE.setText("");
					txtVPLACE.setText("");
					txtBATT.setText("");
					txtSTATE.setText("");
					txtRESERVE.setText("");
					txtUSING.setText("");
					
					txtVID.requestFocus();
				
			}
				catch (SQLException e1)
		        {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Record Failed");
		}
			}
		});
		btnNewButton_1.setBounds(147, 587, 95, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="delete from vehicletbl where VEH_ID='"+txtVID.getText()+"'";
					PreparedStatement pst=con.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Deleted");
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_2.setBounds(266, 587, 95, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_4 = new JLabel("MODEL");
		lblNewLabel_4.setBounds(58, 165, 52, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("DEFPLACE");
		lblNewLabel_5.setBounds(218, 138, 70, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("VEH_PLACE");
		lblNewLabel_6.setBounds(218, 165, 76, 15);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("BATT");
		lblNewLabel_7.setBounds(236, 196, 52, 15);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("STATE");
		lblNewLabel_8.setBounds(402, 138, 52, 15);
		frame.getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("RESERVE");
		lblNewLabel_9.setBounds(402, 165, 63, 15);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("USING");
		lblNewLabel_10.setBounds(402, 196, 52, 15);
		frame.getContentPane().add(lblNewLabel_10);
		
		txtMODEL = new JTextField();
		txtMODEL.setColumns(10);
		txtMODEL.setBounds(105, 162, 101, 21);
		frame.getContentPane().add(txtMODEL);
		
		txtDEFPLACE = new JTextField();
		txtDEFPLACE.setColumns(10);
		txtDEFPLACE.setBounds(289, 135, 101, 21);
		frame.getContentPane().add(txtDEFPLACE);
		
		txtVPLACE = new JTextField();
		txtVPLACE.setColumns(10);
		txtVPLACE.setBounds(289, 162, 101, 21);
		frame.getContentPane().add(txtVPLACE);
		
		txtBATT = new JTextField();
		txtBATT.setColumns(10);
		txtBATT.setBounds(289, 193, 101, 21);
		frame.getContentPane().add(txtBATT);
		
		txtSTATE = new JTextField();
		txtSTATE.setColumns(10);
		txtSTATE.setBounds(460, 135, 101, 21);
		frame.getContentPane().add(txtSTATE);
		
		txtRESERVE = new JTextField();
		txtRESERVE.setColumns(10);
		txtRESERVE.setBounds(460, 162, 101, 21);
		frame.getContentPane().add(txtRESERVE);
		
		txtUSING = new JTextField();
		txtUSING.setColumns(10);
		txtUSING.setBounds(460, 193, 101, 21);
		frame.getContentPane().add(txtUSING);
		
		JButton btnNewButton_3 = new JButton("Search");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String VEH_ID = txtID.getSelectedItem().toString();
				
				try
			     {
			    pst = con.prepareStatement("SELECT * FROM vehicletbl where VEH_ID=?");
			    pst.setString(1, VEH_ID);
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			     }
			     catch (SQLException p)
			     {
			     p.printStackTrace();
			     }
				
			
				
	             try
	                {
	                pst = con.prepareStatement( "SELECT * FROM vehicletbl where VEH_ID = ?" );
	                pst.setString(1, VEH_ID);
	                rs = pst.executeQuery();
	               
	                if ( rs.next() == true )
	                {
	             txtVID.setText(rs.getString(1));
	            
	             txtMODEL.setText(rs.getString(2));
	             txtDEFPLACE.setText(rs.getString(3));
	             txtVPLACE.setText(rs.getString(4));
	             txtBATT.setText(rs.getString(5));
	             txtSTATE.setText(rs.getString(6));
	             txtRESERVE.setText(rs.getString(7));
	             txtUSING.setText(rs.getString(8));
	                }
	             else
	             {
	                 JOptionPane.showMessageDialog(null, "Record Not Found");
	             }
	                }
	                catch ( Exception e1 )
	                {
	                e1.printStackTrace();
	                }

			
	         
			
			
			
			
			
			
			
			
		
       
		}
              
	                
			
		});
		btnNewButton_3.setBounds(692, 204, 85, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_11 = new JLabel("Look up V_ID");
		lblNewLabel_11.setBounds(689, 125, 88, 23);
		frame.getContentPane().add(lblNewLabel_11);
		
		txtID = new JComboBox();
		txtID.setBounds(692, 158, 88, 29);
		frame.getContentPane().add(txtID);
		
		JButton btnNewButton_4 = new JButton("Search");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String VEH_STATE = txtCSTATE.getSelectedItem().toString();
				try
			     {
			    pst = con.prepareStatement("SELECT * FROM vehicletbl where VEH_STATE= ?");
			    pst.setString(1, VEH_STATE);
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			     }
			     catch (SQLException p)
			     {
			     p.printStackTrace();
			     }
				
				
				
				
			}
		});
		btnNewButton_4.setBounds(806, 204, 95, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("All");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
			     {
			    pst = con.prepareStatement("SELECT * FROM vehicletbl ");
			   
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			     }
			     catch (SQLException p)
			     {
			     p.printStackTrace();
			     }
				
				
				
			}
		});
		btnNewButton_5.setBounds(806, 237, 95, 23);
		frame.getContentPane().add(btnNewButton_5);
		
		JLabel lblNewLabel_3 = new JLabel("Look up Place");
		lblNewLabel_3.setBounds(573, 129, 83, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtCPLACE = new JComboBox();
		txtCPLACE.setBounds(583, 158, 73, 29);
		frame.getContentPane().add(txtCPLACE);
		
		
		JButton btnNewButton_6 = new JButton("Search");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String VEH_PLACE = txtCPLACE.getSelectedItem().toString();
				try
			     {
			    pst = con.prepareStatement("SELECT * FROM vehicletbl where VEH_PLACE= ?");
			    pst.setString(1, VEH_PLACE);
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			     }
			     catch (SQLException p)
			     {
			     p.printStackTrace();
			     }
				
			}
		});
		btnNewButton_6.setBounds(580, 204, 76, 23);
		frame.getContentPane().add(btnNewButton_6);
	}

	public void setVisible(boolean b) {
			// TODO Auto-generated method stub

}
}