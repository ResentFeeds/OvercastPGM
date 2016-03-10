package overcast.pgm.module.modules.region;

public enum RFAScope {
	PLAYER_ENTER, PLAYER_LEAVE, BLOCK_PLACE, BLOCK_BREAK, USE, EFFECT, BLOCK_PLACE_AGAINST;

	
	
	public String getNewName(){
		switch(this){
		case BLOCK_BREAK:
			return "block-break";
		case BLOCK_PLACE:
			return "block-place";
		case BLOCK_PLACE_AGAINST:
			return "block-place-against";
		case EFFECT:
			return "effect";
		case PLAYER_ENTER:
			return "enter";
		case PLAYER_LEAVE:
			return "player-leave";
		case USE:
			return "use";  
		} 
		return null;
	}
}
