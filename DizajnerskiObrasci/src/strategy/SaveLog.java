package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.ListModel;

import mvc.DrawingFrame;

public class SaveLog implements Saving {

	
	 private DrawingFrame frame;

	 public SaveLog(DrawingFrame frame) {
	        this.frame = frame;
	    }
	
	@Override
	public void save(String fileAddress) {
		
		
		File file = new File(fileAddress + ".txt");
		ListModel<String> listModel = frame.getLogList().getModel();
        
        FileWriter fileWriter;
  
	      
        try {
				file.createNewFile();
		} catch (IOException e1) {
				
			JOptionPane.showMessageDialog(null, "An error occured!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}

        
		try {
			fileWriter = new FileWriter(file);
			
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) ;
            
			for (int i = 0; i < listModel.getSize(); i++) {
                	
				bufferedWriter.write((String)listModel.getElementAt(i));
                bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			fileWriter.close();
			 JOptionPane.showMessageDialog(null, "File saved successfully!", "Success",
	                    JOptionPane.INFORMATION_MESSAGE);
			
		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "An error occured!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
        

	}

}
