import java.util.*;
class ExpPosition1 { // izberi pozicijo za expansitions
	int x;
	int y;

	public ExpPosition1(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
public class test {
	public static int a=5;
	
	public static void main(String[]args) {
          int a = 65;
          char b = (char)a;
          System.out.println(b);
	}

}

/*
                                   { {20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,30 },     // Nasprotnik:20 ; Agent: 30;  Mozna pozicija: 40 (zacasna oznaka)
			                         { 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0,30,30,30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0,30, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0,30, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0,30, 0,20,30, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0,20, 0,30, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0,20,30,20,20, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0,30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,30, 0, 0, 0,20 }, };

*/