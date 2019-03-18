import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Bang extends FieldItem 
{
	private int timeCounter = 0;
			
	public Bang(int x, int y) 
	{		
		super(ImageManager.getInstance().getBangIcons(1), x, y, 0, 1);
						
		task = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> update(), 50, 50, TimeUnit.MILLISECONDS);
		
		SoundEffect.BANG.play(); //play bang
			
	}
	
	private void update()
	{
		if(timeCounter == 0)
		{
			timeCounter++;
		}
		else if(timeCounter == 1)
		{
			sprite = ImageManager.getInstance().getBangIcons(2);
			timeCounter++;
		}
		else if (timeCounter == 2)
		{
			sprite = ImageManager.getInstance().getBangIcons(3);
			timeCounter++;
		}
		else if(timeCounter == 3)
		{
			kill();					
		}
		
	}
	
	
}
