package com.dhemery.victor;

/**
 * A driver that can interact with a running iPhone or other iOS device.
 * @author Dale Emery
 *
 */
public interface PhoneDriver {
	public void rotateLeft();
	public void rotateRight();
	
	
	/**
	 * Saves an image of the screen to the desktop.
	 */
	public void saveScreenShot();
}
