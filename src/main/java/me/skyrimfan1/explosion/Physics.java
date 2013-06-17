package me.skyrimfan1.explosion;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.skyrimfan1.explosion.api.CraftPhysicsAPI;
import me.skyrimfan1.explosion.api.PhysicsAPI;
import me.skyrimfan1.explosion.api.PhysicsFallingBlock;
import me.skyrimfan1.explosion.commands.PhysicsGeneralCommand;
import me.skyrimfan1.explosion.listeners.EntityGroundListener;
import me.skyrimfan1.explosion.listeners.ExplosionPhysics;

import org.bukkit.plugin.java.JavaPlugin;

public class Physics extends JavaPlugin {
	private CraftPhysicsAPI api = new CraftPhysicsAPI();
	
	public PhysicsAPI getAPI(){
		return (PhysicsAPI) api;
	}

	@Override
	public void onEnable(){
		logInfo("Processing ...");
		
		api.startup();
		
		registerConfig();
		
		registerCommands();
		
		registerEvents();
		
		logInfo("One pinch of potassium chlorate and KABLOOIE!");
		logInfo("Loading Stage: SUCCESS");
		
		invoke();
	}
	
	@Override
	public void onDisable() {
		logInfo("Closing ...");
		
		api.shutdown();
		
		closeConfig();
		
		secureTasks();
		
		logInfo("Me, a demoman!?");
		logInfo("Shutdown Stage: SUCCESS");
	}
	
	public Logger getLog(){
		return Logger.getLogger("Minecraft");
	}
	
	private void logInfo(String msg){
		getLog().log(Level.INFO, "[Physics] "+msg);
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new ExplosionPhysics(this), this);
		getServer().getPluginManager().registerEvents(new EntityGroundListener(), this);
		logInfo("Primed all events successfully.");
	}
	
	private void registerConfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
		logInfo("A whirlgig of a config has been loaded!");
		logInfo("Explosion Fire: "+Boolean.valueOf(getConfig().getBoolean("explosion_fire")));
		logInfo("Explosion Trickling: "+Boolean.valueOf(getConfig().getBoolean("explosion_trickle")));
		logInfo("[Lag-Combat] Block Destruction: "+Boolean.valueOf(getConfig().getBoolean("lag_reduction.block_destroy")));
		logInfo("[Lag-Combat] Block Drops: "+Boolean.valueOf(getConfig().getBoolean("lag_reduction.block_drop")));
	}

	private void closeConfig(){
		saveConfig();
		logInfo("Config saved successfully!");
	}
	
	private void secureTasks(){
		this.getServer().getScheduler().cancelTasks(this);
		logInfo("[Lag-Combat] Cancelled all tasks.");
	}
	
	private void registerCommands(){
		getCommand("physics").setExecutor(new PhysicsGeneralCommand(this));
		logInfo("Commands successfully registered.");
	}
	
	private void invoke(){
		try {
	        @SuppressWarnings("rawtypes")
	        Class[] args = new Class[3];
	        args[0] = Class.class;
	        args[1] = String.class;
	        args[2] = int.class;
	
	        Method a = net.minecraft.server.v1_5_R3.EntityTypes.class.getDeclaredMethod("a", args);
	        a.setAccessible(true);
	
	        a.invoke(a, PhysicsFallingBlock.class, "FallingSand", 21);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void reloadPlugin(){
		setEnabled(false);
		setEnabled(true);
	}
	
}
