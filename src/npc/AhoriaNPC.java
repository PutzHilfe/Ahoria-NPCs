package npc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import main.AhoriaNPCs;
import net.minecraft.server.v1_7_R4.EntityVillager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import quest.Quest;
import util.ItemSerialization;
import util.ItemUtil;

public class AhoriaNPC implements InventoryHolder {

	private AhoriaNPCs main;
	private FileConfiguration cfg;
	private UUID id;
	private Villager tempEnt;
	
	public AhoriaNPC(AhoriaNPCs an, Entity ent) {
		main = an;
		if(main.npcManager.isNPC(ent.getUniqueId())) {
			cfg = main.npcManager.getNPCConfig(ent.getUniqueId());
			id = ent.getUniqueId();
		}
	}
	
	public UUID getId() {
		return id;
	}
	
	public Villager getBukkitEntity() {
		if(tempEnt != null) {
			return tempEnt;
		}
		else {
			for(Entity ent : getWorld().getEntitiesByClass(Villager.class)) {
				if(ent.getUniqueId().equals(getId())) {
					tempEnt = (Villager)ent;
					return (Villager)ent;
				}
			}
		}
		return null;
	}
	
	public EntityVillager getEntityVillager() {
		Entity ent = getBukkitEntity();
		if(ent!= null) {
			return ((CraftVillager)ent).getHandle();
		}
		return null;
	}
	
	public World getWorld() {
		return Bukkit.getWorld(cfg.getString("spawn.world"));
	}
	
	public Location getSpawn() {
		return cfg.getVector("spawn.coords").toLocation(getWorld());
	}
	
	public boolean isInvulnerable() {
		if(getConfig().isSet("behaviour.invulnerable") && getConfig().getBoolean("behaviour.invulnerable")) {
			return true;
		}
		return false;
	}
	
	public boolean isTrader() {
		if(getConfig().isSet("shop.enabled") && getConfig().getBoolean("shop.enabled")) {
			return true;
		}
		return false;
	}
	
	public boolean isStock() {
		if(getConfig().isSet("stock.enabled") && getConfig().getBoolean("stock.enabled")) {
			return true;
		}
		return false;
	}
	
	public List<String> getIdleMessages() {
		List<String> list = new ArrayList<String>();
		if(getConfig().isSet("idle messages")) {
			list = getConfig().getStringList("idle messages");
		}
		return list;
	}
	
	public String getChatName() {
		String name = getName();
		if(name.length() >= 16) {
			name = name.substring(0, 15);
		}
		return name;
	}
	
	public String getIdleMessage(Player p) {
		List<String> idle = getIdleMessages();
		if(!idle.isEmpty()) {
			Random r = new Random();
			String msg = main.repStr(idle.get(r.nextInt(idle.size()-1)));
			if(p != null) {
				msg = msg.replace("%p", p.getName());
			}
			return "ß2[ß6" + getChatName() + "ß2]ßf: " + msg;
		}
		return null;
	}
	
	public String getName() {
		String name = getBukkitEntity().getCustomName();
		if(getConfig().isSet("name")) {
			name = getConfig().getString("name").replace("&", "ß");
			if(getBukkitEntity().getCustomName() == null || !getBukkitEntity().getCustomName().equals(name)) {
				getBukkitEntity().setCustomName(name);
			}
		}
		if(name == null) {
			name = "Unbekannter";
		}
		else if(name.length() > 18) {
			name = name.substring(0, 15) + "..";
		}
		return name;
	}
	
	public Inventory getEditor(EditorPage page) {
		String name = getName();
		Inventory inv = Bukkit.createInventory(this, page.getSize(), page.getName() + ": " + name);
		inv.setContents(page.getItems());
		if(page == EditorPage.SHOP && getConfig().isSet("shop.inventory")) {
			try {
				inv.setContents(ItemSerialization.fromBase64(getConfig().getString("shop.inventory"), "shop").getContents());
			} catch (IllegalArgumentException | IOException e) {
				e.printStackTrace();
			}
		}
		else if(page == EditorPage.QUESTS && getConfig().isSet("quests")) {
			List<Quest> quests = getQuests();
			for(Quest q : quests) {
				inv.addItem(q.getQuestBook());
			}
		}
		return inv;
	}
	
	public List<Quest> getQuests() {
		List<Quest> list = new ArrayList<Quest>();
		if(cfg.isSet("quests")) {
			List<String> str = cfg.getStringList("quests");
			for(String s : str) {
				Quest q = main.getQuestByName(s);
				if(main.quests.contains(q)) {
					list.add(q);
				}
			}
		}
		return list;
	}
	
	public FileConfiguration getConfig() {
		return cfg;
	}
	
	public boolean saveConfig() {
		return main.npcManager.saveNPCConfig(id, cfg);
	}
	
	public boolean deleteConfig() {
		if(main.npcs.containsKey(getBukkitEntity())) {
			main.npcs.remove(getBukkitEntity());
		}
		return main.npcManager.deleteNPCConfig(id);
	}

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 9, getName());
		inv.setItem(0, ItemUtil.setName(new ItemStack(Material.WRITTEN_BOOK), "ß2Quests"));
		if(isTrader()) {
			inv.addItem(ItemUtil.setName(new ItemStack(Material.DIAMOND), "ß2Shop"));
		}
		if(isStock()) {
			inv.addItem(ItemUtil.setName(new ItemStack(Material.CHEST), "ß2Lager"));
		}
		inv.setItem(8, ItemUtil.setName(new ItemStack(Material.WOOL, 1, (short)14), "ß7Schlieﬂen"));
		return inv;
	}
	
	public void playSound(Sound s) {
		getWorld().playSound(getBukkitEntity().getLocation(), s, 0.2F, 1F);
	}
	
}