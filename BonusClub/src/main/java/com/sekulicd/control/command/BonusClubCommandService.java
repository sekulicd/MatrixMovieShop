package com.sekulicd.control.command;

import javax.inject.Inject;

import com.sekulicd.control.datastore.BonusClubDB;
import com.sekulicd.entity.events.BonusClubEvent;
import com.sekulicd.entity.model.MovieType;
import com.sekulicd.kafka.boundary.EventProducer;

public class BonusClubCommandService {

	@Inject
	EventProducer eventProducer;

	@Inject
	BonusClubDB bonusClubDB;

	public void increaseCustomerBonus(String customerName, MovieType movieType) {
		eventProducer.publish(new BonusClubEvent(customerName, movieType));
	}

}
