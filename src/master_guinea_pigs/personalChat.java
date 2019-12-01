package master_guinea_pigs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class personalChat extends JFrame implements Runnable {

	private JPanel pChatContentPane;
	private JTextField pChatTextField;

	// Create the frame
	public personalChat() {
		setTitle("BATTLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 408);
		pChatContentPane = new JPanel();
		pChatContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pChatContentPane);
		pChatContentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(14, 12, 404, 308);
		pChatContentPane.add(textArea);
		textArea.setEditable(false);

		pChatTextField = new JTextField();
		pChatTextField.setBounds(14, 325, 270, 31);
		pChatContentPane.add(pChatTextField);
		pChatTextField.setColumns(10);

		JToggleButton btnReady = new JToggleButton("READY");
		btnReady.setBounds(292, 327, 126, 27);
		pChatContentPane.add(btnReady);

		btnReady.addActionListener(new ActionListener() { // 익명클래스로 리스너 작성
			public void actionPerformed(ActionEvent e) {
				JToggleButton btnReady = (JToggleButton) e.getSource();
				if (btnReady.getText().equals("READY"))
					btnReady.setText("START");
				/**
				 * 여기에다가 유조거 준비됐다는 신호 서버에 보내는 코드 추가 양쪽 두명의 신호를 받으면 서버가 두쪽에 '게임시작'
				 **/
				else
					btnReady.setText("READY");
			}
		});
	}

	@Override
	public void run() {
		try {
			personalChat frame = new personalChat();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}