/**
 * 
 * @author Rachel Holman, Ryan Yu, Ben Collinson
 * @description: CSE 274 Final Project to create a program similar to a GPS system
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


// This can be our basis for the GUI

public class GUI extends JFrame implements ActionListener{

	private static final int WIDTH = 1220;
	private static final int HEIGHT = 740;
	private ImageIcon imageIcon;

	Graph g = new Graph("MapInformation.txt");
	JLabel label1, label2;
	JButton compute;
	JTextArea directions, placeholder;
	Vertex startV, endV;
	String symb1, symb2;

	/**
	 * Constructs the GUI
	 */
	public GUI() {
		setSize(WIDTH, HEIGHT);
		setTitle("Shortest Path GPS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new FlowLayout());

		//canvas = new DrawingCanvas();
		//add(canvas);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		//-------------------------------------------------------------- Right side of screen

		JPanel picPanel = new JPanel();
		picPanel.setLayout(new FlowLayout());

		imageIcon = new ImageIcon(getClass().getResource("FinalProjectGraph_Final.png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(620, 690,  java.awt.Image.SCALE_SMOOTH); // scale image the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back

		label1 = new JLabel(imageIcon);
		picPanel.add(label1);			// adds graph picture to the gui

		//-------------------------------------------------------------- Left side of screen

		JPanel info = new JPanel();
		info.setLayout(new BorderLayout());

		JPanel select = select();
		JPanel options = options();
		JPanel output = output();

		info.add(select, BorderLayout.NORTH);
		info.add(options, BorderLayout.CENTER);
		info.add(output, BorderLayout.SOUTH);

		container.add(info, BorderLayout.WEST);
		container.add(picPanel, BorderLayout.EAST);
	}

	
	/**
	 * Selection panel where user selects a starting vertex and a destination
	 * @return JPanel
	 */
	public JPanel select() {
		JPanel select = new JPanel();
		select.setLayout(new GridLayout(1,2));

		JPanel selectStart = new JPanel();
		JPanel selectEnd = new JPanel();

		selectStart.setBorder(new TitledBorder("Starting Location"));
		selectEnd.setBorder(new TitledBorder("Destination"));

		String[] toAdd = new String[20];
		try {
			// Creates a scanner
			Scanner file = new Scanner(new File("MapInformation.txt"));
			String line = file.nextLine();
			while (!line.equals("<Nodes>")) { line = file.nextLine(); }

			// Skips two lines of header text in the file
			file.nextLine();
			line = file.nextLine();
			int i = 0;
			
			// Creates Vertex objects (each of which contains a symbol and an 
			// address property)
			while (!line.equals("</Nodes>")) {
					toAdd[i] = line;
					line = file.nextLine();
					i++;
			}
			} catch (FileNotFoundException e) {
				
			}

			JList<String> startL = new JList<String>(toAdd);
			JList<String> endL = new JList<String>(toAdd);

			ListSelectionListener startSelec = new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					String selection = startL.getSelectedValue();
					String[] bits = selection.split("\t");
					symb1 = "" + bits[0];
					startV = new Vertex(bits[0], bits[1]);
				}

			};
			ListSelectionListener endSelec = new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					String selection = endL.getSelectedValue();
					String[] bits = selection.split("\t");
					symb2 = "" + bits[0];
					endV = new Vertex(bits[0], bits[1]);
				}

			};

			startL.addListSelectionListener(startSelec);
			endL.addListSelectionListener(endSelec);
			selectStart.add(startL);
			selectEnd.add(endL);
			select.add(selectStart, BorderLayout.WEST);
			select.add(selectEnd, BorderLayout.EAST);
			
			return select;
	}

	
	/**
	 * Options panel where user selects time/distance cost,
	 * selects how to display the directions, and presses compute button
	 * @return JPanel
	 */
	public JPanel options() {
		JPanel options = new JPanel();
		options.setBorder(new TitledBorder("Options and Controls"));
		options.setLayout(new GridLayout(2,4));


		//Create the radio buttons.
		JRadioButton time = new JRadioButton("Quickest");
		time.setMnemonic(KeyEvent.VK_Q);
		time.setActionCommand("time");
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setSelected(true);

		JRadioButton distance = new JRadioButton("Shortest");
		distance.setMnemonic(KeyEvent.VK_D);
		distance.setActionCommand("dist");

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(time);
		group.add(distance);

		//Create the radio buttons.
		JRadioButton symbol = new JRadioButton("Symbol");
		symbol.setMnemonic(KeyEvent.VK_S);
		symbol.setActionCommand("symbol");
		symbol.setHorizontalAlignment(SwingConstants.CENTER);
		symbol.setSelected(true);

		JRadioButton address = new JRadioButton("Address");
		address.setMnemonic(KeyEvent.VK_A);
		address.setActionCommand("address");

		//Group the radio buttons.
		ButtonGroup group2 = new ButtonGroup();
		group2.add(symbol);
		group2.add(address);

		//Register a listener for the radio buttons.
		time.addActionListener(this);
		distance.addActionListener(this);
		address.addActionListener(this);
		symbol.addActionListener(this);

		//Create button to display directions in JTestArea
		compute = new JButton("Find path");
		compute.setActionCommand("compute");
		compute.addActionListener(this);

		//Create labels to make more user-friendly
		JLabel lab1 = new JLabel("Path type:", SwingConstants.RIGHT);
		JLabel lab2 = new JLabel("Directions output:", SwingConstants.RIGHT);

		options.add(lab1,0);
		options.add(time,1);
		options.add(distance,2);
		options.add(compute,3);
		options.add(lab2,4);
		options.add(symbol,5);
		options.add(address,6);

		return options;
	}

	
	/**
	 * Output panel which displays shortest path
	 * @return JPanel
	 */
	public JPanel output() {
		JPanel output = new JPanel();
		output.setBorder(new TitledBorder("Directions"));

		directions = new JTextArea(10, 45);
		directions.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(directions);

		output.add(scrollPane);

		return output;
	}

	/**
	 * Action listener for all buttons. This method changes the useDistCost and
	 * returnAddress variable based on user selection. When the "Find path" button is
	 * pressed, this method prints the directions output in the JTextArea
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("time"))
			Graph.useDistCost = false;
		else if (e.getActionCommand().equals("dist")) 
			Graph.useDistCost = true;
		else if (e.getActionCommand().equals("symbol"))
			Graph.returnAddress = false;
		else if (e.getActionCommand().equals("address"))
			Graph.returnAddress = true;
		else if (e.getActionCommand().equals("compute")) {
			if (startV == null || endV == null)
				directions.setText("");
			else {
				Path p = Dijkstra.shortestPath(g, g.getVertex(symb1), g.getVertex(symb2));
				if (p == null)
					directions.setText("Path does not exist");
				else {
					String s = p.toString();
					directions.setText(s);
				}
			}
		}

	}


	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame graph = new GUI();
		graph.setVisible(true);
	}

}
