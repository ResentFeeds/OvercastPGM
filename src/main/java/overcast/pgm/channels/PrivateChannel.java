package overcast.pgm.channels;

import org.bukkit.permissions.Permission;


public abstract class PrivateChannel extends Channel{

	Permission permission;

	public PrivateChannel(String name, Permission permission) {
		super(name);
		this.permission = permission;
	}
	
	
	public Permission getPermssion(){
		return this.permission;
	}
}
