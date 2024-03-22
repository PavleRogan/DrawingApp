package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import mvc.DrawingModel;

public class SaveDrawing implements Saving {
	
	DrawingModel model;
	
	public SaveDrawing(DrawingModel model) {
		this.model=model;
	}

	@Override
	public void save(String fileAddress) {
		// TODO Auto-generated method stub
		
		File file = new File(fileAddress);
		
		try {
			
			file.createNewFile();
			System.out.println(fileAddress);
		} catch (IOException e1) {
			
			JOptionPane.showMessageDialog(null, "An error occured!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
        try {
        	
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(model.getShapeList());
			objectOutputStream.flush();
			
			objectOutputStream.close();
			fileOutputStream.close();
			
			JOptionPane.showMessageDialog(null, "File saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
			
		} catch (FileNotFoundException e) {
			
			JOptionPane.showMessageDialog(null, "File do not exist!", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error occured!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

}
