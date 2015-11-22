import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Created by makjdrn on 2015-11-19.
 */
class newRC4 {
    private static final int[] S = new int[256];
    private final int[] T = new int[256];
    private final int keylen;

    public newRC4(String key) {
        if (key.length() < 1 || key.length() > 256) {
            throw new IllegalArgumentException(
                    "key must be between 1 and 256 bytes");
        } else {
            keylen = key.length();
            for (int i = 0; i < 256; i++) {
                S[i] = i;
                T[i] = key.charAt(i % keylen);
            }
            int j = 0;
            int tmp;
            for (int i = 0; i < 256; i++) {
                j = (j + S[i] + T[i]) & 0xFF;
                tmp = S[j];
                S[j] = S[i];
                S[i] = tmp;
            }
        }
    }

    public static String encrypt(String plaintext) {
        char[] ciphertext = new char[plaintext.length()];
        int i = 0, j = 0, k, t;
        int tmp;
        for (int counter = 0; counter < plaintext.length(); counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            ciphertext[counter] = (char) (plaintext.charAt(counter) ^ k);
        }
        return String.valueOf(ciphertext);
    }

    public static String decrypt(String ciphertext) {
        return encrypt(ciphertext);
    }
    public static String cryptogram = "00101110 11110000 00000111 01110001 00101111 00100101 11010011 11110111 01000000 10110001 01111110 01001010 00100100 00111110 11000101 00111101 11011010 00110110 10011011 00000011 11110110 10011110 10000101 10110010 11101111 10111101 00101100 00101100 10110100 11110110 00100011 11111011 01110110 00101001 01111001 01111000 10110101 00110101 11101101 01001001 11011011 11111111 11101111 11011000 00011001 01000110 11111010 01000110 11101001 00000110 00111111 00100101 11100101 11111010 01111110 11110001 00011110 01101111 00011101 00010010 01110011 00111001 01011110 01110101";
    public static void main(String[] args) throws UnsupportedEncodingException {
        cryptogram = cryptogram.replaceAll("\\s+", "");
        String ciphertext = toAscii(cryptogram);
        BigInteger op = new BigInteger("4294967280");
        BigInteger i = new BigInteger("1");
        BigInteger ii = new BigInteger("1");
        while(i.compareTo(op) == -1) {
            String key = CreateKey(i);
            new newRC4(key);
            i = i.add(ii);
            String result = decrypt(ciphertext);
            if(CheckifCorrect(result) == 64)
                System.out.println(result);
        }
    }

    private static int CheckifCorrect(String decrypt) {
        char c;
        int good = 0;
        int i = 0;
        int length = decrypt.length();
        while(i < length ) {
            //System.out.println(good);
            c = decrypt.charAt(i);
            if ((c >= 32 && c <= 127))// || (c >= 97 && c <= 122))// || c == 32 || c == 44 || c == 46 || c == 40 || c == 41 || c == 63 || (c >= 48 && c <=57) || c == 33 || c == 34))
                good++;
            i++;
        }
        return good;
    }

    private static String CreateKey(BigInteger i) {
        String zero = "00000000";
        String halfkey = "8e9d019d";
        String s;
        StringBuilder hex2String = new StringBuilder();
        String nexthex = i.toString(16);
        if (nexthex.length() < 8) hex2String.append(zero.substring(0, zero.length() - nexthex.length()));
        hex2String.append(nexthex);
        s = hex2String.toString() + halfkey;
        return s;
    }

    private static String toAscii(String result) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < result.length(); i+=8)
            sb.append((char)Integer.parseInt(result.substring(i, i + 8), 2));
        String s;
        s = sb.toString();
        return s;
    }
}