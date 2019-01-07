import java.util.ArrayList;

public class Node {  
   ArrayList<Node>children=new ArrayList<Node>();
   Node parent;
   int numVisited;
   double value;
   double uct;
   int lastI;
   int lastJ;
   int C = 1; //constant
   byte [][] state;
   public Node() {
	 state = new byte[19][19];     	  
   }

   public int findBestChild(int C) {
	   
	   int ix=0;
	   double max=0;
	   for(int i = 0; i < children.size(); i++) {
		   Node child = children.get(i);
		   if(numVisited != 0) {
			   if(child.numVisited != 0) {
				  child.uct = child.value + 2*C*(Math.sqrt((2*Math.log(numVisited))/child.numVisited)); 
			   }else {
				  child.uct = Double.POSITIVE_INFINITY; 
			   }
		   }else {
			   child.uct = child.value;			   
		   }
		   if(i == 0) {
			   max=child.uct;
		   }		   
		   if(child.uct>max) {
			   max = child.uct;
			   ix = i;
		   }
		   //System.out.println(child.value+" "+ix);
	   }
	   return ix;
   }
 }
