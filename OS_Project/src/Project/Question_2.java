package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Question_2 {
	

	public static void main(String[] args) 
			throws FileNotFoundException{
		
		File file = new File("/Users/bryanp/eclipse-workspace/OS_Project/Process_List.txt");
	    Scanner sc = new Scanner(file);
	 
	    // we just need to use \\; as delimiter
	    sc.useDelimiter("\\;");
	    
	    
	    Process proc;						//proc stores current process
	    Schedule master = new Schedule("Master Schedule"); 	//master stores the crreated jobs in arraylist
		
	    
	    
	    
	    for(int i = 0; i < 250; i++){		//for loop fetches processes from the file
	    	proc = new Process();
	    	proc.setPID(Integer.parseInt(sc.next()));
	    	proc.setCycles(Long.parseLong(sc.next()));
	    	proc.setMem(Integer.parseInt(sc.next()));
	    	
	    	master.addProcess(proc);
	    }
	    sc.close();
	    
	    Boolean done = false;
	    
	    Scanner in = new Scanner(System.in);
	    
	    master.setSpeed(3000000000L);
	    
	    Average(master);
	    
	    Schedule PA = new Schedule("CPU A");
		PA.setSpeed(2000000000L);		//3GH
		Schedule PB = new Schedule("CPU B");
		PB.setSpeed(2000000000L);
		Schedule PC = new Schedule("CPU C");
		PC.setSpeed(2000000000L);
		Schedule PD = new Schedule("CPU D");
		PD.setSpeed(4000000000L);
		Schedule PE = new Schedule("CPU E");
		PE.setSpeed(4000000000L);
		Schedule PF = new Schedule("CPU F");
		PF.setSpeed(4000000000L);
		String cpu = "A";
		
 
	    
	    

	}
	public static void Average(Schedule sched) {
		Process proc = new Process();
		long sumC = 0;
		long averageC;
		int num = sched.getNum();
		long runtime;
		int sumM = 0;
		int averageM;
		
		for(int i = 0; i < num; i++) {
			proc = sched.getProcess(i);
			sumC += proc.getCycles();
			sumM = proc.getMem();
			
		}
		
		averageC = sumC / num;
		averageM = sumM / num;
		runtime = averageC / sched.getSpeed();
				
		System.out.println("Average Cycles:  " + averageC);
		System.out.println("Average Runtime: " + runtime + " seconds");
		System.out.println("Average Memory:  " + averageM);
	}
	
	
}
