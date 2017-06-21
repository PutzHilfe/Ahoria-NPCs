package commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import main.AhoriaNPCs;
import main.AhoriaRaces;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import quest.Quest;
import quest.QuestProperties;

public class QuestCommand implements CommandExecutor {

	private AhoriaNPCs main;

	public QuestCommand(AhoriaNPCs an) {
		main = an;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
			if(cs.hasPermission("quest.help")) {
				help(cs);
			}
			else {
				permMsg(cs);
			}
		}
		else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				if(cs.hasPermission("quest.list")) {
					List<String> list = main.getQuests();
					if(!list.isEmpty()) {
						String res = "";
						for(String str : list) {
							if(res.length() != 0) {
								res = res + "§a, " + str;
							}
							else {
								res = str;
							}
						}
						cs.sendMessage("§aAktuell gibt es folgende Quests:");
						cs.sendMessage("§6" + res);
					}
					else {
						cs.sendMessage("§cAktuell gibt es keine Quests.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("create")) {
				if(cs.hasPermission("quest.create")) {
					cs.sendMessage("§cBitte gib einen Namen für die neue Quest ein.");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("delete")) {
				if(cs.hasPermission("quest.delete")) {
					cs.sendMessage("§cBitte gib einen Namen für die neue Quest ein.");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("addcondition")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib eine Quest und die Bedingung an, die du hinzufügen willst: /quest addcondition [quest] [condition]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("removecondition")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib eine Quest und die ID der Bedingung an, die du entfernen willst: /quest removecondition [quest] [conditionId]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("addtask")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib eine Quest und die Aufgabe an, die du hinzufügen willst: /quest addtask [quest] [task]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("removetask")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib eine Quest und die ID der Aufgabe an, die du entfernen willst: /quest addtask [quest] [taskId]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("addreward")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib eine Quest und die Belohnung an, die du hinzufügen willst: /quest addreward [quest] [task]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("removereward")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib eine Quest und die ID der Belohnung an, die du entfernen willst: /quest addreward [quest] [taskId]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("conditions")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die Quest an, deren Bedingungen du ansehen möchtest: /quest conditions [quest]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("tasks")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die Quest an, deren Aufgaben du ansehen möchtest: /quest tasks [quest]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("rewards")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die Quest an, deren Belohnungen du ansehen möchtest: /quest rewards [quest]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("getbook")) {
				if(cs.hasPermission("quest.book")) {
					cs.sendMessage("§cBitte gib die Quest an, deren Questbook du haben möchtest: /quest getbook [quest]");
				}
				else {
					permMsg(cs);
				}
			}
			else {
				cs.sendMessage("§cDas ist kein gültiger Befehl. Siehe /quest help");
			}
		}
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("create")) {
				if(cs.hasPermission("quest.create")) {
					String name = args[1];
					List<String> quests = main.getQuests();
					if(!quests.contains(name)) {
						File f = new File(main.getDataFolder() + "/quests/", name + ".yml");
						try {
							YamlConfiguration.loadConfiguration(main.getResource("quest.yml")).save(f);
							cs.sendMessage("§aQuest erfolgreich erstellt.");
							main.quests.add(new Quest(main, name));
						} catch (IOException e) {
							cs.sendMessage("§cFehler bei der Erstellung der Quest.");
							e.printStackTrace();
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert bereits.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("delete")) {
				if(cs.hasPermission("quest.delete")) {
					String name = args[1];
					List<String> quests = main.getQuests();
					if(quests.contains(name)) {
						File f = new File(main.getDataFolder() + "/quests/", name + ".yml");
						if(f.exists()) {
							f.delete();
							main.quests.remove(main.getQuestByName(name));
							cs.sendMessage("§aQuest erfolgreich gelöscht.");
						}
						else {
							cs.sendMessage("§cDiese Quest existiert nicht.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("conditions")) {
				if(cs.hasPermission("quest.edit")) {
					String quest = args[1];
					Quest q = main.getQuestByName(quest);
					if(q != null) {
						List<String> list = q.getConditions();
						if(!list.isEmpty()) {
							cs.sendMessage("§7╔═══════════════════");
							cs.sendMessage("§7║ §2§lQuest Bedingungen: §6§l" + quest);
							for(int i = 0; i < list.size(); i++) {
								cs.sendMessage("§7║ §6" + (i+1) + ". §a" + list.get(i));
							}
							cs.sendMessage("§7╚═══════════════════");
						}
						else {
							cs.sendMessage("§cFür diese Quest gibt es keine Bedingungen.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("rewards")) {
				if(cs.hasPermission("quest.edit")) {
					String quest = args[1];
					Quest q = main.getQuestByName(quest);
					if(q != null) {
						List<String> list = q.getRewards();
						if(!list.isEmpty()) {
							cs.sendMessage("§7╔═══════════════════");
							cs.sendMessage("§7║ §2§lQuest Belohnungen: §6§l" + quest);
							for(int i = 0; i < list.size(); i++) {
								cs.sendMessage("§7║ §6" + (i+1) + ". §a" + list.get(i));
							}
							cs.sendMessage("§7╚═══════════════════");
						}
						else {
							cs.sendMessage("§cFür diese Quest gibt es keine Belohnungen.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("tasks")) {
				if(cs.hasPermission("quest.edit")) {
					String quest = args[1];
					Quest q = main.getQuestByName(quest);
					if(q != null) {
						List<String> list = q.getTasks();
						if(!list.isEmpty()) {
							cs.sendMessage("§7╔═══════════════════");
							cs.sendMessage("§7║ §2§lQuest Aufgaben: §6§l" + quest);
							for(int i = 0; i < list.size(); i++) {
								cs.sendMessage("§7║ §6" + (i+1) + ". §a" + list.get(i));
							}
							cs.sendMessage("§7╚═══════════════════");
						}
						else {
							cs.sendMessage("§cDiese Quest beinhaltet keine Aufgaben.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("getbook")) {
				if(cs.hasPermission("quest.book")) {
					String quest = args[1];
					Quest q = main.getQuestByName(quest);
					if(q != null) {
						if(cs instanceof Player) {
							Player p = (Player) cs;
							p.getInventory().addItem(q.getQuestBook());
							p.updateInventory();
							p.sendMessage("§aDas Questbook wurde zu deinem Inventar hinzugefügt.");
						}
						else {
							cs.sendMessage("§cDas ist nur als Spieler möglich.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("addcondition")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die Bedingung an, die du hinzufügen willst: /quest addcondition [quest] [condition]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("removecondition")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die ID der Bedingung an, die du entfernen willst: /quest removecondition [quest] [conditionId]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("addtask")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die Aufgabe an, die du hinzufügen willst: /quest addtask [quest] [task]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("removetask")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die ID der Aufgabe an, die du entfernen willst: /quest addtask [quest] [taskId]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("addreward")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die Belohnung an, die du hinzufügen willst: /quest addreward [quest] [task]");
				}
				else {
					permMsg(cs);
				}
			}
			else if(args[0].equalsIgnoreCase("removereward")) {
				if(cs.hasPermission("quest.edit")) {
					cs.sendMessage("§cBitte gib die ID der Belohnung an, die du entfernen willst: /quest addreward [quest] [taskId]");
				}
				else {
					permMsg(cs);
				}
			}
			else {
				cs.sendMessage("§cDas ist kein gültiger Befehl. Siehe /quest help");
			}
		}
		else if(args.length == 3) {
			if(cs.hasPermission("quest.edit")) {
				if(args[0].equalsIgnoreCase("addcondition")) {
					String quest = args[1];
					String con = args[2];
					if(main.getQuests().contains(quest)) {
						Quest q = main.getQuestByName(quest);
						FileConfiguration cfg = q.getConfig();
						if(con.startsWith("q-")) {
							if(main.getQuests().contains(con.replace("q-", ""))) {
								List<String> list = q.getConditions();
								list.add(con);
								cfg.set("bedingungen", list);
								q.saveConfig();
								cs.sendMessage("§aBedingung hinzugefügt!");
							}
							else {
								cs.sendMessage("§cDie angegebene Quest existiert nicht.");
							}
						}
						else if(con.startsWith("l-")) {
							List<String> list = q.getConditions();
							try {
								Integer.parseInt(con.split("-")[1]);
								list.add(con);
								cfg.set("bedingungen", list);
								q.saveConfig();
								cs.sendMessage("§aBedingung hinzugefügt!");
							}
							catch(NumberFormatException ex) {
								cs.sendMessage("§cBitte gib als Mindestlevel eine Zahl an.");
							}
						}
						else if(con.startsWith("i-")) {
							String res = getItemString(cs, con);
							if(res != null) {
								List<String> list = q.getConditions();
								list.add(res);
								cfg.set("bedingungen", list);
								q.saveConfig();
								cs.sendMessage("§aBedingung hinzugefügt!");
							}
						}
						else if(con.startsWith("r-")) {
							if(QuestProperties.getRace(con) != null) {
								AhoriaRaces ar = main.getAhoriaRaces();
								if(ar != null) {
									if(ar.races.isSet(QuestProperties.getRace(con))) {
										List<String> list = q.getTasks();
										list.add(con);
										cfg.set("bedingungen", list);
										q.saveConfig();
										cs.sendMessage("§aAufgabe hinzugefügt!");
									}
									else {
										cs.sendMessage("§cDie angegebene Rasse existiert nicht.");
									}
								}
								else {
									cs.sendMessage("§cDafür wird das Rassenplugin (AhoriaRaces) benötigt.");
								}
							}
							else {
								cs.sendMessage("§cBitte gib eine gültige Rasse ein.");
							}
						}
						else {
							cs.sendMessage("§7═════════════════════════════════");
							cs.sendMessage("§cFehler - Versuche es im §6Format §cdieser §bBedingungen:");
							cs.sendMessage("");
							cs.sendMessage("§cAbsolvierte Quest als Bedingung§f: §6q-§bquestname");
							cs.sendMessage("§cErreichtes Level als Bedingung§f: §6l-§b2");
							cs.sendMessage("§cGegenstand im Inventar als Bedingung§f: §6i-§bdiamond_sword");
							cs.sendMessage("§7═════════════════════════════════");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
					
				}
				else if(args[0].equalsIgnoreCase("removecondition")) {
					String quest = args[1];
					String conStr = args[2];
					if(main.getQuests().contains(quest)) {
						Quest q = main.getQuestByName(quest);
						List<String> list = q.getConditions();
						if(!list.isEmpty()) {
							try {
								int con = Integer.parseInt(conStr)-1;
								if(con >= 0 && con < list.size()) {
									list.remove(con);
									q.getConfig().set("bedingungen", list);
									q.saveConfig();
									cs.sendMessage("§aBedingung entfernt!");
								}
								else {
									cs.sendMessage("§cBitte gib die gültige ID einer Bedingung ein.");
									cs.sendMessage("§cEin Liste der IDs findest du hier: /quest conditions [quest]");
								}
							}
							catch(NumberFormatException ex) {
								cs.sendMessage("§cBitte gib die ID einer Bedingung ein.");
								cs.sendMessage("§cEin Liste der IDs findest du hier: /quest conditions [quest]");
							}
						}
						else {
							cs.sendMessage("§cFür diese Quest gibt es keine Bedingungen.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else if(args[0].equalsIgnoreCase("addreward")) {
					String quest = args[1];
					String reward = args[2];
					if(main.getQuests().contains(quest)) {
						Quest q = main.getQuestByName(quest);
						FileConfiguration cfg = q.getConfig();
						if(reward.startsWith("q-")) {
							if(main.getQuests().contains(reward.replace("q-", ""))) {
								List<String> list = q.getRewards();
								list.add(reward);
								cfg.set("belohnungen", list);
								q.saveConfig();
								cs.sendMessage("§Belohnung hinzugefügt!");
							}
							else {
								cs.sendMessage("§cDie angegebene Quest existiert nicht.");
							}
						}
						else if(reward.startsWith("l-")) {
							List<String> list = q.getRewards();
							try {
								Integer.parseInt(reward.split(":")[1]);
								list.add(reward);
								cfg.set("belohnungen", list);
								q.saveConfig();
								cs.sendMessage("§aBelohnung hinzugefügt!");
							}
							catch(NumberFormatException ex) {
								cs.sendMessage("§cBitte gib als Level eine Zahl an.");
							}
						}
						else if(reward.startsWith("xp-")) {
							List<String> list = q.getRewards();
							try {
								Integer.parseInt(reward.split("-")[1]);
								list.add(reward);
								cfg.set("belohnungen", list);
								q.saveConfig();
								cs.sendMessage("§aBelohnung hinzugefügt!");
							}
							catch(NumberFormatException ex) {
								cs.sendMessage("§cBitte gib als XP eine Zahl an.");
							}
						}
						else if(reward.startsWith("i-")) {
							String res = getItemString(cs, reward);
							if(res != null) {
								List<String> list = q.getRewards();
								list.add(res);
								cfg.set("belohnungen", list);
								q.saveConfig();
								cs.sendMessage("§aBelohnung hinzugefügt!");
							}
							else {
								cs.sendMessage("§cBitte gib ein gültiges Item an.");
							}
						}
						else {
							cs.sendMessage("§7═════════════════════════════════");
							cs.sendMessage("§cFehler - Versuche es im §6Format §cdieser §bBelohnungen:");
							cs.sendMessage("");
							cs.sendMessage("§cNeue Quest als Belohnung§f: §6q-§bquestname");
							cs.sendMessage("§cXP als Belohnung§f: §6l-§b2");
							cs.sendMessage("§cGegenstand als Belohnung§f: §6i-§bdiamond_sword");
							cs.sendMessage("§7═════════════════════════════════");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
					
				}
				else if(args[0].equalsIgnoreCase("removereward")) {
					String quest = args[1];
					String conStr = args[2];
					if(main.getQuests().contains(quest)) {
						Quest q = main.getQuestByName(quest);
						List<String> list = q.getRewards();
						if(!list.isEmpty()) {
							try {
								int con = Integer.parseInt(conStr)-1;
								if(con >= 0 && con < list.size()) {
									list.remove(con);
									q.getConfig().set("belohnungen", list);
									q.saveConfig();
									cs.sendMessage("§aBelohnung entfernt!");
								}
								else {
									cs.sendMessage("§cBitte gib die gültige ID einer Belohnung ein.");
									cs.sendMessage("§cEin Liste der IDs findest du hier: /quest rewards [quest]");
								}
							}
							catch(NumberFormatException ex) {
								cs.sendMessage("§cBitte gib die ID einer Belohnung ein.");
								cs.sendMessage("§cEin Liste der IDs findest du hier: /quest rewards [quest]");
							}
						}
						else {
							cs.sendMessage("§cFür diese Quest gibt es keine Belohnungen.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else if(args[0].equalsIgnoreCase("addtask")) {
					String quest = args[1];
					String task = args[2];
					if(main.getQuests().contains(quest)) {
						Quest q = main.getQuestByName(quest);
						FileConfiguration cfg = q.getConfig();
						if(task.startsWith("kill:")) {
							QuestProperties qp = QuestProperties.getByString(task);
							if(qp == QuestProperties.RACE) {
								AhoriaRaces ar = main.getAhoriaRaces();
								if(ar != null) {
									if(ar.races.isSet(QuestProperties.getRace(task))) {
										List<String> list = q.getTasks();
										list.add(task);
										cfg.set("aufgaben", list);
										q.saveConfig();
										cs.sendMessage("§aAufgabe hinzugefügt!");
									}
									else {
										cs.sendMessage("§cDie angegebene Rasse existiert nicht.");
									}
								}
								else {
									cs.sendMessage("§cDafür wird das Rassenplugin (AhoriaRaces) benötigt.");
								}
							}
							else if(qp == QuestProperties.MONSTER) {
								if(QuestProperties.getEntityType(task) != null) {
									List<String> list = q.getTasks();
									list.add(task);
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cBitte gib ein gültiges Mob ein.");
								}
							}
							else if(qp == QuestProperties.ID) {
								if(QuestProperties.getUniqueId(task) != null) {
									List<String> list = q.getTasks();
									list.add(task);
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cBitte gib eine gültige UUID ein.");
								}
							}
							else if(qp == QuestProperties.PLAYER) {
								Object obj = QuestProperties.getPlayer(task);
								if(obj != null) {
									List<String> list = q.getTasks();
									list.add(task);
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cBitte gib die UUID oder den Namen eines Spielers an.");
								}
							}
							else {
								cs.sendMessage("§cBitte gib ein gültiges Attentatsziel an.");
							}
						}
						else if(task.startsWith("collect:")) {
							String iString = getItemString(cs, task);
							ItemStack is = null;
							if(iString != null) {
								is = QuestProperties.getItem(iString);
							}
							if(is != null) {
								List<String> list = q.getTasks();
								list.add(iString);
								cfg.set("aufgaben", list);
								q.saveConfig();
								cs.sendMessage("§aAufgabe hinzugefügt!");
							}
						}
						else if(task.startsWith("move:")) {
							if(!task.replace("move:", "").startsWith("current")) {
								if(QuestProperties.getLocation(task) != null) {
									List<String> list = q.getTasks();
									list.add(task);
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cBitte gib eine gültige Position an: welt,x,y,z");
								}
							}
							else {
								if(cs instanceof Player) {
									Player p = (Player) cs;
									Location loc = p.getLocation();
									List<String> list = q.getTasks();
									list.add("move:" + loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + task.replace("move:current", ""));
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cDas ist nur als Spieler möglich.");
								}
							}
						}
						else if(task.startsWith("click:")) {
							if(!task.replace("click:", "").startsWith("current")) {
								if(QuestProperties.getLocation(task) != null) {
									List<String> list = q.getTasks();
									list.add(task);
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cBitte gib eine gültige Position an: welt,x,y,z");
								}
							}
							else {
								if(cs instanceof Player) {
									Player p = (Player) cs;
									Location loc = p.getLocation();
									List<String> list = q.getTasks();
									list.add("click:" + loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + task.replace("click:current", ""));
									cfg.set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe hinzugefügt!");
								}
								else {
									cs.sendMessage("§cDas ist nur als Spieler möglich.");
								}
							}
						}
						else {
							cs.sendMessage("§7═════════════════════════════════");
							cs.sendMessage("§cFehler - Versuche es im §6Format §cdieser §bAufgaben:");
							cs.sendMessage("");
							cs.sendMessage("§cMob töten§f: §6kill:§bm:zombie,1,Zombiekönig");
							cs.sendMessage("§cSpieler töten§f: §6kill:§bp-spieler");
							cs.sendMessage("§cSpieler einer Rasse töten§f: §6kill:§br-rasse");
							cs.sendMessage("§cItem finden/sammeln§f: §6collect:§bi-wool;14,1,Wolle des Todes");
							cs.sendMessage("§cBlock anklicken§f: §6click:§bwelt,x,y,z");
							cs.sendMessage("§cZu einem Ziel laufen§f: §6move:§bwelt,x,y,z,radius");
							cs.sendMessage("§7═════════════════════════════════");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
				else if(args[0].equalsIgnoreCase("removetask")) {
					String quest = args[1];
					String taskStr = args[2];
					if(main.getQuests().contains(quest)) {
						Quest q = main.getQuestByName(quest);
						List<String> list = q.getTasks();
						if(!list.isEmpty()) {
							try {
								int con = Integer.parseInt(taskStr)-1;
								if(con >= 0 && con < list.size()) {
									list.remove(con);
									q.getConfig().set("aufgaben", list);
									q.saveConfig();
									cs.sendMessage("§aAufgabe entfernt!");
								}
								else {
									cs.sendMessage("§cBitte gib die gültige ID einer Aufgabe ein.");
									cs.sendMessage("§cEin Liste der IDs findest du hier: /quest tasks [quest]");
								}
							}
							catch(NumberFormatException ex) {
								cs.sendMessage("§cBitte gib die ID einer Aufgabe ein.");
								cs.sendMessage("§cEin Liste der IDs findest du hier: /quest tasks [quest]");
							}
						}
						else {
							cs.sendMessage("§cDiese Quest beinhaltet keine Aufgaben.");
						}
					}
					else {
						cs.sendMessage("§cDiese Quest existiert nicht.");
					}
				}
			}
		}
		
		return true;
	}
	
	private void help(CommandSender cs) {
		cs.sendMessage("§7╔═════════════════════════════");
		cs.sendMessage("§7║ §2§lQuest-Hilfe");
		cs.sendMessage("§7║ §6/quest §ahelp§f: Zeigt diese Hilfe an");
		cs.sendMessage("§7║ §6/quest §alist§f: Zeigt eine Liste aller Quests an");
		cs.sendMessage("§7║ §6/quest §acreate §b[name]§f: Erstellt eine neue Quest");
		cs.sendMessage("§7║ §6/quest §adelete §b[name]§f: Löscht eine Quest");
		cs.sendMessage("§7║ §6/quest §agetquestbook §b[quest]§f: Gibt dir ein Questbook");
		cs.sendMessage("§7║ §6/quest §aconditions §b[quest]§f: Liste der IDs der Bedingungen");
		cs.sendMessage("§7║ §6/quest §atasks §b[quest]§f: Liste der IDs der Aufgaben");
		cs.sendMessage("§7║ §6/quest §arewards §b[quest]§f: Liste der IDs der Belohnungen");
		cs.sendMessage("§7║ §6/quest §aaddcondition §b[quest] [cond.]§f: Bedingung hinzufügen");
		cs.sendMessage("§7║ §6/quest §aremovecondition §b[quest] [id]§f: Bedingung entfernen");
		cs.sendMessage("§7║ §6/quest §aaddtask §b[quest] [task]§f: Aufgabe hinzufügen");
		cs.sendMessage("§7║ §6/quest §aremovetask §b[quest] [id]§f: Aufgabe entfernen");
		cs.sendMessage("§7║ §6/quest §aaddreward §b[quest] [reward]§f: Belohnung hinzufügen");
		cs.sendMessage("§7║ §6/quest §aremovereward §b[quest] [id]§f: Belohnung entfernen");
		cs.sendMessage("§7║ §eAusführliches Tutorial im Quests-Ordner!");
		cs.sendMessage("§7╚═════════════════════════════");
	}

	private void permMsg(CommandSender cs) {
		cs.sendMessage("§cDazu hast du kein Recht.");
	}
	
	public String getItemString(CommandSender cs, String s) {
		String[] data = s.split("i-");
		if(data.length > 1) {
			if(data[1].equalsIgnoreCase("inhand")) {
				if(cs instanceof Player) {
					Player p = (Player) cs;
					ItemStack is = p.getItemInHand();
					if(is != null && is.getType() != Material.AIR) {
						String res =  "collect:i-" + is.getType().toString();
						if(is.getDurability() != 0) {
							res = res + ";" + is.getDurability();
						}
						res = res + "," + is.getAmount();
						if(is.hasItemMeta()) {
							ItemMeta im = is.getItemMeta();
							if(im.getDisplayName() != null) {
								res = res + "," + im.getDisplayName().replace("§", "&");
							}
							if(im.getLore() != null) {
								String lore = "";
								for(String str : im.getLore()) {
									if(lore.length() != 0) {
										lore = lore + ";" + str;
									}
									else {
										lore = str;
									}
								}
								res = res + "," + lore.replace("§", "&");
							}
						}
						return res;
					}
					else {
						p.sendMessage("§cDafür musst du ein Item in der Hand halten.");
					}
				}
				else {
					cs.sendMessage("§cDas kannst du nur als Spieler machen.");
				}
			}
			else {
				if(QuestProperties.getItem(s) != null) {
					return s;
				}
				else {
					cs.sendMessage("§cDu hast einen Fehler gemacht. Bitte gib ein gültiges Item an.");
				}
			}
		}
		return null;
	}
	
}
