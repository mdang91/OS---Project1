package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Question_2 {
	

	public static void main(String[] args) 
			throws FileNotFoundException{
		
		File file = new File("E:/GSU-GPC/10th - Spring 2022 (4)/OPERATING SYSTEMS/Project 1/OS_Project/Process_List.csv");
	    Scanner sc = new Scanner(file);
	 
	    // we just need to use \\; as delimiter
	    sc.useDelimiter("\\;");
	    
	    
	    Process proc;						//proc stores current process
	    Schedule master = new Schedule("Master Schedule"); 	//master stores the created jobs in arraylist
		
	    
	    
	    
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
		PA.setSpeed(2000000000L);	
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
		
 
		RR(master.copySchedule());
	    

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
			sumM += proc.getMem();
			
		}
		
		averageC = sumC / num;
		averageM = sumM / num;
		runtime = averageC / sched.getSpeed();
		
		System.out.println("Number of process:  " + num);
		System.out.println("Average Cycles:  " + averageC);
		System.out.println("Average Runtime: " + runtime + " seconds");
		System.out.println("Average Memory:  " + averageM);
	}
	
	
	public static void RRSched(Schedule sched, Schedule master, long burstTime) {
	
		//sched.printSchedule();
		//master.printSchedule();
		//split the CPUS by half 3 have 2Ghz and 3 have 4 GHz
		Schedule PA = new Schedule("CPU A");
		PA.setSpeed(2000000000L);		
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
		
		Process proc; 
		//distributes each process to a CPU,
		//Starts with CPU PA and goes around
		   
		 for(int i = 0; i < sched.getNum(); i++) {		
			
			proc = sched.getProcess(i);
			
			if(i == sched.getNum()) {
				cpu = "D";
			}
			if(i < sched.getNum()/2)
			{

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
				cpu = "A";
				}
			}	
			if(i > sched.getNum()/2) {
				
					if(cpu == "D") {
					PD.addProcess(proc);
				cpu = "E";
				}
				 else if(cpu == "E") {
					 PE.addProcess(proc);
				cpu = "F";
				 }
				 else if(cpu == "F") {
					 PF.addProcess(proc);
				cpu = "D";
				 }
				 }

		
		}
		/*now all the cpus have a split of the processes
		 * two CPUs could run the same processes at the same time\
		 * 
		*/
		
		int waitSum = PA.waitTimeAvgRR(burstTime, master);
	
		int tatSum = PA.turnAroundTimeAvgRR(burstTime, master);
		
		waitSum += PB.waitTimeAvgRR(burstTime, master);
		
		tatSum += PB.turnAroundTimeAvgRR(burstTime, master);
		
		waitSum += PC.waitTimeAvgRR(burstTime, master);
		
		tatSum += PC.turnAroundTimeAvgRR(burstTime, master);
		
		waitSum += PD.waitTimeAvgRR(burstTime, master);
		
		tatSum += PD.turnAroundTimeAvgRR(burstTime, master);
		
		waitSum += PE.waitTimeAvgRR(burstTime, master);
		
		tatSum += PE.turnAroundTimeAvgRR(burstTime, master);
		
		waitSum += PF.waitTimeAvgRR(burstTime, master);
		
		tatSum += PF.turnAroundTimeAvgRR(burstTime, master);
		
		
		int waitAvg = waitSum / master.getNum();
		int tatAvg = tatSum / master.getNum();
		
		System.out.println("Round Robin Optimize");
		System.out.println("Number of process:  " + master.getNum());
		System.out.println("Number of scheduled process:  " + sched.getNum());
		System.out.println("Average wait time: " + waitAvg);
		System.out.println("Average turn-around time: " + tatAvg);
		System.out.println();
		
		
	}
	
	public static void RR(Schedule master) {
		Schedule mCopy = new Schedule("Master Copy");
		mCopy = master.copySchedule();
		
		mCopy.setSpeed(3000000000L);
		//retrieved
		int num = mCopy.getNum();
		long speed = mCopy.getSpeed();
		Process proc;
		long cycles;
		
		//calculated
		long burstTime = 100;
		long burstCycles = burstTime * speed;
		int timeElapsed = 0;
		long runTime;
		
		//stores the schedule in a RR order. 
		Schedule RR = new Schedule("RR");
		//copy all the fields of the current process to a temp process
		Process copy = new Process();
		
		
		
		Boolean done = false;
		int i = 0;
		
		
		//System.out.println(master.getNum() + "  " + mCopy.getNum() + "  ");	
		while(!done) {
			

			//get current process and cycles
			proc = mCopy.getProcess(i);
			cycles = proc.getCycles();
			
			if(cycles <= burstCycles) {
				//the CPU will only run the amount of time it takes to complete the process
				burstTime = cycles / speed;
				burstCycles = cycles;
			}

			//subtract the amount of cycles ran from the current
			mCopy.getProcess(i).setCycles(cycles - burstCycles);
			
			if(mCopy.getProcess(i).getCycles() > 0) {
				//copy all the process fields to copy
				copy = new Process();
				copy.setPID(proc.getPID());
				copy.setCycles(proc.getCycles());
				copy.setMem(proc.getMem());
				RR.addProcess(copy);
			}
			else {
				mCopy.removeProcess(i);
			}
			
			//if no more processes in schedule
			if(mCopy.getNum() <= 0) {
				done = true;
			}
			i++;
			
			if(i >= mCopy.getNum()) {
				i = 0;
			}
			//reset burst cycles and burst time
			burstTime = 100;
			burstCycles = burstTime * speed;

		}

		//RR.printSchedule();

		RRSched(RR, master, burstTime);
		
		
		
		
	}
	
		

}
	
