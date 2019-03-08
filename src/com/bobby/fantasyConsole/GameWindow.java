package com.bobby.fantasyConsole;

import com.bobby.fantasyConsole.Modules.GPU;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;

public class GameWindow extends Canvas implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame container;
	JPanel panel;
	BufferStrategy strategy;
	int width, height;
	
	GameInterface gi;
	
	public GameWindow(GameInterface gi){
		this.width = 800;
		this.height = 600;
		
		container = new JFrame("Fantasy Console");
		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(this.width, this.height));
		panel.setLayout(null);
		setBounds(0, 0, this.width, this.height);
		panel.add(this);
		panel.setBackground(new Color(30,30,30));
		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(true);
		container.setVisible(true);
		this.requestFocusInWindow();
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.gi = gi;

		this.addKeyListener(new KeyboardInput(this.gi));
	
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		this.panel.setPreferredSize(new Dimension(width, height));
		this.setBounds(0,0, width, height);
		this.container.pack();
	}
	
	public void start(){
		long delta, lastLoopTime = 0;
		boolean running = true;
		Random random = new Random();
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();
		
		BufferedImage canvas = new BufferedImage(258, 258, BufferedImage.TYPE_INT_RGB);
		GPU gpu = new GPU(canvas);
		this.gi.setup(gpu);
		while(running){
			this.width = this.panel.getWidth();
			this.height = this.panel.getHeight();
			delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			g = (Graphics2D) this.strategy.getDrawGraphics();
			this.gi.draw(gpu, delta);
			float scale;
			if(canvas.getWidth() < canvas.getHeight()){
				scale = (float)this.height / canvas.getHeight();
			}else if(canvas.getHeight() < canvas.getWidth()){
				scale = (float)this.width / canvas.getWidth();
			}else{
				if(this.width < this.height){
					scale = (float)this.width / canvas.getWidth();
				}else{
					scale = (float)this.height / canvas.getHeight();
				}
			}
			
			int x = (int)(canvas.getWidth() * scale);
			int y = (int)(canvas.getHeight() * scale);
			int padX = 0, padY = 0;
			if(y < this.height){
				padY = (this.height - y) / 2;
			}
			if(x < this.width){
				padX = (this.width - x) / 2;
			}


			g.drawImage(canvas, padX, padY, x + padX, y + padY, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
			g.dispose();
			strategy.show();

			try{
				Thread.sleep((long) (1000f / 30f));
			}catch (Exception e){
				
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
