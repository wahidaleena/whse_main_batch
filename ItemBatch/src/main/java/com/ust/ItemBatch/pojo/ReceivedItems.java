package com.ust.ItemBatch.pojo;

import java.sql.Date;

public class ReceivedItems {

	private int itemNumber;
	private int rcvdQty;
	private int poNum;
	private String storageLocation;
	private int movementStatus;
	private String comments;
	private String currentLocation;
	private Date timestamp;

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public int getRcvdQty() {
		return rcvdQty;
	}

	public void setRcvdQty(int rcvdQty) {
		this.rcvdQty = rcvdQty;
	}

	public int getPoNum() {
		return poNum;
	}

	public void setPoNum(int poNum) {
		this.poNum = poNum;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public int getMovementStatus() {
		return movementStatus;
	}

	public void setMovementStatus(int movementStatus) {
		this.movementStatus = movementStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
