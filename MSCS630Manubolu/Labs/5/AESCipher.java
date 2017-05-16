
/**
 *
 * @author praneeth
 * AESCipher Takes key of size 16 hex string and returns 11 round keys
 * Sbox and Rcon helper methods
 *
 */
public class AESCipher {

  static String[][] Ke = new String[4][4];
  static String[][] We = new String[44][4];
  static String[] output = new String[11];
  static int ColumnCount;

  public static String aesSBox(String[] inHex) {

    int x = (Integer.parseInt(inHex[0], 16) > 10) ? Integer.parseInt(inHex[0], 16) : Integer.parseInt(inHex[0], 16);
    int y = (Integer.parseInt(inHex[1], 16) > 10) ? Integer.parseInt(inHex[1], 16) : Integer.parseInt(inHex[1], 16);

    int[][] sbox = {{0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
    {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
    {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
    {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
    {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
    {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
    {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
    {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
    {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
    {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
    {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
    {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
    {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
    {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
    {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
    {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}};

    return Integer.toHexString(sbox[x][y]);
  }

  public static String aesRcon(int round) {

    int[] rcon = {0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
      0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
      0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
      0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
      0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
      0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
      0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
      0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
      0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
      0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
      0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
      0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
      0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
      0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
      0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
      0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb};

    return Integer.toHexString(rcon[round]);
  }

  public static String[] aesRoundKeys(String KeyHex) {
    int j = 0;
    ColumnCount = 4;
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 4; k++) {
        Ke[i][k] = KeyHex.substring(j, j + 2);
        We[i][k] = KeyHex.substring(j, j + 2);
        j += 2;
      }
    }
    for (int c = 0; c < 40; c++) {
      if (ColumnCount % 4 == 0) {
        String[] temp = new String[4];
        for (int i = 0; i < 4; i++) {

          if (i == 3) {
            temp[i] = aesSBox(new String[]{Character.toString(We[ColumnCount - 1][0].charAt(0)), Character.toString(We[ColumnCount - 1][0].charAt(1))});
          } else {
            temp[i] = aesSBox(new String[]{Character.toString(We[ColumnCount - 1][i + 1].charAt(0)), Character.toString(We[ColumnCount - 1][i + 1].charAt(1))});
          }
        }
        int r = Integer.parseInt(temp[0], 16) ^ Integer.parseInt(aesRcon(ColumnCount / 4), 16);
        temp[0] = Integer.toHexString(r);
        for (int i = 0; i < 4; i++) {
          int holder = Integer.parseInt(We[ColumnCount - 4][i], 16) ^ Integer.parseInt(temp[i], 16);
          if (Integer.toHexString(holder).length() < 2) {
            We[ColumnCount][i] = "0" + Integer.toHexString(holder);
          } else {
            We[ColumnCount][i] = Integer.toHexString(holder);
          }
        }
        ColumnCount++;
      } else {
        for (int i = 0; i < 4; i++) {

          int holder = Integer.parseInt(We[ColumnCount - 4][i], 16) ^ Integer.parseInt(We[ColumnCount - 1][i], 16);
          if (Integer.toHexString(holder).length() < 2) {
            We[ColumnCount][i] = "0" + Integer.toHexString(holder);
          } else {
            We[ColumnCount][i] = Integer.toHexString(holder);
          }
        }
        ColumnCount++;

      }

    }

    int pointer = 0;
    for (int i = 0; i < 11; i++) {
      output[i] = "";
      for (int x = 0; x < 4; x++) {
        for (int y = 0; y < 4; y++) {
          output[i] += We[x + pointer][y];

        }

      }
      pointer += 4;
    }

    return output;
  }

  public static String[][] AESStateXOR(String[][] sHex, String[][] keyHex) {
    String[][] outStateHex = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        int n3 = Integer.parseInt(sHex[i][j], 16) ^ Integer.parseInt(keyHex[i][j], 16);
        String temp = Integer.toHexString(n3).toUpperCase();
        if (temp.length() < 2) {
          outStateHex[i][j] = "0" + temp;
        } else {
          outStateHex[i][j] = temp;
        }
      }

    }

    return outStateHex;
  }

  public static String[][] AESNibbleSub(String[][] inStateHex) {

    String[][] temp = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        String[] cha = new String[2];
        cha[0] = String.valueOf(inStateHex[i][j].charAt(0));
        cha[1] = String.valueOf(inStateHex[i][j].charAt(1));
        String t = aesSBox(cha);
        if (t.length() < 2) {
          temp[i][j] = "0" + aesSBox(cha).toUpperCase();
        } else {
          temp[i][j] = aesSBox(cha).toUpperCase();
        }
      }

    }
    return temp;
  }

  public static String[][] AESShiftRow(String[][] mat) {

    String[][] temp = new String[4][4];

    for (int i = 0; i < 4; i++) {

      for (int j = 0; j < 4; j++) {

        if (i == 0) {
          temp[i][j] = mat[i][j];
        }

        if (i == 1) {
          temp[i][(j + 3) % 4] = mat[i][j];
        }

        if (i == 2) {
          temp[i][(j + 2) % 4] = mat[i][j];
        }

        if (i == 3) {
          temp[i][(j + 1) % 4] = mat[i][j];
        }
      }

    }
    return temp;

  }

  public static String[][] AESMixColumn(String[][] inStateHex) {
    int[][] mat = new int[][]{{2, 3, 1, 1}, {1, 2, 3, 1}, {1, 1, 2, 3}, {3, 1, 1, 2}};
    int[][] one = new int[4][4];
    for (int i = 0; i < 4; i++) {
      for (int y = 0; y < 4; y++) {

        one[i][y] = Integer.parseInt(inStateHex[i][y], 16);

      }
    }

    return new MyMatrix().multiplicar(mat, one);

  }

  public static void AES(String pTextHex, String keyHex) {
    String[][] temp = new String[4][4];
    int round = 0;

    aesRoundKeys(keyHex);

    int j = 0;
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 4; k++) {
        temp[k][i] = pTextHex.substring(j, j + 2);
        j += 2;
      }
    }

    //xor 1st round key and text
    temp = AESStateXOR(temp, ToMatrix(output[round]));

    for (int i = 0; i < 9; i++) {
      round++;
      temp = AESNibbleSub(temp);
      temp = AESShiftRow(temp);
      temp = AESMixColumn(temp);
      temp = AESStateXOR(temp, ToMatrix(output[round]));

    }
    round++;
    temp = AESNibbleSub(temp);
    temp = AESShiftRow(temp);
    temp = AESStateXOR(temp, ToMatrix(output[round]));
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 4; k++) {
        System.out.print(temp[k][i]);
      }
    }

  }

  public static String[][] ToMatrix(String input) {
    String[][] temp = new String[4][4];
    int j = 0;
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 4; k++) {
        temp[k][i] = input.substring(j, j + 2);
        j += 2;
      }
    }
    return temp;

  }

  public static class MyMatrix {

    public String[][] multiplicar(int[][] A, int[][] B) {

      int aRows = A.length;
      int aColumns = A[0].length;
      int bRows = B.length;
      int bColumns = B[0].length;

      if (aColumns != bRows) {
        throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
      }

      int[][] C = new int[aRows][bColumns];
      String[][] output = new String[aRows][bColumns];
      for (int i = 0; i < aRows; i++) {
        for (int j = 0; j < bColumns; j++) {
          C[i][j] = 0;
        }
      }

      for (int i = 0; i < aRows; i++) { // aRow
        for (int j = 0; j < bColumns; j++) { // bColumn
          for (int k = 0; k < aColumns; k++) { // aColumn
            //System.out.println("multypling "+Integer.toHexString(A[i][k])+" and "+Integer.toHexString(B[k][j]));
            char x, y;
            if (Integer.toHexString(B[k][j]).length() < 2) {
              x = '0';
              y = Integer.toHexString(B[k][j]).charAt(0);
            } else {
              x = Integer.toHexString(B[k][j]).charAt(0);
              y = Integer.toHexString(B[k][j]).charAt(1);
            }
            //System.out.println("x "+x+"y "+y);
            if (A[i][k] == 2) {
              C[i][j] ^= MCTables.mc2[Character.getNumericValue(x)][Character.getNumericValue(y)];
            }
            if (A[i][k] == 3) {
              C[i][j] ^= MCTables.mc3[Character.getNumericValue(x)][Character.getNumericValue(y)];
            }
            if (A[i][k] == 1) {
              C[i][j] ^= B[k][j];
            }

          }
          // System.out.print(Integer.toHexString(C[i][j])+" ");
          String temp = Integer.toHexString(C[i][j]).toUpperCase();
          if (temp.length() < 2) {
            output[i][j] = "0" + temp;
          } else {
            output[i][j] = temp;
          }
          //System.out.print(output[i][j]+" ");
        }
        //System.out.println("");
      }

      return output;
    }

  }

}
