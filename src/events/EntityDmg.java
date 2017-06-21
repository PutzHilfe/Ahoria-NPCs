package events;

import main.AhoriaNPCs;
import npc.AhoriaNPC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDmg implements Listener {

	private AhoriaNPCs main;

	public EntityDmg(AhoriaNPCs ahoriaNPCs) {
		main = ahoriaNPCs;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(main.npcManager.isNPC(e.getEntity().getUniqueId())) {
			AhoriaNPC npc = main.npcs.get(e.getEntity());
			e.setCancelled(npc.isInvulnerable());
		}
	}
	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(main.npcManager.isNPC(e.getEntity().getUniqueId())) {
			AhoriaNPC npc = main.npcs.get(e.getEntity());
			if(e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				if(p.getItemInHand() != null && p.getItemInHand().isSimilar(main.getNPCTool())) {
					if(p.hasPermission("npc.edit")) {
						p.sendMessage("§2NPC-ID: §6" + npc.getId().toString());
					}
				}
			}
		}
	}
	
}