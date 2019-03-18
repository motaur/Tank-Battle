import java.awt.event.KeyEvent;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Bullet extends FieldItem
{		 			
	public Bullet(int x, int y, int direction, int speed)
	{			
		super(ImageManager.getInstance().getBulletIcons(direction), x, y, speed, /*armor*/ 1);
				
		this.direction = direction;
				
		task = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> update(), speed, speed, TimeUnit.MILLISECONDS);
			
	}
	
	private void update()
	{			
		if(isCrossed() != null)
		{
			isCrossed().setArmor(isCrossed().getArmor() - 1);
			bang();
		}
		
		switch(direction)
		{			
			case KeyEvent.VK_LEFT: 
				if(getX() > 0)	
					x--;
				else
					bang();
				break;
				
			case KeyEvent.VK_RIGHT: 
				if(getX() < Main.WIDTH - 50)
					x++;
				else
					bang();
				break;
				
			case KeyEvent.VK_UP:
				if(getY() > 0)
					y--;
				else
					bang();
				break;
				
			case KeyEvent.VK_DOWN:
				if(getY() < Main.HEIGHT - 50)
					y++;
				else
					bang();
				break;
		}
				
	}
		
}
