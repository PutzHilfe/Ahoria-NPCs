package events;

import main.AhoriaNPCs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	private AhoriaNPCs main;

	public PlayerJoin(AhoriaNPCs ahoriaNPCs) {
		main = ahoriaNPCs;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		main.stocks.put(p.getUniqueId(), main.getStockInventory(p));
	}

}
