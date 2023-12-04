package dbproject;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;

public class Dbsummary {

	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dbsummary window = new Dbsummary();
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
	public Dbsummary() {
		initialize();
		Connect();
		table_load();
		table_1_load();
		table_2_load();
		
		
	}
	
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
		

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
    pst = con.prepareStatement("select * from rentaltbl");
    rs = pst.executeQuery();
    table.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }

	public void table_1_load()
    {
     try
     {
    pst = con.prepareStatement("select* from returntbl");
    rs = pst.executeQuery();
    table_1.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void table_2_load()
    {
     try
     {
    pst = con.prepareStatement("select * from Placetbl");
    rs = pst.executeQuery();
    table_2.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Summary Management");
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 26));
		lblNewLabel.setBounds(251, 37, 336, 47);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(607, 603, 101, 39);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Rental");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(76, 144, 162, 29);
		frame.getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(76, 468, 677, 102);
		frame.getContentPane().add(scrollPane);
		
		table_2 = new JTable();
		scrollPane.setViewportView(table_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(76, 328, 677, 102);
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(76, 183, 677, 110);
		frame.getContentPane().add(scrollPane_2);
		
		table = new JTable();
		scrollPane_2.setViewportView(table);
		
		JLabel lblNewLabel_2 = new JLabel("Place");
		lblNewLabel_2.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(76, 440, 162, 18);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_5 = new JLabel("Return");
		lblNewLabel_5.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(76, 303, 52, 15);
		frame.getContentPane().add(lblNewLabel_5);
	}
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
