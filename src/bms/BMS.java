package bms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BMS {

	final static String directory = "/Dropbox/7_lns/results/bms";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readOutput("random");
	}
	
	// instanceType=random/grid/
	public static void readOutput(String instanceType) {
		String homeDirectory = System.getProperty("user.home").toString();
		String folder = homeDirectory + directory + "/" + instanceType;
		double p = 0;
		int averLB = 0;
		int averUB = 0;
		for (int i=0; i<50; i++) {
			if (i == 34) continue;
			String inFile = folder + "/" + i + ".txt";
			System.out.println(inFile);
			try (BufferedReader br = new BufferedReader(new FileReader(inFile)))
	        {
	            String line = br.readLine();
	            System.out.println(line);

	            Scanner sc = new Scanner(line);
				double LB = sc.nextDouble();
				double UB = sc.nextDouble();
				p += UB / LB;
				averLB += LB;
				averUB += UB;
				System.out.println(LB + " " + UB);
				sc.close();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		System.out.println(p/49);
		System.out.println(averLB*1.0/49);
		System.out.println(averUB*1.0/49);
	}

}
