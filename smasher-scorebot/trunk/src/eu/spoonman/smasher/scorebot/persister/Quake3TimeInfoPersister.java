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

package eu.spoonman.smasher.scorebot.persister;

import org.joda.time.Period;

import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.RoundInfo;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Quake3TimeInfoPersister extends ScorebotPersister {
    
	public Quake3TimeInfoPersister(Scorebot parent) {
		super(parent);
	}

	private Period oldPeriod;
    
    /**
     * Quake 3 doesn't record how many time it's left in timeouts. Copy these values from previous time states.
     */
    @Override
    public void persist(ProgressInfo left, ProgressInfo right) {
    	super.persist(left, right);
    }
    
    /*
     *   //Quake 3 doesn't record how many time it's left in timeouts.
        //Copy these values from previous time states.
        if (this->currentMatchState->progressStatus->flags & (TIME_STATUS_TIMEOUT_RED | TIME_STATUS_TIMEOUT_BLUE | TIME_STATUS_TIMEOUT_ANY | TIME_STATUS_FRAGLIMIT | TIME_STATUS_CAPTURELIMIT))
            this->UpgradeCurrentProgressStatusToTimeStatus (this->previousMatchState->progressStatus);

        //Countdown between rounds forgets about current round status. Retreive it.
        if (this->currentMatchState->progressStatus->type == PROGRESS_STATUS &&
            this->currentMatchState->progressStatus->flags & (TIME_STATUS_COUNTDOWN) &&
            this->previousMatchState->progressStatus->type == ROUND_STATUS)
        {
            RoundStatus* oldRoundStatus = dynamic_cast<RoundStatus*> (this->previousMatchState->progressStatus);

            RoundStatus* newRoundStatus = new RoundStatus (oldRoundStatus->round, oldRoundStatus->roundLimit, this->currentMatchState->progressStatus->flags);
            delete this->currentMatchState->progressStatus;

            this->currentMatchState->progressStatus = newRoundStatus;
        }
     */
    
}
