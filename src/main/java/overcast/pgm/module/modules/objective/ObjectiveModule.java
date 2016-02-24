package overcast.pgm.module.modules.objective;

import overcast.pgm.module.Module;

public abstract class ObjectiveModule extends Module {

	boolean completed = false;
	
	String id;
	
	boolean required = true;
	
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

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean value) {
		this.required = value;
	}
}
