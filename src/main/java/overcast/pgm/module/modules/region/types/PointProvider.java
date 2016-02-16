package overcast.pgm.module.modules.region.types;

import org.bukkit.util.Vector;
 
import overcast.pgm.module.modules.region.parsers.PointParser;

public class PointProvider {

	private float yaw;
	private float pitch;
	private Vector point;
	private Vector angle;

	public PointProvider(float yaw, float pitch, Vector point, Vector angle) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.point = point;
		this.angle = angle;
	}  
	
	
	
	public PointProvider(PointParser parser){
		this(parser.getYaw(), parser.getPitch(), parser.getPoint(), null);
	}
	
	
	
	public float getYaw(){
		return this.yaw;
	}
	
	
	public float getPitch(){
		return this.pitch;
	}
	
	
	public Vector getPoint(){
		return this.point;
	}
	
	
	public Vector getAngle(){
		return this.angle;
	}
}
