/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funcservices;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import javax.mail.NoSuchProviderException;

/**
 *
 * @author Mbah Royce
 */
public class HashCreator {
    public static String createPasswordHashWithSalt(final String textToHash, byte[] salt) {
      try {
//         byte[] salt =;
         System.out.println(salt);
         return createSaltedHash(textToHash, salt);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
    
    private static String createSaltedHash(String textToHash, byte[] salt)
           throws NoSuchAlgorithmException {

      String saltedHash = null;
      // Create MessageDigest instance for MD5
      MessageDigest md = MessageDigest.getInstance("MD5");

      //Add salted bytes to digest
      md.update(salt);

      //Get the hash's bytes 
      byte[] bytes = md.digest(textToHash.getBytes());

      //Convert it to hexadecimal format to
      //get complete salted hash in hex format
      saltedHash = convertToHex(bytes);
      return saltedHash;
   }


   //Create salt
   private static byte[] createSalt()
           throws NoSuchAlgorithmException,
           NoSuchProviderException,
           java.security.NoSuchProviderException {

      //Always use a SecureRandom generator for random salt
      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
      //Create array for salt
      byte[] salt = new byte[16];
      //Get a random salt
      sr.nextBytes(salt);
      //return salt
      return salt;
   }
   
      private static String convertToHex(final byte[] messageDigest) {
      BigInteger bigint = new BigInteger(1, messageDigest);
      String hexText = bigint.toString(16);
      while (hexText.length() < 32) {
         hexText = "0".concat(hexText);
      }
      return hexText;
   }

}
