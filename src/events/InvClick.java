package events;

import java.util.Arrays;
import java.util.List;

import main.AhoriaNPCs;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import npc.AhoriaNPC;
import npc.EditorPage;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import util.ItemSerialization;

public class InvClick implements Listener {

	public AhoriaNPCs main;

	public InvClick(AhoriaNPCs an) {
		main = an;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		final Player p = (Player) e.getView().getPlayer();
		if(e.getInventory().getHolder() instanceof AhoriaNPC) {
			e.setCancelled(true);
			e.setResult(Result.DENY);
			boolean clearCursor = true;
			AhoriaNPC npc = (AhoriaNPC) e.getInventory().getHolder();
			ItemStack c = e.getCurrentItem();
			EditorPage ep = EditorPage.getByInventory(e.getInventory());
			if(c != null) {
				if(ep == EditorPage.MAIN) {
					if(isEqual(c, "§cLöschen")) {
						sound(p, Sound.CLICK);
						p.openInventory(npc.getEditor(EditorPage.DELETE));
					}
					else if(isEqual(c, "§7Umbenennen")) {
						if(!main.npcRenamer.containsKey(p.getUniqueId())) {
							p.closeInventory();
							sound(p, Sound.ORB_PICKUP);
							p.sendMessage("§9§lBitte gib einen Namen für den NPC in den Chat ein.");
							main.npcRenamer.put(p.getUniqueId(), npc);
							main.renameTasks.put(p.getUniqueId(), Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {

								@Override
								public void run() {
									if(main.npcRenamer.containsKey(p.getUniqueId())) {
										p.sendMessage("§c§lDie Zeit für die Namensgebung des NPCs ist abgelaufen.");
										main.npcRenamer.remove(p.getUniqueId());
									}
									if(main.renameTasks.containsKey(p.getUniqueId())) {
										Bukkit.getScheduler().cancelTask(main.renameTasks.get(p.getUniqueId()));
										main.renameTasks.remove(p.getUniqueId());
									}
								}
								
							}, 300L));
						}
						else {
							sound(p, Sound.IRONGOLEM_HIT);
						}
					}
					else if(isEqual(c, "§2Aussehen")) {
						p.openInventory(npc.getEditor(EditorPage.LOOK));
						sound(p, Sound.CLICK);
					}
					else if(isEqual(c, "§7UUID anzeigen")) {
						sound(p, Sound.ORB_PICKUP);
						p.closeInventory();
						p.sendMessage("§2NPC-ID: §6" + npc.getId().toString());
						p.sendMessage("§2Konfigurationsdatei befindet sich hier: §6/plugins/AhoriaNPCs/npcs/" + npc.getId().toString().charAt(0) + "/" + npc.getId().toString() + ".yml");
					}
					else if(isEqual(c, "§2Lager")) {
						sound(p, Sound.CLICK);
						p.openInventory(npc.getEditor(EditorPage.STOCK));
					}
					else if(isEqual(c, "§2Shop")) {
						sound(p, Sound.CLICK);
						p.openInventory(npc.getEditor(EditorPage.SHOP));
					}
					else if(isEqual(c, "§2Quests")) {
						sound(p, Sound.CLICK);
						p.openInventory(npc.getEditor(EditorPage.QUESTS));
					}
				}
				else if(ep == EditorPage.LOOK) {
					if(isEqual(c, "§2Namen anzeigen")) {
						if(npc.getBukkitEntity().getCustomName() != null) {
							npc.getBukkitEntity().setCustomNameVisible(true);
							sound(p, Sound.ORB_PICKUP);
							p.openInventory(npc.getEditor(EditorPage.MAIN));
						}
					}
					else if(isEqual(c, "§cNamen ausblenden")) {
						if(npc.getBukkitEntity().getCustomName() != null) {
							npc.getBukkitEntity().setCustomNameVisible(false);
							sound(p, Sound.CLICK);
							p.openInventory(npc.getEditor(EditorPage.MAIN));
						}
					}
					else if(e.getSlot() <= 4 && e.getSlotType() != SlotType.QUICKBAR) {
						p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 0.05F, 1F);
						Villager v = npc.getBukkitEntity();
						List<Profession> pr = Arrays.asList(Profession.values());
						v.setProfession(pr.get(e.getSlot()));
					}
				}
				else if(ep == EditorPage.DELETE) {
					if(isEqual(c, "§2Ja")) {
						npc.deleteConfig();
						npc.getBukkitEntity().remove();
						p.closeInventory();
						sound(p, Sound.LEVEL_UP);
						p.sendMessage("§aNPC wurde erfolgreich gelöscht!");
					}
					if(isEqual(c, "§cNein")) {
						p.openInventory(npc.getEditor(EditorPage.MAIN));
						sound(p, Sound.CLICK);
					}
				}
				else if(ep == EditorPage.STOCK) {
					if(isEqual(c, "§2Aktivieren")) {
						boolean enabled = false;
						if(npc.getConfig().isSet("stock.enabled")) {
							enabled = npc.getConfig().getBoolean("stock.enabled");
						}
						if(enabled) {
							sound(p, Sound.ORB_PICKUP);
							p.openInventory(npc.getEditor(EditorPage.MAIN));
						}
						else {
							npc.getConfig().set("stock.enabled", true);
							npc.saveConfig();
							sound(p, Sound.LEVEL_UP);
							p.closeInventory();
							p.sendMessage("§2NPC-Lager wurde aktiviert.");
						}
					}
					else if(isEqual(c, "§cDeaktivieren")) {
						boolean enabled = false;
						if(npc.getConfig().isSet("stock.enabled")) {
							enabled = npc.getConfig().getBoolean("stock.enabled");
						}
						if(!enabled) {
							sound(p, Sound.ORB_PICKUP);
							p.openInventory(npc.getEditor(EditorPage.MAIN));
						}
						else {
							npc.getConfig().set("stock.enabled", false);
							npc.saveConfig();
							sound(p, Sound.LEVEL_UP);
							p.closeInventory();
							p.sendMessage("§2NPC-Lager wurde deaktiviert.");
						}
					}
				}
				else if(ep == EditorPage.SHOP) {
					if(isEqual(c, "§2Aktivieren")) {
						boolean enabled = false;
						p.setItemOnCursor(null);
						if(npc.getConfig().isSet("shop.enabled")) {
							enabled = npc.getConfig().getBoolean("shop.enabled");
						}
						if(enabled) {
							sound(p, Sound.ORB_PICKUP);
							p.openInventory(npc.getEditor(EditorPage.MAIN));
						}
						else {
							npc.getConfig().set("shop.enabled", true);
							npc.saveConfig();
							sound(p, Sound.LEVEL_UP);
							p.closeInventory();
							p.sendMessage("§2NPC-Shop wurde aktiviert.");
						}
					}
					else if(isEqual(c, "§cDeaktivieren")) {
						boolean enabled = false;
						p.setItemOnCursor(null);
						if(npc.getConfig().isSet("shop.enabled")) {
							enabled = npc.getConfig().getBoolean("shop.enabled");
						}
						if(!enabled) {
							sound(p, Sound.ORB_PICKUP);
							p.openInventory(npc.getEditor(EditorPage.MAIN));
						}
						else {
							npc.getConfig().set("shop.enabled", false);
							npc.saveConfig();
							sound(p, Sound.LEVEL_UP);
							p.closeInventory();
							p.sendMessage("§2NPC-Shop wurde deaktiviert.");
						}
					}
					else {
						clearCursor = false;
						e.setResult(Result.ALLOW);
						e.setCancelled(false);
						if(e.getSlotType() == SlotType.CONTAINER) {
							npc.getConfig().set("shop.inventory", ItemSerialization.toBase64(e.getInventory()));
							npc.saveConfig();
						}
					}
				}
				else if(ep == EditorPage.QUESTS) {
					clearCursor = false;
					e.setResult(Result.ALLOW);
					e.setCancelled(false);
				}
				else {
					if(isEqual(c, "§2Quests")) {
						p.openInventory(main.stocks.get(p.getUniqueId()));
					}
					else if(isEqual(c, "§2Shop")) {
						p.closeInventory();
						EntityPlayer enp = ((CraftPlayer)p).getHandle();
						npc.getEntityVillager().a_(enp);
						enp.openTrade(npc.getEntityVillager(), npc.getName());
						sound(p, Sound.CHEST_OPEN);
					}
					else if(isEqual(c, "§2Lager")) {
						p.openInventory(main.stocks.get(p.getUniqueId()));
					}
					else if(isEqual(c, "§7Schließen")) {
						p.closeInventory();
					}
				}
				if(clearCursor) {
					p.setItemOnCursor(null);
				}
			}
		}
		else if(main.stocks.containsKey(p) && e.getInventory().getTitle().equals("Lager")) {
			if(e.getSlotType() == SlotType.CONTAINER && (e.getCurrentItem() != null || e.getView().getCursor() != null)) {
				FileConfiguration cfg = main.getStock(p);
				cfg = main.getStock(p);
				cfg.set("inventory", ItemSerialization.toBase64(e.getInventory()));
				main.saveStock(p, cfg);
			}
		}
	}
	
	public boolean isEqual(ItemStack is, String name) {
		if(is.hasItemMeta()) {
			if(is.getItemMeta().getDisplayName() != null) {
				if(is.getItemMeta().getDisplayName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void sound(Player p, Sound s) {
		p.playSound(p.getLocation(), s, 0.1F, 1F);
	}
	
}