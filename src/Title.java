
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Title extends JPanel
{
	JButton newGame = new JButton("New Game");
	JButton readme = new JButton("Help");
	JButton quit = new JButton("Quit");
				
	BufferedImage img;	
		
	 public void paintComponent(Graphics g) 
	 {
		    super.paintComponent(g);

		    // Draw the background image.
		    g.drawImage(img, 0, 0, null);
	 }
 
    public Title() 
    {         
       img = ImageManager.getInstance().getTitle();
         
       newGame.addActionListener(new ActionListener()
	   {

		@Override
		public void actionPerformed(ActionEvent event)
		{
			//JButton pressed = (JButton) event.getSource();
					
			Main.startNewGame();
					
		}
   
	   });

       readme.addActionListener(new ActionListener()
       {
    		@Override
    		public void actionPerformed(ActionEvent event)
    		{
    			//JButton pressed = (JButton) event.getSource();
    					
    			Main.startReadme();
    					
    		}
    	   
       });
       
       quit.addActionListener(new ActionListener()
       {
    		@Override
    		public void actionPerformed(ActionEvent event)
    		{    					
    			System.exit(0);   					
    		}
    	   
       });
       
       	add(newGame);
	   	add(readme);
	   	add(quit);
	 }
     
}
