package maxsum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MaxSum {
	final static String directory = "/Dropbox/7_lns/results/maxsum";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readOutput("random");
	}

	// instanceType=random/grid/
	// repairType=TDBR/GDBR
	public static void readOutput(String instanceType) {
		String homeDirectory = System.getProperty("user.home").toString();
		String folder = homeDirectory + directory + "/" + instanceType;
		double averageQuality = 0;
		for (int i=0; i<50; i++) {
			if (i == 34)	continue;
			String inFile = folder + "/" + i + ".txt";
			System.out.println(inFile);
			double solutionQuality = 0;
			try (BufferedReader br = new BufferedReader(new FileReader(inFile)))
			{   String line = br.readLine();
				String[] words;
				while (line != null) {
//                if (line.contains("Algorithm")) {
//                    words = line.split(" ");
//                    time = Integer.parseInt(words[3].replace(",", ""));
//                    //time = ukFormat.parse(words[3]).intValue();
//                    timeMax = Math.max(time, timeMax);
//                    // Messages
//                    words = br.readLine().split("\t");
//                    outputFormat.setMaxAgtTotMsgs(ukFormat.parse(words[2]).intValue());
//                    outputFormat.setNetLoad(ukFormat.parse(words[2]).intValue());
//                }
	                if (line.contains("Utility")) {
	                    words = line.split(" ");
	                    solutionQuality = Double.parseDouble(words[4]);
	                }
	                line = br.readLine();
				}
				System.out.println("Instance " + i + ": " + solutionQuality);
				averageQuality += solutionQuality;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			System.out.println(averageQuality/49);
		}
	}
}
