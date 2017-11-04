package com.jcs.magazine.util;

import com.jcs.magazine.network.YzuClientDemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author：Jics
 * 2017/3/24 16:29
 */

public class HtmlUtil {

    public static String fmt(String str) {
        //用正则将img的style置为空
        Pattern pattern = Pattern.compile("style=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");

        Pattern imgPattern=Pattern.compile("src=\"");
        str=imgPattern.matcher(str).replaceAll("src=\""+YzuClientDemo.HOST);
        return "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>Sign in | Score System</title>" +
                "<style type=\"text/css\">\n" +
                "img{margin-top:15px;margin-bottom:15px;max-width:100%; height:auto;}" +
                "body{display:flex;flex-direction:column;justify-content:center;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                str +
                "</body>" +
                "</html>";
    }
   }
