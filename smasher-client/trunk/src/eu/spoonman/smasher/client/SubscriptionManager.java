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

import java.util.List;

import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.scorebot.Scorebot;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class SubscriptionManager {
	
	private class SubscriptionItem {
		public Subscription subscription;
		public Client client;
		public Scorebot scorebot;
		
		public SubscriptionItem(Subscription subscription, Client client, Scorebot scorebot) {
			super();
			this.subscription = subscription;
			this.client = client;
			this.scorebot = scorebot;
		}
	}
	
	private static SubscriptionManager subscriptionManager;
	
	private List<SubscriptionItem> subscriptions;
	
	private SubscriptionManager() {}
	
	public synchronized static SubscriptionManager getInstance() {
		if (subscriptionManager == null)
			subscriptionManager = new SubscriptionManager();
		
		return subscriptionManager;
	}
	
	public void subscribe(Client client, Scorebot scorebot) {
		Subscription subscription = getConsoleSubscription();
		ConsoleSubscription consoleSubscription = new ConsoleSubscription(new ConsoleFormatter(new ConsoleColors(), new OutputConfiguration()));
		
	}
	
	private Subscription getIRCSubscription() {
		return getConsoleSubscription();
	}
	
	private Subscription getConsoleSubscription() {
		
		// Color scheme
		ConsoleColors consoleColors = new ConsoleColors();
		
		// Formatting theme
		ConsoleFormatter consoleFormatter = new ConsoleFormatter(consoleColors, new OutputConfiguration());
		
		// Client is console based - lines with ASCII characters.
		Subscription client = new ConsoleSubscription(consoleFormatter);
		
		return client;
	}
	
	

}
