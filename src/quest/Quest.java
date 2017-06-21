package quest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.AhoriaNPCs;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Quest {

	private AhoriaNPCs main;
	private String name;
	private FileConfiguration cfg;

	public Quest(AhoriaNPCs an, String name) {
		main = an;
		this.name = name;
		cfg = YamlConfiguration.loadConfiguration(new File(main.getDataFolder() + "/quests/", name + ".yml"));
	}
	
	public String getFileName() {
		return name;
	}
	
	public FileConfiguration getConfig() {
		return cfg;
	}
	
	public String getDisplayName() {
		return cfg.getString("name").replace("&", "§");
	}
	
	public ItemStack getQuestBook() {
		ItemStack is = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) is.getItemMeta();
        bm.setAuthor(null);
        bm.setDisplayName("§2Quest: §6" + getDisplayName());
        String s = "";
        for(String str: getTasks()) {
        	s = s + QuestProperties.getSubject(str) + ";";
        }
        bm.setPages("§2§lQuest: §6§l" + getDisplayName() + "\n \n" + getDescription() + "\n \n§a" + s.replace(";", "\n"));
        bm.setTitle(getFileName());
        is.setItemMeta(bm);
        return is;
	}
	
	public void saveConfig() {
		try {
			cfg.save(new File(main.getDataFolder() + "/quests/", name + ".yml"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDescription() {
		return cfg.getString("beschreibung").replace(";", "\n").replace("&", "§");
	}
	
	public List<String> getConditions() {
		List<String> list = new ArrayList<String>();
		if(cfg.isSet("bedingungen")) {
			list = cfg.getStringList("bedingungen");
		}
		return list;
	}
	
	public List<String> getTasks() {
		List<String> list = new ArrayList<String>();
		if(cfg.isSet("aufgaben")) {
			list = cfg.getStringList("aufgaben");
		}
		return list;
	}
	
	public List<String> getRewards() {
		List<String> list = new ArrayList<String>();
		if(cfg.isSet("belohnungen")) {
			list = cfg.getStringList("belohnungen");
		}
		return list;
	}
	
}