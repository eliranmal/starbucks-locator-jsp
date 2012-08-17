package com.starbucks.locator.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CSVParser {

	public static TableModel parse(File f) throws FileNotFoundException {

		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<String> oneDdata = new ArrayList<String>();

		// Get the headers of the table.
		Scanner lineScan = new Scanner(f);
		Scanner s = new Scanner(lineScan.nextLine());
		s.useDelimiter(",");
		while (s.hasNext()) {
			headers.add(s.next());
		}

		// Go through each line of the table and add each cell to the ArrayList
		while (lineScan.hasNextLine()) {
			s = new Scanner(lineScan.nextLine());
			s.useDelimiter(", *");
			while (s.hasNext()) {
				oneDdata.add(s.next());
			}
		}
		s.close();

		int rowSize = oneDdata.size() / headers.size();
		int colSize = headers.size();
		String[][] data = new String[rowSize][colSize];

		// Move the data into a vanilla array so it can be put in a table.
		for (int x = 0; x < rowSize; x++) {
			for (int y = 0; y < colSize; y++) {
				data[x][y] = oneDdata.remove(0);
			}
		}

		// Create a table and return it.
		return new DefaultTableModel(data, headers.toArray());
	}

	/**
	 * Test parser
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
/*	public static void main(String[] args) throws FileNotFoundException {
		// Call the parse method and put the results in a table.
		TableModel t = CSVParser.parse(new File("resources/initial-data/USA-Starbucks.csv"));
		
		// Print all the columns of the table, followed by a new line.
		for (int x = 0; x < t.getColumnCount(); x++) {
			System.out.print(t.getColumnName(x) + " ");
		}
		System.out.println();

		// Print all the data from the table.
		for (int x = 0; x < t.getRowCount(); x++) {
			for (int y = 0; y < t.getColumnCount(); y++) {
				System.out.print(t.getValueAt(x, y) + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println(t.getColumnCount());
		System.out.println(t.getRowCount());

	}
*/
}
