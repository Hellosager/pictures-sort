package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Input.SearchButtonListener;
import Input.SortButtonListener;

public class MainFrame {
	private static final String defaultPath = System.getProperty("user.home")+System.getProperty("file.separator") + "Pictures";
	private String actualDefaultPath = defaultPath;
	private JFrame frame;	
	private JLabel pathLabel;
	private String actualPath = "";
	
	public MainFrame() {
		frame = new JFrame("File sort");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,1,30,10));
		JPanel content = new JPanel(new GridLayout(2, 1, 10, 10));
		
		JLabel instructions = new JLabel("Bitte den Ordner wählen, in dem die Dateien sortiert werden sollen", JLabel.CENTER);
		content.add(instructions);
		
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		pathLabel = new JLabel();
		pathLabel.setHorizontalAlignment(JLabel.CENTER);
		pathLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pathLabel.setPreferredSize(new Dimension(400, 35));
		JButton searchPath = new JButton("Search directory");
		searchPath.addActionListener(new SearchButtonListener(pathLabel, this));
		JButton sort = new JButton("Sort");
		sort.addActionListener(new SortButtonListener(this));
		row2.add(pathLabel);
		row2.add(searchPath);
		row2.add(sort);
		
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		content.add(row2);
		frame.add(content);
		frame.setVisible(true);
		frame.pack();
		frame.setLocation(frame.getX()-frame.getWidth()/2, frame.getY()-frame.getHeight()/2);
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public String getActualPath(){
		return actualPath;
	}
	
	public void setActualPath(String path){
		this.actualPath = path;
	}
	
	public String getActualDefaultPath(){
		return actualDefaultPath;
	}
	
	public void setActualDefaultPath(String path){
		this.actualDefaultPath = path;
	}
	
	public JLabel getPathLabel(){
		return pathLabel;
	}
	
}
