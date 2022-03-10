package Project;


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class test {
	
	public static void main(String[] args) 
			throws FileNotFoundException{
		
		File file = new File("/Users/bryanp/eclipse-workspace/OS_Project/Process_List.csv");
	    Scanner sc = new Scanner(file);
	 
	    // we just need to use \\; as delimiter
	    sc.useDelimiter("\\;");
	    
	    
	    Process proc;						//proc stores current process
	    Schedule master = new Schedule();	//master stores the crreated jobs in arraylist
	    
	    for(int i = 0; i < 250; i++) {		//for loop fetches processes from the file
	    	proc = new Process();
	    	proc.setPID(Integer.parseInt(sc.next()));
	    	proc.setCycles(Long.parseLong(sc.next()));
	    	proc.setMem(Integer.parseInt(sc.next()));
	    	
	    	master.addProcess(proc);
	    }
	    
	    for(int i = 0; i < 250; i++) {
	    	proc = master.getProcess(i);
	    	
	    	System.out.println("PID: " + proc.getPID() + "   Cyc: " + proc.getCycles() + "  Mem: " + proc.getMem());
	    }
	
		

		
		
		
		
		
	}
}
