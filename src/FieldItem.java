import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.concurrent.ScheduledFuture;

public abstract class FieldItem
{		
	protected boolean live = true;
	protected int direction = 0;
	protected int x;
	protected int y;
	protected int speed;
	protected int armor; // if armor more than 0 - item is breakable, or if less than 0 - is unbreakable
	
	//image of item
	protected Image sprite;
	
	protected int width, height;
		
	//timer for current tread
	protected ScheduledFuture<?> task; 
	
	public FieldItem(Image sprite, int x, int y, int speed, int armor)
	{		
		this.sprite = sprite;
		
		width = sprite.getWidth(null);
	    height = sprite.getHeight(null);
		
		this.x = x;
		this.y = y;
		
		this.speed = speed;
		this.armor = armor;
		
	}
	
	
	
	
	/*
	 *the method checks if this item are crossed by other item
	 *return object, which is crossed
	 *
	 */
	public FieldItem isCrossed()
	{			
		for(FieldItem i: Main.game.getItems())
		{				
			if(i != this)
			{	
				    int tw = this.width;
			        int th = this.height;
			        
			        int iw = i.width;
			        int ih = i.height;
			        
			        int tx = this.x;
			        int ty = this.y;
			        
			        int ix = i.x;
			        int iy = i.y;
			        
			        iw += ix;
			        ih += iy;
			        
			        tw += tx;
			        th += ty;
			        
			             //overflow || intersect
			        if ( (iw < ix || iw > tx) && (ih < iy || ih > ty) && (tw < tx || tw > ix) && (th < ty || th > iy) )
			        {			        	
			        	return i;
			        }
			                		
			        
			        
			}
			
		}
		return null;
		
	}

	/*public FieldItem isCrossed()
	{
		
		for(FieldItem i: Main.game.getItems())
		{				
			if(i != this)
			{
				if (this.getBounds().intersects(i.getBounds()))
				{
					
					return i;
			    }
			}
		}
		
		return null;
	    
	}*/
	
	//explosive this object and kill 
	protected void bang()
	{
		int x = getX();
		int y = getY();
		
		//bang appearing correction
		if(direction != 0)
		{
			switch(direction)
			{
				case KeyEvent.VK_LEFT:
					y -= 20;
					x -= 10;
					
					break;
								
				case KeyEvent.VK_RIGHT:
					y -=20;
					
					break;
					
				case KeyEvent.VK_UP:
					x -= 20;
					y -= 10;
		    			
					break;
					
				case KeyEvent.VK_DOWN:
					x -= 20;
									
					break;
			}
		}
				
		Main.game.getItems().add(new Bang(x, y)); //add bang to items list
		kill();
	}
	
	//kill this item
	protected void kill()
	{	
		Main.game.getItems().remove(this); // delete this item from items list
		live = false;
		
		//because a wall not have task object
		if (!(this instanceof Wall))
		{
			task.cancel(true);
			task = null;
		}
		
		sprite = null;
			
	}
		
	/*
	 * getters and setters
	 */
	public int getArmor() 
	{
		return armor;
	}

	public void setArmor(int armor)
	{
		this.armor = armor;
	}
	
	public Image getSprite() 
	{
		return sprite;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live) 
	{
		this.live = live;
	}
	
	@Override
	public String toString()
	{
		String out = "";
		
		if( this instanceof Wall)
			out += "Wall";
		else if (this instanceof Tank)
			out += "Tank";
		else if (this instanceof Bullet)
			out += "Bullet";
		else if (this instanceof Bang)
			out += "Bang";
					
		return out + " (x: " + x + " y: " + y +")";
		
	}
		
}
