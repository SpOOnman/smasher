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


package eu.spoonman.smasher.client.pircbot;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import eu.spoonman.smasher.client.ClientException;
import eu.spoonman.smasher.client.CommandLineParser;
import eu.spoonman.smasher.common.Configuration;
import eu.spoonman.smasher.common.Configuration.ConfigurationKey;

/**
 * 
 * @author spoonman
 *
 */
public class SmasherPircBot extends PircBot {
	
	private static final Logger log = Logger.getLogger(SmasherPircBot.class);
	
	private Map<String, PircBotClient> channels = new HashMap<String, PircBotClient>();
	
	private boolean ops_only;

    public SmasherPircBot() {
    	setName(Configuration.getInstance().getKey(ConfigurationKey.BOT_NAME_KEY));
    	ops_only = Boolean.parseBoolean(Configuration.getInstance().getKey(ConfigurationKey.BOT_OPS_ONLY_KEY));
    }

    @Override
	public void onMessage(String channel, String sender, String login, 
        String hostname, String message) {
    	
    	if (message.startsWith("!") && checkOperator(channel, sender))
    		parseLine(channel, message.replaceFirst("!", ""));
    }
    
    private boolean checkOperator(String channel, String sender) {
    	if (!ops_only)
    		return true;
    	
    	User user = getUser(sender, channel);
    	return user.isOp();
    }
    
    private void parseLine(String channel, String message) {
    	try {
			CommandLineParser.getInstance().parseAndExecute(getChannelClient(channel), message);
		} catch (ClientException e) {
			log.info("Client exception", e);
			sendMessage(channel, e.getMessage());
		} catch (Exception e) {
			log.error(e);
			sendMessage(channel, e.getMessage());
		}
    	
    }
    
    @Override
    public void log(String line) {
    	log.debug(line);
    }
    
    private PircBotClient getChannelClient(String channel) {
    	PircBotClient client = channels.get(channel);
    	
    	if (client == null) {
    		client = new PircBotClient(this, channel);
    		channels.put(channel, client);
    	}
    	
    	return client;
    }
}