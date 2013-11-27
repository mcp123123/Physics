package com.rimofthesky.explosion.api.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Cuboid {
	
	public static List<Block> getCuboid(Location loc, int range){
		List<Block> cuboid = new ArrayList<Block>();
		int minX = loc.getBlockX() - range / 2;
		int minY = loc.getBlockY() - range / 2;
		int minZ = loc.getBlockZ() - range / 2;
		for(int x = minX; x < minX + range; x++) {
		  for(int y = minY; y < minY + range; y++) {
		    for(int z = minZ; z < minZ + range; z++) {
		      Block b = loc.getWorld().getBlockAt(x, y, z);
		      cuboid.add(b);
		    }
		  }
		}
		return cuboid;
	}
}
