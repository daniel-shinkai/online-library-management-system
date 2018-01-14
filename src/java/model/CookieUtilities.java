/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Igor Santos
 */
public class CookieUtilities {

    public static String getCookieValue(HttpServletRequest request,
            String cookieName, String defaultValue) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return (URLDecoder.decode(cookie.getValue(), "UTF-8"));
                }
            }
        }
        return (defaultValue);
    }

    public static Cookie getCookie(HttpServletRequest request,
            String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return (cookie);
                }
            }
        }
        return null;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie c = getCookie(request, cookieName);
        if (c != null) {
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }
}
