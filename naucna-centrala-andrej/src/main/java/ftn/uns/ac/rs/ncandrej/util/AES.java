package ftn.uns.ac.rs.ncandrej.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AES {

    public static String encrypt(String value) {
        try {
            byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
                    0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            // encryption url
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] cipherText = cipher.doFinal(value.getBytes());
            //Encode Character which are not allowed on URL
            String encodedTxt = Base64.encodeBase64URLSafeString(cipherText);

            return encodedTxt;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
                    0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            String decodeStr = URLDecoder.decode(
                    encrypted,
                    StandardCharsets.UTF_8.toString());
            //Decode URl safe to base 64
            byte[] base64decodedTokenArr = Base64.decodeBase64(decodeStr.getBytes());

            byte[] decryptedPassword = cipher.doFinal(base64decodedTokenArr);
            //byte[] decryptedPassword = cipher.doFinal(decodeStr.getBytes());
            String  decodeTxt=new String(decryptedPassword);
            return decodeTxt;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
