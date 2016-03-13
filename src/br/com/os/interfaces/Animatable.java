package br.com.os.interfaces;

import java.awt.Graphics;

/** Represents an element that can be animated. */
public interface Animatable {
	
	/** Must set the sprites and interval for the animation. */
	public void build();
	
	/** Updates and draw the current sprite.
	 * @param time The current time, for testing if the current sprite has to change.
	 * @param g The Graphics object in which to draw the current sprite. */
	public void updateAndDraw(long time, Graphics g);

	/** Starts animating the sprites. */
	public void play();
	
}
