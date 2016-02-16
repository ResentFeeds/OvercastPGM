package overcast.pgm.util;

import overcast.pgm.channels.AdminChannel;
import overcast.pgm.channels.ChannelFactory;
import overcast.pgm.channels.GlobalChannel;
import overcast.pgm.channels.TeamChannel;


public class ChannelUtil {


	public static TeamChannel newTeamChannel() {
		return new TeamChannel();
	}

	public static GlobalChannel newGlobalChannel() {
		return new GlobalChannel();
	}

	public static GlobalChannel getGlobalChannel() {
		return (GlobalChannel) ChannelFactory.getChannel("Global");
	}
	
	

	public static TeamChannel getTeamChannel() {
		return (TeamChannel) ChannelFactory.getChannel("Team");
	}

	public static AdminChannel getAdminChannel() {
		return (AdminChannel) ChannelFactory.getChannel("Admin");
	}
	

}
