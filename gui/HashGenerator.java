package gui;
import java.security.*;
import java.nio.charset.StandardCharsets;


public class HashGenerator
{
    private String salt;
    private String ergebnis;
    
    public HashGenerator() {}
   
    public String erzeugeSHA256Hash(String klartext, String salt) {
        try {
            this.salt = salt;
            String gesalteterKlartext = klartext + "." + salt;
            
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = hasher.digest(gesalteterKlartext.getBytes(StandardCharsets.UTF_8));
            ergebnis = bytesToHex(hashBytes);
            return ergebnis;
        }
        catch(Exception e) {
            System.out.println("Hashing fehlgeschlagen, Fehlermeldung:");
            e.printStackTrace();
            return null;
        }
    }
    
    public String erzeugeSHA256Hash(String klartext) {
        return erzeugeSHA256Hash(klartext, erzeugeSalt());
    }
    
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private String erzeugeSalt() {
        String salt = "";
        for (int i = 0; i < 16; i++) {
            salt += "" + Integer.toHexString((int) (Math.random()*16));
        }
        this.salt = salt;
        return this.salt;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public String getErgebnis() {
        return ergebnis;
    }
}
