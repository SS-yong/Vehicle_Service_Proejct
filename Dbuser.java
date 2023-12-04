package dbproject;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;


public class Dbuser {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dbuser window = new Dbuser();
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
	public Dbuser() {
		initialize();
		Connect();
		table_load();
		LoadCode();
		
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	private JTable table;
	private JTextField txtID;
	private JTextField txtPASSWORD;
	private JTextField txtNAME;
	private JTextField txtBIRTH;
	private JTextField txtPHONE;
	private JTextField txtLICENSE;
	private JComboBox txtCID;

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
    pst = con.prepareStatement("select * from usertbl");
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
        pst = con.prepareStatement("SELECT USER_ID FROM usertbl");
        rs = pst.executeQuery();
         
        txtCID.removeAllItems();

        while (rs.next())
          {
        txtCID.addItem(rs.getString("USER_ID"));
          }
        }
        catch ( Exception e)
        {
           e.printStackTrace();
        }
    }

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("User management");
		lblNewLabel.setBounds(385, 34, 258, 47);
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 26));
		frame.getContentPane().add(lblNewLabel);
		
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(823, 587, 101, 39);
		frame.getContentPane().add(btnNewButton);
		
		txtCID = new JComboBox();
		txtCID.setBounds(671, 171, 135, 29);
		frame.getContentPane().add(txtCID);
		
		JLabel lblNewLabel_1 = new JLabel("Look up ID");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(671, 138, 135, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String USER_ID = txtCID.getSelectedItem().toString();
				
				try
			     {
			    pst = con.prepareStatement("SELECT * FROM USERtbl where USER_ID= ?");
			    pst.setString(1, USER_ID);
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			     }
			     catch (SQLException p)
			     {
			     p.printStackTrace();
			     }
				
				
				
				
	             try
	                {
	                pst = con.prepareStatement( "SELECT * FROM usertbl where USER_ID = ?" );
	                pst.setString(1, USER_ID);
	                rs = pst.executeQuery();
	    
	             if ( rs.next() == true )
	                {
	            txtID.setText(rs.getString(1));
	             txtPASSWORD.setText(rs.getString(2));
	             txtNAME.setText(rs.getString(3));
	             txtBIRTH.setText(rs.getString(4));
	             txtPHONE.setText(rs.getString(5));
	             txtLICENSE.setText(rs.getString(6));
	             
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
		btnNewButton_1.setBounds(691, 218, 115, 37);
		frame.getContentPane().add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 265, 742, 271);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_2 = new JButton("Add");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String USER_ID, USER_PASSWORD, USER_NAME, USER_BIRTH, USER_PHONE, USER_LICENSE;
				
				USER_ID = txtID.getText();
				USER_PASSWORD = txtPASSWORD.getText();
				USER_NAME = txtNAME.getText();
				USER_BIRTH = txtBIRTH.getText();
				USER_PHONE = txtPHONE.getText();
				USER_LICENSE = txtLICENSE.getText();
				
				try {
					pst = con.prepareStatement("insert into usertbl(USER_ID,USER_PASSWORD,USER_NAME,USER_BIRTH,USER_PHONE,USER_LICENSE)values(?,?,?,?,?,?)");
					pst.setString(1, USER_ID);
					pst.setString(2, USER_PASSWORD);
					pst.setString(3, USER_NAME);
					pst.setString(4, USER_BIRTH);
					pst.setString(5, USER_PHONE);
					pst.setString(6, USER_LICENSE);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "ADD COMPLETED");
					
					table_load();
					
					
					txtID.setText("");
					txtPASSWORD.setText("");
					txtNAME.setText("");
					txtBIRTH.setText("");
					txtPHONE.setText("");
					txtLICENSE.setText("");
					txtID.requestFocus();
				}
				
				catch (SQLException e1)
		        {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Record Failed");
		}

				
				
				
				}
				
		});
		btnNewButton_2.setBounds(87, 573, 101, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="delete from usertbl where USER_ID='"+txtID.getText()+"'";
					PreparedStatement pst=con.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Deleted");
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnNewButton_3.setBounds(207, 573, 108, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		txtID = new JTextField();
		txtID.setBounds(130, 135, 106, 21);
		frame.getContentPane().add(txtID);
		txtID.setColumns(10);
		
		txtPASSWORD = new JTextField();
		txtPASSWORD.setBounds(130, 175, 106, 21);
		frame.getContentPane().add(txtPASSWORD);
		txtPASSWORD.setColumns(10);
		
		txtNAME = new JTextField();
		txtNAME.setBounds(130, 218, 106, 21);
		frame.getContentPane().add(txtNAME);
		txtNAME.setColumns(10);
		
		txtBIRTH = new JTextField();
		txtBIRTH.setBounds(332, 135, 106, 21);
		frame.getContentPane().add(txtBIRTH);
		txtBIRTH.setColumns(10);
		
		txtPHONE = new JTextField();
		txtPHONE.setBounds(332, 175, 106, 21);
		frame.getContentPane().add(txtPHONE);
		txtPHONE.setColumns(10);
		
		txtLICENSE = new JTextField();
		txtLICENSE.setBounds(332, 218, 106, 21);
		frame.getContentPane().add(txtLICENSE);
		txtLICENSE.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(51, 138, 30, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setBounds(51, 178, 67, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Name");
		lblNewLabel_4.setBounds(51, 218, 64, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Birth");
		lblNewLabel_5.setBounds(269, 138, 64, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Phone");
		lblNewLabel_6.setBounds(266, 178, 67, 15);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("License");
		lblNewLabel_7.setBounds(269, 218, 64, 15);
		frame.getContentPane().add(lblNewLabel_7);
		
		JButton btnNewButton_5 = new JButton("All");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
			     {
			    pst = con.prepareStatement("SELECT * FROM USERtbl ");
			   
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			     }
			     catch (SQLException p)
			     {
			     p.printStackTrace();
			     }
				
				
				
			}
		});
		btnNewButton_5.setBounds(829, 218, 95, 37);
		frame.getContentPane().add(btnNewButton_5);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
