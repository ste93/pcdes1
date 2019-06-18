
package es1;

public interface Constants {

	public int PLANET_NUMBER = 6;
	public int DELTA_TIME = 1;
	public double GRAVITY = 6.67408 * 10E-11;
	public int PANEL_SIZE_X = 1300;
	public int PANEL_SIZE_Y = 700;
	public int PANEL_BORDER_SIZE_X = 120;
	public int BUTTON_SPACING = 15;
	public int DRAWING_PANEL_SIZE_X = PANEL_SIZE_X - PANEL_BORDER_SIZE_X;
	public int DRAWING_PANEL_SIZE_Y = PANEL_SIZE_Y;
	public int BUTTON_SIZE_X = PANEL_BORDER_SIZE_X - BUTTON_SPACING * 2;
	public int BUTTON_SIZE_Y = 40;
	public int MIN_DISTANCE_BETWEEN_PLANETS = 10;
}
