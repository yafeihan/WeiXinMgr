package com.yafei.wx.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WxConstantUtil {
    private static String filePath = "weixin.properties";

    public static String APPID = null;
    public static String APPSECRET = null;
    public static String TOKEN = null;
    public static String AESKEY = null;

    private WxConstantUtil() {

    }

    static {
        Properties proper = new Properties();
        Map<String, String> properties = null;
        FileInputStream fis = null;
        InputStream in = null;
        try {
            fis = new FileInputStream(filePath);
            in = new BufferedInputStream(fis);
            proper.load(in);
            properties = readAllProperties(proper);

            if (properties.containsKey("APPID")) {
                APPID = (String) properties.get("APPID").trim();
            } else {
                // log.warn("Œ¥≈‰÷√APPID");
            }

            if (properties.containsKey("APPSECRET")) {
                APPSECRET = (String) properties.get("APPSECRET").trim();
            } else {
                //  log.warn("Œ¥≈‰÷√APPSECRET");
            }

            if (properties.containsKey("TOKEN")) {
                TOKEN = (String) properties.get("TOKEN").trim();
            } else {
                // log.warn("Œ¥≈‰÷√TOKEN");
            }

            if (properties.containsKey("AESKEY")) {
                AESKEY = (String) properties.get("AESKEY").trim();
            } else {
                // log.warn("Œ¥≈‰÷√AESKEY");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Map<String, String> readAllProperties(Properties prop) throws FileNotFoundException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> en = prop.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String Property = prop.getProperty(key);
            map.put(key, Property);
        }
        return map;
    }

}
