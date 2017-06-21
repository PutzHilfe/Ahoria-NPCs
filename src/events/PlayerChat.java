package events;

import main.AhoriaNPCs;
import npc.AhoriaNPC;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

	private AhoriaNPCs main;

	public PlayerChat(AhoriaNPCs an) {
		main = an;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(main.npcRenamer.containsKey(p.getUniqueId())) {
			AhoriaNPC npc = main.npcRenamer.get(p.getUniqueId());
			String name = e.getMessage();
			if(name.length() <= 60) {
				npc.getConfig().set("name", e.getMessage());
				npc.saveConfig();
				npc.getBukkitEntity().setCustomName(e.getMessage().replace("&", "§"));
				main.npcRenamer.remove(p.getUniqueId());
				e.getRecipients().clear();
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.1F, 1F);
				p.sendMessage("§2§lNamensgebung abgeschlossen!");
				if(main.renameTasks.containsKey(p.getUniqueId())) {
					Bukkit.getScheduler().cancelTask(main.renameTasks.get(p.getUniqueId()));
					main.renameTasks.remove(p.getUniqueId());
				}
			}
			else {
				p.sendMessage("§cDer Name für den NPC darf maximal 60 Zeichen lang sein.");
			}
			e.getRecipients().clear();
		}
	}
	
}