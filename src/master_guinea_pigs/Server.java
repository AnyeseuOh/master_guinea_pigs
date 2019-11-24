package master_guinea_pigs;

import java.util.Random;

/**
 * Created by developerkhy@gmail.com on 2017. 6. 15.
 * Blog      : http://soulduse.tistory.com
 * Github    : http://github.com/soulduse
 */

public class Server {

    private static final int ROW        = 10;
    private static final int COL        = 10;
    private static final int pig_CNT   	= 5;
    //기니피그 수
    private static final int key 		= 1;
    //열쇠 수
    
    private static final String MINE    = " * ";
    private static final String KEY 	= " K ";
    private static final String NONE    = " x ";
    private String mineArr[][]          = null;
    
    static int pigArr_x[] = new int[5];
    static int pigArr_y[] = new int[5];
    
    static int key_x;
	static int key_y;
    static int cnt=0;
    

    public static void main(String[] args) {

    	Server server = new Server();

    	server.setInit();

    	server.setMine(pig_CNT);
        
    	server.setKey(key);

    	server.printMine();
        
    	for (int i = 0; i < cnt; i++) {
    		System.out.println(pigArr_x[i] + " " + pigArr_y[i]);
    	}
    	
        System.out.println(key_x + " "+ key_y);
    }

    // 생성자
    public Server(){
        mineArr = new String[ROW][COL];
    }

    // 초기화 데이터 주입
    private void setInit(){
        for(int i=0; i<ROW; i++){
            for (int j=0; j<COL; j++){
                mineArr[i][j] = NONE;
            }
        }
    }

    // 랜덤 값으로 지뢰 주입
    private void setMine(int mineCnt){
        Random ran = new Random();

        while (mineCnt-- > 0){
            int row = ran.nextInt(ROW);
            int col = ran.nextInt(COL);

            // 랜덤한 배열 주소에 이미 지뢰가 있는 경우, 루프를 한번더 돌려서 랜덤위치 재생성
            if(mineArr[row][col].equals(MINE)){
                mineCnt++;
            }
            // 값이 비어 있는 경우 지뢰를 추가한다
            if(mineArr[row][col].equals(NONE)){
                mineArr[row][col] = MINE;
                
                pigArr_x[cnt] = row;
                pigArr_y[cnt] = col;
                cnt++;
                
                //지뢰의 위치를 저장한다
            }
        }
    }
    
 // 랜덤 값으로 지뢰 주입
    private void setKey(int keyCnt){
        Random ran = new Random();

        while (keyCnt-- > 0){
            int row = ran.nextInt(ROW);
            int col = ran.nextInt(COL);

            // 랜덤한 배열 주소에 이미 지뢰가 있는 경우, 루프를 한번더 돌려서 랜덤위치 재생성
            if(mineArr[row][col].equals(MINE)){
            	keyCnt++;
            }
            // 값이 비어 있는 경우 지뢰를 추가한다
            if(mineArr[row][col].equals(NONE)){
                mineArr[row][col] = KEY;
                key_x = row;
                key_y = col;
                //key의 위치를 저장한다
            }
        }
    }
    
    

    // 지뢰 존재여부 판단
    private boolean isExistMine(int row, int col){
        // ArrayIndexOutOfBoundsException 예방
        if(row < 0 || row >= ROW || col < 0 || col >= COL){
            return false;
        }

        return mineArr[row][col].equals(MINE);
    }

    // 해당 배열 기준 자기 자신을 제외한 8칸에서 지뢰를 찾은 후 카운팅 한다.
    private int getMineNumber(int row, int col){
        int mineCnt = 0;
        if(isExistMine(row-1, col-1))mineCnt++;
        if(isExistMine(row-1, col))mineCnt++;
        if(isExistMine(row-1, col+1))mineCnt++;
        if(isExistMine(row, col-1))mineCnt++;
        if(isExistMine(row, col+1))mineCnt++;
        if(isExistMine(row+1, col-1))mineCnt++;
        if(isExistMine(row+1, col))mineCnt++;
        if(isExistMine(row+1, col+1))mineCnt++;

        return mineCnt;
    }

    // 지뢰 근처에 지뢰 개수 숫자 주입
    private void setNumber(int row, int col){
        if(mineArr[row][col].equals(NONE) && getMineNumber(row,col)!=0){
            mineArr[row][col] = " "+getMineNumber(row,col)+" ";
        }
    }

    // 지뢰찾기 배열 출력
    private void printMine(){
        for(int i=0; i<ROW; i++){
            for (int j=0; j<COL; j++){
                setNumber(i, j);
                System.out.print(mineArr[i][j]);
            }
            System.out.println();
        }
    }
}


