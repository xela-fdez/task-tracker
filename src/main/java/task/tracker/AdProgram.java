package task.tracker;

public class AdProgram {
	private String program;
	private float target;
	private float tasks;
	private static float baseTarget = 1114.23f;			//SP ads target
	
	public static final float SBTarget = 1376.44f;
	public static final float SBVTarget = 248.88f;
	public static final float SDTarget = 731.25f;
	public static final float SPTarget = 1114.23f;
	public static final float StoresTarget = 365.62f;
	public static final float BooksTarget = 1063.59f;
	
//	SB = 1376.44
//	SBV = 248.88
//	SD = 731.25
//	SP = 1114.23
//	Stores = 365.62
//	Books = 1063.59
	
	public AdProgram(String program, float target) {
		this.program = program;
		this.target = target;
		tasks = 0;
	}
	
	public void addTaskDone() {
		tasks += baseTarget/target;
	}
	
	public String getProgram() {
		return program;
	}
	
	public float getTarget() {
		return target;
	}
	
	public float getTasks() {
		return tasks;
	}
}
