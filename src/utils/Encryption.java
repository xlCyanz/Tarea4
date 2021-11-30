package utils;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Encryption {

    /**
     * Convierte el texto pasado por parametro a un texto
     * encriptado por MD5. (No se puede decriptar)
     * 
     * @param text Texto a encriptar
     * @return {@code String} Devuelve un texto encriptado.
     */
    public String getMD5(String text) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(text.getBytes("UTF-8"));
            return DatatypeConverter.printHexBinary(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
