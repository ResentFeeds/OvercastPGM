package overcast.pgm.module.modules.filter;

public enum FilterState {

	ABSTAIN(),
	ALLOW(),
	DENY();
	
	public boolean isAllowed(){
		return this == ALLOW || this == ABSTAIN;
	}
	
	
	public boolean isDenied(){
		return this == DENY;
	}
	
	
	public static FilterState fromBoolean(boolean value){
		 return value ? ALLOW : DENY;
	}
}
