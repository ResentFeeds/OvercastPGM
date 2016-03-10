package overcast.pgm.channels;

import org.bukkit.permissions.Permission;

import overcast.pgm.player.OvercastPlayer;

public abstract class PrivateChannel extends Channel {

	Permission permission;

	public PrivateChannel(String name, Permission permission) {
		super(name);
		this.permission = permission;
	}

	public Permission getPermssion() {
		return this.permission;
	}

	public boolean check(OvercastPlayer overcast) {
		if (overcast != null) {
			if (overcast.hasPermssion(this.permission) || overcast.isOperator()) {
				return true;
			}
		}

		return false;
	}
}
