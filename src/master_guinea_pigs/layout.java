package master_guinea_pigs;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import java.util.*;
/****************************************************************************
 * The Layout class is a gui class that shows opening page for users
 * if user click start button it shows game screen and gets server's ip address 
 * and user name
 * **************************************************************************/
 
public class layout extends JFrame {
	// JTextField textField = new JTextField(80);
	// JTextArea messageArea = new JTextArea(16, 80);
	Container frm;
	gameGUI gui = new gameGUI();
	JFrame frame = new JFrame();
	//BGM _music = new BGM("requiem.wav");
	
	public layout() { // constructor for opening screen
		setTitle("Master geinea pigs"); // title for opening screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon background = new ImageIcon("opening.png"); // background image for opening screen

		frm = new JFrame("Master geinea pigs"); // initialize frame

		frm.setBounds(0, 0, 1042, 816);
		ImageIcon start_btt = new ImageIcon("start_button.png"); // initialize start button 
		ImageIcon start_btt_p = new ImageIcon("start_button_pressed.png"); // if user press the button it will open room map
		JLabel imageLabel = new JLabel(background);
		imageLabel.setBounds(0, 0, 1024, 768);
		frm.add(imageLabel);
		/*
		 * below this code set size for start button
		 * and remove background of button
		 */
		JLabel btt_L = new JLabel();
		JButton strt_btt = new JButton(start_btt);
		strt_btt.setRolloverIcon(start_btt_p);
		strt_btt.setPressedIcon(start_btt_p);// 
		strt_btt.setSize(150, 90);
		strt_btt.setBorderPainted(false);
		strt_btt.setBounds(446, 480, 150, 90);
		btt_L.add(strt_btt);
		strt_btt.addActionListener(new press_start());
		frm.add(btt_L);
		//_music.Play();
		frm.setSize(1042, 816);
		frm.setVisible(true);
	}
	/*
	 * action handler for start button
	 * if user clicks the start button press_start handler
	 * starts send_socket class thread and room gui thread
	 */
	private class press_start implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			frm.setVisible(false);
			Thread t2 = new Thread(new Client());
			t2.start();
		}
	}

	public static void main(String[] args) {
		layout client = new layout();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}