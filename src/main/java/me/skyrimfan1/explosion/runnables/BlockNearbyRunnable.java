package me.skyrimfan1.explosion.runnables;

import java.util.ArrayList;
import java.util.List;

import me.skyrimfan1.explosion.Physics;

import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

public class BlockNearbyRunnable implements Runnable {
	private Physics physics;
	
	public BlockNearbyRunnable(Physics physics){
		this.physics = physics;
	}
	
	@Override
	public void run() {
		for (Player p : physics.getServer().getOnlinePlayers()){
			List<Entity> entities = p.getNearbyEntities(1.0, 1.0, 1.0);
			List<FallingBlock> blocks = new ArrayList<FallingBlock>();
			for (Entity e : entities){
				if (e instanceof FallingBlock){
					blocks.add((FallingBlock) e);
				}
			}
			if (blocks.isEmpty() == false){
				p.damage(8.0);
			}
			entities.clear();
			blocks.clear();
		}
		return;
	}

}
