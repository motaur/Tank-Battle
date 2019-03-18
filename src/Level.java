import java.awt.event.KeyEvent;
import java.util.Random;

public class Level 
{	
	private int number;
	private int pArmor;
	private int pAmmo;
	private int bSpeed;
	private int bArmor;
	private int wallArmor;
	
	private Tank p1;//, p2;
	
	private final int MW = Main.WIDTH / 50;
	private final int WH = Main.HEIGHT / 50;
	
	private int[][] mapMatrix = 
		{ 	
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
	
	public Level(int number, int [][] mapMatrix) 
	{
		this.number = number;
		this.mapMatrix = mapMatrix;
			
		create();
	}
	
	

	public void  create() 
	{
		initParam();
		
		//objects mapper
		for(int i = 0; i < WH; i++)
		{
			//create items with current parameters
			for(int j = 0; j < MW; j++)
			{		  		
				Main.game.getTiles().add(new Tile(new Random().nextInt(4) +  1 , j * 50, i * 50 )); //random tiles
				
				if(mapMatrix[i][j] == 5)	// player creating 
				{	
					p1 = new Tank( j * 50, i * 50, KeyEvent.VK_RIGHT,  /*speed */ 10, /*armor*/ pArmor, /*ammo*/ pAmmo, /*player*/ 1);
					Main.game.getItems().add(p1);
				}
				
				if(mapMatrix[i][j] == 1)	// wall creating
					Main.game.getItems().add(new Wall( j * 50, i * 50, /*armor*/ wallArmor));
								
				if(mapMatrix[i][j] == 2)	// bot creating
				{
					Main.game.getItems().add(new Tank( j * 50, i * 50, KeyEvent.VK_DOWN,  /*speed */ bSpeed, /*armor*/ bArmor, /*bot*/ true));
					
				}
			}	
		}
		
	}
	
	private void initParam()
	{							
				if (number == 1) 
				{
					number = 1;
					pArmor = 1;
					pAmmo = 3;
					bSpeed = 20;
					bArmor = 1;
					wallArmor = 3;
									
				}		
				else if(number == 2)
				{
					pArmor = 1;
					pAmmo = 3;
					bSpeed = 20;
					bArmor = 1;
					wallArmor = 3;
					
				}
				else if(number == 3)
				{					
					pArmor = 1;
					pAmmo = 4;
					bSpeed = 20;
					bArmor = 1;
					wallArmor = 3;
				}
				else if(number == 4)
				{						
					pArmor = 1;
					pAmmo = 5;
					bSpeed = 20;
					bArmor = 1;
					wallArmor = 3;
						
						
				}
				else if(number == 5)
				{						
					pArmor = 2;
					pAmmo = 6;
					bSpeed = 20;
					bArmor = 1;
					wallArmor = 3;
						
				}
						
				else if(number == 6)
				{		
					pArmor = 5;
					pAmmo = 12;
					bSpeed = 17;
					bArmor = 1;
					wallArmor = 3;
						
				}
				else if (number == 7)		
				{
						
						pArmor = 5;
						pAmmo = 10;
						bSpeed = 17;
						bArmor = 1;
						wallArmor = 3;
						
				}
				else if (number == 8)		
				{
						pArmor = 1;
						pAmmo = 10;
						bSpeed = 18;
						bArmor = 1;
						wallArmor = 3;
						
											
				}
						
				else if(number == 9)
				{
					
						pArmor = 1;
						pAmmo = 15;
						bSpeed = 17;
						bArmor = 2;
						wallArmor = 9999;
						
						
						
				}
				else if(number == 10)
				{		
						pArmor = 3;
						pAmmo = 30;
						bSpeed = 13;
						bArmor = 1;
						wallArmor = 2;
							
				}
	}			

	public Tank getP1() {
		// TODO Auto-generated method stub
		return p1;
	}


	
	
}
