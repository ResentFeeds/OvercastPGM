package overcast.pgm.util;

import overcast.pgm.server.ServerType;

public class ServerUtils {

	public static ServerType getType(String type) {
		for (ServerType types : ServerType.values()) {
			if (types.name().equalsIgnoreCase(type)) {
				return types;
			}
		}
		return null;
	}
}
