package br.com.os.model;

import br.com.os.controller.Controller;

/** This class describes the train, take takes passengers along a trail and takes
 * travellingTime (ms) to make an entire lap. */
public class Train extends Thread {
	
	private final int maxSeats;
	private int seats = 0;
	private int passengersEnjoying = 0;
	private int travelingTime;
	private boolean moving = false;
	
	private Controller controller;
	
	public Train(int maxSeats, int travelingTime) {
		this.maxSeats = maxSeats;
		this.setTravelingTime(travelingTime);
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			// Saying: "Available seats"
			if(this.controller.getLine() < this.maxSeats) {
				for(int i = 0; i < this.maxSeats; i++) {
					this.controller.getSemaphoreLine().up();
				}
			}
			
			// Sleeping while passengers do not enter
			if(this.seats < this.maxSeats) {
				this.controller.getSemaphoreTrain().down();
			}
			
			// Set moving
			this.controller.getSemaphoreMutex().down();
			this.moving = true;
			this.controller.getSemaphoreMutex().up();
			
			// Waking up passenger for enjoying the landscape
			for(int i = 0; i < this.maxSeats; i++) {
				this.controller.getSemaphorePassengers().up();
			}
			
			// Actually moving
			this.move();
			
			// Stop moving
			this.controller.getSemaphoreMutex().down();
			this.moving = false;
			this.controller.getSemaphoreMutex().up();
			
			// Waiting for passengers to stop enjoying the landscape
			this.controller.getSemaphoreTrain().down();
			
			// Waking up passengers to get out
			for(int i = 0; i < this.maxSeats; i++) {
				this.controller.getSemaphorePassengers().up();
			}
			
			// Waiting for passengers to get out
			this.controller.getSemaphoreTrain().down();
			
		}
		
	}
	
	private void move() {
		this.moving = true;
		System.out.println("The crazy train is moving...");
	}
	
	/** Getters and Setters */
	
	public int getMaxSeats() {
		return maxSeats;
	}

	public int getTravelingTime() {
		return travelingTime;
	}

	public void setTravelingTime(int travelingTime) {
		this.travelingTime = travelingTime;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	public void increaseSeats() {
		this.seats++;
	}
	
	public void decreaseSeats() {
		this.seats--;
	}
	
	public boolean isFull() {
		return this.seats == this.maxSeats;
	}
	
	public boolean isEmpty() {
		return this.seats == 0;
	}

	public int getPassengersEnjoying() {
		return passengersEnjoying;
	}

	public void setPassengersEnjoying(int passengersEnjoying) {
		this.passengersEnjoying = passengersEnjoying;
	}
	
	public void increasePassengersEnjoying() {
		this.passengersEnjoying++;
	}
	
	public void decreasePassengersEnjoying() {
		this.passengersEnjoying--;
	}

}
