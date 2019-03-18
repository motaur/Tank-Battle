import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Tank extends FieldItem 
{
	private HashMap<Integer, Image> icons = new HashMap<>();
	
	private boolean bot; //controled by bot
	private int num;
			
	private boolean isMove;
	
	private boolean shootTimer;
	private ScheduledThreadPoolExecutor timer;
	
	private int ammo;
	
	//AI for bots
	private BotController controller;
	private AStar astar;
		
	//constructor for player1
	public Tank(int x, int y, int direction, int speed, int armor, int ammo, int num)
	{	
		super(ImageManager.getInstance().getHeroIcon(direction), x, y, speed, armor);
		
		icons = ImageManager.getInstance().getHeroIcons();
		this.direction = direction;
		shootTimer = true;
		
		bot = false;
		this.setNum(num);
		
		this.ammo = ammo;
								
		task = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> update(), speed, speed, TimeUnit.MILLISECONDS);
		timer = new ScheduledThreadPoolExecutor(1);
		
		Main.keyController.addTank(this, num); //control this tank by keyboard
					
	}
	

	//constructor for bot
	public Tank(int x, int y, int direction, int speed, int armor, boolean bot)
	{	
		super(ImageManager.getInstance().getBotTankIcon(direction), x, y, speed, armor);
		
		icons = ImageManager.getInstance().getBotTankIcons();
				
		this.direction = direction;
		shootTimer = true;
		
		this.bot = true;
		ammo = 999999999;
						
		task = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> update(), speed, speed, TimeUnit.MILLISECONDS);
		timer = new ScheduledThreadPoolExecutor(1);	
		
		new ScheduledThreadPoolExecutor(1).schedule(() -> astar = new AStar(this), /*delay*/1000, TimeUnit.MILLISECONDS); 
		
		/*if(Main.astar)
		{
			System.out.println("Astar");
			
		}
		else	
			new ScheduledThreadPoolExecutor(1).schedule(() -> controller = new BotController(this), /*delay*/  /*1000, TimeUnit.MILLISECONDS);*/ 
	}
			
	private void update()
	{	
		if(armor <= 0 )
		{
			if(controller != null)
			{
				controller.task.cancel(true);
				controller = null;
			}
			
						
			//if bot killed + 100 points
			if(bot)
				Main.game.setPoints(Main.game.getPoints() + 100);
			
			//if player killed - 1 live
			if(!bot)
			{
				Main.game.setP1Lives(Main.game.getP1Lives() - 1);
				
			}
								
			bang(); //anyway bang this tank
		}
		
		if(isMove && !Main.game.isPause())
		{				
			switch(direction)
			{
				case KeyEvent.VK_LEFT: 
					sprite = icons.get(direction);
					if(getX() > 0 )	
					{
						x--;
						
						if(isCrossed() instanceof FieldItem)
							x++;
					}
					
											
					break;
					
				case KeyEvent.VK_RIGHT: 
					sprite = icons.get(direction);
					if(getX() < Main.WIDTH - 50)
					{
						x++;
						if(isCrossed() instanceof FieldItem)
							x--;
					}
						
					break;
					
				case KeyEvent.VK_UP:
					sprite = icons.get(direction);
					if(getY() > 0)
					{
						y--;
						if(isCrossed() instanceof FieldItem)
							y++;
					}
						
					break;
					
				case KeyEvent.VK_DOWN:
					
					sprite = icons.get(direction);
					
					if(getY() < Main.HEIGHT - 50)
					{
						y++;
						if(isCrossed() instanceof FieldItem)
							y--;
					}
						
					break;
				
				default: return;
			}
		}
	}

	public void shoot() 
	{	
		if(ammo <= 0)
		{
			SoundEffect.MISFIRE.play();
		}
		
		//if there is timer, ammo, tank is live and game not paused ---> tank can shoot
		if(shootTimer && ammo > 0&& live && !Main.game.isPause())
		{
			//getting hero coordinates
			int x = getX();
			int y = getY();
			
			//bullet appearing correction coordinates
			switch(direction)
			{
				case KeyEvent.VK_LEFT:
					x -= 40;
					y += 15;
					
					break;
								
				case KeyEvent.VK_RIGHT:
					x += 60;
					y += 15;
					
					break;
					
				case KeyEvent.VK_UP:
					x += 15;
					y -= 40;
		    			
					break;
					
				case KeyEvent.VK_DOWN:
					x += 15;
					y += 60;
					
					break;
			}
			
			if(isLive())
			{	//add bullet to list for painting
				Main.game.getItems().add(new Bullet(x, y, direction, speed/3));
								
				shootTimer = false;
			}	
			
			if(bot)
				timer.schedule(() -> shootTimer = true, 1000, TimeUnit.MILLISECONDS); //timer between shoots
			else
				timer.schedule(() -> shootTimer = true, 500, TimeUnit.MILLISECONDS); //timer between shoots
			
			ammo--;
			if(!bot)
				Main.game.setPoints(Main.game.getPoints() - 10); //for shooting player lose 10 points
		}
		
	}
	
	public int getDirection()
	{
		return direction;
	}
		
	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public boolean isMove() 
	{
		return isMove;
	}

	public void setMove(boolean isMove)
	{
		this.isMove = isMove;
	}
	
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public boolean isBot() {
		return bot;
	}
	
	public int getAmmo()
	{
		return ammo;
	}
	
	public void setAmmo(int ammo)
	{
		this.ammo = ammo;
	}
	
	
}	