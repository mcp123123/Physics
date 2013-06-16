package me.skyrimfan1.explosion.api;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.skyrimfan1.explosion.api.events.PhysicsLaunchEvent;
import me.skyrimfan1.explosion.threads.CallEventThread;
import net.minecraft.server.v1_5_R3.WorldServer;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class CraftPhysicsAPI implements PhysicsAPI {
	private Plugin plugin;
	private int future = 0;

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
	
	@Override
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block, Vector vector){
		
		Validate.notNull(loc, "The location is null: method cannot proceed.");
		Validate.notNull(block, "The block is null: method cannot proceed.");
		
		
		PhysicsFallingBlock fBomb = spawnPhysicsFallingBlock(loc, block, vector, 1200);
		
		return fBomb;
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
					if (future <= 1) {
						fBomb.getLocation().getWorld().playEffect(loc, Effect.STEP_SOUND, fBomb.getMaterialID());
						future = future + 1;
					}
				}
			}
				
		}, 0L, 20L);
			
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

			@Override
			public void run() {
				future = 0;
				bombtask.cancel();
				fBomb.remove();
				fBomb.getLocation().getBlock().setType(Material.AIR);
			}
				
		}, 400L);
		
		return (PhysicsFallingBlock) fBomb;
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
		
		final CraftPhysicsFallingBlock fBomb = new CraftPhysicsFallingBlock(sWor, sX, sY, sZ, block.getTypeId(), block.getData(), block);
		fBomb.setDamaging(true);
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
							if (future <= 2) {
								fBomb.getLocation().getWorld().playEffect(loc, Effect.STEP_SOUND, fBomb.getBlock().getTypeId());
								++future;
							}
						}
					}
				}
				
			}, 0L, 20L);
			
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

			@Override
			public void run() {
				bombtask.cancel();
				future = 0;
			}
				
		}, ticks);
		
		return (PhysicsFallingBlock) fBomb;
	}

}
