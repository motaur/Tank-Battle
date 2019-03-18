import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BotController 
{	
	//size of map is 800 / 50 = 16 x 12 = 600 / 50
	private final int MW = Main.WIDTH / 50;
	private final int WH = Main.HEIGHT / 50;
	
	private PriorityQueue<Node> openList = new PriorityQueue<Node>(new NodeComparator());
	
	private ScheduledThreadPoolExecutor timer;
	boolean dirPrir = false;
	
	//coordinates of player on mini map
	protected int pmapx, pmapy;
	
	//coordinates on bot on mini map
	protected int bmapx, bmapy;
	
	//next node in route
	protected Node next, prev;
	
	public ScheduledFuture<?> task; 
	
	//controlled tank
	private Tank b; 	
	
	private Node p = new Node(0, 0);		//player is final destination
		
	//directions of moving that instance of Tank understand
	private int right = KeyEvent.VK_RIGHT;
	private int left = KeyEvent.VK_LEFT;
	private int up = KeyEvent.VK_UP;
	private int down = KeyEvent.VK_DOWN;
		
	//node of matrix, intermediate point of destination, from those created path
	private class Node
	{
		//
		 // 0 empty
		//  1 wall
		//  2 another tank
		//  3 this bot
		// 5 player
		
		public int type;
		
		public int h;
		
		public int name;
		public String sname;
		
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
		
	private int[][] mapMatrix = { 	
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
		}; 
	
	BotController(Tank bot)
	{		
		b = bot; 
		task = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> update(), 1, 1, TimeUnit.MILLISECONDS);
		timer = new ScheduledThreadPoolExecutor(1);
			
	}
	
	private void printMatrix()
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
	}
	
	//add nodes to queue
	private void openNode(int mx, int my, String name)
	{
		Node n = new Node(mx, my); 
		n.sname = name;
		
		if(name.equals("right"))
			n.name = right;
		else if(name.equals("left"))
			n.name = left;
		else if(name.equals("down"))
			n.name = down;
		else if(name.equals("up"))
			n.name = up;
			
		try
		{
			n.type = mapMatrix[my][mx];
		}
		catch(IndexOutOfBoundsException e) //out of bound
		{
			n.type = 1;
		}
		
		//Manhattan distance from node to player
		n.h = Math.abs(mx - pmapx) + Math.abs(my - pmapy);
		
		if(n.type == 0)
		{
			//System.out.println(n.sname + "---> x: " + mx + ", y: " + my + ", type: " + n.type + ", h: " +  n.h);	
			openList.add(n);
		}
			
	}
		
	//A* chose next step by heuristic, to move ahead to player
	private void aStar() 
	{
		
		//open 4 availability to move
		openNode(bmapx + 1, bmapy, "right");
		openNode(bmapx - 1, bmapy, "left");
		openNode(bmapx, bmapy + 1, "down");
		openNode(bmapx, bmapy - 1, "up");
		
		//pop node by lowest h, clear q, go to node
		
		next = openList.remove();
			
		//System.out.println("choosed h: " + next.sname +" ---> x: " + next.x + ", y: " + next.y + ", type: " + next.type + ", h: " +  next.h);
				
		b.setDirection(next.name);
		b.setMove(true);
				
		openList.clear();
				
	}
	
	class NodeComparator implements Comparator<Node> 
	{
		@Override
		public int compare(Node n1, Node n2)
		{		
			
			if (n1.h < n2.h)
                return -1;
			
            else if (n1.h > n2.h)
                return 1;
                       	
            		
			if(new Random().nextInt(2) == 0)
				return -1;
			else 
				return 1;
			
		}
		
	}
	
	private void update()
	{	
		//checking if player or bot are not in live, finish this thread
		if(Main.game.getCurrentLevel().getP1().isLive() == false || b.isLive() == false)
		{
			b.task.cancel(true);
			b = null;
			task.cancel(true);
			task = null;
		}
						
		//searching for items on map and set it on matrix
		mapping();
							
		printMatrix();
				
		aStar();

		//move to node from the path
		//moveAheadToNode(p, dirPrir);
		
		
				
		shootToNode(p);
			
		zerophyMatrix();
						
	}
	
	private void zerophyMatrix()
	{
		for(int i = 0; i < WH; i++)
		{
			for(int j = 0; j < MW; j++)
				mapMatrix[i][j] = 0;
		}	
	}
	
	private void mapping()
	{		
		
		if(b.x % 50 == 0)
			bmapx = (b.x / 50);
		
		
		
		
		/*if(b.y % 50 == 0)
			bmapy = (b.y / 50);*/
		
		bmapy = (b.y / 50);
		
		
		
		
		
		//System.out.println("bot: xMap: " + bmapx + ", yMap: " + bmapy + ", x: " + b.x + ", y: " + b.y);
		//System.out.println("player: xMap: " + pmapx + ", yMap: " + pmapy);
				
		mapMatrix[bmapy][bmapx] = 3; 				// self mapping
	
		for(FieldItem i: Main.game.getItems())
		{					
			if(i instanceof Tank )									// if found a Tank
			{	
				if( ((Tank) i).isBot() == false) 					//and this tank is the player
				{	
					//save map coordinates of player
					p.x = i.getX();
					p.y = i.getY();
					
					pmapx = (p.x / 50);
					pmapy = (p.y / 50) ;
										
					mapMatrix[pmapy][pmapx] = 5;				//is 5 on map
					
				}
				
				if( ((Tank) i).isBot() && ((Tank) i) != b) 			//or this tank is another bot
					mapMatrix[i.getY() / 50 ][i.getX() / 50] = 2;				//is 2 on map
			}
						
			if(i instanceof Wall)									// if found a wall
				mapMatrix[i.getY() / 50 ][i.getX() / 50] = 1;			//is 1 on map
				
		}
		
	}
	
	private void shootToNode(Node n)
	{
		//if MD lower than 5
		if(Math.abs(bmapx - pmapx) + Math.abs(bmapy - pmapy) < 5)
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
		
	//straight moving to a node with X or Y priority (0 or 1 values of integer)
	private void moveAheadToNode(Node n, boolean dirPrir2)
	{	
			//move forward player, with Y priority (first, move on Y, after on X)
			if(!dirPrir2)
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
			else if(dirPrir2) 
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

}
