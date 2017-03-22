package Input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import Gui.MainFrame;

public class SortButtonListener implements ActionListener{
	private static final SimpleDateFormat year = new SimpleDateFormat("YYYY");
	private MainFrame mainFrame;
	private String rootDirectory;
	private boolean handleFileExistsError;
	
	public SortButtonListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(mainFrame.getPathLabel().getText().equals("")){
			JOptionPane.showMessageDialog(mainFrame.getFrame(), "You did not chose a directory", "Directory empty", JOptionPane.ERROR_MESSAGE);
		}else{
			rootDirectory = mainFrame.getActualPath();
			handleFileExistsError = true;
			sort(rootDirectory);
			JOptionPane.showMessageDialog(mainFrame.getFrame(), "Succeeded");
		}
	}
	
	private int getCreationYear(File file){
			try {
				Metadata metadata;
				metadata = ImageMetadataReader.readMetadata(file);
				ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
				if(directory == null)
					return -1;
				Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
				if(date == null)
					return -1;
				System.out.println(year.format(date.getTime()));
				return Integer.parseInt(year.format(date.getTime()));
			} catch (ImageProcessingException e) {
				return -1;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return -1;
	}
	
	private void sort(String actualDirectory){
		File sortDirectory = new File(actualDirectory);
		File[] filesInSortDirectory = sortDirectory.listFiles();
		for(File file : filesInSortDirectory){
			if(file.isFile()){
				putFileToDirectory(file);
			}
			else if(file.isDirectory()){
				sort(file.getAbsolutePath());
			}
		}
	}
	
	
	private void putFileToDirectory(File file){
		try {
			if(getCreationYear(file) != -1){
				copyFileToNewLocation(file);
			}else{
				String leftFilesPath =  rootDirectory + "/No date/";
				File leftFiles = new File(leftFilesPath);
				if(!leftFiles.exists())
					leftFiles.mkdir();
				File newFileLocation = new File(leftFilesPath + file.getName());
				Path source = Paths.get(file.toURI());
				Path target = Paths.get(newFileLocation.toURI());
				try {
					System.out.println("--NO-DATE-- Copy " + file.toURI() + " into No Date directory");
					Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}catch (IOException e1) {
			e1.printStackTrace();
		}	
	}
	
	private void copyFileToNewLocation(File file) throws IOException{
		File sortOrdner;
		String sortordnerPath;
		sortordnerPath = rootDirectory + "/" + getCreationYear(file) + "/";
		sortOrdner = new File(sortordnerPath);
		File newFileLocation = new File(sortordnerPath + file.getName());
		if(!sortOrdner.exists())
			sortOrdner.mkdir();
		Path source = Paths.get(file.toURI());
		Path target = Paths.get(newFileLocation.toURI());
		if(!newFileLocation.exists()){
			System.out.println("Copy " + file.toURI() + " to " + newFileLocation.toURI());
			Files.copy(source, target);
		}
		else{
			if(handleFileExistsError){
				String[] options = new String[] {"Yes", "Yes for all errors of that kind", "No"};
				int yesNo = JOptionPane.showOptionDialog(mainFrame.getFrame(), "Replace it?", "Eroor existing", JOptionPane.ERROR_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				switch(yesNo){
				case 0: // yes
					System.out.println("Copy " + file.toURI() + " to " + newFileLocation.toURI());
					Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
					break;
				case 1: // for all
					handleFileExistsError = false;
					System.out.println("Copy " + file.toURI() + " to " + newFileLocation.toURI());
					Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
					break;
				case 2: // no
					break;
				}
			}
				else{
					System.out.println("Copy " + file.toURI() + " to " + newFileLocation.toURI());
					Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				}
		}
	}
	
}
