import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main 
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static JFrame frame;
	public static Game game;
	public static Title title;
	public static CardLayout layout;
	public static KeyController keyController; 
	public static Midi m;
	public static Help help;
	public static final String NAME = "Tank Battle";
	
	//game play variables
	public static int levelOnStart = 1; 	//1-10
	public static int lives = 10;
	public static boolean astar = false;
	
					
	public static void main(String[] args) 
	{	
		keyController = new KeyController();
		frame = new JFrame(NAME);
		
				
		SoundEffect.init();
		m = new Midi(1, 1);
		
		//layout manager
		layout = new CardLayout();
		frame.setLayout(layout);
		
		//creating JPAnels
		title = new Title();
		help = new Help();
		
				
		//set resolutions to JPanels
		title.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		help.setPreferredSize(new Dimension(WIDTH, HEIGHT));
				
		//add both panels to frame
		frame.add(title, "title");
		frame.add(help, "help");
						
		//key listener
		frame.addKeyListener(keyController);
		
		//frame parameters
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		frame.setIconImage(ImageManager.getInstance().getIcon());
		frame.setVisible(true);
		frame.pack();
	}
	
	static void startNewGame()
	{
		game = new Game();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(game, "game");
		game.newLevel(levelOnStart); 			//put number of level
		frame.requestFocus();
		layout.show(frame.getContentPane(), "game");
		
	}
	
	static void startTitle()
	{
		//frame.remove(game);
		frame.requestFocus();
		layout.show(frame.getContentPane(), "title");				
	}
	
	static void startReadme()
	{			
		frame.requestFocus();
		layout.show(frame.getContentPane(), "help");			
	}
	
}
