package Project;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Scheduler {
	
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
	    
	    while(!done) {
	    	//Will be used to select which type of schedule (FIFO,SJF,RR)
		    in = new Scanner(System.in);
		    int sch;
		    
		    System.out.println("Enter \"1\" for FIFO ");
		    System.out.println("Enter \"2\" for SJF ");
		    System.out.print("Enter: ");
		    sch = in.nextInt();
			System.out.println();
		    
		    if(sch == 1) {
			    System.out.println("FIFO: ");
			    FIFO(master.copySchedule());
		    }else if (sch == 2){
			    System.out.println("SJF: ");
			    SJF(master.copySchedule()); 	
		    }else {
		    	System.out.println("Program Ended");
		    	done = true;
		    }
		    
		    
		    
		   
	    	
	    }
	    
	  
	}
	

	
	public static void FIFO(Schedule sched) {
		//gave all CPUS the same speed of 3 GHZ = 3*10^9 Hz
		Schedule PA = new Schedule("CPU A");
		PA.setSpeed(3000000000L);		//3GH
		Schedule PB = new Schedule("CPU B");
		PB.setSpeed(3000000000L);
		Schedule PC = new Schedule("CPU C");
		PC.setSpeed(3000000000L);
		Schedule PD = new Schedule("CPU D");
		PD.setSpeed(3000000000L);
		Schedule PE = new Schedule("CPU E");
		PE.setSpeed(3000000000L);
		Schedule PF = new Schedule("CPU F");
		PF.setSpeed(3000000000L);
		String cpu = "A";
		
		Process proc; 
		//distributes each process to a CPU,
		//Starts with CPU PA and goes around
		for(int i = 0; i < sched.getNum(); i++) {
			
			proc = sched.getProcess(i);
			
			if(cpu == "A") {
				PA.addProcess(proc);
				cpu = "B";
			}
			else if(cpu == "B") {
				PB.addProcess(proc);
				cpu = "C";
			}
			else if(cpu == "C") {
				PC.addProcess(proc);
				cpu = "D";
			}
			else if(cpu == "D") {
				PD.addProcess(proc);
				cpu = "E";
			}
			else if(cpu == "E") {
				PE.addProcess(proc);
				cpu = "F";
			}
			
			else if(cpu == "F") {
				PF.addProcess(proc);
				cpu = "A";
			}
		
		
		
		
		
		
	}
		//new way ( will not work for round robin
		int waitSum = PA.waitTimeAvg();
		int tatSum = PA.turnAroundTimeAvg();
		waitSum += PB.waitTimeAvg();
		tatSum += PB.turnAroundTimeAvg();
		waitSum += PC.waitTimeAvg();
		tatSum += PC.turnAroundTimeAvg();
		waitSum += PD.waitTimeAvg();
		tatSum += PD.turnAroundTimeAvg();
		waitSum += PE.waitTimeAvg();
		tatSum += PE.turnAroundTimeAvg();
		waitSum += PF.waitTimeAvg();
		tatSum += PF.turnAroundTimeAvg();
		
		int waitAvg = waitSum / 6;
		int tatAvg = tatSum / 6;
		

		

		
		
		
		System.out.println("Wait time Average = " + waitAvg);
		System.out.println("Turn arount time Average = " + tatAvg);
		System.out.println();
	}
	
	public static void SJF(Schedule sched) {
		//WIP
		
		//input varibles
		
		
		//calculated variables
		long low = sched.getProcess(0).getCycles();
		int lowIndex = 0;
		Schedule sorted = new Schedule("SJF");
		
		//while there still processes in sched
		while(sched.getNum() > 0) {
			//for each process still in sched
			for(int i = 0; i<sched.getNum(); i++) {
				//if current process is shorter than low process
				if(sched.getProcess(i).getCycles() < low) {
					//move new process to low
					low = sched.getProcess(i).getCycles();
					//save index
					lowIndex = i;
				}
			}
			//add the shortest process to the sorted schedule
			sorted.addProcess(sched.getProcess(lowIndex));
			//remove the shortest process from sched
			sched.removeProcess(lowIndex);
			
			//if theres more to sort , resets low and lowIndex
			if(sched.getNum() > 0) {
				low = sched.getProcess(0).getCycles();
				lowIndex = 0;
			}
			
		}
		//will distribute to each CPU evenly
		FIFO(sorted);
		
		

	}
}























