 /**
  * Encoder
  * 
  * This class implements a simple encoder 
  * conveting alphabets to integers
  * and uses console for taking input
  */

import java.util.*;
import java.io.BufferedInputStream;
import java.util.Scanner;

public class Encoder {

  private static HashMap encode;
  private static int length;
  private static char[] rawData;
  private static ArrayList<String> holder = new ArrayList<String>();

  public static void init() {
    encode = new HashMap();
    encode.put('a', 0);
    encode.put('b', 1);
    encode.put('c', 2);
    encode.put('d', 3);
    encode.put('e', 4);
    encode.put('f', 5);
    encode.put('g', 6);
    encode.put('h', 7);
    encode.put('i', 8);
    encode.put('j', 9);
    encode.put('k', 10);
    encode.put('l', 11);
    encode.put('m', 12);
    encode.put('n', 13);
    encode.put('o', 14);
    encode.put('p', 15);
    encode.put('q', 16);
    encode.put('r', 17);
    encode.put('s', 18);
    encode.put('t', 19);
    encode.put('u', 20);
    encode.put('v', 21);
    encode.put('w', 22);
    encode.put('x', 23);
    encode.put('y', 24);
    encode.put('z', 25);
    encode.put(' ', 26);
  // System.out.println("created Hashmap");
  }

  public static void main(String[] args) {
    init();
    Scanner stdin = new Scanner(new BufferedInputStream(System.in));
    System.out.println("Please use '$' in the last line complete input");
    //While function to take input till it reads '$'
    while (stdin.hasNext()) {
      String temp = stdin.nextLine();
      if (temp.equals("$"))
      break;
      holder.add(temp);
      }
    //uses str2int method to convert alphabets to int
    for (String data : holder) {
      int[] line=str2int(data);
      for(int i:line){
      System.out.print(i+" ");
      }
      /*for (Character letter : data.toCharArray()) {
      if(null!=encode.get(Character.toLowerCase(letter)))
      System.out.print(encode.get(Character.toLowerCase(letter))+ " ");
      }*/
      System.out.println();
      }
}
//
  public static int[] str2int(String plainText){
  //int[] encodedLine = new int[50];
  ArrayList<Integer> encodedLine = new ArrayList<Integer>();
  for (Character letter : plainText.toCharArray()) {
    if(null!=encode.get(Character.toLowerCase(letter)))
      encodedLine.add((Integer) encode.get(Character.toLowerCase(letter)));
    }
  int[] line=new int[encodedLine.size()];
  for(int i=0;i<line.length;i++){
    line[i]=encodedLine.get(i);
  }
  return line;
  }

}