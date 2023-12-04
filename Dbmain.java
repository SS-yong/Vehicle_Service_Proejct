package dbproject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Dbmain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dbmain window = new Dbmain();
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
	public Dbmain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 445, 388);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Management System");
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		lblNewLabel.setBounds(79, 21, 287, 48);
		frame.getContentPane().add(lblNewLabel);
		
		JButton user = new JButton("User management");
		user.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		user.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Dbuser u = new Dbuser();
				u.setVisible(true);
			}
		});
		
		user.setBounds(103, 114, 196, 35);
		frame.getContentPane().add(user);
		
		JButton vehicle = new JButton("Vehicle management");
		vehicle.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		vehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dbvehicle v = new Dbvehicle();
				v.setVisible(true);
			}
		});
		
		vehicle.setBounds(103, 160, 196, 35);
		frame.getContentPane().add(vehicle);
		
		JButton summary = new JButton("Summary management");
		summary.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		summary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dbsummary m = new Dbsummary();
				m.setVisible(true);
			}
		});
		
		summary.setBounds(103, 205, 196, 36);
		frame.getContentPane().add(summary);
		
		JButton analysis = new JButton("Analysis");
		analysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dbanalysis a= new Dbanalysis();
				a.setVisible(true);
			}
		});
		analysis.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		analysis.setBounds(103, 251, 196, 35);
		frame.getContentPane().add(analysis);
	}
}
