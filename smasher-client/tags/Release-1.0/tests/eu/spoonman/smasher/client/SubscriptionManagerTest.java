package eu.spoonman.smasher.client;

import org.junit.Test;

import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerQuery;

public class SubscriptionManagerTest {

	@Test
	public void testSubscribe() {
		Client client = new Client("test");

		ServerQuery query = org.easymock.classextension.EasyMock.createMock(ServerQuery.class);
		org.easymock.classextension.EasyMock.expect(query.getIdentification()).andReturn("ident");
		org.easymock.classextension.EasyMock.expect(query.getGame()).andReturn(Games.QUAKELIVE);
		org.easymock.classextension.EasyMock.replay(query);

		Scorebot scorebot = new ServerInfoScorebot("mock", query);

		SubscriptionManager.getInstance().subscribe(client, scorebot);
		SubscriptionManager.getInstance().unsubscribe(client, scorebot);
	}
}
