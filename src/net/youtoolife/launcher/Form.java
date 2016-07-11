package net.youtoolife.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.youtoolife.launcher.handlers.Download;
import net.youtoolife.launcher.handlers.HTTPRequest;

public class Form extends JPanel implements ActionListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	
	int state = 0;
	float fileSize = 400, curSize = 300;

	Timer mainTimer = new Timer(25, this);
	
	Image bg = new ImageIcon(getClass().getClassLoader().getResource("res/wall.png")).getImage();
	Image icon = new ImageIcon(getClass().getClassLoader().getResource("res/ICON.png")).getImage();
	Image install = new ImageIcon(getClass().getClassLoader().getResource("res/install.png")).getImage();
	Image pb = new ImageIcon(getClass().getClassLoader().getResource("res/pb.png")).getImage();
	Image play = new ImageIcon(getClass().getClassLoader().getResource("res/play.png")).getImage();
	/*Image bg = new ImageIcon("res/wall.png").getImage();
	Image icon = new ImageIcon("res/ICON.png").getImage();
	Image install = new ImageIcon("res/install.png").getImage();
	Image pb = new ImageIcon("res/pb.png").getImage();
	Image play = new ImageIcon("res/play.png").getImage();*/
	
	//Player p = new Player();
	
	//Thread enemiesFactory = new Thread(this);
	
	//Thread audioThread = new Thread(new AudioThread());
	
	//List<Enemy> enemies = new ArrayList<Enemy>();
	
	HTTPRequest request = new HTTPRequest();
	 Download download = new Download();
	//static Download download;
	 String ver;
	
	public Form() {
		mainTimer.start();
		//enemiesFactory.start();
		//audioThread.start();
		addKeyListener(new myKeyAdapter()); 
		addMouseListener(new customListener());
		setFocusable(true);
		
		File file;
		file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/STALKER_DOC.jar");
		if (file.exists() == true) 
			state = 2;
		else
			state = 0;
	}
	
	public class customListener implements MouseListener {
		 
        public void mouseClicked(MouseEvent e) {
            
        }

        public void mouseEntered(MouseEvent e) {
             
        }

        public void mouseExited(MouseEvent e) {
             
        }

        public void mousePressed(MouseEvent e) {
             
        }

        public void mouseReleased(MouseEvent e) {
        	
        	if (state == 0) {
				if (e.getX()>=(350/2-100/2) && e.getX()<=350/2+100/2
						&& e.getY()>=100+20+20 && e.getY()<=100+20+20+35) {
					System.out.println("install");
					state = 1;
					play();
				}
			}
        	
        	if (state == 2) {
				if (e.getX()>=(350/2-100/2) && e.getX()<=350/2+100/2
						&& e.getY()>=100+20+20 && e.getY()<=100+20+20+35) {
					System.out.println("play");
					state = -1;
					play();
				}
			}
        	
        }
   }
	
	private class myKeyAdapter extends KeyAdapter {
	
		public void keyPressed(KeyEvent e) {
		
	
		}
		public void keyReleased(KeyEvent e) {
			
			if (state == 0) {
		
					//System.out.println("install");
			
			}
	
		}

	}
	
	public void paint(Graphics g) {
		
		g = (Graphics2D) g;
		
		g.fillRect(0, 0, 350, 250);
		
		g.setColor(Color.white);
		Font font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		
		for (int y = 0; y < 250/50; y++)
			for (int x = 0; x < 350/50; x++)
				g.drawImage(bg, x*50, y*50, null);
		g.drawImage(icon, 350/2-100/2, 20, 100,100, null);
		
		if (state == 0) {
			g.drawImage(install, 350/2-100/2, 100 + 20+20, null);
		}
		
		if (state == 1) {
			g.drawString("DOWNLOADING...", 350/2-50-25, 100 + 20+20+15);
			g.drawImage(pb, 10, 250-70, (int)download.getValue(), 10, null);
			Font font2 = new Font("Arial", Font.BOLD, 10);
			g.setFont(font2);
			g.drawString(download.getState(), 10, 250-50);
		}
		
		if (state == 2) {
			g.drawImage(play, 350/2-100/2, 100 + 20+20, null);
		}
		
		
	}
	
	public void play() {
		
		File file;
		file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/STALKER_DOC.jar");
		if (file.exists() == false) {
			state = 1;
			System.out.print("1");
			
			file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC");
			file.mkdirs();
			file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/Resources");
			file.mkdirs();
			file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/STALKER_DOC_lib");
			file.mkdirs();
			file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/SFX");
			file.mkdirs();
			file = new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/Textures");
			file.mkdirs();
			upDate();
		} else
		{
			String keys[] = {"key","cmd"}, values[] = {"app","getVersion"};
			try {
				Scanner sc = new Scanner(new File(System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/ver.inf"));
				ver = sc.nextLine();
				sc.close();
				System.out.println(ver);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				state = 1;
				upDate();
				e1.printStackTrace();
			}
					String html;
					try {
						html = request.getPost("http://ytl.96.lt/stalker_doc/handler.php", keys, values, "ASCII");
					System.out.println(html);
					if (!html.contains(ver)) {
						state = 1;
						upDate();
					} else {
						Runtime.getRuntime().exec("java -jar "+System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/STALKER_DOC.jar");
						System.exit(0);
					}
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						upDate();
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						try {
							Runtime.getRuntime().exec("java -jar "+System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/STALKER_DOC.jar");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							//label.setText("Error: INTERNET_CANNOT_CONNECT!");
							error("Error: INTERNET_CANNOT_CONNECT!");
							upDate();
							e1.printStackTrace();
						}
						System.exit(0);
						e.printStackTrace();
					}
			}
	}
	public void upDate()  {
		//progressBar.setVisible(true);
		//label.setVisible(true);
		state = 1;
		System.out.print("2");
		
		String files[];
		String platform = null;
		boolean is64Bit = System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64");
		if (System.getProperty("os.name").contains("Windows")) platform = "win"+(is64Bit ? "64":"32");
		if (System.getProperty("os.name").contains("Linux")) platform = "linux"+(is64Bit ? "64":"32");
		if (System.getProperty("os.name").contains("Mac")) platform = "mac";
		
		String keys[] = {"key","cmd","platform"}, values[] = {"app","getFiles",platform};
		try {
			String firsString = request.getPost("http://ytl.96.lt/stalker_doc/handler.php", keys, values, "ASCII");
			files = firsString.split("<>");
			System.out.println(firsString);
			
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
			}
				//progressBar.setValue(0);
				curSize = 0;
				fileSize = 0;
				download.download(files);
				
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//label.setText("Error: INTERNET_CANNOT_CONNECT!");
			error("Error: INTERNET_CANNOT_CONNECT!");
			e.printStackTrace();
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		//p.move();
	    repaint();
	    //testWin();
	}

	private void error(String text) {
			JOptionPane.showMessageDialog(null,text);
			System.exit(0);
	}

	@Override
	public void run() {
		
	}
}