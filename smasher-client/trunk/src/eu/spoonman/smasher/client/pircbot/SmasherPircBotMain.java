/**
 * 
 */
package eu.spoonman.smasher.client.pircbot;

/**
 * @author spoonman
 * 
 */
public class SmasherPircBotMain {

	public static void main(String[] args) throws Exception {

		// Now start our bot up.
		SmasherPircBot bot = new SmasherPircBot();

		// Enable debugging output.
		bot.setVerbose(true);

		// Connect to the IRC server.
		bot.connect("irc.quakenet.org");

		// Join the #pircbot channel.
		bot.joinChannel("#highquality.bot");

	}
}
