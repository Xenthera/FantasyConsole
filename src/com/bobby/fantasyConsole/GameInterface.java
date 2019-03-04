package com.bobby.fantasyConsole;

public interface GameInterface {
	public void setup(GPU g);
	public void draw(GPU g, float dt);
	public void keyPressed(int code);
	public void keyReleased(int code);
	public void keyTyped(char code);
}
