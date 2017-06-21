package events;

import java.util.HashMap;

import main.AhoriaNPCs;
import npc.AhoriaNPC;
import npc.EditorPage;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {

	private AhoriaNPCs main;
	private HashMap<Player, Integer> openTasks = new HashMap<Player, Integer>();

	public PlayerInteractEntity(AhoriaNPCs an) {
		main = an;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if(main.npcManager.isNPC(e.getRightClicked().getUniqueId())) {
			final AhoriaNPC npc = main.npcs.get(e.getRightClicked());
			if(p.isSneaking() && p.getItemInHand() != null && p.getItemInHand().isSimilar(main.getNPCTool())) {
				if(p.hasPermission("npc.edit")) {
					p.openInventory(npc.getEditor(EditorPage.MAIN));
					e.setCancelled(true);
				}
				else {
					p.sendMessage("§cDazu hast du kein Recht.");
				}
			}
			else if(!p.hasMetadata("shop")) {
				String idle = npc.getIdleMessage(p);
				npc.playSound(Sound.VILLAGER_YES);
				if(npc.isStock() || npc.isTrader()) {
					if(!openTasks.containsKey(p)) {
						if(idle != null) {
							p.sendMessage(idle);
						}
						openTasks.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {

							@Override
							public void run() {
								p.openInventory(npc.getInventory());
								p.playSound(p.getLocation(), Sound.CHEST_OPEN, 0.1F, 1F);
								openTasks.remove(p);
							}
							
						}, 20L));
					}
				}
				else {
					if(idle != null) {
						p.sendMessage(idle);
					}
				}
				e.setCancelled(true);
			}
		}
	}

}
