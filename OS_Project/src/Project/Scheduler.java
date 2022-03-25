package Project;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Scheduler {
	
	public static void main(String[] args) 
			throws FileNotFoundException{
		
		File file = new File("E:/GSU-GPC/10th - Spring 2022 (4)/OPERATING SYSTEMS/Project 1/OS_Project/Process_List2.csv");
		//File file = new File("E:/GSU-GPC/10th - Spring 2022 (4)/OPERATING SYSTEMS/Project 1/OS_Project/Process_List.csv");
		
	    Scanner sc = new Scanner(file);
	    
		File file1 = new File("E:/GSU-GPC/10th - Spring 2022 (4)/OPERATING SYSTEMS/Project 1/OS_Project/Process_List3.csv");
		
		
	    Scanner sc1 = new Scanner(file1);
	 
	    // we just need to use \\; as delimiter
	    sc.useDelimiter("\\;");
	    sc1.useDelimiter("\\;");
	    
	    
	    Process proc;
	    Process proc1;	//proc stores current process
	    Schedule master = new Schedule("Master Schedule");	//master stores the created jobs in arraylist
	    Schedule master1 = new Schedule("Master Schedule");
		
	    
	    
	    
	    for(int i = 0; i < 250; i++){		//for loop fetches processes from the file
	    	proc = new Process();
	    	proc.setPID(Integer.parseInt(sc.next()));
	    	proc.setCycles(Long.parseLong(sc.next()));
	    	proc.setMem(Integer.parseInt(sc.next()));
	    	
	    	master.addProcess(proc);
	    }
	    for(int i = 0; i < 200; i++){		//for loop fetches processes from the file 200 process
	    	proc1 = new Process();
	    	proc1.setPID(Integer.parseInt(sc1.next()));
	    	proc1.setCycles(Long.parseLong(sc1.next()));
	    	proc1.setMem(Integer.parseInt(sc1.next()));
	    	
	    	master1.addProcess(proc1);
	    }
	    sc.close();
	    sc1.close();
	    
	    Boolean done = false;
	    
	    Scanner in = new Scanner(System.in);
	    
	    master.setSpeed(3000000000L);
	    master1.setSpeed(3000000000L);
	    
	    Average(master);
	    Average(master1);
	   
	    System.out.println("FIFO: ");
	    FIFO(master.copySchedule());
		
	    System.out.println("SJF: ");
		SJF(master.copySchedule()); 	
		
		System.out.println("RR: ");
		RR(master.copySchedule()); 	
		
		System.out.println("Question 2: ");
		Question2(master.copySchedule());
		    
		System.out.println("Question 3: ");
		Question3(master1.copySchedule());
		
		System.out.println("Question 3: ");
		Question3(master.copySchedule());
		
		System.out.println("Question 4: ");
		Question4();
	  
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
		

		

		
		
		
		System.out.println("Average wait time = " + waitAvg);
		System.out.println("Average turn-around time = " + tatAvg);
		System.out.println();
	}
	
	public static void SJF(Schedule sched) {
		
		
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
			
			//if there's more to sort , resets low and lowIndex
			if(sched.getNum() > 0) {
				low = sched.getProcess(0).getCycles();
				lowIndex = 0;
			}
			
		}
		//will distribute to each CPU evenly
		FIFO(sorted);
		
		

	}

	public static void RRSched(Schedule sched, Schedule master, long burstTime) {
		
		//sched.printSchedule();
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
		
		System.out.println("RR:");
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
		
		
		System.out.println(master.getNum() + "  " + mCopy.getNum() + "  ");	
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


	public static void Question2(Schedule sched){
		

	    
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
					
			//if there's more to sort , resets low and lowIndex
			if(sched.getNum() > 0) {
				low = sched.getProcess(0).getCycles();
				lowIndex = 0;
			}
					
		}
		
		
		
		//get total number of cycles
		long cycTotal = 0;
		Process temp = new Process();
		
		for(int i = 0; i < sorted.getNum(); i++) {
			temp = sorted.getProcess(i);
			
			cycTotal += temp.getCycles();
			
		}

		
		//CPU A,B,C should have 1/3 of the total cycles so that 
		//CPU D,E,F have twice the total cycles 
		long ABCTot = cycTotal * 1 / 3;
		long defTot = cycTotal * 2 / 3;

/*		
		String cpu = "D";
		
		Process proc; 
		//distributes each process to a CPU,
		//Starts with CPU PA and goes around
		
		Boolean defDone = false;
		long defCycles = 0;
		for(int i = 0; i < sorted.getNum(); i++) {
			
			proc = sorted.getProcess(i);
			
			if(!defDone) {
				if(cpu == "D") {
					PD.addProcess(proc);
					defCycles += proc.getCycles();
					if (defCycles >= defTot) {
						defDone = true;
						cpu = "A";
					}
					else cpu = "E";
				}
				else if(cpu == "E") {
					PE.addProcess(proc);
					defCycles += proc.getCycles();
					if (defCycles >= defTot) {
						defDone = true;
						cpu = "A";
					}
					else cpu = "F";
				}
			
				else if(cpu == "F") {
					PF.addProcess(proc);
					defCycles += proc.getCycles();
					if (defCycles >= defTot) {
						defDone = true;
						cpu = "A";
					}
					else cpu = "D";
				}
			}
			else {
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
		}

*/		
		
		//now the schedule is sorted to SJF
		long abcCycles = 0;

		String cpu = "A";
		
		Process proc; 
		//distributes each process to a CPU,
		//Starts with CPU PA and goes around
		//if ABC have 1/3 of total cycles already then they are removed from the distribution cycle
		Boolean abcDone = false;
		
		
	
		for(int i = 0; i < sorted.getNum(); i++) {
			
			proc = sorted.getProcess(i);
			
			if((cpu == "A") && !abcDone) {
				//System.out.println("ABC:" + proc.getCycles());
				PA.addProcess(proc);
				abcCycles += proc.getCycles();
				
				if(abcCycles >= ABCTot) {
					abcDone = true;
					cpu = "D";
				}
				else cpu = "B";
			}
			else if((cpu == "B") && !abcDone) {
				PB.addProcess(proc);
				abcCycles += proc.getCycles();
				if(abcCycles >= ABCTot) {
					abcDone = true;
					cpu = "D";
				}
				else cpu = "C";
			}
			else if((cpu == "C") && !abcDone) {
				PC.addProcess(proc);
				abcCycles += proc.getCycles();
				
				if(abcCycles >= ABCTot) {
					abcDone = true;
					cpu = "D";
				}
				else cpu = "D";
			}
			else if(cpu == "D") {
				//System.out.println("DEF:" + proc.getCycles());
				PD.addProcess(proc);
				cpu = "E";
			}
			else if(cpu == "E") {
				PE.addProcess(proc);
				cpu = "F";
			}
			
			else if(cpu == "F") {
				PF.addProcess(proc);
				
				if(abcDone) cpu = "D";
				else cpu = "A";
				
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
		

		System.out.println("Attempt 1");
		System.out.println("Average wait time = " + waitAvg);
		System.out.println("Average turn-around time = " + tatAvg);
		System.out.println();
		
	
		
	}

	public static void Question3(Schedule sched){
	

    
    Schedule PA = new Schedule("CPU A");
	PA.setSpeed(2000000000L);		//2GHz
	Schedule PB = new Schedule("CPU B");
	PB.setSpeed(2000000000L);
	Schedule PC = new Schedule("CPU C");
	PC.setSpeed(2000000000L);
	Schedule PD = new Schedule("CPU D");
	PD.setSpeed(4000000000L);		//4GHz
	Schedule PE = new Schedule("CPU E");
	PE.setSpeed(4000000000L);
	Schedule PF = new Schedule("CPU F");
	PF.setSpeed(4000000000L);
	
	
	//input varibles
	
	
	//calculated variables
	long low = sched.getProcess(0).getCycles();
	int lowIndex = 0;
	Schedule sorted = new Schedule("SJF");
	System.out.println("Number of Process: " + sched.getNum());
	//while there still processes in sched
	int q3 = sched.getNum()-50;
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
				
		//if there's more to sort , resets low and lowIndex
		if(sched.getNum() > 0) {
			low = sched.getProcess(0).getCycles();
			lowIndex = 0;
		}
				
	}
	
	
	
	//get total number of cycles
	long cycTotal = 0;
	Process temp = new Process();
	
	for(int i = 0; i < sorted.getNum(); i++) {
		temp = sorted.getProcess(i);
		
		cycTotal += temp.getCycles();
		
	}

	
	//CPU A,B,C should have 1/3 of the total cycles so that 
	//CPU D,E,F have twice the total cycles 
	long ABCTot = cycTotal * 1 / 3;
	long defTot = cycTotal * 2 / 3;
	int abcmem = 8000;
	int defmem = 16000;

	
	//now the schedule is sorted to SJF
	long abcCycles = 0;

	String cpu = "A";
	
	Process proc; 
	//distributes each process to a CPU,
	//Starts with CPU PA and goes around
	//if ABC have 1/3 of total cycles already then they are removed from the distribution cycle
	Boolean abcDone = false;
	
	

	for(int i = 0; i < sorted.getNum(); i++) {
		
		proc = sorted.getProcess(i);
		
		if((cpu == "A") && !abcDone && (proc.getMem() <= abcmem)) {
			PA.addProcess(proc);
			//System.out.println("ABC: " + proc.getMem());
			abcCycles += proc.getCycles();
			
			if((abcCycles >= ABCTot))  {
				abcDone = true;
				cpu = "D";
			}
			else cpu = "B";
		}
		else if((cpu == "B") && !abcDone && (proc.getMem() <= abcmem)) {
			PB.addProcess(proc);
			abcCycles += proc.getCycles();
			if(abcCycles >= ABCTot) {
				abcDone = true;
				cpu = "D";
			}
			else cpu = "C";
		}
		else if((cpu == "C") && !abcDone && (proc.getMem() <= abcmem)) {
			PC.addProcess(proc);
			abcCycles += proc.getCycles();
			
			if(abcCycles >= ABCTot) {
				
				abcDone = true;
				cpu = "D";
			}
			else cpu = "D";
		}
		else if(cpu == "D") {
			//System.out.println("DEF:" + proc.getMem());
			PD.addProcess(proc);
			cpu = "E";
		}
		else if(cpu == "E") {
			PE.addProcess(proc);
			cpu = "F";
		}
		
		else if(cpu == "F") {
			PF.addProcess(proc);
			
			if(abcDone) cpu = "D";
			else cpu = "A";
			
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
	

	System.out.println("Attempt 1");
	System.out.println("Average wait time = " + waitAvg);
	System.out.println("Average turn-around time = " + tatAvg);
	System.out.println();
	
	
}

	public static void Question4(){
		

	    
	    Schedule PA = new Schedule("CPU A");
		PA.setSpeed(2000000000L);		//2GHz
		Schedule PB = new Schedule("CPU B");
		PB.setSpeed(2000000000L);
		Schedule PC = new Schedule("CPU C");
		PC.setSpeed(2000000000L);
		Schedule PD = new Schedule("CPU D");
		PD.setSpeed(4000000000L);		//4GHz
		Schedule PE = new Schedule("CPU E");
		PE.setSpeed(4000000000L);
		Schedule PF = new Schedule("CPU F");
		PF.setSpeed(4000000000L);
		
		


		
		//CPU A,B,C should have 1/3 of the total cycles so that 
		//CPU D,E,F have twice the total cycles 
		int abcmem = 8000;
		int defmem = 16000;

		
		//now the schedule is sorted to SJF
		long abcCycles = 0;

		String cpu = "A";
		
		Process proc; 
		//distributes each process to a CPU,
		//Starts with CPU PA and goes around
		//if ABC have 1/3 of total cycles already then they are removed from the distribution cycle
		//Boolean abcDone = false;
		
		int k = 250;
		for(int i = 0; i < k; i++) {
			proc = new Process();
			
			if(cpu == "A" && (proc.getMem() <= abcmem)) {

				PA.addProcess(proc);
				cpu = "B";
			}
			else if(cpu == "B" && (proc.getMem() <= abcmem)) {

				PB.addProcess(proc);
				cpu = "C";
			}
			else if(cpu == "C" && (proc.getMem() <= abcmem)) {

				PC.addProcess(proc);
				cpu = "D";
			}
			else if(cpu == "D" && (proc.getMem() > abcmem)) {

				PD.addProcess(proc);
				cpu = "E";
			}
			else if(cpu == "E" && (proc.getMem() > abcmem)) {

				PE.addProcess(proc);
				cpu = "F";
			}
			
			else if(cpu == "F" && (proc.getMem() > abcmem)) {

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
		

		System.out.println("Attempt 1");
		System.out.println("Average wait time = " + waitAvg);
		System.out.println("Average turn-around time = " + tatAvg);
		System.out.println();
		
		
	}
}






















