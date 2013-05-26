package me.skyrimfan1.explosion.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;

import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.EntityFallingBlock;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.WorldServer;

public class CraftPhysicsFallingBlock extends EntityFallingBlock implements PhysicsFallingBlock {
	private org.bukkit.block.Block blockus;
    private float brightness = 0.0F;
    private boolean doingDamage = true;
    private int damageAmt = 0;
    private boolean playEffect = true;
 
    @Override
    public Material getMaterial() {
        return Material.getMaterial(this.id);
    }
 
    @Override
    public int getMaterialID() {
        return this.id;
    }
 
    @Override
    public byte getMaterialData() {
        return (byte)this.data;
    }
 
    @Override
    public boolean isDropping() {
        return this.dropItem;
    }
    
    public void setOnFire(int ticks){
    	this.burn(ticks);
    }
    
    public boolean isOnFire(){
    	if (this.fireTicks >= 1){
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void setDropping(boolean flag){
    	this.dropItem = flag;
    }
 
    public void setPassenger(Entity e) {
        this.passenger = (net.minecraft.server.v1_5_R3.Entity) e;
    }
 
    public void setPassenger(Player p) {
        this.passenger = ((CraftPlayer)p).getHandle();
    }
 
    @Deprecated
    public CraftPhysicsFallingBlock(World sW, double d1, double d2, double d3, int m, int da) {
        super(sW, d1, d2, d3, m, da);
        setBrightness(Block.lightEmission[this.id]);
        this.dropItem = false;
        this.c = 1;
    }
    
    public CraftPhysicsFallingBlock(World sW, double d1, double d2, double d3, int m, int da, org.bukkit.block.Block block) {
        super(sW, d1, d2, d3, m, da);
        setBrightness(Block.lightEmission[this.id]);
        this.dropItem = false;
        this.c = 1;
        this.blockus = block;
    }
    
    @Override
    public Vector getVelocity() {
        return new Vector(this.motX, this.motY, this.motZ);
    }
 
    @Override
    public void setVelocity(Vector vel) {
        this.motX = vel.getX();
        this.motY = vel.getY();
        this.motZ = vel.getZ();
        this.velocityChanged = true;
    }

    public CraftWorld getCraftWorld() {
        return ((WorldServer)this.world).getWorld();
    }
 
    @Override
    public boolean teleport(Location location) {
        return teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
 
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        this.world = ((CraftWorld)location.getWorld()).getHandle();
        setLocation(location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());
 
        return true;
    }
 
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
 
    public float getYaw() {
        return this.yaw;
    }
 
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
 
    public float getPitch() {
        return this.pitch;
    }
 
    @Override
    public Location getLocation() {
        return new Location(getCraftWorld(), this.locX, this.locY, this.locZ);
    }
 
    public void setLocation(Location l) {
        this.locX = l.getX();
        this.locY = l.getY();
        this.locZ = l.getZ();
    }
 
    public void remove() {
        die();
    }
 
    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
 
    public float getBrightness() {
        return this.brightness;
    }

    @Override
	public boolean isOnGround() {
		return this.onGround;
	}

    @Deprecated
	@Override
	public int getDamagingAmount() {
		return damageAmt;
	}

	@Deprecated
	@Override
	public void setDamagingAmount(int damage) {
		damageAmt = damage;
		super.a(damage);
	}

	@Override
	public void setDamaging(boolean doDamage) {
		doingDamage = doDamage;
		super.a(doDamage);
	}

	@Override
	public boolean isDamaging() {
		return doingDamage;
	}

	@Override
	public void setLandingEffect(boolean should) {
		this.playEffect = should;
	}
	
	@Override
	public boolean isLandingEffectOn(){
		return playEffect;
	}

	@Override
	public org.bukkit.block.Block getBlock() {
		return this.blockus;
	}

}
