package overcast.pgm.builder;

import com.google.common.base.Preconditions;

public class BuilderAbout {

	private BuilderInfo info;

	public BuilderAbout(Class<? extends Builder> builder){
		Preconditions.checkArgument(builder.isAnnotationPresent(BuilderInfo.class), "no @BuilderINFO annotation present!");
		this.info = builder.getAnnotation(BuilderInfo.class);
	}
	
	public BuilderInfo getInfo(){
		return this.info;
	}
}
