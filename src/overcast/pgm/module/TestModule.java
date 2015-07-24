package overcast.pgm.module;

import overcast.pgm.builder.Builder;

@ModuleInfo(name = "test", desc = "a test", listener = false)
public class TestModule extends Module {

	public String name;
	
	public TestModule(){
		
	}
	
	public TestModule(String name){
		this.name = name;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	
	@Override
	public void unload() {
		//do nothing
	}
	
	
	public Class<? extends Builder> builder(){
		return TestBuilder.class;
	}
}
