
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 *
 * @author praneeth 
 * This class calculates Modulo determent of a square matrix
 */
public class ModDet {

  public static void main(String[] args) {
    int mod;
    int size;
    String input;
    String[] LongValues;
    Scanner stdin = new Scanner(new BufferedInputStream(System.in));
    input = stdin.nextLine();
    LongValues = input.split(" ");
    mod = Integer.parseInt(LongValues[0]);
    size = Integer.parseInt(LongValues[1]);
    int[][] matrix = new int[size][size];
    for (int i = 0; i < size; i++) {
      input = stdin.nextLine();
      LongValues = input.split(" ");
      for (int y = 0; y < size; y++) {
        matrix[i][y] = Integer.parseInt(LongValues[y]);
      }
    }

    System.out.println(cofModDet(size, mod(mod, size, matrix)) % mod);

  }

  /**
   * This method implements the logic for Euclidean
   *
   * @param N - modulo number
   * @param A - Square matrix
   * @return modulo determent of the square matrix
   */
  public static int cofModDet(int N, int[][] A) {

    int det;
    switch (N) {
      case 1:
        det = A[0][0];
        break;
      case 2:
        det = A[0][0] * A[1][1] - A[1][0] * A[0][1];
        break;
      default:
        det = 0;
        for (int j1 = 0; j1 < N; j1++) {
          int[][] m = new int[N - 1][];
          for (int k = 0; k < (N - 1); k++) {
            m[k] = new int[N - 1];
          }
          for (int i = 1; i < N; i++) {
            int j2 = 0;
            for (int j = 0; j < N; j++) {
              if (j == j1) {
                continue;
              }
              m[i - 1][j2] = A[i][j];
              j2++;
            }
          }
          det += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1] * cofModDet(N - 1, m);
        } break;
    }
    return det;
  }

  public static int[][] mod(int m, int size, int[][] array) {
    for (int i = 0; i < size; i++) {
      for (int y = 0; y < size; y++) {
        array[i][y] = array[i][y] % m;
      }

    }
    return array;
  }
}
