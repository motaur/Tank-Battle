import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Help extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton back;
	BufferedImage img;
		
	public Help()
	{
		back = new JButton("Back");
		this.setLayout(null);
		back.setBounds(330, 555, 80, 30);
			   
        img = ImageManager.getInstance().getHelp();
         	
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				JButton pressed = (JButton) event.getSource();
						
				if(pressed.getText().equals("Back"))
				{				
					Main.startTitle();
				}				
						
			}
	  
		 });
		
		
		add(back);
		
	}
	
	public void paintComponent(Graphics g) 
	{	
	    super.paintComponent(g);
	        
	    // Draw the background image.
	    g.drawImage(img, 0, 0, null);
	    
	}
}
