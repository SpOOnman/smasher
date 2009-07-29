package eu.spoonman.smasher.client.pircbot;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jibble.pircbot.PircBot;

import eu.spoonman.smasher.client.ClientException;
import eu.spoonman.smasher.client.CommandLineParser;

public class SmasherPircBot extends PircBot {
	
	private static final Logger log = Logger.getLogger(SmasherPircBot.class);
	
	private Map<String, PircBotClient> channels = new HashMap<String, PircBotClient>();

    public SmasherPircBot() {
    	setName("HQ|Smasher");
    }

    @Override
	public void onMessage(String channel, String sender, String login, 
        String hostname, String message) {
    	
    	if (message.startsWith("!"))
    		parseLine(channel, message.replaceFirst("!", ""));

        if(message.equalsIgnoreCase("hello")) {
        	sendMessage(channel, "Hello World!");
        }
    }
    
    private void parseLine(String channel, String message) {
    	try {
			CommandLineParser.getInstance().parseAndExecute(getChannelClient(channel), message);
		} catch (ClientException e) {
			log.info("Client exception", e);
			sendMessage(channel, e.toString());
		} catch (Exception e) {
			log.error(e);
			sendMessage(channel, e.toString());
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