package overcast.pgm.module;

import java.util.ArrayList;
import java.util.List;

public enum ModuleStage {

	START(0), LOAD(1);

	public int id;

	ModuleStage(int id) {
		this.id = id;
	}

	public static List<ModuleStage> time() {
		List<ModuleStage> stages = new ArrayList<>();
		stages.add(0, START);
		stages.add(1, LOAD);
		return stages;
	}

	public int getID() {
		return this.id;
	}
}
