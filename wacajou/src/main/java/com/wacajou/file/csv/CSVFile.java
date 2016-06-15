package com.wacajou.file.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CSVFile {

	private File file;
	
	public CSVFile(File file){
		this.file = file;
	}
	
	public HashMap<Integer, String[]> run() {
		HashMap<Integer, String[]> map = new HashMap<Integer, String[]>();
	//	String csvFile = "L://test2.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {

			HashMap<Integer, String[]> maps = new HashMap<Integer, String[]>();

			br = new BufferedReader(new FileReader(file));
			int i = 0;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] values = line.split(cvsSplitBy);
				
				maps.put(i, values);
				i++;
			}

			/*// loop map
			for (Entry<Integer, String[]> entry : maps.entrySet()) {
				
				System.out.println("code= " + entry.getKey() + " , name= "
						+ entry.getValue()[4]);

			}*/
			map = maps;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		return map;
	}

}