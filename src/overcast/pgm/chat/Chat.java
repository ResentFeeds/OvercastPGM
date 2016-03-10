package overcast.pgm.chat;

import overcast.pgm.util.StringUtils;

public enum Chat { 
	
	CRAP("****"), SHIT("****"), FAGGOT("******"), ASS("***"), NIGGER("******"), BITCH("*****");
	
	
	private String rep;

	Chat(String rep){
		this.rep = rep;
	}
	
	
	
	public String getReplacement(){
		return this.rep;
	}
	
	public static String toReplacement(String[] message){
		String msg = StringUtils.build(message, 0);
		if(message != null){
			for(Chat chat : values()){
				if(msg.contains(chat.name()) || msg.equalsIgnoreCase(chat.name())){
					msg.replace(chat.name(), chat.getReplacement());
				}
			}
		}
		return msg;
	}
}
