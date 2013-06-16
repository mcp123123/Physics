package me.skyrimfan1.explosion.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.skyrimfan1.explosion.Physics;
import me.skyrimfan1.explosion.api.CraftPhysicsAPI;
import me.skyrimfan1.explosion.api.PhysicsAPI;
import me.skyrimfan1.explosion.api.helpers.Queue;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class ExplosionPhysics implements Listener {
	private Queue queue = new Queue();
	private Physics physics;
	
	public ExplosionPhysics(Physics physics){
		this.physics = physics;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(final EntityExplodeEvent e){
		final PhysicsAPI api = physics.getAPI();
		for (int i = 0; i < e.blockList().size() / 2; i++){
			Block temp = e.blockList().get(i);
			if (temp.getType() != Material.TNT){
				queue.addBlock(temp);
				// Only add a 1/3 of the blocks to the quene not counting TNT
			}
		}
		for (Block b : queue.getRegisteredBlocks()){
			((CraftPhysicsAPI)api).internal(e.getLocation(), b, physics.getConfig().getBoolean("lag_reduction.block_destroy"));
		}
			
		if (physics.getConfig().getBoolean("lag_reduction.block_drop") == false){
			e.setYield(0f);
		}
		
		if (physics.getConfig().getBoolean("explosion_fire") == true){
			List<Block> temp = new ArrayList<Block>();
			Random random = new Random();
			for (Block b : e.blockList()){
				b.getLocation().getWorld().playEffect(b.getLocation(), Effect.SMOKE, 1);
				if (random.nextBoolean() == true){
					temp.add(b);
				}
			}
			for (Block block : temp){
				block.getLocation().getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			}
		}
		
		trickle(e.blockList());
		queue.refreshQuene();
	}
	
	private void trickle(List<Block> blockList){
		PhysicsAPI api = physics.getAPI();
		if (physics.getConfig().getBoolean("explosion_trickle") == true){
			for (Block block : blockList){
				final Block above = block.getRelative(BlockFace.UP);
				if (above.getType() == Material.AIR || above == null || above.isEmpty()){
					return;
				}
				api.spawnPhysicsFallingBlock(above.getLocation(), above, new Vector(Math.random(), Math.random(), Math.random()), 100L);
				physics.getServer().getScheduler().scheduleSyncDelayedTask(physics, new Runnable(){

					@Override
					public void run() {
						trickle(above);
						above.getWorld().playEffect(above.getLocation(), Effect.STEP_SOUND, above.getTypeId());
						above.setType(Material.AIR);
					}
					
				}, 1L);
			}
		}
	}
	
	private void trickle(Block block){
		PhysicsAPI api = physics.getAPI();
		if (physics.getConfig().getBoolean("explosion_trickle") == true){
				final Block above = block.getRelative(BlockFace.UP);
				if (above.getType() == Material.AIR || above == null){
					return;
				}
				api.spawnPhysicsFallingBlock(above.getLocation(), above, new Vector(Math.random(), Math.random(), Math.random()), 100L);
				physics.getServer().getScheduler().scheduleSyncDelayedTask(physics, new Runnable(){

					@Override
					public void run() {
						trickle(above);
						above.getWorld().playEffect(above.getLocation(), Effect.STEP_SOUND, above.getTypeId());
						above.setType(Material.AIR);
					}
					
				}, 1L);
		}
	}

}
