package overcast.pgm.module.modules.objective;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;

public class ObjectiveModule extends Module {

	boolean completed = false;
	String id;
	
	//TODO 

	public ObjectiveModule(String id) {
		this.id = id;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public void setCompleted(boolean value) {
		this.completed = value;
	}

	@Override
	public Class<? extends Builder> builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
