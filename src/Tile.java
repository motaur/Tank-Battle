import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tile extends JPanel
{
	private Image sprite;
	private int x;
	private int y;
	
	
	Tile(int sprite, int x, int y)
	{	
		this.sprite = ImageManager.getInstance().getTiles().get(sprite);
		
				
		this.x = x;
		this.y = y;
	}
	
	@Override
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      
      g.drawImage(sprite, x, y, null);
   }
	
	public Image getSprite() 
	{
		return sprite;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
