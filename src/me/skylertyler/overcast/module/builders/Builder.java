package me.skylertyler.overcast.module.builders;

import me.skylertyler.overcast.module.Module;

public class Builder {


	private Class<? extends Module> module;

	public Builder(Class<? extends > module){
	  this.module = module;
	}
}
