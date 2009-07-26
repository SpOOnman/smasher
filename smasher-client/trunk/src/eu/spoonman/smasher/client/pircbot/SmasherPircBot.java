package eu.spoonman.smasher.client.pircbot;

import org.apache.log4j.Logger;
import org.jibble.pircbot.PircBot;

import eu.spoonman.smasher.client.ClientException;
import eu.spoonman.smasher.client.CommandLineParser;

public class SmasherPircBot extends PircBot {
	
	private static final Logger log = Logger.getLogger(SmasherPircBot.class);

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
			CommandLineParser.getInstance().parseAndExecute(new PircBotClient(this, channel), message);
		} catch (ClientException e) {
			log.info("Client exception", e);
			sendMessage(channel, e.toString());
		} catch (Exception e) {
			log.error(e);
			sendMessage(channel, e.toString());
		}
    	
    }
}