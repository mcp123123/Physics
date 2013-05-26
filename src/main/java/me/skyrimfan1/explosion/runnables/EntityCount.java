package me.skyrimfan1.explosion.runnables;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import me.skyrimfan1.explosion.Physics;
import me.skyrimfan1.explosion.api.helpers.StringUtil;

public class EntityCount implements Runnable {
	private Physics physics;
	
	public EntityCount(Physics physics){
		this.physics = physics;
	}

	@Override
	public void run() {
		for (World wor : physics.getServer().getWorlds()){
			for ( Entity e : wor.getEntities()){
				if (e instanceof Item) {
					if (wor.getEntities().size() > 3000){
						e.remove();
						physics.getServer().broadcastMessage(StringUtil.getPrefixWithSpace()+ChatColor.AQUA+""+ChatColor.BOLD+"Entity count over 3000!");
						physics.getServer().broadcastMessage(StringUtil.getPrefixWithSpace()+ChatColor.DARK_GREEN+""+ChatColor.BOLD+"Removed all dropped items.");
					}
				}
			}
		}
	}

}
