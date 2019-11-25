package master_guinea_pigs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.Object;

public class gameGUI implements Runnable{
	static JButton[] button = new JButton[100];
	static int index = 0;
	static JFrame frame = new JFrame("기니피그를 잡아라!");
	static JFrame actionFrame = new JFrame();
	ImageIcon room = new ImageIcon("ground2.png");
	Image newImage = room.getImage();
	Image changedImage = newImage.getScaledInstance(768, 768, Image.SCALE_SMOOTH);
	ImageIcon newRoom = new ImageIcon(changedImage);
	
	@SuppressWarnings("serial")
	/*
	 * JPanel value panel set background room image for RoomGUI class
	 */
	JPanel panel = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(newRoom.getImage(), 0, 0, null);
		}
	};
	
	/*
	 * method runGUI gets imageicon value of object image
	 */
	public void runGUI() {
		frame.setBounds(0, 0, 1000, 815);
		frame.getContentPane().add(panel);
		ImageIcon grass = new ImageIcon("tileGrass2.png");
		
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				this.setObject(grass, i*64+64, j*64+64);
			}
		}
		
	}
	/*
	 * method setObject initalize object's location
	 */
	public void setObject(ImageIcon object, int x, int y) {

		panel.setLayout(null);
		button[index] = new JButton(object); // initialize button array

		Dimension size = button[0].getPreferredSize();
		button[index].setBackground(Color.red);
		button[index].setBorderPainted(false);
		button[index].setFocusPainted(false);
		button[index].setContentAreaFilled(false);
		button[index].setPressedIcon(object);

		panel.add(button[index]);
		button[index].setBounds(x, y, 64, 64);
		panel.setVisible(true);
		frame.setVisible(true);

		//button[index].setEnabled(false);
		button[index].addActionListener(new TheHandler());
		index++;
	}

	public void run() {
		this.runGUI();
	}
	
	private class TheHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for (int i = 0; i < 100; i++) {
				if (event.getSource() == button[i])
					System.out.println("object_clicked" + i);
			}
		}
	}
	public static void main(String[] args) {
		gameGUI client = new gameGUI();
		client.run();
	}
}
