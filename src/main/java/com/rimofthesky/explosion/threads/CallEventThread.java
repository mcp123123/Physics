package com.rimofthesky.explosion.threads;

import org.bukkit.Bukkit;

import com.rimofthesky.explosion.api.PhysicsFallingBlock;
import com.rimofthesky.explosion.api.PhysicsMethodType;
import com.rimofthesky.explosion.api.events.PhysicsGroundEvent;


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
				e.printStackTrace();
			}
		}
	}
	
	public void cancel(){
		callEvent = false;
	}
}
