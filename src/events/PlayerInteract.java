package events;

import main.AhoriaNPCs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

	private AhoriaNPCs main;

	public PlayerInteract(AhoriaNPCs an) {
		main = an;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
			if(e.getItem().isSimilar(main.getNPCTool())) {
				Player p = e.getPlayer();
				if(p.hasPermission("npc.spawn")) {
					main.npcManager.createNPC(e.getClickedBlock().getLocation().add(0, 1, 0), p);
					p.sendMessage("§2NPC erstellt!");
				}
				else {
					p.sendMessage("§cDazu hast du kein Recht.");
				}
			}
		}
	}

}
