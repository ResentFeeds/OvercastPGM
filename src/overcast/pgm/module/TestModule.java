package overcast.pgm.module;

@ModuleInfo(name = "test", desc = "a test", listener = false, builder = TestBuilder.class)
public class TestModule extends Module {

	@Override
	public void unload() {

	}
}
