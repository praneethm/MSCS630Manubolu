
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 *
 * @author praneeth
 * This class implements Euclidean for finding GDC of two numbers
 */
public class Euclidean {
    
   public static void main(String[] args){
   
       
       Scanner stdin = new Scanner(new BufferedInputStream(System.in));
       while(stdin.hasNext()){
       String input=stdin.nextLine();
       String[] LongValues = input.split(" ");
       System.out.println(euclidAlg((long)Long.parseLong(LongValues[0]),(long)Long.parseLong(LongValues[1])));
       }
   
   }
   /**
    * This method implements the logic for Euclidean
    * @param a - first input
    * @param b - second input
    * @return GCD for above numbers
    */
   public static long euclidAlg(long a, long b){
   long c;
   if(a<b){
     c=a;
     a=b;
     b=a;
   }
   long Division =a/b;
   long reminder = a-(b*Division);
     //  System.out.println(reminder+" "+Division);
   if(reminder==0)
       return b;
   else{
      return euclidAlg(b, reminder);
       }
     }   
   }
   
