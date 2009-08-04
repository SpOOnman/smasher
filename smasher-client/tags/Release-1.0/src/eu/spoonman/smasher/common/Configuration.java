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

package eu.spoonman.smasher.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Configuration {
	
	public final static String VERSION = "1.0";
	public final static String RELEASE_DATE = "4.08.2009";
	
	public enum ConfigurationKey {
		BOT_NAME_KEY ("bot.name"),
		BOT_CHANNELS_KEY ("bot.channels"),
		BOT_OPS_ONLY_KEY ("bot.ops_only"),
		QUAKELIVE_USERNAME_KEY ("quakelive.username"),
		QUAKELIVE_PASSWORD_KEY ("quakelive.password");
				
		
		private ConfigurationKey(String key) {
			this.key = key;
		}
		
		private String key;

		public String getKey() {
			return key;
		}
	}
	
	private final static Map<ConfigurationKey, String> values = new HashMap<ConfigurationKey, String>();
	
	private final static Configuration instance = new Configuration();
	
	private Configuration() {}
	
	public static Configuration getInstance() {
		return instance;
	}
	
	public void init() throws ConfigurationException {
		Properties properties = new Properties();
        try {
			properties.load(new FileInputStream("configuration.properties"));
			
			synchronized (properties) {
				
				readValue(properties, ConfigurationKey.BOT_NAME_KEY);
				readValue(properties, ConfigurationKey.BOT_CHANNELS_KEY);
				readValue(properties, ConfigurationKey.BOT_OPS_ONLY_KEY);
				
				readValue(properties, ConfigurationKey.QUAKELIVE_USERNAME_KEY);
				readValue(properties, ConfigurationKey.QUAKELIVE_PASSWORD_KEY);
			
			}
			
		} catch (IOException e) {
			throw new ConfigurationException("Cannot load file configuration.properties.", e);
		}
	}
	
	public String getKey(ConfigurationKey key) {
		return values.get(key);
	}
	
	private void readValue(Properties properties, ConfigurationKey key) throws ConfigurationException {
		String value = (String) properties.get(key.getKey());
		
		if (value == null || value.trim().length() == 0)
			throw new ConfigurationException("Cannot read a value for " + key + ".");
		
		values.put(key, value);
	}

}
