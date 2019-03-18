import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager     
{ 
	//path to all files
	private String path = "../res/";
	
	private HashMap<Integer, Image> tiles = new HashMap<>();
	
	private HashMap<Integer, Image> heroIcons = new HashMap<>();
	private HashMap<Integer, Image> botTankIcons = new HashMap<>();
	
	private HashMap<Integer, Image> bulletIcons = new HashMap<>();
	private HashMap<Integer, Image> bangIcons = new HashMap<>();
	
	private Image wall;
	private Image icon; //icon of application
	
	private BufferedImage help;	//help tutorial
	private BufferedImage title; //title background
	
	//pabel icons
	private Image lives;
	private Image armor;
	private Image ammo;
		
	private int[][] l1 = { 	
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 5, 0, 0},
			{0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		};
	
	public int[][] l2 = { 	
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 5, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 2, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		};
	
	
	private int[][] l3 = { 	
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 0, 5, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 2, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0} 
		}; 
	
	private int[][] l4 = { 	
			{0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 5, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
			{2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		}; 
	
	private int[][] l5 = { 	
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 5, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		}; 
	
	private int[][] l6 = { 	
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 2, 0, 0, 0, 1, 0, 5, 0, 1, 0, 0, 0, 0, 2, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2} 
		}; 
	
	private int[][] l7 = { 	
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{0, 0, 0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2} 
		}; 
	
	private int[][] l8 = { 	
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 2, 1, 2, 0, 1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 1, 5, 1, 0, 1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 2, 1, 2, 0, 1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 2, 2, 2, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		}; 
	
	private int[][] l9 = { 	
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 1, 2, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 0},
			{0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 2, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{5, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		}; 
	
	private int[][] l10 = { 	
			{1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0},
			{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
			{1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{0, 2, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 2, 0, 0, 0},
			{0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 0, 0},
			{0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 2, 0, 0, 0},
			{0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 2, 2, 0, 0, 1, 0, 1, 0, 1, 0, 2, 0, 0, 0},
			{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
		}; 
	
	private ImageManager()
	{	
		try 
		{
			/*images initialization*/
			
			//player 1
			heroIcons.put(KeyEvent.VK_LEFT, new ImageIcon(path + "leftTank.png").getImage());	
			heroIcons.put(KeyEvent.VK_RIGHT, new ImageIcon(path + "rightTank.png").getImage());
			heroIcons.put(KeyEvent.VK_UP, new ImageIcon(path + "upTank.png").getImage());
			heroIcons.put(KeyEvent.VK_DOWN, new ImageIcon(path + "downTank.png").getImage());
			//bot		
			botTankIcons.put(KeyEvent.VK_LEFT, new ImageIcon(path + "leftBotTank.png").getImage());	
			botTankIcons.put(KeyEvent.VK_RIGHT, new ImageIcon(path + "rightBotTank.png").getImage());
			botTankIcons.put(KeyEvent.VK_UP, new ImageIcon(path + "upBotTank.png").getImage());
			botTankIcons.put(KeyEvent.VK_DOWN, new ImageIcon(path + "downBotTank.png").getImage());
			//bullet
			bulletIcons.put(KeyEvent.VK_LEFT, new ImageIcon(path + "leftBullet.png").getImage());	
			bulletIcons.put(KeyEvent.VK_RIGHT, new ImageIcon(path + "rightBullet.png").getImage());
			bulletIcons.put(KeyEvent.VK_UP, new ImageIcon(path + "upBullet.png").getImage());
			bulletIcons.put(KeyEvent.VK_DOWN, new ImageIcon(path + "downBullet.png").getImage());
			//bang
			bangIcons.put(1, new ImageIcon(path + "bang1.png").getImage());
			bangIcons.put(2, new ImageIcon(path + "bang2.png").getImage());
			bangIcons.put(3, new ImageIcon(path + "bang3.png").getImage());
			//grass
			tiles.put(1, new ImageIcon(path + "tiles/1.png").getImage());	
			tiles.put(2, new ImageIcon(path + "tiles/2.png").getImage());
			tiles.put(3, new ImageIcon(path + "tiles/3.png").getImage());
			tiles.put(4, new ImageIcon(path + "tiles/4.png").getImage());
					
			icon = new ImageIcon(path + "icon.png").getImage();
			wall = new ImageIcon(path + "wall.png").getImage();
			
			help = ImageIO.read(new File(path + "help.png"));
			title = ImageIO.read(new File(path + "title.jpg"));
			
			 lives =ImageIO.read(new File(path + "lives.png"));
			 armor = ImageIO.read(new File(path + "armor.png"));
			 ammo = ImageIO.read(new File(path + "ammo.png"));
			 
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
					
	}
		
	/*getters and setters*/

	//player
	public Image getHeroIcon(int key) 
	{
		return heroIcons.get(key);
	}
	
	public HashMap<Integer, Image> getHeroIcons() 
	{
		return heroIcons;
	}
		
	//bot
	public Image getBotTankIcon(int key) 
	{
		return botTankIcons.get(key);
	}
		
	public HashMap<Integer, Image> getBotTankIcons() 
	{
		return botTankIcons;
	}

	public Image getBulletIcons(int key)
	{
		return bulletIcons.get(key);
	}
	
	public Image getWall() 
	{		
		return wall;
	}
	
	public Image getIcon() 
	{		
		return icon;
	}
		
	public HashMap<Integer, Image> getTiles() 
	{
		return tiles;
	}

	public Image getBangIcons(int key)
	{
		return bangIcons.get(key);
	}
	
	public int[][] getL1() {
		return l1;
	}


	public int[][] getL2() {
		return l2;
	}


	public int[][] getL3() {
		return l3;
	}


	public int[][] getL4() {
		return l4;
	}


	public int[][] getL5() {
		return l5;
	}


	public int[][] getL6() {
		return l6;
	}


	public int[][] getL7() {
		return l7;
	}


	public int[][] getL8() {
		return l8;
	}


	public int[][] getL9() {
		return l9;
	}


	public int[][] getL10() {
		return l10;
	}

		
	public String getPath() {
		return path;
	}

	public BufferedImage getHelp() {
		return help;
	}

	public BufferedImage getTitle() {
		return title;
	}

	public Image getLives() {
		return lives;
	}

	public Image getArmor() {
		return armor;
	}

	public Image getAmmo() {
		return ammo;
	}



	public void setLives(Image lives) {
		this.lives = lives;
	}

	public void setArmor(Image armor) {
		this.armor = armor;
	}

	public void setAmmo(Image ammo) {
		this.ammo = ammo;
	}



	//Singleton
	public static ImageManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ImageManager INSTANCE = new ImageManager();
	}
	
}
