package npc;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import main.AhoriaNPCs;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class NPCManager {

	private AhoriaNPCs main;

	public NPCManager(AhoriaNPCs an) {
		main = an;
	}
	
	public boolean isNPC(UUID id) {
		File f = new File(main.getDataFolder() + "/npcs/" + id.toString().charAt(0) + "/", id.toString() + ".yml");
		return f.exists();
	}
	
	public FileConfiguration getNPCConfig(UUID id) {
		if(isNPC(id)) {
			File f = new File(main.getDataFolder() + "/npcs/" + id.toString().charAt(0) + "/", id.toString() + ".yml");
			if(!f.exists()) {
				saveNPCConfig(id, YamlConfiguration.loadConfiguration(f));
			}
			return YamlConfiguration.loadConfiguration(f);
		}
		return null;
	}
	
	public boolean saveNPCConfig(UUID id, FileConfiguration cfg) {
		File f = new File(main.getDataFolder() + "/npcs/" + id.toString().charAt(0) + "/", id.toString() + ".yml");
		try {
			cfg.save(f);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteNPCConfig(UUID id) {
		File f = new File(main.getDataFolder() + "/npcs/" + id.toString().charAt(0) + "/", id.toString() + ".yml");
		if(f.exists()) {
			f.delete();
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean createNPC(Location loc, Player creator) {
		try {
			EntityNPC npc = spawnNPC(loc);
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(main.getResource("npc.yml"));
			cfg.set("creator", creator.getUniqueId().toString());
			cfg.set("spawn.world", loc.getWorld().getName());
			cfg.set("spawn.coords", loc.toVector());
			cfg.set("behaviour.move", false);
			cfg.set("behaviour.invulnerable", true);
			saveNPCConfig(npc.getUniqueID(), cfg);
			main.npcs.put(npc.getBukkitEntity(), new AhoriaNPC(main, npc.getBukkitEntity()));
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public EntityNPC spawnNPC(Location loc) {
		EntityNPC npc = EntityNPC.spawn(loc);
		return npc;
	}
	
}