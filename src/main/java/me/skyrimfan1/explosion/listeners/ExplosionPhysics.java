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
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(final EntityExplodeEvent e){
		final PhysicsAPI api = physics.getAPI();
		for (int i = 1; i <= e.blockList().size() / 3; i++){
			Block temp = e.blockList().get(i);
			if (temp.getType() != Material.TNT && temp.getType() != Material.SNOW && temp.getType() != Material.THIN_GLASS && temp.getType() != Material.LEAVES && temp.getType() != Material.SUGAR_CANE_BLOCK){
					queue.addBlock(temp);
				// Only add a 1/3 of the blocks to the quene not counting TNT and other blocks that shouldn't be flung
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
				if (random.nextBoolean() == true){
					temp.add(b);
				}
			}
			for (Block block : temp){
				block.getLocation().getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			}
			
			for (int i = 1; i <= random.nextInt((int) Math.min(Math.floor(Math.random() + (Math.PI * 3)), e.blockList().size())); i++){
				try {
					Block randomBlock = e.blockList().get(i);
					randomBlock.getWorld().playEffect(randomBlock.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				} catch (Exception ex){
					// Ignore any exception caught when getting the index of the block
				}
			}
		}
		
		queue.refreshQuene();
	}

}
