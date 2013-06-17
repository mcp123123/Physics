package me.skyrimfan1.explosion.listeners;

import java.util.Random;

import me.skyrimfan1.explosion.Physics;
import me.skyrimfan1.explosion.api.CraftPhysicsAPI;
import me.skyrimfan1.explosion.api.PhysicsAPI;
import me.skyrimfan1.explosion.api.helpers.Queue;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionPhysics implements Listener {
	private Queue queue = new Queue();
	private Physics physics;
	
	public ExplosionPhysics(Physics physics){
		this.physics = physics;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(final EntityExplodeEvent e){
		final PhysicsAPI api = physics.getAPI();
		for (int i = 0; i < e.blockList().size() / 3; i++){
			Block temp = e.blockList().get(i);
			if (temp.getType() != Material.TNT){
				queue.addBlock(temp);
			}
		}
		for (Block b : queue.getRegisteredBlocks()){
			((CraftPhysicsAPI)api).internal(e.getLocation(), b, physics.getConfig().getBoolean("lag_reduction.block_destroy"));
		}
			
		if (physics.getConfig().getBoolean("lag_reduction.block_drop") == false){
			e.setYield(0f);
		}
		
		if (physics.getConfig().getBoolean("explosion_fire") == true){
			Random random = new Random();
			for (Block b : e.blockList()){
				if (random.nextBoolean() == true){
					b.getLocation().getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				}
			}
		}
		
		api.trickleBlock(e.blockList());
		queue.refreshQuene();
	}

}
