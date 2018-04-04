package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * Adrian Sypos - G00309646
 * CipherBreaker - Class containing the main method, printing menu and allowing user to interact through command line UI
 */

public class CipherBreaker {

	public static void main(String[] args) throws Exception, Throwable {
//		String cipherText = "HFZQLYVEDWNITIQPQDUVHYLGXZHFNYBKPACAZQHFVQIQCUUVYCBXABQZQZURHQDZHBKDMVZQHXRGURLQ"
//				+ "HTXZQVDFYXZHRGGWHBYEGXNYYEGKYVHFLQDBWDVQIZEAUCAHHPQIBRRVBREZNYYQAHPUQDUVHYZXGNRDEOZWQFKCLZZH"
//				+ "XVRDEOFEINQZZKZPKDYDCAMEEQUDBCLDBKPAEDUVYCHFZQQEUMSVPBUMURLQHTXZXZCUHTVTPHMDLDRGMDLDVBHCMGU"
//				+ "VYCQVPVDMSZXQCPDIQZLQKDUBEMTCYDDBCQGDFEUKQZVPCYUHKDIA"
//				+ "BDFVFEETGKIDOZEFURLQUVYCKDPTACYQUCFUPVVBBREZZXDTZPWCMEDILYTHZHADMUDBGQHBKIFEMDEWIZRGVQHT"
//				+ "KCNWIEGNHCPLLUDPCOFTQGDPNWBYHCHFQZITQVGKUVYCHFBDQVHVHCHFDIYXHFBRUMLZKDZDFQFHNYLGSAPLQCCAZ"
//				+ "QHCPCBODITCVBMUHFDIYXHFBRUMLZKDLULIDLIDDLQRKWZQACYQUZBHZBDUBHQZUKUZEDGWTVBXABQZQZBUFEUFFT"
//				+ "QVEKZQINAHMEPTDFNYFBIZEXBRRVBREZTCILEVFBEDHUBRWDLYTHFHIZNYCPOVBDLIZQHFQPQDUVHYLGCUNYOKDMPC"
//				+ "HTXZPCGCHFDYLQDBLTHPQEKCGKTIQIBRVQHBQNDBRXBZEFRFVUEDQYNYMZCPBDHYLKCUXF";
//
//		int OPTIMAL_TEMP = (int)((10 + 0.087 * (cipherText.length() - 84)));
//		int BEST_TEMP = OPTIMAL_TEMP / 3;
//		
//		Playfair playfair = new Playfair();
//		playfair.setCipherText(cipherText);
//		
//		long startTime = System.currentTimeMillis();
//
//		SimulatedAnnealing sa = new SimulatedAnnealing(BEST_TEMP, 50000, cipherText);
//		sa.annealing();
//		
//		System.out.println("Execution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");	
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("-----------------------------------------------------");
		System.out.println("--------------Adrian Sypos - G00309646---------------");
		System.out.println("--------------Playfair Cipher Breaker----------------");
		System.out.println("-----------------------------------------------------");
		
		int menu = 0;
		int transitions = 50000;
		boolean end = false;
		String filename = "";
		
		do {
			System.out.println("\n\n-----------------------------------------------------");
			System.out.println("1. Decrypt text from file----------------------------");
			System.out.println("2. Decrypt text from command line--------------------");
			System.out.println("3. Exit----------------------------------------------");
			System.out.println("-----------------------------------------------------");
			System.out.print("Choice: ");
			
			menu = console.nextInt();
			
			switch(menu) {
				case 1:
					boolean debug = false;
					System.out.println("Debug mode? (In debug mode you can see debugging information) [y/n]");
					String opt = console.next();
					if(opt.equalsIgnoreCase("y")) debug = true;
					else if(opt.equalsIgnoreCase("n")) debug = false;
					else debug = false;
					
					System.out.println("Filename (e.g. exam_tips): ");
					filename = console.next();
					filename += ".txt";
					
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
					StringBuilder sb = new StringBuilder();
					String line;
					
					while((line = br.readLine()) != null) {
						sb.append(line.toUpperCase().replaceAll("\\W", "").replace("J", ""));
					}
					
					System.out.print("Do you know the key? [y/n]: ");
					console.nextLine();
					String knowKey = console.nextLine();
					if(knowKey.equalsIgnoreCase("y")) {
						System.out.print("Enter key: ");
						String key = console.nextLine();
						Playfair playfair = new Playfair();
						playfair.setCipherText(sb.toString());
						System.out.println("\nDecrypted message: " + playfair.decrypt(key) + "\n");
						break;
					}
					else{
						//System.out.println("\nText in file: " + sb.toString());
						System.out.println("\nPlease wait patiently...");
						int OPTIMAL_TEMP = (int)((10 + 0.087 * (sb.toString().length() - 84)));
						int BEST_TEMP = OPTIMAL_TEMP / 3;
						long startTime = System.currentTimeMillis();
						SimulatedAnnealing sa = new SimulatedAnnealing(BEST_TEMP, transitions, sb.toString());
						sa.annealing(debug);
						System.out.println("\nExecution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");	
						
						System.out.println("\nIf the result is not satisfactory please try again\nTry again?: [y/n]");
						String again = console.next();
						if(again.equalsIgnoreCase("y")) {
							System.out.println("\nPlease wait patiently...");
							sa.annealing(debug);
							break;
						}
						else if(again.equalsIgnoreCase("n")) {
							break;
						}
						else {
							break;
						}
					}
					
				case 2: 
					debug = false;
					System.out.println("Debug mode? (In debug mode you can see debugging information) [y/n]");
					opt = console.next();
					if(opt.equalsIgnoreCase("y")) debug = true;
					else if(opt.equalsIgnoreCase("n")) debug = false;
					else debug = false;
					console.nextLine();
					
					System.out.print("Enter encrypted text to decrypt: ");
					String cypherText = console.nextLine();
					
					System.out.println("\nPlease wait patiently...");
					int OPTIMAL_TEMP = (int)((10 + 0.087 * (cypherText.length() - 84)));
					int BEST_TEMP = OPTIMAL_TEMP / 3;
					long startTime = System.currentTimeMillis();
					SimulatedAnnealing sa = new SimulatedAnnealing(BEST_TEMP, transitions, cypherText);
					sa.annealing(debug);
					System.out.println("\nExecution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");	
					
					System.out.println("\nIf the result is not satisfactory please try again\nTry again?: [y/n]");
					String again = console.next();
					if(again.equalsIgnoreCase("y")) {
						System.out.println("\nPlease wait patiently...");
						sa.annealing(debug);
						break;
					}
					else if(again.equalsIgnoreCase("n")) {
						break;
					}
					else {
						break;
					}
					
				case 3:
					end = true;
					break;
			}
		}while(!end);
	}
}
