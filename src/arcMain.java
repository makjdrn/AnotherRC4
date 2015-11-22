public class arcMain {

    public arcMain() {
    }

    public static void main(String[] args) {
        StringBuffer input,key;
        input = new StringBuffer("11100000000000111111110111001110101110010100100101000001000010000010100111110001100100101101111101110011011011100010101111101110010100010001001011001011001101011110010100100111000111001000000111000000101001101100010111100000110100101000111111011011011000110100000011001011000011111101010011100110110001100000000100111101001111000110010111100110100100000101110101100100000000000111011111001111001001111010101010011011111001110111010010010001101001011111010000111001000011101001001101001100000110101010010101001001100001101001010011111110110101101100111000110010101101001000001000001110010001010010111000100000000000111100000000100111000100100100101101000100110110111000111001000011001010000000001110100001110001001110001110101001101110100101000001110001100100010101110100011010000000011000010000001010");
        key=new StringBuffer("5b7c959d92e969e9");
        input = toAscii(input);
        Crypt(input,key);
        System.out.println("DATA : "+input);
    }
    static void Crypt(StringBuffer inp,StringBuffer key)
    {
        int Sbox[];
        int Sbox2[];
        Sbox=new int[257];
        Sbox2=new int[257];
        int i, j, t, x;

        String OurUnSecuredKey = "5b7c959d92e969e9";
        char temp , k;
        //initialize sbox i
        for( i = 0; i < 256; i++)
        {
            Sbox[i] = i;
        }

        j = 0;
        if(key.length() >0)
        {
            for(i = 0; i < 256 ; i++)
            {
                if(j == key.length() )
                    j = 0;

                Sbox2[i] = key.charAt(j++);
            }
        }
        else
        {
            for(i = 0; i < 256 ; i++)
            {
                if(j == OurUnSecuredKey.length() +1)
                    j = 0;

                Sbox2[i] = OurUnSecuredKey.charAt(j++);
            }
        }

        j = 0 ;
        for(i = 0; i < 256; i++)
        {
            j = (j + Sbox[i] + Sbox2[i]) % 256;
            temp = (char)Sbox[i];
            Sbox[i] = Sbox[j];
            Sbox[j] = temp;
        }

        i = j = 0;
        for(x = 0; x < inp.length() ; x++)
        {
            //increment i
            i = (i + 1) % 256;
            //increment j
            j = (j + Sbox[i]) % 256;

            //Scramble SBox #1 further so encryption routine will
            //will repeat itself at great interval
            temp = (char)Sbox[i];
            Sbox[i] = Sbox[j] ;
            Sbox[j] = temp;

            //Get ready to create pseudo random byte for encryption key
            t = ( Sbox[i] + Sbox[j]) % 256 ;

            //get the random byte
            k = (char)Sbox[t];

            //xor with the data and done
            inp.setCharAt(x, (char)(inp.charAt(x) ^ k));
        }
    }
    private static StringBuffer toAscii(StringBuffer result) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < result.length(); i+=8)
            sb.append((char)Integer.parseInt(result.substring(i, i + 8), 2));
        //StringBuffe s;
        //s = sb.toString();
        return sb;
    }
}