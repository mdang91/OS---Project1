package Project;


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
	    
	   /* 
	    for(int i = 0; i < 250; i++) {
	    	proc = master.getProcess(i);
	    	
	    	System.out.println("PID: " + proc.getPID() + "   Cyc: " + proc.getCycles() + "  Mem: " + proc.getMem());
	    }
	    */
	    
	   /*
	    *Will be used to select which type of schedule (FIFO,SJF,RR)
	    Scanner in = new Scanner(System.in);
	    int sch;
	    
	    System.out.println("Enter 1 for FIFO: ");
	    sch = in.nextInt();
	    
	    in.close();
	    

		*/
	    	
	    
	
	    FIFO(master.clone());
	    
	    
	     
	
		
	}
	
	public static void Times(Schedule sched) {
		int num = sched.getNum();
		
		int wait = 0;					//wait stores wait time of each 
		int waitSum = 0;				//waitSum sums the wait times of each
		int tat =0;						//tat turn around time for each 
		int tatSum = 0;					//tatSum sums the turnaround times
		long runTime = 0;				//runtime calculation of each process
		int timeElapsed = 0;			//total time elapsed
		
		int avgWT = 0;
		int avgTAT = 0;
		
		long speed = sched.getSpeed();	//get processor speed
		
		Process proc = new Process();
		long cycles;
		
		for(int i = 0; i < num; i++) {
			
			proc = sched.getProcess(i);
			cycles = proc.getCycles();
			
			//wait time is equal to the time elapsed before running current process
			wait = timeElapsed;
			//wait sum is sum of wait time for all processes
			waitSum += wait;
			//runTime is cycles of current process divided by speed of CPU used
			runTime =  (cycles / speed);
			//tat or turn around time is the wait time + runtime 
			tat = (int) (wait + runTime);
			//tatSum is the sum of all turn around times
			tatSum += tat;
			//total time elapsed at end of current process
			timeElapsed += runTime;
			
		}
		//average wait time and average runt time
		avgWT = waitSum / num;
		avgTAT = tatSum / num;
		
		System.out.println("Wait time Average = " + avgWT);
		System.out.println("Turn arount time Average = " + avgTAT);
		

	}
	
	public static void FIFO(Schedule sched) {
		System.out.println(sched.getNum());
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

		
		Times(PA);
		Times(PB);
		Times(PC);
		Times(PD);
		Times(PE);
		Times(PF);
}
}























