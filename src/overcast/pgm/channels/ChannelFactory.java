package overcast.pgm.channels;

import java.util.ArrayList;
import java.util.List;

import overcast.pgm.player.OvercastPlayer;

public class ChannelFactory {

	static List<Channel> channels;

	public ChannelFactory() {
		channels = new ArrayList<>();
		channels.add(new GlobalChannel());
		channels.add(new TeamChannel());
		channels.add(new AdminChannel());
	}

	public static List<Channel> getChannels() {
		return channels;
	}

	public static Channel getChannel(OvercastPlayer p) {
		for (Channel channel : getChannels()) {
			if (channel.getMembers().contains(p)) {
				return channel;
			}
		}
		return null;
	}

	public static Channel getChannel(String string) {
		for (Channel channel : getChannels()) {
			if (channel.getName().equalsIgnoreCase(string)) {
				return channel;
			}
		}
		return null;
	}
}
