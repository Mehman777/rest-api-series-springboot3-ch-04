package com.tiagoamp.booksapi.util;

import com.tiagoamp.booksapi.exception.BusinessException;
import com.tiagoamp.booksapi.exception.ExceptionMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralUtil {

    public static String createToken(UserDetails userDetails, long expires) {
        return computeSignature(userDetails, expires);
    }

    public static String computeSignature(UserDetails userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getAuthorities().toString()).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append(userDetails.getPassword()).append(":");

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!" + e);
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    public static boolean expired(long expireTime) {
        return expireTime >= System.currentTimeMillis();
    }

    public static Date calculateConfirmCodeExpiryDate(long expiryTime) {
        return new Date(System.currentTimeMillis() + expiryTime);
    }

   /* public static void isValidEmailAddress(String email) throws BusinessException {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) {
            throw new BusinessException(BusinessException.BusinessError.EMAIL_IS_NOT_VALID);
        }
    }*/

    public static byte[] getImage(String str) {
        byte[] bytes = null;
        File file = new File(str);
        try (FileInputStream fis = new FileInputStream(file);) {
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
        } catch (IOException ex) {
            Logger.getLogger(GeneralUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;
    }

    public static String formatDate(Date date,String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    // delete
    public static boolean deleteDirectory(File index) throws BusinessException {
        try {
            String[] entries = index.list();
            for (String s : entries) {
                File currentFile = new File(index.getPath(), s);
                currentFile.delete();
            }
            return index.delete();

        } catch (Exception e) {
            throw ExceptionMapper.map(e);
        }
    }
}