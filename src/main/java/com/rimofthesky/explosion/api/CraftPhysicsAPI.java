package com.rimofthesky.explosion.api;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_6_R3.WorldServer;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.rimofthesky.explosion.Physics;
import com.rimofthesky.explosion.api.events.PhysicsLaunchEvent;
import com.rimofthesky.explosion.threads.CallEventThread;

public class CraftPhysicsAPI implements PhysicsAPI {
	private Plugin plugin;
	private boolean future = false;

	public void startup() {
		Logger.getLogger("Minecraft").log(Level.INFO, "[Physics] API has been intialized.");
		plugin = Bukkit.getPluginManager().getPlugin("Physics");
		
		Logger.getLogger("Minecraft").log(Level.INFO, "[Physics] [API] Created physics events.");
		Logger.getLogger("Minecraft").log(Level.INFO, "[Physics] [API] Created custom falling block.");
	}

	public void shutdown() {
		Logger.getLogger("Minecraft").log(Level.INFO, "[Physics] API has been unhooked.");
	}
	
	public float x(){
		Random r = new Random();
		int random = r.nextInt(2);
		if (random == 0){
			random = 1;
		}
		int nRandom = random - (random * 2); // Convert the number to negative
		return (float) Math.floor(nRandom) + (float) (Math.random() * ((Math.abs(random * 2)) + 1));
	}
	
	public float y(){
		return (float) -0.3 + (float)(Math.random() * ((0.6) + 1));
	}
	
	public float z(){
		Random r = new Random();
		int random = r.nextInt(2);
		if (random == 0){
			random = 1;
		}
		int nRandom = random - (random * 2); // Convert the number to negative
		return (float) Math.floor(nRandom) + (float)(Math.random() * ((Math.abs(random * 2)) + 1));
	}
	
	/*
	 * Don't touch me: I'm an internal plugin method!
	 */
	public PhysicsFallingBlock internal(Location loc, Block block, final boolean b1){
		
		Validate.notNull(loc, "The location is null: method cannot proceed.");
		Validate.notNull(block, "The block is null: method cannot proceed.");
		
		double sX = loc.getBlockX() + 0.5;
        double sY = loc.getBlockY() + 1.5;
        double sZ = loc.getBlockZ() + 0.5;
        WorldServer sWor = ((CraftWorld)loc.getWorld()).getHandle();
		
		@SuppressWarnings("deprecation")
		final CraftPhysicsFallingBlock fBomb = new CraftPhysicsFallingBlock(sWor, sX, sY, sZ, block.getTypeId(), block.getData(), block);
		sWor.addEntity(fBomb);
		
		float x = (float) x(); 
		float y = (float) y();
		float z = (float) z();
		fBomb.setVelocity(new Vector(x, y, z));
		PhysicsLaunchEvent pLE = new PhysicsLaunchEvent(((PhysicsFallingBlock) fBomb), new Vector(x, y, z), PhysicsMethodType.INTERNAL);
		Bukkit.getPluginManager().callEvent(pLE);
		if (pLE.isCancelled()){
			fBomb.remove();
			return null;
		}
		fBomb.setVelocity(pLE.getVector());
		
		final CallEventThread thread = new CallEventThread(fBomb);
		thread.start();
			
		final BukkitTask bombtask = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable(){

			@Override
			public void run() {
				if (fBomb.isOnGround() == true){
					if (b1 == true){
						Block bloc = fBomb.getLocation().getBlock();
						bloc.setType(Material.AIR);
					}
					Location loc = fBomb.getLocation();
					if (future == true) {
						fBomb.getLocation().getWorld().playEffect(loc, Effect.STEP_SOUND, fBomb.getMaterialID());
						future = false;
					}
				}
			}
				
		}, 0L, 20L);
			
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

			@Override
			public void run() {
				future = true;
				bombtask.cancel();
				if (b1 == true){
					fBomb.remove();
					fBomb.getLocation().getBlock().setType(Material.AIR);
				}
			}
				
		}, 600L);
		
		return (PhysicsFallingBlock) fBomb;
	}
	
	
	@Override
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block, Vector vector){
		
		Validate.notNull(loc, "The location is null: method cannot proceed.");
		Validate.notNull(block, "The block is null: method cannot proceed.");
		
		
		PhysicsFallingBlock fBomb = spawnPhysicsFallingBlock(loc, block, vector, 1200);
		
		return fBomb;
	}
	
	@Override
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block){
		
		Validate.notNull(loc, "The location is null: method cannot proceed.");
		Validate.notNull(block, "The block is null: method cannot proceed.");
		
		PhysicsFallingBlock fBomb = spawnPhysicsFallingBlock(loc, block, new Vector(0, 0, 0));
		
		return fBomb;
	}
	
	@Override
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block, Vector vector, long ticks){
		
		Validate.notNull(loc, "The location is null: method cannot proceed.");
		Validate.notNull(block, "The block is null: method cannot proceed.");
		
		double sX = loc.getBlockX() + 0.5;
        double sY = loc.getBlockY() + 1.5;
        double sZ = loc.getBlockZ() + 0.5;
        WorldServer sWor = ((CraftWorld)loc.getWorld()).getHandle();
		
		@SuppressWarnings("deprecation")
		final CraftPhysicsFallingBlock fBomb = new CraftPhysicsFallingBlock(sWor, sX, sY, sZ, block.getTypeId(), block.getData(), block);
		sWor.addEntity(fBomb);
		
		fBomb.setVelocity(vector);
		PhysicsLaunchEvent pLE = new PhysicsLaunchEvent(((PhysicsFallingBlock) fBomb), vector, PhysicsMethodType.EXTERNAL);
		Bukkit.getPluginManager().callEvent(pLE);
		if (pLE.isCancelled()){
			fBomb.remove();
			return null;
		}
		fBomb.setVelocity(pLE.getVector());
		
		final CallEventThread thread = new CallEventThread(fBomb);
		thread.start();
			
		final BukkitTask bombtask = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable(){

			@Override
			public void run() {
					if (fBomb.isOnGround() == true){
						if (fBomb.isLandingEffectOn()){
							Location loc = fBomb.getLocation();
							if (future == true) {
								fBomb.getLocation().getWorld().playEffect(loc, Effect.STEP_SOUND, fBomb.getMaterialID());
								future = false;
							}
						}
					}
				}
				
			}, 0L, 20L);
			
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

			@Override
			public void run() {
				future = true;
				bombtask.cancel();
				if (plugin.getConfig().getBoolean("lag_reduction.block_destroy")){
					fBomb.remove();
					fBomb.getLocation().getBlock().setType(Material.AIR);
				}
			}
				
		}, ticks);
		
		return (PhysicsFallingBlock) fBomb;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void trickleBlock(Block block) {
		final PhysicsAPI api = ((Physics) plugin).getAPI();
		if (((Physics) plugin).getConfig().getBoolean("explosion_trickle") == true){
				final Block above = block.getRelative(BlockFace.UP);
				if (above.getType() == Material.AIR || above == null || above.isEmpty()){
					return;
				}
				above.getWorld().playEffect(above.getLocation(), Effect.STEP_SOUND, above.getTypeId());
				above.setType(Material.AIR);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

					@Override
					public void run() {
						api.spawnPhysicsFallingBlock(above.getLocation(), above, new Vector(Math.random(), Math.random() , Math.random()));
						trickleBlock(above);
					}
					
				}, 2L);
		}
	}

	@Override
	public void trickleBlock(List<Block> blockList) {
		for (Block b : blockList){
			trickleBlock(b);
		}
	}

}
