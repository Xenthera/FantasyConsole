package com.bobby.fantasyConsole;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GPU {
	private BufferedImage canvas;
	private int currentColor;
	private Pallette pallette;
	FontSet font;
	
	public GPU(BufferedImage canvas){
		this.canvas = canvas;
		this.currentColor = 15;
		this.pallette = new Pallette();
		this.font = new FontSet();
	}


	
	public void clear(int color){
		GPU gpu = this;

		gpu.setColor(color);
		for (int i = 0; i < gpu.canvas.getWidth(); i++) {
			for (int j = 0; j < gpu.canvas.getHeight(); j++) {

				gpu._drawPixel(i, j);
			}
		}

	}

	private void printChar(int x, int y, int character){
		String c = this.font.get(character);
		for (int i = 0; i < c.length(); i++) {
			if(c.substring(i, i + 1).equals("1")){
				this.drawPixel(x + (i % 6), y + (i / 6));
			}
		}
	}

	public void drawString(int x, int y, String s) {
		s = s.toUpperCase();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int value = (int) c;
			this.printChar(x + (i * 6), y, value);

		}
	}

	public void fillRect(int x, int y, int width, int height){
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.drawPixel(i + x, j + y);
			}
		}
	}
	
	public void setColor(int color){
		this.currentColor = color;
	}

	
	public int getColor(){
		return this.currentColor;
	}

	public void _drawPixel(int x, int y){

		if(x >= 0 && x < this.canvas.getWidth() && y >= 0 && y < this.canvas.getHeight()){
			this.canvas.setRGB(x, y, this.pallette.get(this.currentColor).getRGB());
		}


	}

	public void drawPixel(int x, int y){
		if(x >= 0 && x < this.canvas.getWidth() && y >= 0 && y < this.canvas.getHeight()){
			this.canvas.setRGB(x, y, this.pallette.get(this.currentColor).getRGB());
		}
	}
	
	public void drawPixel(int x, int y, int color){
		this.setColor(color);
		this.drawPixel(x,y);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2){
		int x, y, dx, dy, dx1, dy1, px, py, xe, ye, i;
		dx = x2 - x1; dy = y2 - y1;
		dx1 = Math.abs(dx); dy1 = Math.abs(dy);
		px = 2 * dy1 - dx1;	py = 2 * dx1 - dy1;
		if (dy1 <= dx1)
		{
			if (dx >= 0)
				{ x = x1; y = y1; xe = x2; }
			else
				{ x = x2; y = y2; xe = x1;}

			drawPixel(x, y);
			
			for (i = 0; x<xe; i++)
			{
				x = x + 1;
				if (px<0)
					px = px + 2 * dy1;
				else
				{
					if ((dx<0 && dy<0) || (dx>0 && dy>0)) y = y + 1; else y = y - 1;
					px = px + 2 * (dy1 - dx1);
				}
				drawPixel(x, y);
			}
		}
		else
		{
			if (dy >= 0)
				{ x = x1; y = y1; ye = y2; }
			else
				{ x = x2; y = y2; ye = y1; }

			drawPixel(x, y);

			for (i = 0; y<ye; i++)
			{
				y = y + 1;
				if (py <= 0)
					py = py + 2 * dx1;
				else
				{
					if ((dx<0 && dy<0) || (dx>0 && dy>0)) x = x + 1; else x = x - 1;
					py = py + 2 * (dx1 - dy1);
				}
				drawPixel(x, y);
			}
		}
	}
	
	private void _drawLine(int sx, int ex, int ny){
		for (int i = sx; i <= ex; i++)
			drawPixel(i, ny);
	}
	
	public void fillCircle(int xc, int yc, int r){
		int x = 0;
		int y = r;
		int p = 3 - 2 * r;
		while (y >= x)
		{

			_drawLine(xc - x, xc + x, yc - y);
			_drawLine(xc - y, xc + y, yc - x);
			_drawLine(xc - x, xc + x, yc + y);
			_drawLine(xc - y, xc + y, yc + x);
			if (p < 0) p += 4 * x++ + 6;
			else p += 4 * (x++ - y--) + 10;
		}
	}
	
	public void drawCircle(int xc, int yc, int r){

			GPU gpu = this;

			int x = 0;
			int y = r;
			int p = 3 - 2 * r;

			while (y >= x) {
				_drawPixel(xc - x, yc - y);
				_drawPixel(xc - y, yc - x);
				_drawPixel(xc + y, yc - x);
				_drawPixel(xc + x, yc - y);
				_drawPixel(xc - x, yc + y);
				_drawPixel(xc - y, yc + x);
				_drawPixel(xc + y, yc + x);
				_drawPixel(xc + x, yc + y);
				if (p < 0) p += 4 * x++ + 6;
				else p += 4 * (x++ - y--) + 10;
			}



	}
	
	public int getWidth(){
		return this.canvas.getWidth();
	}
	public int getHeight(){
		return this.canvas.getHeight();
	}
}
