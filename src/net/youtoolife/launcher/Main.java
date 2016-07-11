package net.youtoolife.launcher;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("STALKER DOC");
		frame.setSize(350, 250);
		
		//frame.setPreferredSize(new Dimension(350, 250));
        //frame.setMaximumSize(new Dimension(350, 250));
        //frame.setMinimumSize(new Dimension(350, 250));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = genv.getDefaultScreenDevice();
		java.awt.DisplayMode mode = device.getDisplayMode();
        frame.setLocation(mode.getWidth()/2-350/2,mode.getHeight()/2-250/2);
        
        frame.add(new Form());
		
        frame.setVisible(true);

	}

}
