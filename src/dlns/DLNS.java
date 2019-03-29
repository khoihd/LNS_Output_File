package dlns;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DLNS {

	final static String directory = "/Dropbox/7_lns/results/lns";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readOutput("random", "TDBR");
	}
	
	// instanceType=random/grid/
	// repairType=TDBR/GDBR
	public static void readOutput(String instanceType, String repairType) {
		String homeDirectory = System.getProperty("user.home").toString();
		String folder = homeDirectory + directory + "/" + instanceType + "/" + repairType;
		double p = 0;
		int averLB = 0;
		int averUB = 0;
		for (int i=0; i<50; i++) {
			if (i == 34) continue;
			String inFile = folder + "/" + i + "_processed.txt";
			System.out.println(inFile);
			try (BufferedReader br = new BufferedReader(new FileReader(inFile)))
	        {
	            String line = br.readLine();
	            System.out.println(line);
	            
	            Scanner sc = new Scanner(line);
	            sc.nextInt();
				int LB = sc.nextInt();
				int UB = sc.nextInt();
				
				

				p += UB * 1.0 / LB;
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
