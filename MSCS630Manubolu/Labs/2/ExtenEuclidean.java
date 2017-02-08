
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 *
 * @author praneeth
 * This class implements Extended Euclidean Algorithm and also returns GDC
 */
public class ExtenEuclidean {
   
   public static void main(String[] args){
     Scanner stdin = new Scanner(new BufferedInputStream(System.in));
     while(stdin.hasNext()){
     String input=stdin.nextLine();
     String[] LongValues = input.split(" ");
     long[] result=eucliAlgExt((long)Long.parseLong(LongValues[0]),(long)Long.parseLong(LongValues[1]));
     System.out.println(result[0]+" "+result[1]+" "+result[2]);
     }    
   }
    
    /**
    * This method implements the logic for Extended Euclidean
    * Logic - https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
    *   ri = ri-2 - ri-1 qi-2
    *   si = si-2 - si-1 qi-2
    *   ti = ti-2 - ti-1 qi-2
    * 
    * a={r0,s0,t0} , s={r1,s1,t1}, t={r2,s2,t2}    
    */
   public static long[] eucliAlgExt(long m,long n){
    long c;
    if(m<n){
      c=m;
      m=n;
      n=m;
    }
    long[] a = {m, 1, 0};
    long[] s = {n, 0, 1};
    long[] t = new long[3]; 
       while (a[0]-s[0]*(a[0]/s[0]) > 0) {
            for (int j=0;j<3;j++) t[j] = s[j]; 
              long q = a[0]/s[0];        
            for (int i = 0; i < 3; i++) {
              s[i] = (a[i]-s[i]*q);
            }

            for (int k=0;k<3;k++) a[k] = t[k];
       }
    return s;
    }
    }
    

