package overcast.pgm.module;

public abstract class TaskedModule extends Module implements Runnable{

	boolean cancelled = false;
	
	@Override
	public abstract void run(); 
	
	public abstract void create();
	
	public abstract String status(int amount);
	 
}
