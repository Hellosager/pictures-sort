package Input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import Gui.MainFrame;

public class SearchButtonListener implements ActionListener{
	private JLabel pathLabel;
	private MainFrame mainFrame;
	
	public SearchButtonListener(JLabel pathLabel, MainFrame mainFrame) {
		this.pathLabel = pathLabel;
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(mainFrame.getActualPath());
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(mainFrame.getActualDefaultPath()));
//		mainFrame.setActualDefaultPath(fc.getCurrentDirectory().getAbsolutePath());
		int returnVar = fc.showOpenDialog(mainFrame.getFrame());
		if(returnVar == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			mainFrame.setActualPath(file.getAbsolutePath());
			pathLabel.setText(mainFrame.getActualPath());
			mainFrame.setActualDefaultPath(fc.getSelectedFile().getPath());
		}
		else{
			
		}
	}

}
