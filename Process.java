package Project;

public class Process {
	
	private static int countPID;
	private int pID;
	private long cycles;
	private int mem;
	
	
	public Process() {
		this.pID = createPID();
		this.cycles = createCycles();
		this.mem = createMem();

		
	}
	
	public int getPID() {
		return this.pID;
	}
	public long getCycles() {
		return this.cycles;
	}
	public int getMem() {
		return this.mem;
	}
	
	
	public void nextPID() {
		countPID++;
	}
	public int createPID() {
		nextPID();
		return countPID;
	}
	public long createCycles() {
		long min = 10000000L;
	    long max = 10000000000000L;
	    long cycles = min + (long) (Math.random() * (max - min));
	    
	    return cycles;
	}
	public int createMem() {
		int min = 1000;						//in BYTES
	    int max = 16000;
	    int mem = min + (int) (Math.random() * (max - min));
	    
	    return mem;
	}
	public void setPID(int id) {
		this.pID = id;
	}
	public void setCycles(long cyc) {
		this.cycles = cyc;
	}
	public void setMem(int m) {
		this.mem = m;
	}
}
