package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.BiomeMeta;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityTypes;
import net.minecraft.server.v1_7_R4.EntityVillager;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import npc.AhoriaNPC;
import npc.EntityNPC;
import npc.NPCManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import quest.Quest;
import quest.QuestStatus;
import util.ItemSerialization;

import commands.NPCCommand;
import commands.QuestCommand;

import events.EntityDmg;
import events.InvClick;
import events.InventoryClose;
import events.InventoryOpen;
import events.PlayerChat;
import events.PlayerInteract;
import events.PlayerInteractEntity;
import events.PlayerJoin;

public class AhoriaNPCs extends JavaPlugin {

	public NPCManager npcManager;
	public HashMap<UUID, AhoriaNPC> npcRenamer = new HashMap<UUID, AhoriaNPC>();
	public HashMap<UUID, Integer> renameTasks = new HashMap<UUID, Integer>();
	public HashMap<UUID, Inventory> stocks = new HashMap<UUID, Inventory>();
	public CopyOnWriteArrayList<Quest> quests = new CopyOnWriteArrayList<Quest>();
	public ConcurrentHashMap<Entity, AhoriaNPC> npcs = new ConcurrentHashMap<Entity, AhoriaNPC>();
	
	@Override
	public void onEnable() {
		init();
		registerEntity("NPC", 120, EntityVillager.class, EntityNPC.class);
		npcManager = new NPCManager(this);
		getCommand("npc").setExecutor(new NPCCommand(this));
		getCommand("quest").setExecutor(new QuestCommand(this));
		registerEvents();
		initStocks();
		for(String s : getQuests()) {
			quests.add(new Quest(this, s));
		}
		for(File f : getNPCs()) {
			Entity ent = getEntityByUnique(UUID.fromString(f.getName().replace(".yml", "")));
			if(ent == null || !ent.isValid()) {
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
				World w = Bukkit.getWorld(cfg.getString("spawn.world"));
				EntityNPC npc = EntityNPC.spawn(cfg.getVector("spawn.coords").toLocation(w));
				npc.uniqueID = UUID.fromString(f.getName().replace(".yml", ""));
				ent = npc.getBukkitEntity();
			}
			AhoriaNPC npc = new AhoriaNPC(this, ent);
			ent.teleport(npc.getSpawn());
			npcs.put(ent, npc);
		}
		System.out.println("[AhoriaNPCs] Plugin v." + getDescription().getVersion() + " enabled!");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[AhoriaNPCs] Plugin v." + getDescription().getVersion() + " disabled!");
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEntity(this), this);
		Bukkit.getPluginManager().registerEvents(new InvClick(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityDmg(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerChat(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryOpen(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClose(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
	}
	
	@SuppressWarnings("deprecation")
	public void initStocks() {
		for(Player op : Bukkit.getOnlinePlayers()) {
			stocks.put(op.getUniqueId(), getStockInventory(op));
		}
	}
	
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
		try {
			
			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}
			
			if (dataMaps.get(2).containsKey(id)) {
				dataMaps.get(0).remove(name);
				dataMaps.get(2).remove(id);
			}
	            
			Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			method.setAccessible(true);
			method.invoke(null, customClass, name, id);
	 
			for (Field f : BiomeBase.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName())) {
					if (f.get(null) != null) {
						
						for (Field list : BiomeBase.class.getDeclaredFields()) {
							if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
								list.setAccessible(true);
								@SuppressWarnings("unchecked")
								List<BiomeMeta> metaList = (List<BiomeMeta>) list.get(f.get(null));
								
								for (BiomeMeta meta : metaList) {
									Field clazz = BiomeMeta.class.getDeclaredFields()[0];
									if (clazz.get(meta).equals(nmsClass)) {
										clazz.set(meta, customClass);
									}
								}
							}
						}
	 
					}
				}
			}
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Plugin getHeroes() {
		return Bukkit.getPluginManager().getPlugin("Heroes");
	}
	
	public Entity getEntityByUnique(UUID id) {
		for(World w : Bukkit.getWorlds()) {
			for(Entity ent : w.getEntitiesByClass(Villager.class)) {
				if(ent.getUniqueId().equals(id)) {
					return ent;
				}
			}
		}
		return null;
	}
	
	public List<File> getNPCs() {
		List<File> files = new ArrayList<File>();
		File f = new File(getDataFolder() + "/npcs/");
		if(f.exists()) {
			for(String folder : f.list()) {
				File fl = new File(getDataFolder() + "/npcs/" + folder + "/");
				if(fl.isDirectory()) {
					for(File fin : fl.listFiles()) {
						files.add(fin);
					}
				}
			}
		}
		return files;
	}
	
	public AhoriaRaces getAhoriaRaces() {
		return (AhoriaRaces) Bukkit.getPluginManager().getPlugin("AhoriaRaces");
	}
	
	public String repStr(String s) {
		s = s.replace("%A", "Ä").replace("%U", "Ü").replace("%O", "Ö").replace("%a", "ä").replace("%u", "ü").replace("%o", "ö").replace("&", "§");
		return s;
	}
	
	public Inventory getStockInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, getStockSize(p), "Lager");
		FileConfiguration cfg = getStock(p);
		saveStock(p, cfg);
		if(cfg != null && cfg.isSet("inventory")) {
			try {
				inv.setContents(ItemSerialization.fromBase64(cfg.getString("inventory"), "stock").getContents());
			} catch (IllegalArgumentException | IOException e) {
				e.printStackTrace();
			}
		}
		return inv;
	}
	
