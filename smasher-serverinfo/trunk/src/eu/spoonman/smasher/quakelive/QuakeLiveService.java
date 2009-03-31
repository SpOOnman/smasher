/*
 * This file is part of Smasher.
 * Copyright 2008, 2009 Tomasz 'SpOOnman' Kalkosiński <spoonman@op.pl>
 * 
 * Smasher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Smasher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Smasher.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.spoonman.smasher.quakelive;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.management.RuntimeErrorException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLServerSocketFactory;
import javax.security.auth.login.LoginException;

/**
 * @author Tomasz Kalkosiński
 * QuakeLive Service to retrieve information.
 */
public class QuakeLiveService {
    
    private final String QUAKELIVE_URL_STRING = "http://www.quakelive.com";
    
    public void Login (String username, String password) throws LoginException {
        URL url = new URL(QUAKELIVE_URL_STRING);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("username", username);
        connection.setRequestProperty("pass", password);
        
        String responseMessage = connection.getResponseMessage();
        
        JSONObject json = new JSONObject(responseMessage);
    }

}
