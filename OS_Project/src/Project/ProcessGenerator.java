package Project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ProcessGenerator {
	
	public static void main(String[] args) {
		

		Scanner input = new Scanner(System.in);
		int k;
		System.out.println("Input k:");				//get number of processes
		k = input.nextInt();
		
		
		Process proc;
		
		for(int i = 0; i < k; i++) {
			proc = new Process();
			
			try {
			     
			      //each process written on new line of file
			      FileWriter fw = new FileWriter("Process_List2.txt", true);
			      BufferedWriter bw = new BufferedWriter(fw);
			      bw.write(proc.getPID() + ";" + proc.getCycles() + ";" + proc.getMem() + ";");
			      bw.close();
			      
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			
			
		}
		
		
		
		
		
	}
}