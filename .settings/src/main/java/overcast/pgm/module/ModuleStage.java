package overcast.pgm.module;

import java.util.ArrayList;
import java.util.List;

public enum ModuleStage {

	 START, NORMAL, LOAD, LATE, LATER;

	public static List<ModuleStage> time() {
		List<ModuleStage> stages = new ArrayList<>();
		stages.add(0, START);
		stages.add(1, NORMAL);
		stages.add(2, LOAD);
		stages.add(3, LATE);
		stages.add(4, LATER);
		return stages;
	}
}
