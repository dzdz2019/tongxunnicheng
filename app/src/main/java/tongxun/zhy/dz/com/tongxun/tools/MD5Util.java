package tongxun.zhy.dz.com.tongxun.tools;

import com.hygj.security.MD5;

public class MD5Util {

	 public static String getMD5Str(String str) {
         MD5 md5 = new MD5();
         str = md5.getMD5ofStr(str);
         return str;
        /*MessageDigest messageDigest = null;
     
        try {     
            messageDigest = MessageDigest.getInstance("MD5");     
     
            messageDigest.reset();     
     
            messageDigest.update(str.getBytes("UTF-8"));     
        } catch (NoSuchAlgorithmException e) {     
            System.out.println("NoSuchAlgorithmException caught!");     
            System.exit(-1);     
        } catch (UnsupportedEncodingException e) {     
            e.printStackTrace();     
        }     
     
        byte[] byteArray = messageDigest.digest();     
     
        StringBuffer md5StrBuff = new StringBuffer();     
        
        for (int i = 0; i < byteArray.length; i++) {                 
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)     
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));     
            else     
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));     
        }     
      //16λ���ܣ��ӵ�9λ��25λ
        return md5StrBuff.substring(8, 24).toString().toUpperCase();   */
    }
}
