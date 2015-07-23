package overcast.pgm.module;


import org.jdom2.Document;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;

@BuilderInfo(documentable = false, stage = ModuleStage.LOAD)
public class TestBuilder extends Builder {

	@Override
	public Module build(Document doc) {
		return new TestModule();
	}

}
