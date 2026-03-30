package com.cesco.sys.common;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static String getCurrentYear(){
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy", Locale.KOREA );
        Date currentTime = new Date ( );
        return formatter.format ( currentTime );
    }

    public static String getCurrentMonth(){
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
        Date currentTime = new Date ( );
        return formatter.format ( currentTime );
    }
    /**
     * 6자리 인증키 생성, int 반환
     * @return
     */
    public static int generateAuthNo() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
    
    public static String convertSHA256(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.toString().getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            //출력
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /**
     * 파일 사이즈 계산
     * @return
     */    
    public static String getFileSize(Long fileSize) {
        String fileSizeNm = "";
        if ((fileSize/1024) < 1024) {
            fileSizeNm = fileSize/1024 + "kb";
        } else if((fileSize/1024) > 1024) {
            fileSizeNm = (fileSize/1024/1024) + "mb";
        }
        return fileSizeNm;
    }

}
