import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ProcessOutput {
	static final int numberAgents = 20;
	static final String resultsDirectory = System.getProperty("user.dir") + "/results_" + numberAgents + "/";
//	static final String resultsDirectory = System.getProperty("user.home").toString() + 
//	                        "/Dropbox/7_lns/results_" + numberAgents + "/";
//	static final String resultsSATDirectory = System.getProperty("user.home").toString() + 
//	                        "/Dropbox/7_lns/results_sat/";

	static final String outputsDirectory = "/Dropbox/7_lns/outputs/";
	
	static final String instanceType = "random";
	static final String repairType = "TDBR";
	
	static final String destroyType = "RAND"; //RAND, MEETING

	public static void main(String[] args) {
//	    hardConstraints();
	    softContraints();
	}
	
	public static void hardConstraints() {
//		String LNS_SAT_folder = homeDirectory + resultsSATDirectory + "lns/" + instanceType + "/" + repairType + "_" + destroyType + "/";
        String LNS_folder = resultsDirectory + "lns/" + instanceType + "/" + repairType + "/";
        String Maxsum_folder = resultsDirectory + "maxsum/" + instanceType + "/";
        String DSA_folder = resultsDirectory + "dsa/" + instanceType + "/";

		int LNS_unsat = 0;
		int Maxsum_unsat = 0;
		int DSA_unsat = 0;
		
		double MStime = 0;
		double DSAtime = 0;
		double LNS_time = 0;
		
		for (int i = 0; i < 50; i++) {
	        String LNS_SAT_inputFile = LNS_folder + i + ".txt";
	        String Maxsum_inputFile = Maxsum_folder + i + ".txt";
	        String DSA_inputFile = DSA_folder + i + ".txt";
			
	        System.out.println("Instance " + i + " =================");			
			/* DLNS MEETING */
			try (BufferedReader br = new BufferedReader(new FileReader(LNS_SAT_inputFile))) {
				String lineTemp;
				String line = null;
				while ((lineTemp = br.readLine()) != null) {
					if (lineTemp.contains("ITER") || lineTemp.length() == 0)
						continue;
					line = lineTemp;
				}
				System.out.println(line);
				String lines[] = line.split("\t");
				if (lines[1].equals("NA") || lines[1].equals("0")) {
					LNS_unsat++;
				}
                LNS_time += Double.valueOf(lines[0].replace(",",""));

			} catch (IOException e) {
				e.printStackTrace();
			}
			
	        /* MAXSUM MEETING */
            try (BufferedReader br = new BufferedReader(new FileReader(Maxsum_inputFile))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.contains("Utility of solution found:"))  {
                        if (line.endsWith("infinity")) Maxsum_unsat++;
                    }
                    if (line.contains("simulated time")) {
                        line = line.split(" ")[3].replace(",","");
                        MStime += Double.valueOf(line);;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            /* DSA MEETING */
            try (BufferedReader br = new BufferedReader(new FileReader(DSA_inputFile))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.contains("Total optimal utility:"))  {
                        if (line.endsWith("infinity")) DSA_unsat++;
                    }
                    if (line.contains("simulated time")) {
                        line = line.split(" ")[3].replace(",","");
                        DSAtime += Double.valueOf(line);;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		
		System.out.println("LNS UNSAT instances: " + LNS_unsat + " with time " + LNS_time/50);
		System.out.println("Maxsum UNSAT instances: " + Maxsum_unsat + " with time " + MStime/50);
        System.out.println("DSA UNSAT instances: " + DSA_unsat + " with time " + DSAtime/50);
	}
	
	public static void softContraints() {
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.DOWN);
		
		// TODO Auto-generated method stub
		/* For each instance k:
		 * Get the best UB and best LB
		 * Read k.txt from DLNS, Maxsum, BMS and DSA
		 * Get the largest UB or LB => Calculate the normalized values
		 * Get time   
		 * Write to result.txt for each algorithm
		 * InstanceID  Time  LB  UB
		 */		
		String LNS_folder = resultsDirectory + "lns/" + instanceType + "/" + repairType + "/";
		String BMS_folder = resultsDirectory + "bms/" + instanceType + "/";
		String Maxsum_folder = resultsDirectory + "maxsum/" + instanceType + "/";
		String DSA_folder = resultsDirectory + "dsa/" + instanceType + "/";
		String KOPT2_folder = resultsDirectory + "kopt2/" + instanceType + "/";
		String KOPT3_folder = resultsDirectory + "kopt3/" + instanceType + "/";
        String SBB_folder = resultsDirectory + "sbb/" + instanceType + "/";

//		String outputs_folder = homeDirectory + outputsDirectory + instanceType + "/";
		
//		String LNS_output_file = outputs_folder + "lns.txt";
//		String BMS_output_file = outputs_folder + "bms.txt";
//		String Maxsum_output_file = outputs_folder + "maxsum.txt";
//		String DSA_output_file = outputs_folder + "dsa.txt";
//		String KOPT2_output_file = outputs_folder + "dsa.txt";
//		String KOPT3_output_file = outputs_folder + "dsa.txt";

		
//		writeToFile(LNS_output_file, "InstanceID\tTime\tLB\tUB\n", TRUNCATE_EXISTING);
//		writeToFile(BMS_output_file, "InstanceID\tTime\tLB\tUB\n", TRUNCATE_EXISTING);
//		writeToFile(Maxsum_output_file, "InstanceID\tTime\tLB\n", TRUNCATE_EXISTING);
//		writeToFile(DSA_output_file, "InstanceID\tTime\tLB\n", TRUNCATE_EXISTING);
//		writeToFile(KOPT2_output_file, "InstanceID\tTime\tLB\n", TRUNCATE_EXISTING);
//		writeToFile(KOPT3_output_file, "InstanceID\tTime\tLB\n", TRUNCATE_EXISTING);

		double maxLB, minUB;
		maxLB = -Double.MAX_VALUE;
		minUB = Double.MAX_VALUE; 

		double LNS_LB, LNS_UB, BMS_LB, BMS_UB, Maxsum_LB, DSA_LB, KOPT2_LB, KOPT2_UB, KOPT3_LB, KOPT3_UB, SBB_LB;
		
		LNS_LB = LNS_UB = BMS_LB = BMS_UB = Maxsum_LB = DSA_LB 
				= KOPT2_LB = KOPT2_UB = KOPT3_LB = KOPT3_UB = SBB_LB= -Double.MAX_VALUE;
		
		double LNS_P, BMS_P, KOPT2_P, KOPT3_P;
		LNS_P = BMS_P = KOPT2_P = KOPT3_P = 0;
		
		double LNS_time, BMS_time, Maxsum_time, DSA_time, KOPT2_time, KOPT3_time;
		LNS_time = BMS_time = Maxsum_time = DSA_time = KOPT2_time = KOPT3_time = 0;
		
		double LNS_normalized_UB, BMS_normalized_UB, KOPT2_normalized_UB, KOPT3_normalized_UB;
		LNS_normalized_UB = BMS_normalized_UB = KOPT2_normalized_UB = KOPT3_normalized_UB = 0;

		
		double LNS_normalized_LB, BMS_normalized_LB, Maxsum_normalized_LB,
				DSA_normalized_LB, KOPT2_normalized_LB, KOPT3_normalized_LB, SBB_normalized_LB;
		LNS_normalized_LB = BMS_normalized_LB = Maxsum_normalized_LB = 
				DSA_normalized_LB = KOPT2_normalized_LB = KOPT3_normalized_LB = SBB_normalized_LB = 0;
		
//		double LNS_Mean_UB, BMS_Mean_UB, KOPT2_Mean_UB, KOPT3_Mean_UB;
//		LNS_Mean_UB = BMS_Mean_UB = KOPT2_Mean_UB = KOPT3_Mean_UB = 0;
//		
//		double LNS_Mean_LB, BMS_Mean_LB, Maxsum_Mean_LB, DSA_Mean_LB, KOPT2_Mean_LB, KOPT3_Mean_LB;
//		LNS_Mean_LB = BMS_Mean_LB = Maxsum_Mean_LB = DSA_Mean_LB = KOPT2_Mean_LB = KOPT3_Mean_LB = 0;
		
		for (int i=1; i<50; i++) {
			String LNS_inputFile = LNS_folder + i + ".txt";
			String BMS_inputFile = BMS_folder + i + ".txt";
			String Maxsum_inputFile = Maxsum_folder + i + ".txt";
			String DSA_inputFile = DSA_folder + i + ".txt";
			String KOPT2_inputFile = KOPT2_folder + i + ".txt";
			String KOPT3_inputFile = KOPT3_folder + i + ".txt";
	        String SBB_inputFile = SBB_folder + i + ".txt";

			
			System.out.println("Instance " + i + " =================");
			// LNS
			// Calculate LB, UB, maxLB, maxUB, time, P
			try (BufferedReader br = new BufferedReader(new FileReader(LNS_inputFile))) {
				String lineTemp;
				String line = null;
				while ((lineTemp = br.readLine()) != null) {
					if (lineTemp.contains("ITER") || lineTemp.length() == 0) continue;
					    line = lineTemp;
//					System.out.println(line);
				}
//				System.out.println(line);

				String lines[] = line.split("\t");
				String time = lines[0].replace(",","");
//				writeToFile(LNS_output_file, i + "\t" + time + "\t", APPEND);
				LNS_LB = Double.valueOf(lines[1]);
				LNS_UB = Double.valueOf(lines[2]);
				if (LNS_LB > maxLB) maxLB = LNS_LB;
				if (LNS_UB < minUB) minUB = LNS_UB;
				
				LNS_time += Double.valueOf(time);
				LNS_P += LNS_UB / LNS_LB;
//				LNS_Mean_LB += LNS_LB;
				System.out.println("LNS has time " + time + " LB=" + LNS_LB + " UB=" + LNS_UB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// BMS
			// Calculate LB, UB, maxLB, maxUB, time, P
			try (BufferedReader br = new BufferedReader(new FileReader(BMS_inputFile))) {
//				String line = br.readLine();
//				line = line.replace("Algorithm finished in ", "").replace(" ms (simulated time)", "").replace(",","");
//				writeToFile(BMS_output_file, i + "\t" + line + "\t", APPEND);
//				br.readLine();
//				br.readLine();
				String line = null;
				Scanner sc = new Scanner(br.readLine());
				BMS_LB = sc.nextDouble();
				BMS_UB = sc.nextDouble();
				if (BMS_LB > maxLB) maxLB = BMS_LB;
				if (BMS_UB < minUB) minUB = BMS_UB;
				System.out.println("BMS has time " + line + " LB=" + BMS_LB + " UB=" + BMS_UB);
				sc.close();
				
//				BMS_time += Double.valueOf(line);
				BMS_P += BMS_UB / BMS_LB;
//				BMS_Mean_LB += BMS_LB;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Maxsum
			// Calculate LB, UB, maxLB, maxUB, time, P
			try (BufferedReader br = new BufferedReader(new FileReader(Maxsum_inputFile))) {
				String line = null;
				double MStime = 0;
				while ((line = br.readLine()) != null) {
					if (line.contains("Utility of solution found:"))  {
						double quality = Double.parseDouble(line.replace("Utility of solution found: ",""));
						if (quality > Maxsum_LB) Maxsum_LB = quality;
					}
					if (line.contains("simulated time")) {
						line = line.split(" ")[3].replace(",","");
						System.out.println("Maxsum has time " + line + " LB=" + Maxsum_LB);
						MStime = Double.valueOf(line);;
					}
				}
				if (Maxsum_LB > maxLB) maxLB = Maxsum_LB;
				Maxsum_time += MStime;

//				writeToFile(Maxsum_output_file, i + "\t" + line + "\t", APPEND);
//				Maxsum_Mean_LB += Maxsum_LB;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// DSA
			// Calculate LB, UB, maxLB, maxUB, time, P
			try (BufferedReader br = new BufferedReader(new FileReader(DSA_inputFile))) {
				String line = br.readLine();
				DSA_LB = Double.parseDouble(line.split(" ")[3]);
				if (DSA_LB > maxLB) maxLB = DSA_LB;
				line = br.readLine();
				line = line.split(" ")[3].replace(",","");
//				writeToFile(DSA_output_file, i + "\t" + line + "\t", APPEND);
				System.out.println("DSA has time " + line + " LB=" + DSA_LB);
				DSA_time += Double.valueOf(line);
//				DSA_Mean_LB += DSA_LB;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	         // SBB
            // Calculate LB
            try (BufferedReader br = new BufferedReader(new FileReader(SBB_inputFile))) {
                String line = br.readLine();
                SBB_LB = Double.parseDouble(line.split(" ")[3]);
                if (SBB_LB > maxLB) maxLB = SBB_LB;
//              writeToFile(DSA_output_file, i + "\t" + line + "\t", APPEND);
                System.out.println("SBB has LB=" + SBB_LB);
//              DSA_time += Double.valueOf(line);
//              DSA_Mean_LB += DSA_LB;
            } catch (IOException e) {
                e.printStackTrace();
            }
			
			//KOPT2
			// Calculate LB, UB, maxLB, maxUB, time, P
//			try (BufferedReader br = new BufferedReader(new FileReader(KOPT2_inputFile))) {
//				String line = br.readLine();
//				String results[] = line.split("\t");
//				KOPT2_LB = Double.parseDouble(results[1]);
//				KOPT2_UB = Double.parseDouble(results[2]);
//				if (KOPT2_LB > maxLB) maxLB = KOPT2_LB;
//				if (KOPT2_UB < minUB) minUB = KOPT2_UB;
//
////				line = br.readLine();
////				line = line.split(" ")[3].replace(",","");
////				writeToFile(DSA_output_file, i + "\t" + line + "\t", APPEND);
//				System.out.println("KOPT2 has time " + results[0] + " LB=" + KOPT2_LB);
//				KOPT2_time += Double.parseDouble(results[0]);
//				KOPT2_P += KOPT2_UB / KOPT2_LB;
////				DSA_Mean_LB += DSA_LB;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			//KOPT3
			// Calculate LB, UB, maxLB, maxUB, time, P
//			try (BufferedReader br = new BufferedReader(new FileReader(KOPT3_inputFile))) {
//				String line = br.readLine();
//				String results[] = line.split("\t");
//				KOPT3_LB = Double.parseDouble(results[1]);
//				KOPT3_UB = Double.parseDouble(results[2]);
//				if (KOPT3_LB > maxLB) maxLB = KOPT3_LB;
//				if (KOPT3_UB < minUB) minUB = KOPT3_UB;
//
////				line = br.readLine();
////				line = line.split(" ")[3].replace(",","");
////				writeToFile(DSA_output_file, i + "\t" + line + "\t", APPEND);
//				System.out.println("KOPT3 has time " + results[0] + " LB=" + KOPT3_LB);
//				KOPT3_time += Double.parseDouble(results[0]);
//				KOPT3_P += KOPT3_UB / KOPT3_LB;
////				DSA_Mean_LB += DSA_LB;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			LNS_normalized_LB += LNS_LB / maxLB;
			BMS_normalized_LB += BMS_LB / maxLB;
			Maxsum_normalized_LB += Maxsum_LB / maxLB;
			DSA_normalized_LB += DSA_LB / maxLB;
			KOPT2_normalized_LB += KOPT2_LB / maxLB;
			KOPT3_normalized_LB += KOPT3_LB / maxLB;
	        SBB_normalized_LB += SBB_LB / maxLB;
			
//			LNS_normalized_UB += LNS_UB / minUB;
//			BMS_normalized_UB += BMS_UB / minUB;
//			KOPT2_normalized_UB += KOPT2_UB / minUB;
//			KOPT3_normalized_UB += KOPT3_UB / minUB;
			
			LNS_normalized_UB += minUB / LNS_UB;
			BMS_normalized_UB += minUB / BMS_UB;
			KOPT2_normalized_UB += minUB / KOPT2_UB;
			KOPT3_normalized_UB += minUB / KOPT3_UB;
		
//			writeToFile(LNS_output_file, df.format(LNS_LB/maxLB) + "\t", APPEND);
//			writeToFile(LNS_output_file, df.format(LNS_UB/maxUB) + "\n", APPEND);
//			writeToFile(BMS_output_file, df.format(BMS_LB/maxLB) + "\t", APPEND);
//			writeToFile(BMS_output_file, df.format(BMS_UB/maxUB) + "\n", APPEND);
//			writeToFile(Maxsum_output_file, df.format(Maxsum_LB/maxLB) + "\n", APPEND);
//			writeToFile(DSA_output_file, df.format(DSA_LB/maxLB) + "\n", APPEND);
		}
		System.out.println("=====================================");
		System.out.println(instanceType + "-" + numberAgents);
		System.out.println("LNS has p=" + df.format(LNS_P/50)
							+ "\tLB=" + df.format(LNS_normalized_LB/50) 
							+ "\tUB=" + df.format(LNS_normalized_UB/50) 
							+ "\ttime=" + df.format(LNS_time/50));
		
		System.out.println("BMS has p=" + df.format(BMS_P/50)
							+ "\tLB=" + df.format(BMS_normalized_LB/50)
							+ "\tUB=" + df.format(BMS_normalized_UB/50)
							+ "\ttime=" + df.format(BMS_time/50)); 
	
		System.out.println("KOPT2 has p=" + df.format(KOPT2_P/50)
							+ "\tLB=" + df.format(KOPT2_normalized_LB/50) 
							+ "\tUB=" + df.format(KOPT2_normalized_UB/50)
							+ "\ttime=" + df.format(KOPT2_time/50));
		
		System.out.println("KOPT3 has p=" + df.format(KOPT3_P/50)
							+ "\tLB=" + df.format(KOPT3_normalized_LB/50)
							+ "\tUB=" + df.format(KOPT3_normalized_UB/50)
							+ "\ttime=" + df.format(KOPT3_time/50));
		
		System.out.println("MS has LB=" + df.format(Maxsum_normalized_LB/50)
							+ "\ttime=" + df.format(Maxsum_time/50));

		System.out.println("DSA has LB=" + df.format(DSA_normalized_LB/50)
							+ "\ttime= " + df.format(DSA_time/50));
	    
		System.out.println("SBB has LB=" + df.format(SBB_normalized_LB/50));

		
//		System.out.println(LNS_Mean_LB / 50);
//		System.out.println(DSA_Mean_LB / 50);

		
//		maxValue = new ArrayList<Double>(Collections.nCopies(50, -Double.MAX_VALUE));
//		readOutputBMS("random");
//		readOutputDSA("random");
//		readOutputMaxsum("random");
//		readOutputDLNS("random", "TDBR");
	}

	// instanceType=random/grid/

	// instanceType=random/grid/
	// repairType=TDBR/GDBR
//	public static void readOutputDLNS(String instanceType, String repairType) {
//		final String DLNSdirectory = "/Dropbox/7_lns/results/lns";
//		String homeDirectory = System.getProperty("user.home").toString();
//		String folder = homeDirectory + DLNSdirectory + "/" + instanceType + "/" + repairType;
//
//		for (int i = 0; i < 50; i++) {
//			String inFile = folder + "/" + i + "_processed.txt";
//			System.out.println(inFile);
//			try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
//				String line = br.readLine();
//				System.out.println(line);
//
//				Scanner sc = new Scanner(line);
//				sc.nextInt();
//				int LB = sc.nextInt();
//				int UB = sc.nextInt();
//
//				p += UB * 1.0 / LB;
//				averLB += LB;
//				averUB += UB;
//				System.out.println(LB + " " + UB);
//				sc.close();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
////
////		System.out.println("p = " + p / 49);
////		System.out.println("LB = " +  averLB * 1.0 / 49);
////		System.out.println("UB = " + averUB * 1.0 / 49);
//	}
	
//	public static void readOutputBMS(String instanceType) {
//		final String BMSdirectory = "/Dropbox/7_lns/results/bms";
//		String homeDirectory = System.getProperty("user.home").toString();
//		String folder = homeDirectory + BMSdirectory + "/" + instanceType;
//		double p = 0;
//		int averLB = 0;
//		int averUB = 0;
//		for (int i = 0; i < 50; i++) {
//			String inFile = folder + "/" + i + ".txt";
//			System.out.println(inFile);
//			try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
//				String line = br.readLine();
//				System.out.println(line);
//
//				Scanner sc = new Scanner(line);
//				double LB = sc.nextDouble();
//				double UB = sc.nextDouble();
//				p += UB / LB;
//				averLB += LB;
//				averUB += UB;
//				System.out.println(LB + " " + UB);
//				sc.close();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		System.out.println("p = " + p / 49);
//		System.out.println("LB = " +  averLB * 1.0 / 49);
//		System.out.println("UB = " + averUB * 1.0 / 49);
//	}


	// instanceType=random/grid/
//	public static void readOutputMaxsum(String instanceType) {
//		final String MaxsumDirectory = "/Dropbox/7_lns/results/maxsum";
//
//		String homeDirectory = System.getProperty("user.home").toString();
//		String folder = homeDirectory + MaxsumDirectory + "/" + instanceType;
//		for (int i = 0; i < 50; i++) {
//			String inFile = folder + "/" + i + ".txt";
//			System.out.println(inFile);
//			double solutionQuality = 0;
//			try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
//				String line = br.readLine();
//				String[] words;
//				while (line != null) {
//
//					if (line.contains("Utility")) {
//						words = line.split(" ");
//						solutionQuality = Double.parseDouble(words[4]);
//					}
//					line = br.readLine();
//				}
//				System.out.println("Instance " + i + ": " + solutionQuality);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	// instanceType=random/grid/
	// repairType=TDBR/GDBR
//	public static void readOutputDSA(String instanceType) {
//		final String DSAdirectory = "/Dropbox/7_lns/results/dsa";
//
//		String homeDirectory = System.getProperty("user.home").toString();
//		String folder = homeDirectory + DSAdirectory + "/" + instanceType;
//		for (int i = 0; i < 50; i++) {
//			String inFile = folder + "/" + i + ".txt";
//			System.out.println(inFile);
//			double solutionQuality = 0;
//			double time = 0;
//			try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
//				String line = br.readLine();
////				String[] words;
//				while (line != null) {
//					// if (line.contains("Algorithm")) {
//					// words = line.split(" ");
//					// time = Integer.parseInt(words[3].replace(",", ""));
//					// //time = ukFormat.parse(words[3]).intValue();
//					// timeMax = Math.max(time, timeMax);
//					// // Messages
//					// words = br.readLine().split("\t");
//					// outputFormat.setMaxAgtTotMsgs(ukFormat.parse(words[2]).intValue());
//					// outputFormat.setNetLoad(ukFormat.parse(words[2]).intValue());
//					// }
//					if (line.contains("Total optimal utility")) {
//						solutionQuality = Double.parseDouble(line.split(" ")[3]);
//					}
//					if (line.contains("Algorithm finished in")) {
//						time = Double.parseDouble(line.split(" ")[3]);
//					}
//					line = br.readLine();
//				}
//				System.out.println("Instance " + i + ": " + solutionQuality);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public static void writeToFile(String fileName, String content, StandardOpenOption type) {
		byte data[] = content.getBytes();
	    Path p = Paths.get(fileName);

	    try (OutputStream out = new BufferedOutputStream(
	      Files.newOutputStream(p, CREATE, type))) {
	      out.write(data, 0, data.length);
	      out.flush();
	      out.close();
	    } catch (IOException x) {
	      System.err.println(x);
	    }
	}
}
