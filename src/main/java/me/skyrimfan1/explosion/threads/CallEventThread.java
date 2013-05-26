package me.skyrimfan1.explosion.threads;

import org.bukkit.Bukkit;

import me.skyrimfan1.explosion.api.PhysicsFallingBlock;
import me.skyrimfan1.explosion.api.PhysicsMethodType;
import me.skyrimfan1.explosion.api.events.PhysicsGroundEvent;

public class CallEventThread extends Thread {
	private PhysicsFallingBlock fblock;
	private volatile boolean callEvent = true;
	
	public CallEventThread(PhysicsFallingBlock block){
		fblock = block;
	}
	
	@Override
	public void run(){
		if (callEvent == true){
			PhysicsGroundEvent fall = new PhysicsGroundEvent(fblock, fblock.getBlock(), fblock.getLocation(), PhysicsMethodType.EXTERNAL);
			Bukkit.getPluginManager().callEvent(fall);
			cancel();
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				// Ignore it
			}
		}
	}
	
	public void cancel(){
		callEvent = false;
	}
}
