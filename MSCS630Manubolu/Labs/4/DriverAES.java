
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 *
 * @author praneeth Driver Implementation for AES.
 */
public class DriverAES {

  public static void main(String[] args) {
    //aesRoundKeys("5468617473206D79204B756E67204675");
    // AESCipher.aesRoundKeys(args[0]);
    Scanner stdin = new Scanner(new BufferedInputStream(System.in));
    while (stdin.hasNext()) {
      String input = stdin.nextLine();
      if (input.length() == 32) {
        new AESCipher().aesRoundKeys(input);
      }
    }
  }

}
