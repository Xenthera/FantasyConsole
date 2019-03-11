package com.bobby.fantasyConsole;

import com.bobby.fantasyConsole.Modules.GPU;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameWindow extends Canvas implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JFrame container;
	private JPanel panel;
	private BufferStrategy strategy;
	private int width, height;
	private GameInterface gi;

	public GameWindow(GameInterface gi){
		this.width = 800;
		this.height = 600;
		this.container = new JFrame("Fantasy Console");
		this.panel = (JPanel) this.container.getContentPane();
		this.panel.setPreferredSize(new Dimension(this.width, this.height));
		this.panel.setLayout(null);
		this.panel.add(this);
		this.panel.setBackground(new Color(0,0,0));
		this.container.setVisible(true);
		this.container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(0, 0, this.width, this.height);
		this.setIgnoreRepaint(true);
		this.createBufferStrategy(2);
		this.strategy = getBufferStrategy();
		this.requestFocusInWindow();
		this.gi = gi;
		this.addKeyListener(new KeyboardInput(this.gi));
		this.panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setSize(panel.getWidth(), panel.getHeight());
			}
		});
		this.container.pack();
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
		Graphics2D g;
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
				System.out.println("Unable to sleep for who knows why");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}