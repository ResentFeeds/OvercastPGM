package overcast.pgm.listener;

import java.util.ArrayList;

public class ListenerList<M> extends ArrayList<M>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	public void addListener(M m){
		this.add(m);
	}
}
