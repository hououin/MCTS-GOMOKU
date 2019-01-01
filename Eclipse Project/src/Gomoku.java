import java.util.Scanner;
import java.util.ArrayList;
public class Gomoku {
	public static int W = 7;
	public static int L = 7;
    public static int lastI = 0;
    public static int lastJ = 0;
	public static byte[][] board = {{ 0, 0, 0, 0, 0, 0, 0},
			                        { 0, 0, 0, 0, 0, 0, 0},
			                        { 0, 0, 0, 0, 0, 0, 0},
			                        { 0, 0, 0, 0, 0, 0, 0},
			                        { 0, 0, 0, 0, 0, 0, 0},
			                        { 0, 0, 0, 0, 0, 0, 0},
			                        { 0, 0, 0, 0, 0, 0, 0}};
	                                                       

	// public static byte[][] board = new byte[L][W];
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		/*MCTTree mct = new MCTTree(board);		
		Node nextStep;
		int NameCounter = 1;
		int freePos = NumFreePosition(board);
		long startTime,endTime;
		while ((mct.ifWin(board, 30) == -1) && mct.ifWin(board, 20) == -1) { 
			
			mct = new MCTTree(board);
			
			play(board);
			freePos = NumFreePosition(board);
			mct.freePos = freePos;
			printBoard(board);
			
			System.out.println("mct play");	
			startTime = System.nanoTime();
			nextStep = mct.search(board, NameCounter,lastI,lastJ);
			endTime = System.nanoTime();
			System.out.println("time "+(endTime-startTime));
			
			NameCounter++;
			copyBoard(nextStep.state, board);
			printBoard(board);
            
			freePos--;
			if (freePos == 0) {
				break;
			}
          
		}*/
		//sc.close();
        
		MCTTree mct = new MCTTree(board);
		int freePos = NumFreePosition(board)-1;
		int c = 5;
	//	while(c > 0) {
			play(board);
			printBoard(board);
	        System.out.println(mct.simulation1(board, lastI, lastJ,freePos));
			
			c--;
	//	}
        sc.close();
		
	}

	public static void copyBoard(byte[][] src, byte[][] dest) {
		for (int i = 0; i < L; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, W);
		}
	}

	public static int NumFreePosition(byte[][] board) {
		int freeNum = 0;
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < W; j++) {
				if (board[i][j] != 20 && board[i][j] != 30) {
					freeNum++;
				}
			}
		}
		return freeNum;
	}

	public static void play(byte[][] board) {
		boolean waitToPlay = true;

		while (waitToPlay) {
			try {
				System.out.println("Izberi pozicijo (napisi A do G za vrstico, 1 do 7 pa za stolpca):");
				String inputPos = sc.nextLine();
				System.out.println(inputPos);
				char row = inputPos.charAt(0);
				int column = inputPos.charAt(1) - 48;
				// System.out.println(row+" "+column);
				int pRow = row - 'A';
				int pColumn = column - 1; // board se zacne z 1, matrika pa 0;
				// System.out.println(pRow+" "+pColumn);
				if (pColumn >= 0 && pColumn < 7 && pRow >= 0 && pRow < 7) {
					if (board[pRow][pColumn] != 30 && board[pRow][pColumn] != 20) {
						board[pRow][pColumn] = 20;
						lastI = pRow;
						lastJ = pColumn;
						waitToPlay = false;
					} else {
						System.out.println("zasedena pozicija");

					}
				} else {
					System.out.println("ne veljavna pozicija");
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("ne veljaven input");
			}
		}

	}

	public static void printBoard(byte[][] board) {
		for (int i = 0; i < W; i++) {
			System.out.println("------------------------------");
			// System.out.println("------------------------------------------------------------------------------");
			System.out.print("|");
			for (int j = 0; j < L; j++) {

				switch (board[i][j]) {
				case 0:
					System.out.print(" " + " " + " " + "|");
					break;
				case 20:
					System.out.print(" " + "O" + " " + "|");
					break;
				case 30:
					System.out.print(" " + "x" + " " + "|");
					break;
				case 40:
					System.out.print(" " + "e" + " " + "|");
					break;
				case 50:
					System.out.print(" " + "s" + " " + "|");
					break;
				default:
					System.out.print(" " + board[i][j] + " " + "|");

				}

				// System.out.print(" " + board[i][j] + " " + "|");
			}
			System.out.println();
		}
		System.out.println("------------------------------");
		// System.out.println("------------------------------------------------------------------------------");
	}

}