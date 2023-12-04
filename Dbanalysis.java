package dbproject;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Dbanalysis {

	private JFrame frame;
	private JTable table_11;
	private JTable table_12;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dbanalysis window = new Dbanalysis();
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
	public Dbanalysis() {
		initialize();
		Connect();
		table_11_load();
		table_12_load();
		table_13_load();
		table_14_load();
		table_15_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JScrollPane scrollPane_2;
	private JTable table_13;
	private JLabel lblNewLabel_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private JTable table_14;
	private JTable table_15;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	
	
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
	
	public void table_11_load()
    {
     try
     {
    pst = con.prepareStatement("select ren_splace, count(*) as count_ren\r\n"
    		+ "from history\r\n"
    		+ "group by ren_splace\r\n"
    		+ "order by count_ren desc limit 5;\r\n"
    		+ "");
    rs = pst.executeQuery();
    table_11.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void table_12_load()
    {
     try
     {
    pst = con.prepareStatement("select rtn_eplace, count(*) as count_rtn\r\n"
    		+ "from history\r\n"
    		+ "group by rtn_eplace\r\n"
    		+ "order by count_rtn desc limit 5;\r\n"
    		+ "");
    rs = pst.executeQuery();
    table_12.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void table_13_load()
    {
     try
     {
    pst = con.prepareStatement("select user_id, veh_id, rtn_etime, rtn_eplace\r\n"
    		+ "from history\r\n"
    		+ "where rtn_eplace not in \r\n"
    		+ "(select place_name from placetbl);\r\n"
    		+ "");
    rs = pst.executeQuery();
    table_13.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void table_14_load()
    {
     try
     {
    pst = con.prepareStatement("select a.ren_date,bike, kick\r\n"
    		+ "from (select DATE_FORMAT(ren_stime, '%Y-%m-%d') as ren_date, count(*) as bike\r\n"
    		+ "from history\r\n"
    		+ "where veh_id < 200\r\n"
    		+ "group by ren_date) as a\r\n"
    		+ "join\r\n"
    		+ "(select DATE_FORMAT(ren_stime, '%Y-%m-%d') as ren_date, count(*) as kick\r\n"
    		+ "from history\r\n"
    		+ "where veh_id >= 200\r\n"
    		+ "group by ren_date) as b\r\n"
    		+ "on a.ren_date = b.ren_date;\r\n"
    		+ "");
    rs = pst.executeQuery();
    table_14.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void table_15_load()
    {
     try
     {
    pst = con.prepareStatement("select Mor.ren_date,MOR_rev, AFT_rev, SWI_rev, date_rev,total_rev\r\n"
    		+ "\r\n"
    		+ "from\r\n"
    		+ "(select DATE_FORMAT(ren_stime, '%Y-%m-%d') as ren_date, format(sum(rtn_price),'C') as MOR_rev\r\n"
    		+ "from history\r\n"
    		+ "where hour(ren_stime) >= 6 and hour(ren_stime) < 11\r\n"
    		+ "group by ren_date) as MOR\r\n"
    		+ "\r\n"
    		+ "join\r\n"
    		+ "(select DATE_FORMAT(ren_stime, '%Y-%m-%d') as ren_date, format(sum(rtn_price),'C') as aft_rev\r\n"
    		+ "from history\r\n"
    		+ "where hour(ren_stime) >= 11 and hour(ren_stime) < 18\r\n"
    		+ "group by ren_date) as AFT\r\n"
    		+ "\r\n"
    		+ "on mor.ren_date = aft.ren_date\r\n"
    		+ "\r\n"
    		+ "join\r\n"
    		+ "\r\n"
    		+ "(select DATE_FORMAT(ren_stime, '%Y-%m-%d') as ren_date, format(sum(rtn_price),'C') as swi_rev\r\n"
    		+ "from history\r\n"
    		+ "where hour(ren_stime) >= 18 and hour(ren_stime) < 24\r\n"
    		+ "group by ren_date) as swi\r\n"
    		+ "on mor.ren_Date = swi.ren_date\r\n"
    		+ "\r\n"
    		+ "\r\n"
    		+ "join\r\n"
    		+ "(select DATE_FORMAT(ren_stime, '%Y-%m-%d') as ren_date, \r\n"
    		+ "format(sum(rtn_price),'C') as date_rev,\r\n"
    		+ "(select format(sum(rtn_price),'C') from history) as total_rev\r\n"
    		+ "from history\r\n"
    		+ "group by ren_date) as total\r\n"
    		+ "\r\n"
    		+ "on mor.ren_date = total.ren_date;");
    rs = pst.executeQuery();
    table_15.setModel(DbUtils.resultSetToTableModel(rs));
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(26, 80, 533, 232);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 56, 231, 155);
		panel.add(scrollPane);
		
		table_11 = new JTable();
		scrollPane.setViewportView(table_11);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(278, 56, 233, 155);
		panel.add(scrollPane_1);
		
		table_12 = new JTable();
		scrollPane_1.setViewportView(table_12);
		
		lblNewLabel = new JLabel("\uCD5C\uB2E4 \uB300\uC5EC\uC9C0\uC5ED top 5");
		lblNewLabel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		lblNewLabel.setBounds(38, 20, 148, 26);
		panel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("\uCD5C\uB2E4 \uBC18\uB0A9\uC9C0\uC5ED top 5");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.BOLD, 14));
		lblNewLabel_1.setBounds(295, 20, 141, 26);
		panel.add(lblNewLabel_1);
		
		btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(979, 555, 95, 36);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(571, 138, 503, 174);
		frame.getContentPane().add(scrollPane_2);
		
		table_13 = new JTable();
		scrollPane_2.setViewportView(table_13);
		
		lblNewLabel_2 = new JLabel("\uBC29\uCE58 \uC720\uC800, \uAE30\uAE30, \uBC18\uB0A9 \uC2DC\uAC04, \uC7A5\uC18C \uBA85\uB2E8");
		lblNewLabel_2.setFont(new Font("±¼¸²", Font.BOLD, 16));
		lblNewLabel_2.setBounds(678, 80, 298, 48);
		frame.getContentPane().add(lblNewLabel_2);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(40, 371, 508, 172);
		frame.getContentPane().add(scrollPane_3);
		
		table_14 = new JTable();
		scrollPane_3.setViewportView(table_14);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(566, 371, 508, 174);
		frame.getContentPane().add(scrollPane_4);
		
		table_15 = new JTable();
		scrollPane_4.setViewportView(table_15);
		
		lblNewLabel_3 = new JLabel("\uB0A0\uC9DC\uBCC4, \uAE30\uAE30 \uC885\uB958\uBCC4 \uB300\uC5EC \uD69F\uC218");
		lblNewLabel_3.setFont(new Font("±¼¸²", Font.BOLD, 16));
		lblNewLabel_3.setBounds(163, 339, 286, 21);
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel(" \uB0A0\uC9DC\uBCC4, \uC2DC\uAC04\uBCC4 \uB9E4\uCD9C \uBD84\uC11D ");
		lblNewLabel_4.setFont(new Font("±¼¸²", Font.BOLD, 16));
		lblNewLabel_4.setBounds(725, 339, 310, 21);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Analysis");
		lblNewLabel_5.setFont(new Font("±¼¸²", Font.BOLD, 28));
		lblNewLabel_5.setBounds(507, 28, 270, 42);
		frame.getContentPane().add(lblNewLabel_5);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
