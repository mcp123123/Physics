package me.skyrimfan1.explosion.listeners;

import me.skyrimfan1.explosion.api.CraftPhysicsFallingBlock;
import me.skyrimfan1.explosion.api.PhysicsFallingBlock;
import me.skyrimfan1.explosion.threads.CallEventThread;

import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftFallingSand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityGroundListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onGround(EntityBlockFormEvent event){
		if (event.isCancelled()) return;
		
		final Entity entity = event.getEntity();
		EntityType type = event.getEntity().getType();
		
		final net.minecraft.server.v1_5_R3.Entity mcEntity = (((CraftEntity) entity).getHandle());
		
		if (type == EntityType.FALLING_BLOCK && mcEntity instanceof CraftPhysicsFallingBlock){
			
			CallEventThread thread = new CallEventThread(((PhysicsFallingBlock) mcEntity));
			thread.start();
			
			entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, ((CraftFallingSand) entity).getBlockId());
		}
					
	}
}
