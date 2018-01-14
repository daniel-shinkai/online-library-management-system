/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;

/**
 *
 * @author Igor Santos
 */
public class SessionCookie extends Cookie {

    public static final int SESSION_TIME = -1;

    public SessionCookie(String name, String value) throws UnsupportedEncodingException {
        super(name, URLEncoder.encode(value, "UTF-8"));
        setMaxAge(SESSION_TIME);
    }
}
