package commands;

import main.AhoriaNPCs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {

	private AhoriaNPCs main;

	public NPCCommand(AhoriaNPCs an) {
		main = an;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if(cs instanceof Player) {
			Player p = (Player) cs;
			if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
				if(cs.hasPermission("npc.help")) {
					help(cs);
				}
				else {
					permMsg(cs);
				}
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("tool")) {
					if(cs.hasPermission("npc.tool")) {
						p.getInventory().addItem(main.getNPCTool());
						p.updateInventory();
						p.sendMessage("§aNPC-Tool wurde deinem Inventar hinzugefügt.");
					}
					else {
						permMsg(cs);
					}
				}
			}
		}
		else {
			cs.sendMessage("§cDieser Befehl ist nicht für Spieler geeignet.");
		}
		
		return true;
	}
	
	private void permMsg(CommandSender cs) {
		cs.sendMessage("§cDazu hast du kein Recht.");
	}
	
	private void help(CommandSender cs) {
		cs.sendMessage("§cBenutze /npc tool, um NPCs zu spawnen.");
	}
	
}