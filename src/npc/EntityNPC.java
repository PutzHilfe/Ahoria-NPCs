package npc;

import net.minecraft.server.v1_7_R4.EntityVillager;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityNPC extends EntityVillager {
	   
    public EntityNPC(World world) {
        super(world);
    }
   
    @Override
    public String t(){
        return "";
    }
 
   
    @Override
    public void move(double d0, double d1, double d2) {
        return;
    }
 
    @Override
    public void g(double x, double y, double z) {
        super.g(0, 0, 0);
    }
 
    public static EntityNPC spawn(Location location) {
        World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
        final EntityNPC customEntity = new EntityNPC(mcWorld);
        customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        ((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
        mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
        return customEntity;
    }
 
}