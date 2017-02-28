
import java.io.BufferedInputStream;
import static java.lang.Math.ceil;
import java.util.Scanner;

/**
 *
 * @author praneeth 
 * This class converts plain text to ASCI matrix of size 16 with padding.
 * @variable padding - character to be padded
 * @variable input - string plain text
 * @variable inputPadded - StringBuilder object to perform paddings
 * 
 */
public class HexMatrix {

  public static void main(String[] args) {
    String input;
    char padding;
    StringBuilder inputPadded = new StringBuilder();
    Scanner stdin = new Scanner(new BufferedInputStream(System.in));
    padding = stdin.nextLine().charAt(0);
    input = stdin.nextLine();
    int noMatrix = (int) ceil(input.length() / 16.0);
    inputPadded.append(input);
    for (int i = 0; i <= ((noMatrix * 16) - input.length() - 1); i++) {
      inputPadded.append(padding);
    }
    int count = 0;
    for (int i = 0; i < noMatrix; i++) {
      String temp = inputPadded.substring(count, count + 16);
      count = count + 16;
      display(getHexMatP(padding, temp));
    }

  }
   /**
    * This method converts plain text to matrix of size 16 with padding
    * @param s - Padding character
    * @param p - Plain text
    * @return matrix of size 16
    */
  public static int[][] getHexMatP(char s, String p) {
    if (p.length() < 16) {
      for (int i = 0; i < 16 - p.length(); i++) {
        p = p + s;
      }
    }
    char[] c = p.toCharArray();
    int[][] temp = new int[4][4];
    int index = 0;
    for (int i = 0; i < 4; i++) {
      for (int y = 0; y < 4; y++) {
        temp[y][i] = (int) c[index++];
      }
    }
    return temp;
  }

    /**
    * This method displays the matrix in hex values
    * @param matrix - ASCI converted values of size 16(4*4)
    */
  private static void display(int[][] matrix) {
    for (int i = 0; i < 4; i++) {
      for (int y = 0; y < 4; y++) {
        System.out.print(Integer.toHexString(matrix[i][y]) + " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}
