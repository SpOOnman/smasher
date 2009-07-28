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
package eu.spoonman.smasher.client;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import eu.spoonman.smasher.client.pircbot.PircBotColors;
import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.scorebot.Scorebot;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class SubscriptionManager {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(SubscriptionManager.class);
	
	private static SubscriptionManager subscriptionManager;
	
	private List<Subscription> subscriptions = new ArrayList<Subscription>();
	
	private SubscriptionManager() {}
	
	public synchronized static SubscriptionManager getInstance() {
		if (subscriptionManager == null)
			subscriptionManager = new SubscriptionManager();
		
		return subscriptionManager;
	}
	
	public void subscribe(Client client, Scorebot scorebot) {
		Subscription subscription = find(client, scorebot);
		
		if (subscription != null)
			log.info(String.format("Scorebot %s is already subscribed to client %s.", scorebot.getId(), client.getName() ));
		
		subscription = getSubscription(client, scorebot);
		subscription.register();
		subscriptions.add(subscription);
		
		log.info(String.format("Scorebot %s has been subscribed to client %s.", scorebot.getId(), client.getName() ));
	}
	
	public void unsubscribe(Client client, Scorebot scorebot) {
		Subscription subscription = find(client, scorebot);
		
		if (subscription == null) {
			log.error(String.format("Cannot find subscription of scorebot %s for client %s.", scorebot.getId(), client.getName() ));
		}
		
		subscription.unregister();
		subscriptions.remove(subscription);
		
		log.info(String.format("Scorebot %s has been unsubscribed from client %s.", scorebot.getId(), client.getName() ));
	}
	
	private Subscription find(Client client, Scorebot scorebot) {
		for (Subscription subscription : subscriptions) {
			if (subscription.getClient() == client && subscription.getScorebot() == scorebot)
				return subscription;
		}
		
		return null;
	}
	
	private Subscription getSubscription(Client client, Scorebot scorebot) {
		
		// Formatting theme
		ConsoleFormatter consoleFormatter = new ConsoleFormatter(client.getColors(), null);
		
		// Client is console based - lines with ASCII characters.
		Subscription subscription = new ConsoleSubscription(scorebot, client, consoleFormatter);
		consoleFormatter.setSubscription(subscription);
		
		return subscription;
		
	}
	
	

}
