package com.fullstack1.backend.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;

public class UtilEncriptacion {

    private static final String key = "BKGsN85lqJB0Slf5hcAKBkIkAWCYG5KrKCtWkgUrLGD=";  //44b
    private static final String initVector = "fNRJDLaHCK30bqbE"; //16b


    public static String encriptar(String texto) throws Exception {

        try {
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));


            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(texto.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
        }
    };


    public static String desencriptar(String textoEncriptado) throws Exception {
        
        try {

            if (textoEncriptado == null || textoEncriptado.isEmpty()) {
                throw new IllegalArgumentException("La contraseña encriptada no puede ser null o estar vacía");
            }

            SecretKey skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedText = Base64.getDecoder().decode(textoEncriptado);
            System.out.println("TEXTO DECODIFICADO BASE64: " + new String(decodedText, "UTF-8"));
            byte[] decrypted = cipher.doFinal(decodedText);

            return new String(decrypted);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new Exception("Error la contraseña encriptada no es válida o está vacía");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al desencriptar la contraseña: ", e);
        }
    };

}
