package main;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;


public class Viewer {
    public static void main(String[] args) throws Exception {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			JFrame window = new JFrame();
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);
			window.setTitle("Pathfinding Algorithm");
			

			JPanel totalGUI = new JPanel();
			totalGUI.setLayout(new BorderLayout());
			GridPanel gridPanel = new GridPanel();
			JPanel JElements = new JPanel(new GridLayout(20,1));
			JElements.setBackground(Color.gray);
			JElements.setLocation(0,0);
			JElements.setSize(50,50);


			JToggleButton togObstacles = new JToggleButton("Add/Remove Obstacles");
			JToggleButton togSetStart = new JToggleButton("Set Starting Node");
			JToggleButton togSetEnd = new JToggleButton("Set Ending Node");
			JComboBox algoSelection = new JComboBox();
			JPanel spacer = new JPanel();

			spacer.setOpaque(false);
			JToggleButton togEnablePathFinder = new JToggleButton("Enable PathFinder");
			
			ButtonGroup group = new ButtonGroup();
			JElements.add(togObstacles);
			JElements.add(togSetStart);
			JElements.add(togSetEnd);
			JElements.add(algoSelection);
			JElements.add(spacer);
			JElements.add(togEnablePathFinder);

			group.add(togObstacles);
			group.add(togSetStart);
			group.add(togSetEnd);
			

			togObstacles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					Grid.setEndingBoolean = false;
					Grid.setStarterBoolean = false;
					Grid.setObstacleBoolean = true;
					Grid.setObstacleButtonBoolean = true;

				};
			});

			togSetEnd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					Grid.setEndingBoolean = true;
					Grid.setStarterBoolean = false;
					Grid.setObstacleBoolean = false;
					Grid.setObstacleButtonBoolean = false;

				};
			});

			togSetStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					Grid.setEndingBoolean = false;
					Grid.setStarterBoolean = true;
					Grid.setObstacleBoolean = false;
					Grid.setObstacleButtonBoolean = false;

				};
			});

			togEnablePathFinder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					Grid.togPathFinder=!(Grid.togPathFinder);
				};
			});
		

			

			totalGUI.add(JElements, BorderLayout.CENTER);
			totalGUI.add(gridPanel,BorderLayout.WEST);
			window.add(totalGUI);

			window.setSize(gridPanel.screenWidth+200, gridPanel.screenHeight);
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			
			gridPanel.startGameThread();

    }
}
