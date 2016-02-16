package overcast.pgm.module.modules.tntracker;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import overcast.pgm.OvercastPGM;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;

public class TNTTrackerMatchModule extends MatchModule implements Listener{
	
	 private HashMap<String, UUID> tntPlaced = new HashMap<>();

	public TNTTrackerMatchModule(Match match) {
		super(match);
	}

	@Override
	public void load() { 
		this.match.registerEvents(this);
	}

	@Override
	public void unload() {
		 HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
	}
	
	

    public static UUID getWhoPlaced(Entity tnt) {
        if (tnt.getType().equals(EntityType.PRIMED_TNT)) {
            if (tnt.hasMetadata("source")) {
                return (UUID) tnt.getMetadata("source").get(0).value();
            }
        }
        return null;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.TNT) {
            Location location = event.getBlock().getLocation();
            tntPlaced.put(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ(), event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
            Location location = event.getEntity().getLocation();
            if (tntPlaced.containsKey(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ())) {
                UUID player = tntPlaced.get(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
                event.getEntity().setMetadata("source", new FixedMetadataValue(OvercastPGM.getInstance(), player));
                tntPlaced.remove(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
                for (Block block : event.blockList()) {
                    if (block.getType() == Material.TNT && getWhoPlaced(event.getEntity()) != null) {
                        Location location = block.getLocation();
                        tntPlaced.put(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ(), getWhoPlaced(event.getEntity()));
                    }
                }
            }
        }
    }
}
