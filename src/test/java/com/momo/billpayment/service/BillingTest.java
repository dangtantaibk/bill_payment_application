package com.momo.billpayment.service;

import org.junit.Assert;
import org.junit.Test;

import com.momo.billpayment.service.Billing;
import com.momo.billpayment.service.Billing;

public class BillingTest {

	@Test
	public void fillAvailableSlotWhenSlotIsAvailable() {
		Billing parkingLot = new Billing(2);

		int slot1 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot1);

		int slot2 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(2, slot2);
	}

	@Test
	public void fillAvailableSlotWhenNoSlotIsAvailable() {
		Billing parkingLot = new Billing(1);

		int slot = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		try {
			parkingLot.fillAvailableSlot();
			Assert.assertTrue("should throw bill payment is full", false);
		} catch (Exception e) {
			String message = e.getMessage();
			Assert.assertEquals("Sorry, bill payment is full", message);
		}
	}

	@Test
	public void emptySlotWithValidSlotNumber() {
		Billing parkingLot = new Billing(3);

		int slot = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		int slot2 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(2, slot2);

		int slot3 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(3, slot3);

		parkingLot.emptySlot(2);
		int slot4 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(2, slot4);

		parkingLot.emptySlot(1);
		int slot5 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot5);
	}

	@Test
	public void emptySlotWithInvalidSlotNumber() {
		Billing parkingLot = new Billing(2);

		int slot = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		int slot2 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(2, slot2);

		try {
			parkingLot.emptySlot(3);
			Assert.assertTrue("should throw slot number is invalid exception", false);
		} catch (Exception e) {
			String message = e.getMessage();
			Assert.assertEquals("The slot number is invalid", message);
		}
	}

	@Test
	public void emptySlotWhichIsAlreadyEmpty() {
		Billing parkingLot = new Billing(2);

		int slot = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		try {
			parkingLot.emptySlot(2);
			Assert.assertTrue("should throw slot already empty exception", false);
		} catch (Exception e) {
			String message = e.getMessage();
			Assert.assertEquals("The slot is already empty", message);
		}
	}

}
