package master_guinea_pigs;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.net.*;

/**
 * Created by developerkhy@gmail.com on 2017. 6. 15. Blog :
 * http://soulduse.tistory.com Github : http://github.com/soulduse
 */

public class Server {

	private static int max_client = 100;

	private static int game_start_flag = 0; // flag variable checks if game is started
	private static HashSet<String> names = new HashSet<String>(); // hashset that stores user's name -> prevent same
																	// username
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); // hashset that stores client's ip address
																				// -> also prevent same ip
	private static HashMap<String, PrintWriter> info = new HashMap<String, PrintWriter>(); // hashmap that maps username
																							// and user's ip address
																							// hashmap
	private static HashMap<String, String> history = new HashMap<String, String>();

	private static int client_count = 0; // current user numbers in game

	private static String[] user = new String[max_client]; // store user name -> map with index (hashmap)
	private static PrintWriter[] ID = new PrintWriter[max_client]; // store user's address -> map with index(hashmap)

	private static final int ROW = 10;
	private static final int COL = 10;
	private static final int pig_CNT = 5;
	// the number of g_pig

	private static final String MINE = " * ";
	private static final String KEY = " K ";
	private static final String NONE = " x ";
	private static String mineArr[][] = null;

	static int pigArr_x[] = new int[5];
	static int pigArr_y[] = new int[5];

	static int number1[] = new int[100];
	static int number2[] = new int[100];
	static int number3[] = new int[100];
	static int number4[] = new int[100];
	static int number5[] = new int[100];

	static int key_x;
	static int key_y;
	static int cnt = 0;
	static int cnt_1 = 0, cnt_2 = 0, cnt_3 = 0, cnt_4 = 0, cnt_5 = 0;
	static int i = 0; //Store the user info cnt = i

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		
		fileRead();
		
		server.setInit();
		server.setMine(pig_CNT);
		server.printMine();

		for (int i = 0; i < cnt; i++) {
			System.out.println(pigArr_x[i] * 10 + pigArr_y[i]);
		}

		System.out.print("\nNUMBER 1 :");
		for (int i = 0; i < cnt_1; i++) {
			System.out.print(number1[i] + " ");
		}

		System.out.print("\nNUMBER 2 :");
		for (int i = 0; i < cnt_2; i++) {
			System.out.print(number2[i] + " ");
		}

		System.out.print("\nNUMBER 3 :");
		for (int i = 0; i < cnt_3; i++) {
			System.out.print(number3[i] + " ");
		}

		System.out.print("\nNUMBER 4 :");
		for (int i = 0; i < cnt_4; i++) {
			System.out.print(number4[i] + " ");
		}

		System.out.print("\nNUMBER 5 :");
		for (int i = 0; i < cnt_5; i++) {
			System.out.print(number5[i] + " ");
		}

		ServerSocket listener = new ServerSocket(9998);
		ExecutorService pool = Executors.newFixedThreadPool(100);
		try {
			while (true) {
				pool.execute(new Server_(listener.accept()));
			}
		} finally {
			listener.close();
		}
	}

	public Server() {
		mineArr = new String[ROW][COL];
	}

	// initial data
	private void setInit() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				mineArr[i][j] = NONE;
			}
		}
	}

	// set Mine to random value
	private void setMine(int mineCnt) {
		Random ran = new Random();

		while (mineCnt-- > 0) {
			int row = ran.nextInt(ROW);
			int col = ran.nextInt(COL);

			// if mine exists in random address, re-insert
			if (mineArr[row][col].equals(MINE)) {
				mineCnt++;
			}
			// if empty, add the mine
			if (mineArr[row][col].equals(NONE)) {
				mineArr[row][col] = MINE;

				pigArr_x[cnt] = row;
				pigArr_y[cnt] = col;
				cnt++;

			}
		}
	}

	// set Key to random value
	private void setKey(int keyCnt) {
		Random ran = new Random();

		while (keyCnt-- > 0) {
			int row = ran.nextInt(ROW);
			int col = ran.nextInt(COL);

			// if mine exists in random address, re-insert
			if (mineArr[row][col].equals(MINE)) {
				keyCnt++;
			}
			// if empty, add the key
			if (mineArr[row][col].equals(NONE)) {
				mineArr[row][col] = KEY;
				key_x = row;
				key_y = col;

			}
		}
	}

	// judge whether mine is present
	private static boolean isExistMine(int row, int col) {
		// ArrayIndexOutOfBoundsException prevention
		if (row < 0 || row >= ROW || col < 0 || col >= COL) {
			return false;
		}

		return mineArr[row][col].equals(MINE);
	}

	private static boolean isExistKey(int row, int col) {
		// ArrayIndexOutOfBoundsException prevention
		if (row < 0 || row >= ROW || col < 0 || col >= COL) {
			return false;
		}

		return mineArr[row][col].equals(KEY);
	}

	// Count mine after finding mines in 8 spaces except for yourself.
	private static int getMineNumber(int row, int col) {
		int mineCnt = 0;
		if (isExistMine(row - 1, col - 1))
			mineCnt++;
		if (isExistMine(row - 1, col))
			mineCnt++;
		if (isExistMine(row - 1, col + 1))
			mineCnt++;
		if (isExistMine(row, col - 1))
			mineCnt++;
		if (isExistMine(row, col + 1))
			mineCnt++;
		if (isExistMine(row + 1, col - 1))
			mineCnt++;
		if (isExistMine(row + 1, col))
			mineCnt++;
		if (isExistMine(row + 1, col + 1))
			mineCnt++;

		return mineCnt;
	}

	// Insert number of mines near mine
	private void setNumber(int row, int col) {
		if (mineArr[row][col].equals(NONE) && getMineNumber(row, col) != 0) {
			mineArr[row][col] = " " + getMineNumber(row, col) + " ";
		}

		if (getMineNumber(row, col) == 1) {
			number1[cnt_1] = row * 10 + col;
			cnt_1++;
		}
		if (getMineNumber(row, col) == 2) {
			number2[cnt_2] = row * 10 + col;
			cnt_2++;
		}
		if (getMineNumber(row, col) == 3) {
			number3[cnt_3] = row * 10 + col;
			cnt_3++;
		}
		if (getMineNumber(row, col) == 4) {
			number4[cnt_4] = row * 10 + col;
			cnt_4++;
		}
		if (getMineNumber(row, col) == 5) {
			number5[cnt_5] = row * 10 + col;
			cnt_5++;
		}

	}

	// print minesweeper array
	private void printMine() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				setNumber(i, j);
				System.out.print(mineArr[i][j]);
			}
			System.out.println();
		}
	}

	private static class Server_ implements Runnable {
		private Socket socket;
		private String name;
		private BufferedReader in;
		private PrintWriter out;

		public Server_(Socket socket) {
			this.socket = socket;
		}

		// @Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				while (true) {
					/* Check if the game has started or if it is currently waiting */
					if (game_start_flag == 0) {
						out.println("SUBMITNAME");
						name = in.readLine();
						if (name == null) {
							return;
						}
						synchronized (names) {
							sendToallclient("CONNECT " + name + " is connected.\n");
							if (!names.contains(name)) {
								names.add(name);
								System.out.println(names);
								sendToallclient("MESSAGE " + "New user '" + name + "' has entered.");
								break;
							} else {
								out.println("ERROR " + "This nickname is already using.");
							}
						}
					}
				}
				out.println("NAMEACCEPTED");

				writers.add(out);
				out.println("MESSAGE " + "[You have entered the waiting room]");
				user[client_count] = name;
				ID[client_count] = out;

				client_count++;

				info.put(name, out);

				while (true) {

					String inputMessage = in.readLine();
					String res = "";

					if (inputMessage.equals("GAMESTART")) {
						game_start_flag = 1; // flag on

						res += "";
						for (int i = 0; i < cnt; i++) {
							System.out.println(pigArr_x[i] * 10 + pigArr_y[i]);
							res += pigArr_x[i] * 10 + pigArr_y[i] + " ";
						}

						if (cnt_1 != 0) {
							System.out.print("\nNUMBER1:");
							res += "#";
							for (int i = 0; i < cnt_1; i++) {
								System.out.print(number1[i] + " ");
								res += number1[i] + " ";
							}
						}

						if (cnt_2 != 0) {
							System.out.print("\nNUMBER2:");
							res += "#";
							for (int i = 0; i < cnt_2; i++) {
								System.out.print(number2[i] + " ");
								res += number2[i] + " ";
							}
						}

						if (cnt_3 != 0) {
							System.out.print("\nNUMBER3:");
							res += "#";
							for (int i = 0; i < cnt_3; i++) {
								System.out.print(number3[i] + " ");
								res += number3[i] + " ";
							}
						}

						if (cnt_4 != 0) {
							System.out.print("\nNUMBER4:");
							res += "#";
							for (int i = 0; i < cnt_4; i++) {
								System.out.print(number4[i] + " ");
								res += number4[i] + " ";
							}
						}

						if (cnt_5 != 0) {
							System.out.print("\nNUMBER5:");
							res += "#";
							for (int i = 0; i < cnt_5; i++) {
								System.out.print(number5[i] + " ");
								res += number5[i] + " ";
							}
						}
						out.println("GAMESTART" + res + "\n");
					} else if (!inputMessage.equals("")) {
						sendToallclient("MESSAGE " + name + ": " + inputMessage);
					} else if (inputMessage.equals("GAMERESULT")) { // if game end, update the result
						
					} else if (inputMessage.equals("object_clicked")) {
						
					}
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				if (name != null) {
					names.remove(name);
					info.remove(name);
					client_count--;
				}
				if (out != null) {
					writers.remove(out);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}

		}
	}

	public static void sendToallclient(String mssg) {
		for (PrintWriter writer : writers) {
			writer.println(mssg);
			writer.flush();
		}
	}

	
	public static void fileRead() throws NumberFormatException, IOException {

        String path = Server.class.getResource("").getPath();
        File file = new File("serverinfo.txt");
         //������Ʈ ����ο� serverinfo.txt�� ���ս�Ų ���� ������ ��θ� �����Ѵ�.
        
        StringTokenizer st;
        FileReader filereader = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(filereader);
        UserInfo[] u_info = new UserInfo[100];
        String user, pw, name, last_time;
        int win, lose;

        
        String line = "";
          while((line = bufReader.readLine()) != null){
              //System.out.println(line);
              st = new StringTokenizer(line, " ");
              
              user = st.nextToken();
              //pw = st.nextToken();
              //name = st.nextToken();
              //last_time = st.nextToken();
              win = Integer.parseInt(st.nextToken());
              lose = Integer.parseInt(st.nextToken());
              
              u_info[i] = new UserInfo();
              
              u_info[i].user = user;
              //u_info[i].pw = pw;
              //u_info[i].name = name;
              //u_info[i].last_time = last_time;
              u_info[i].win = win;
              u_info[i].lose = lose;
              
              i = i+1;
              //System.out.println("\nUser: " + u_info[i-1].user +" pw : " + pw + "name : " + name + "last login time : " + last_time + "W / L : " + win + " / " + lose);
          } // 
     }

	
}
class UserInfo {
    String user;
    //String pw;
    //String name;
    //String last_time;
    int win;
    int lose;
    
    
    public String getUser() {
       return user;
    }
    public String setUser(String User) {
       return this.user = User;
    }
    
    /*
    public String getPw() {
       return pw;
    }
    public String setPw(String Pw) {
       return this.pw = Pw;
    }
    
    
    public String getLastTime() {
       return this.last_time;
    }
    public String setLastTime(String last_time) {
       return this.last_time = last_time;
    }
    */
    public int getWin() {
       return this.win;
    }
    public int setWin(int win) {
       return this.win = win;
    }
    
    public int getLose() {
       return this.lose;
    }
    public int setLose(int lose) {
       return this.lose = lose;
    }
 }