	public List<String> getQuests() {
		List<String> quests = new ArrayList<String>();
		File f = new File(getDataFolder() + "/quests/");
		if(!f.exists()) {
			f.mkdir();
		}
		for(File q : f.listFiles()) {
			if(q.isFile() && q.getName().contains(".yml")) {
				quests.add(q.getName().replace(".yml", ""));
			}
		}
		return quests;
	}
	
	public Quest getQuestByName(String name) {
		for(int i = 0; i < quests.size(); i++) {
			if(quests.get(i).getFileName().replace(".yml", "").equalsIgnoreCase(name)) {
				return quests.get(i);
			}
		}
		return null;
	}
	
	public int getStockSize(Player p) {
		int size = 27;
		Plugin heroes = getHeroes();
		String cl = null;
		if(getHeroes() != null) {
			cl = YamlConfiguration.loadConfiguration(new File(heroes.getDataFolder() + "/players/", p.getUniqueId().toString() + ".yml")).getString("class");
		}
		if(cl != null && getConfig().getStringList("stocks.double chest requirements").contains(cl)) {
			size = 54;
		}
		return size;
	}
	
	public FileConfiguration getStock(Player p) {
		File f = new File(getDataFolder() + "/stocks/" + p.getUniqueId().toString().charAt(0) + "/", p.getUniqueId().toString() + ".yml");
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public void saveStock(Player p, FileConfiguration cfg) {
		File f = new File(getDataFolder() + "/stocks/" + p.getUniqueId().toString().charAt(0) + "/", p.getUniqueId().toString() + ".yml");
		try {
			cfg.save(f);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Quest getQuestByBook(ItemStack is) {
		for(Quest q : quests) {
			if(q.getQuestBook().isSimilar(is)) {
				return q;
			}
		}
		return null;
	}
	
	public List<Quest> getQuests(Player p, QuestStatus qs) {
		List<Quest> quests = new ArrayList<Quest>();
		FileConfiguration cfg = getPlayer(p);
		if(qs != QuestStatus.AVAILABLE && cfg.isSet("quests." + qs.getPath())) {
			List<String> list = cfg.getStringList("quests." + qs.getPath());
			for(String s : list) {
				quests.add(getQuestByName(s));
			}
		}
		else if(qs == QuestStatus.AVAILABLE) {
			//
		}
		return quests;
	}
	
	public FileConfiguration getPlayer(Player p) {
		return getPlayer(p.getUniqueId());
	}
	
	@SuppressWarnings("deprecation")
	public FileConfiguration getPlayer(UUID id) {
		File f = new File(getDataFolder() + "/players/" + id.toString().charAt(0) + "/", id.toString() + ".yml");
		if(f.exists()) {
			return YamlConfiguration.loadConfiguration(f);
		}
		else {
			return YamlConfiguration.loadConfiguration(getResource("player.yml"));
		}
	}
	
	public void savePlayer(Player p, FileConfiguration cfg) {
		savePlayer(p.getUniqueId(), cfg);
	}
	
	public void savePlayer(UUID id, FileConfiguration cfg) {
		File f = new File(getDataFolder() + "/players/" + id.toString().charAt(0) + "/", id.toString() + ".yml");
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ItemStack getNPCTool() {
		ItemStack is = new ItemStack(Material.STICK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getConfig().getString("npc-tool").replace("&", "§"));
		im.setLore(Arrays.asList("Rechtsklick auf einen Block, um NPCs zu spawnen;Linksklick auf NPC, um UUID anzuzeigen;Schleichen + Rechtsklick auf NPC, um ihn zu editieren".split(";")));
		is.setItemMeta(im);
		return is;
	}
	
	private void init() {
		saveDefaultConfig();
		new File(getDataFolder() + "/quests/").mkdir();
		File f = new File(getDataFolder() + "/quests/", "Quest Tutorial.txt");
		if(!f.exists()) {
			try {
				f.createNewFile();
				OutputStream os = new FileOutputStream(f);
				IOUtils.copy(getResource("Quest Tutorial.txt"), os);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}