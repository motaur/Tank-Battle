import java.awt.event.KeyEvent;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BotController 
{	
	//size of map is 800 / 50 = 16 x 12 = 600 / 50
	/*private final int MW = Main.WIDTH / 50;
	private final int WH = Main.HEIGHT / 50;*/
	
	public ScheduledFuture<?> task; 
	
	private Tank b; 	//controlled tank
	
	private Node p = new Node(0, 0);		//player is final destination
	
	private int dirPrir = 1; //direction priority
		
	//directions of moving that instance of Tank understand
	private int right = KeyEvent.VK_RIGHT;
	private int left = KeyEvent.VK_LEFT;
	private int up = KeyEvent.VK_UP;
	private int down = KeyEvent.VK_DOWN;
		
	//node of matrix, intermediate point of destination, from those created path
	private class Node
	{
		private int x;
		private int y;
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public int getX() 
		{
			return x;
		}

		public int getY() 
		{
			return y;
		}
		
	}
		
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
	
	BotController(Tank bot)
	{		
		b = bot; 
		task = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> update(), 1, 1, TimeUnit.MILLISECONDS);
			
	}
	
	/*private void printMatrix()
	{		
		for(int i = 0; i < WH; i++)
		{		
			for(int j = 0; j < MW; j++)
			{
				System.out.print(mapMatrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}*/
	
	// to do path find algorithm
	private void update()
	{	
		if(Main.game.getCurrentLevel().getP1().isLive() == false || b.isLive() == false)
		{
			b.task.cancel(true);
			b = null;
			task.cancel(true);
			task = null;
		}
						
		//searching for items on map and set it on matrix
		mapping();
							
		//printMatrix();
		
		//move to node from the path
		
		
			
		//change direction if crossed another item
		if(b.isCrossed() instanceof Wall || b.isCrossed() instanceof Tank)
		{				
			//priority toggle
			if(dirPrir == 1)
				dirPrir = 0;
			else
				dirPrir = 1;
		}
		
		moveAheadToNode(p, dirPrir);
		
		if(b.isLive())
			shootToNode(p);
		
		//if bot is dead stop his thread
		if(b.isLive() == false)
			task.cancel(true);
			
		//all nodes = zeroes
		//zerophyMatrix();
						
	}
	
	/*private void zerophyMatrix()
	{
		for(int i = 0; i < WH; i++)
		{
			for(int j = 0; j < MW; j++)
				mapMatrix[i][j] = 0;
		}	
	}*/
	
	private void mapping()
	{
		//mapMatrix[b.y / 50 ][b.x / 50] = 3; 				// self mapping
	
		for(FieldItem i: Main.game.getItems())
		{					
			if(i instanceof Tank )									// if found a Tank
			{	
				if( ((Tank) i).isBot() == false) 					//and this tank is the player
				{						
					p.x = i.getX();
					p.y = i.getY();
					
				//	mapMatrix[p.getY() / 50 ][p.getX() / 50] = 5;				//is 5 on map
				}
				
			//	if( ((Tank) i).isBot() && ((Tank) i) != b) 			//or this tank is another bot
			//		mapMatrix[i.getY() / 50 ][i.getX() / 50] = 2;				//is 2 on map
			}
						
		//	if(i instanceof Wall)									// if found a wall
		//		mapMatrix[i.getY() / 50 ][i.getX() / 50] = 1;			//is 1 on map
			
			//mapMatrix[path[pathIterrator].getY() / 50][path[pathIterrator].getX() / 50] = 6;	//current node from path - is number 6
				
		}
		
	}
		
	//straight moving to a node with X or Y priority (0 or 1 values of integer)
	private void moveAheadToNode(Node n, int priority)
	{	
			//move forward player, with Y priority (first, move on Y, after on X)
			if(priority == 0)
			{	
				if(n.getY() > b.getY())
				{
					b.setDirection(down);
					b.setMove(true);
				}
				else if(n.getY() < b.getY())
				{					
					b.setDirection(up);
					b.setMove(true);
				}
				else if (n.getX() > b.getX())
				{
					b.setDirection(right);
					b.setMove(true);
				}
				else if(n.getX() < b.getX())
				{
					b.setDirection(left);
					b.setMove(true);
				}
				
					
			}
			//x priority (first, move on X, after on Y)
			else 
			{	
				if (n.getX() > b.getX())
				{
					b.setDirection(right);
					b.setMove(true);
				}
				else if(n.getX() < b.getX())
				{
					b.setDirection(left);
					b.setMove(true);
				}
				else if(n.getY() > b.getY())
				{
					b.setDirection(down);
					b.setMove(true);
				}
				else if(n.getY() < b.getY())
				{					
					b.setDirection(up);
					b.setMove(true);
				}
				
			}
		
	}
	
	private void shootToNode(Node n)
	{
			if(n.getX() == b.getX() && n.getY() > b.getY())
			{
				b.setDirection(down);
				b.setMove(true);
				b.shoot();
			}
			
			if(n.getX() == b.getX() && n.getY() < b.getY())
			{
				b.setDirection(up);
				b.setMove(true);
				b.shoot();
			}
			
			if(n.getY() == b.getY() && n.getX() > b.getX())
			{
				b.setDirection(right);
				b.setMove(true);
				b.shoot();
			}
			
			if(n.getY() == b.getY() && n.getX() < b.getX())
			{
				b.setDirection(left);
				b.setMove(true);
				b.shoot();
			}
			
			
	}
		

}
