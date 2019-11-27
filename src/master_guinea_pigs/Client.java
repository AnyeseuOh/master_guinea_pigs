package master_guinea_pigs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	
	static String IPaddress = "localhost";	
	static int portN = 9998;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader in = null;
		PrintWriter out = null;
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			
			socket = new Socket(IPaddress, portN);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
	         out = new PrintWriter(socket.getOutputStream(), true);
			while (true) {
				System.out.print("입력값:"); // 프롬프트
				String outputMessage = scanner.nextLine(); // 키보드에서 수식 읽기
				
				out.write(outputMessage + "\n"); // 키보드에서 읽은 수식 문자열 전송
				out.flush();
				
				String inputMessage = in.readLine(); // 서버로부터 계산 결과 수신
				System.out.println(inputMessage);
				
				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
		try {
			scanner.close();
			if(socket != null) socket.close(); // 클라이언트 소켓 닫기
		} catch (IOException e) {
			System.out.println("오류가 발생했습니다.");
		}
		}
		
	}

}
