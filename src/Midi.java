import java.io.*;
import javax.sound.midi.*;
   
public class Midi 
{
   public Sequencer midiPlayer;
   File song1 = new File(ImageManager.getInstance().getPath() + "song1.mid");
     
   // testing main method
   public Midi(int temp, int songNumber) 
   {
	  /* // Do this on every move step, you could change to another song
	      if (!midiPlayer.isRunning()) 
		  {  // previous song finished
	         // reset midi player and start a new song
	         midiPlayer.stop();
	         midiPlayer.close();
	         //startMidi("song2.mid");
	      }*/
	   
      try 
	  {         
         Sequence song = MidiSystem.getSequence(song1);
         midiPlayer = MidiSystem.getSequencer();
         midiPlayer.open();
         midiPlayer.setSequence(song);
         midiPlayer.setLoopCount(99); // repeat 0 times (play once)
         midiPlayer.setTempoFactor(temp);   // >1 to speed up the tempo
         
      } 
	  catch (MidiUnavailableException e) 
	  {
         e.printStackTrace();
      } 
	  catch (InvalidMidiDataException e) 
	  {
         e.printStackTrace();
      } 
	  catch (IOException e) 
	  {
         e.printStackTrace();
      }
      
     
   }
   
}