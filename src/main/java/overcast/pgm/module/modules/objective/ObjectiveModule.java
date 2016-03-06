package overcast.pgm.module.modules.objective;

import overcast.pgm.module.Module;
import overcast.pgm.util.Characters;

public abstract class ObjectiveModule extends Module {

	// defaults
	boolean completed = false;

	String id;

	boolean required;

	Characters character;

	public ObjectiveModule(String id, boolean required, Characters character) {
		this.id = id;
		this.required = required;
		this.character = character;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public void setCompleted(boolean value) {
		this.completed = value;
	}

	public void setCompleted(boolean completed, Characters character) throws Exception {
		this.completed = completed;
		if (this.character == character)
			throw new Exception("The character of " + character.getUTF() + " is already in use!");
		this.character = character;
	}

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean value) {
		this.required = value;
	}

	public Characters getChatacter() {
		return this.character;
	}
}
