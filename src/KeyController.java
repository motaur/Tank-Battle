import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter 
{	
	private Tank p1;
	//private Tank p2;
	
	
	public void addTank(Tank p, int num) 
	{
		if(num == 1)
			p1 = p;
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{	
		//player1 control presser
		if(p1 != null)
		{
			switch(e.getKeyCode())
			{						
				case KeyEvent.VK_LEFT: 
				case KeyEvent.VK_RIGHT:  
				case KeyEvent.VK_UP: 
				case KeyEvent.VK_DOWN:
														
					p1.setMove(true);
					p1.setDirection(e.getKeyCode());					
						
					break;						
					
				case KeyEvent.VK_SPACE:
					p1.shoot();
					
					break; 
					
				case KeyEvent.VK_ENTER:
					if(!p1.isLive() || Main.game.getBotsCounter() <= 0)
						Main.game.changeLevel();
					
					break;
					
				case KeyEvent.VK_ESCAPE:
					
					if(Main.game.isPause() == false)
						Main.game.setPause(true);
					
					else if(Main.game.isPause())
						Main.game.setPause(false);
					
					break;		
			}
		}
							
	} 
	
	@Override
	public void keyReleased(KeyEvent e) 
	{	
		//player1 control releaser
		if(p1 != null)
		{
			switch(e.getKeyCode())
			{
				//player1
				case KeyEvent.VK_LEFT: 
				case KeyEvent.VK_RIGHT: 
				case KeyEvent.VK_UP: 
				case KeyEvent.VK_DOWN:
					p1.setMove(false);					
					
			}
		}
	}

}