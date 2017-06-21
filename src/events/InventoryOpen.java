package events;

import java.util.ArrayList;
import java.util.List;

import main.AhoriaNPCs;
import npc.AhoriaNPC;
import npc.EditorPage;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import quest.Quest;
import util.ItemSerialization;

public class InventoryOpen implements Listener {

	private AhoriaNPCs main;

	public InventoryOpen(AhoriaNPCs an) {
		main = an;
	}
	  
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		Player p = (Player)e.getPlayer();
		if(e.getInventory().getHolder() instanceof AhoriaNPC) {
			AhoriaNPC npc = (AhoriaNPC) e.getInventory().getHolder();
			EditorPage ep = EditorPage.getByInventory(e.getInventory());
			if(ep == EditorPage.QUESTS) {
				List<String> quests = new ArrayList<String>();
				for(ItemStack is : e.getInventory().getContents()) {
					if(is != null) {
						Quest q = main.getQuestByBook(is);
						if(q != null) {
							if(!quests.contains(q.getFileName())) {
								quests.add(q.getFileName());
							}
						}
					}
				}
				npc.getConfig().set("quests", quests);
				npc.saveConfig();
			}
		}
		if(main.stocks.containsKey(p.getUniqueId()) && e.getInventory().getTitle().equalsIgnoreCase("Lager")) {
			FileConfiguration cfg = main.getStock(p);
			cfg.set("inventory", ItemSerialization.toBase64(main.stocks.get(p.getUniqueId())));
			main.saveStock(p, cfg);;
			p.playSound(p.getTargetBlock(null, 5).getLocation(), Sound.CHEST_OPEN, 0.2F, 1.0F);
		}
	}
	
}