/**
 * 
 */
package eu.spoonman.smasher.client.pircbot;

import eu.spoonman.smasher.common.Configuration;
import eu.spoonman.smasher.common.Configuration.ConfigurationKey;

/**
 * @author spoonman
 * 
 */
public class SmasherPircBotMain {

	public static void main(String[] args) throws Exception {
		
		Configuration.getInstance().init();

		// Now start our bot up.
		SmasherPircBot bot = new SmasherPircBot();

		// Enable debugging output.
		bot.setVerbose(false);

		// Connect to the IRC server.
		bot.connect("irc.quakenet.org");
		
		for (String channel : Configuration.getInstance().getKey(ConfigurationKey.BOT_CHANNELS_KEY).split(";"))
			bot.joinChannel(channel);
	}
}
