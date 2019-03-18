import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

public class Game extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	//list of items on the screen
	private  Set<FieldItem> items = ConcurrentHashMap.newKeySet();
	
	//list of tiles on the screen
	private  Set<Tile> tiles = ConcurrentHashMap.newKeySet();
		
	//thread timer
	private ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(1);
	private ScheduledFuture<?> task;
		
	private int botsCounter = 9999;
	
	private int points = 0;
	private int p1Lives = Main.lives;
		
	private boolean isPause = false;
	private boolean isWin = false;
	
	private Image lives = ImageManager.getInstance().getLives();
	private Image ammo = ImageManager.getInstance().getAmmo();
	private Image armor = ImageManager.getInstance().getArmor();
		
	//temporary data
	int licon;	
	String plives;
	
			
	private final static int[][] GAME_OVER_MSG = 
        {
            {0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},//21x12
            {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
            {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
            {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}
         };
	
	private final static int[][] WIN_MSG = 
        {
            {0,1,0,1,0,0,1,1,0,0,1,0,0,1,0,0,0,0,0,0},	//21x12
            {0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0},
            {0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0},
            {0,0,1,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0},
            {0,0,1,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0},
            {0,0,1,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,0,0},
            {1,0,0,0,0,0,1,0,1,0,1,1,0,0,1,0,0,0,0,0},
            {0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,0,0,0,0,0},
            {0,1,0,1,0,1,0,0,1,0,1,0,0,1,1,0,0,0,0,0},
            {0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0}
         };
	
	private ArrayList<int[][]> levels = new ArrayList<int[][]>();

	private int number; 
		
	private Level currentLevel;
		
	/*private int[][] mapMatrix = { 	
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} 
								}; */
	
	
		
	public Game()
	{	
		this.setLayout(null);
		
		levels.add(ImageManager.getInstance().getL1());
		levels.add(ImageManager.getInstance().getL2());
		levels.add(ImageManager.getInstance().getL3());
		levels.add(ImageManager.getInstance().getL4());
		levels.add(ImageManager.getInstance().getL5());
		levels.add(ImageManager.getInstance().getL6());
		levels.add(ImageManager.getInstance().getL7());
		levels.add(ImageManager.getInstance().getL8());
		levels.add(ImageManager.getInstance().getL9());
		levels.add(ImageManager.getInstance().getL10());
		
		task = threadPool.scheduleAtFixedRate(this::repaint, 25, 25, TimeUnit.MILLISECONDS); // start main thread of level
		
		Main.m.midiPlayer.start();	
	}
				
	@SuppressWarnings("deprecation")
	@Override
	protected void paintComponent(Graphics g)
	{		
			super.paintComponent(g); //if call super - all updated,if not - paint above existing objects
			
			printGrass(g, number);
								
			botsCounter = 0;			
			
			//print all items
			for(FieldItem i: items)
			{
				//print item
				g.drawImage(i.getSprite(), i.getX(), i.getY(), null);
				
				if(i instanceof Tank )
				{
					if( ((Tank) i).isBot()) 
						botsCounter++;
				}
				
				//if armor of wall less than 0, (it's put here instead of wall class logic)
				if (i instanceof Wall && i.getArmor() <= 0 && i.isLive())
				{
					i.bang();	//bang the wall
					i = null;	//delete the object
				}
					
			}
			
			
			
			printInfoPanl(g);			
			
			//game over
			if(p1Lives <= 0)
			{					
				task.cancel(true);
				
				Main.m.midiPlayer.stop();
								
				SoundEffect.FALIURE.play();
				points = 0;
								
				//printing game over
				g.setColor(Color.WHITE);
								
				for (int i = 0; i < GAME_OVER_MSG.length; i++)
				{
					for (int j = 0; j < GAME_OVER_MSG[i].length; j++)
					{
						 if (GAME_OVER_MSG[i][j] == 1) 
							 g.fill3DRect(j*11+300, i*11+200, 10, 10, true);
					}
				}
				
				g.drawString("ENTER - BACK TO MAIN MENU", 320, 380);
						
			}
			
			//print press enter
			if((botsCounter == 0 || getCurrentLevel().getP1().isLive() == false ) && !isWin)
			{	
				g.setColor(Color.WHITE);
				if(new Date().getSeconds() % 2 == 0)
					g.drawString("PRESS ENTER FOR CONTINUE", 325, 350);
			}
			
			//print pause
			if(isPause)
			{
				Main.m.midiPlayer.stop();
				g.setColor(Color.WHITE);
				g.drawString("PAUSE", 400, 300);
				
			}
			else if(p1Lives > 0)
				Main.m.midiPlayer.start();
			
			//print win
			if(isWin)
			{
				finish();
				SoundEffect.FANFARE.play();
				this.getCurrentLevel().getP1().kill();
								
				g.setColor(Color.WHITE);
				
				for (int i = 0; i < WIN_MSG.length; i++)
				{
					for (int j = 0; j < WIN_MSG[i].length; j++)
					{
						 if (WIN_MSG[i][j] == 1) 
							 g.fill3DRect(j*11+300, i*11+200, 10, 10, true);
					}
				}
				
				g.drawString(points + " points", 360, 350);
				g.drawString("ENTER - BACK TO MAIN MENU", 300, 380);
				
			}
								
	}
	
	//free resources
	private void finish() 
	{			
		task.cancel(true);
		Main.m.midiPlayer.stop();
		tiles.clear();
		this.removeAll();
								
	}

	private void printInfoPanl(Graphics g) 
	{		
		
		//rectangle size and color
		g.setColor(Color.LIGHT_GRAY);
		g.fill3DRect(0, 0, 800, 12, true);
		
		//printing data of game;
		g.setColor(Color.BLACK);
		
		//panel icons
		g.drawImage(lives, 65, 1, null);
		g.drawImage(armor, 130, 1, null);
		g.drawImage(ammo, 195, 1, null);
				
		//panel info 
		g.drawString("Level " + (getNumeber()) + "              " + p1Lives + "                 " + getCurrentLevel().getP1().getArmor() + "                   " + getCurrentLevel().getP1().getAmmo() + "              points: " + points, 10, 10);
		
	}

	/*
	 * get level number
	 * 
	 */
	private void printGrass(Graphics g, int num)
	{		
		for(Tile t: tiles)
			g.drawImage(t.getSprite(), t.getX(), t.getY(), this);
				
	}
	
	public void changeLevel() 
	{	
		//restart current level
		if(currentLevel.getP1().isLive() == false && getP1Lives() > 0)
			newLevel(number);	 //init current level without changes
		
		//next level
		else if(currentLevel.getP1().isLive() && getBotsCounter() == 0)
		{				
			number++;
							
			if(number <= levels.size()) //if there is more levels - continue
				newLevel(number);
			
			else								// else - win the game
				setWin(true);
			
		}	
		else if(getP1Lives() <= 0 || isWin)
			if(getP1Lives() <= 0)
				Main.startTitle();
		
			
	}
	
	//start new level
	void newLevel(int number)
	{	
		for(FieldItem i: items)
		{
			if(i.task != null)
				i.task.cancel(true);
		}
					
		getItems().clear(); //clear items list
		getTiles().clear(); //clear tiles*/
		
		this.number = number;
		
		// 1 second pause
		try 
		{	
			Thread.sleep(1000);
		} 
		catch (Exception e1) 
		{	
			System.out.println("interupted");
			
		}
		
		if(number < levels.size()+1)
		{
			currentLevel = new Level(number, levels.get(number - 1));
		}
		else 
		{			
			Main.startTitle();
		}
					
	}


	public Level getCurrentLevel() {
		// TODO Auto-generated method stub
		return currentLevel;
	}


	public int getNumeber() {
		// TODO Auto-generated method stub
		return number;
	}	
	
	
	public ScheduledFuture<?> getTask() 
	{
		return task;
	}
	
	public Set<FieldItem>  getItems() 
	{
		return items;
	}

	public Set<Tile> getTiles() 
	{
		return tiles;
	}

	public int getBotsCounter()
	{
		return botsCounter;
	}

	public int getPoints() 
	{
		return points;
	}

	public void setPoints(int points) 
	{
		this.points = points;
	}

	public int getP1Lives() 
	{
		return p1Lives;
	}

	public void setP1Lives(int p1Lives) 
	{
		this.p1Lives = p1Lives;
	}
	
	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) 
	{
		this.isPause = isPause;
	}

	public boolean isWin() 
	{
		return isWin;
	}
		
	public void setWin(boolean isWin) 
	{
		this.isWin = isWin;
	}

	
}
