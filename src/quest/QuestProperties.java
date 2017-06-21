package quest;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum QuestProperties {

	QUEST("q-"),
	LEVEL("l-"),
	ITEM("i-"),
	MONSTER("m-"),
	PLAYER("p-"),
	ID("u-"),
	XP("xp-"),
	RACE("r-");
	
	private String shortcut;

	QuestProperties(String shortcut) {
		this.shortcut = shortcut;
	}
	
	public static int getLevel(String s) {
		String[] data = split(s);
		if(data.length > 1) {
			try {
				return Integer.parseInt(data[1]);
			}
			catch(NumberFormatException ex) {
				System.out.print("[AhoriaNPCs] Fehler bei Questanweisung - Level ohne Angabe:");
				ex.printStackTrace();
			}
		}
		return 0;
	}
	
	public static float getXP(String s) {
		String[] data = split(s);
		if(data.length > 1) {
			try {
				return Float.parseFloat(data[1]);
			}
			catch(NumberFormatException ex) {
				System.out.print("[AhoriaNPCs] Fehler bei Questanweisung - XP ohne Angabe:");
				ex.printStackTrace();
			}
		}
		return 0;
	}
	
	public static String getQuest(String s) {
		return split(s)[1];
	}
	
	public static String getRace(String s) {
		return split(s)[1];
	}
	
	public static String getMobName(String s) {
		String name = null;
		EntityType et = getEntityType(s);
		name = getMonsterDisplayName(s);
		if(name == null) {
			switch(et) {
				case BAT: name = "Fledermaus";
				case CAVE_SPIDER: name = "Höhlenspinne";
				case CHICKEN: name = "Huhn";
				case COW: name = "Kuh";
				case CREEPER: name = "Creeper";
				case ENDERMAN: name = "Enderman";
				case EGG: name = "Ei";
				case ENDER_DRAGON: name = "Enderdrache";
				case GHAST: name = "Ghast";
				case GIANT: name = "Riese";
				case HORSE: name = "Pferd";
				case MAGMA_CUBE: name = "Lavaschleim";
				case IRON_GOLEM: name = "Eisengolem";
				case BLAZE: name = "Feuergeist";
				case MUSHROOM_COW: name = "Pilzkuh";
				case PIG: name = "Schwein";
				case OCELOT: name = "Tiger";
				case PIG_ZOMBIE: name = "Untotes Schwein";
				case PLAYER: name = "Spieler";
				case SHEEP: name = "Schaf";
				case SKELETON: name = "Skelett";
				case SILVERFISH: name = "Silberfisch";
				case SLIME: name = "Schleim";
				case SNOWMAN: name = "Schneemann";
				case SPIDER: name = "Spinne";
				case SQUID: name = "Kraken";
				case VILLAGER: name = "Dorfbewohner";
				case WITCH: name = "Hexe";
				case WITHER: name = "Wither";
				case WOLF: name = "Wolf";
				case ZOMBIE: name = "Zombie";
			default:
				break;
			}
		}
		return name;
	}
	
	public static String getItemName(String str) {
		String name = null;
		ItemStack is = getItem(str);
		if(is.hasItemMeta() && is.getItemMeta().getDisplayName() != null) {
			name = is.getItemMeta().getDisplayName();
		}
		if(name == null) {
			switch(is.getType()) {
			case ACACIA_STAIRS:
				name = "Akazienstufen";
			case ACTIVATOR_RAIL:
				name = "Sensorschiene";
			case AIR:
				name = "Luft";
			case ANVIL:
				name = "Amboss";
			case APPLE:
				name = "Apfel";
			case ARROW:
				name = "Pfeil";
			case BAKED_POTATO:
				name = "Gebackene Kartoffel";
			case BEACON:
				name = "Leuchtfeuer";
			case BED:
				name = "Bett";
			case BEDROCK:
				name = "Bedrock";
			case BED_BLOCK:
				name = "Bett";
			case BIRCH_WOOD_STAIRS:
				name = "Birkenstufen";
			case BLAZE_POWDER:
				name = "Staub eines Feuergeistes";
			case BLAZE_ROD:
				name = "Stab eines Feuergeists";
			case BOAT:
				name = "Boot";
			case BONE:
				name = "Knochen";
			case BOOK:
				name = "Buch";
			case BOOKSHELF:
				name = "Bücherregal";
			case BOOK_AND_QUILL:
				name = "Buch";
			case BOW:
				name = "Bogen";
			case BOWL:
				name = "Schüssel";
			case BREAD:
				name = "Brot";
			case BREWING_STAND:
				name = "Braustand";
			case BREWING_STAND_ITEM:
				name = "Braustand";
			case BRICK:
				name = "Ziegelstein";
			case BRICK_STAIRS:
				name = "Ziegelsteinstufen";
			case BROWN_MUSHROOM:
				name = "Pilz";
			case BUCKET:
				name = "Eimer";
			case BURNING_FURNACE:
				name = "Brennender Ofen";
			case CACTUS:
				name = "Kaktus";
			case CAKE:
				name = "Kuchen";
			case CAKE_BLOCK:
				name = "Kuchen";
			case CARPET:
				name = "Teppich";
			case CARROT:
				name = "Karotte";
			case CARROT_ITEM:
				name = "Karotte";
			case CARROT_STICK:
				name = "Karotte an einem Stock";
			case CAULDRON:
				name = "Kessel";
			case CAULDRON_ITEM:
				name = "Kessel";
			case CHAINMAIL_BOOTS:
				name = "Kettenstiefel";
			case CHAINMAIL_CHESTPLATE:
				name = "Kettenbrustpanzer";
			case CHAINMAIL_HELMET:
				name = "Kettenhelm";
			case CHAINMAIL_LEGGINGS:
				name = "Kettenhose";
			case CHEST:
				name = "Kiste";
			case CLAY:
				name = "Lehm";
			case CLAY_BALL:
				name = "Lehmklumpen";
			case CLAY_BRICK:
				name = "Lehm";
			case COAL:
				name = "Kohle";
			case COAL_BLOCK:
				name = "Block aus Kohle";
			case COAL_ORE:
				name = "Kohleerz";
			case COBBLESTONE:
				name = "Pflasterstein";
			case COBBLESTONE_STAIRS:
				name = "Pflastersteinstufen";
			case COBBLE_WALL:
				name = "Pflastersteinmauer";
			case COCOA:
				name = "Kakao";
			case COMMAND:
				name = "Befehl";
			case COMMAND_MINECART:
				name = "Lore";
			case COMPASS:
				name = "Kompass";
			case COOKED_BEEF:
				name = "Gebratenes Fleisch";
			case COOKED_CHICKEN:
				name = "Gebratene Pute";
			case COOKED_FISH:
				name = "Gebratener Fisch";
			case COOKIE:
				name = "Keks";
			case CROPS:
				name = "Samen";
			case DARK_OAK_STAIRS:
				name = "Dunkle Eichenstufen";
			case DAYLIGHT_DETECTOR:
				name = "Tageslichtsensor";
			case DEAD_BUSH:
				name = "Akazienstufen";
			case DETECTOR_RAIL:
				name = "Detektorschiene";
			case DIAMOND:
				name = "Diamant";
			case DIAMOND_AXE:
				name = "Diamantaxt";
			case DIAMOND_BARDING:
				name = "Diamantrüstung";
			case DIAMOND_BLOCK:
				name = "Diamantblock";
			case DIAMOND_BOOTS:
				name = "Diamantstiefel";
			case DIAMOND_CHESTPLATE:
				name = "Diamantbrustpanzer";
			case DIAMOND_HELMET:
				name = "Diamanthelm";
			case DIAMOND_HOE:
				name = "Diamanthacke";
			case DIAMOND_LEGGINGS:
				name = "Diamanthose";
			case DIAMOND_ORE:
				name = "Diamanterz";
			case DIAMOND_PICKAXE:
				name = "Diamantspitzhacke";
			case DIAMOND_SPADE:
				name = "Diamantschaufel";
			case DIAMOND_SWORD:
				name = "Diamantschwert";
			case DIODE:
				name = "Diode";
			case DIODE_BLOCK_OFF:
				name = "Diode";
			case DIODE_BLOCK_ON:
				name = "Diode";
			case DIRT:
				name = "Erde";
			case DISPENSER:
				name = "Dispenser";
			case DOUBLE_PLANT:
				name = "Pflanze";
			case DOUBLE_STEP:
				name = "Doppelter Halbblock";
			case DRAGON_EGG:
				name = "Drachenei";
			case DROPPER:
				name = "Dropper";
			case EGG:
				name = "Ei";
			case EMERALD:
				name = "Smaragd";
			case EMERALD_BLOCK:
				name = "Smaragdblock";
			case EMERALD_ORE:
				name = "Smaragderz";
			case EMPTY_MAP:
				name = "Leere Karte";
			case ENCHANTED_BOOK:
				name = "Verzaubertes Buch";
			case ENCHANTMENT_TABLE:
				name = "Zaubertisch";
			case ENDER_CHEST:
				name = "Enderkiste";
			case ENDER_PEARL:
				name = "Enderperle";
			case ENDER_PORTAL:
				name = "Enderportal";
			case ENDER_PORTAL_FRAME:
				name = "Enderportalbruchstück";
			case ENDER_STONE:
				name = "Endstein";
			case EXPLOSIVE_MINECART:
				name = "TNT-Lore";
			case EXP_BOTTLE:
				name = "Erfahrungsfläschchen";
			case EYE_OF_ENDER:
				name = "Endermanauge";
			case FEATHER:
				name = "Feder";
			case FENCE:
				name = "Zaun";
			case FENCE_GATE:
				name = "Zauntor";
			case FERMENTED_SPIDER_EYE:
				name = "Fermentiertes Spinnenauge";
			case FIRE:
				name = "Feuer";
			case FIREBALL:
				name = "Feuerball";
			case FIREWORK:
				name = "Feuerwerk";
			case FIREWORK_CHARGE:
				name = "Feuerwerksladung";
			case FISHING_ROD:
				name = "Angel";
			case FLINT:
				name = "Feuerstein";
			case FLINT_AND_STEEL:
				name = "Feuerzeug";
			case FLOWER_POT:
				name = "Blumentopf";
			case FLOWER_POT_ITEM:
				name = "Blumentopf";
			case FURNACE:
				name = "Ofen";
			case GHAST_TEAR:
				name = "Träne eines Ghasts";
			case GLASS:
				name = "Glas";
			case GLASS_BOTTLE:
				name = "Glasfläschchen";
			case GLOWING_REDSTONE_ORE:
				name = "Glowstone";
			case GLOWSTONE:
				name = "Glowstone";
			case GLOWSTONE_DUST:
				name = "Glowstonestaub";
			case GOLDEN_APPLE:
				name = "Goldener Apfel";
			case GOLDEN_CARROT:
				name = "Goldene Karotte";
			case GOLD_AXE:
				name = "Goldaxt";
			case GOLD_BARDING:
				name = "Goldrüstung";
			case GOLD_BLOCK:
				name = "Goldblock";
			case GOLD_BOOTS:
				name = "Goldstiefel";
			case GOLD_CHESTPLATE:
				name = "Goldbrustpanzer";
			case GOLD_HELMET:
				name = "Goldhelm";
			case GOLD_HOE:
				name = "Goldhacke";
			case GOLD_INGOT:
				name = "Goldbarren";
			case GOLD_LEGGINGS:
				name = "Goldhose";
			case GOLD_NUGGET:
				name = "Goldbruchstück";
			case GOLD_ORE:
				name = "Golderz";
			case GOLD_PICKAXE:
				name = "Goldspitzhacke";
			case GOLD_PLATE:
				name = "Goldplatte";
			case GOLD_RECORD:
				name = "Goldene Schallplatte";
			case GOLD_SPADE:
				name = "Goldschaufel";
			case GOLD_SWORD:
				name = "Goldschwert";
			case GRASS:
				name = "Gras";
			case GRAVEL:
				name = "Kies";
			case GREEN_RECORD:
				name = "Schallplatte";
			case GRILLED_PORK:
				name = "Gegrilltes Schweinefleisch";
			case HARD_CLAY:
				name = "Harter Lehm";
			case HAY_BLOCK:
				name = "Heu";
			case HOPPER:
				name = "Hopper";
			case HOPPER_MINECART:
				name = "Hopper";
			case HUGE_MUSHROOM_1:
				name = "Riesenpilz";
			case HUGE_MUSHROOM_2:
				name = "Riesenpilz";
			case ICE:
				name = "Eis";
			case INK_SACK:
				name = "Farbstoff";
			case IRON_AXE:
				name = "Eisenaxt";
			case IRON_BARDING:
				name = "Eisenrüstung";
			case IRON_BLOCK:
				name = "Eisenblock";
			case IRON_BOOTS:
				name = "Eisenstiefel";
			case IRON_CHESTPLATE:
				name = "Eisenbrustpanzer";
			case IRON_DOOR:
				name = "Eisentür";
			case IRON_DOOR_BLOCK:
				name = "Eisentür";
			case IRON_FENCE:
				name = "Eisenzaun";
			case IRON_HELMET:
				name = "Eisenhelm";
			case IRON_HOE:
				name = "Eisenhacke";
			case IRON_INGOT:
				name = "Eisenbarren";
			case IRON_LEGGINGS:
				name = "Eisenhose";
			case IRON_ORE:
				name = "Eisenerz";
			case IRON_PICKAXE:
				name = "Eisenspitzhacke";
			case IRON_PLATE:
				name = "Eisenplatte";
			case IRON_SPADE:
				name = "Eisenschaufel";
			case IRON_SWORD:
				name = "Eisenschwert";
			case ITEM_FRAME:
				name = "Rahmen";
			case JACK_O_LANTERN:
				name = "Laterne";
			case JUKEBOX:
				name = "Jukebox";
			case JUNGLE_WOOD_STAIRS:
				name = "Dschungelholzstufen";
			case LADDER:
				name = "Leiter";
			case LAPIS_BLOCK:
				name = "Lapisblock";
			case LAPIS_ORE:
				name = "Lapiserz";
			case LAVA:
				name = "Lava";
			case LAVA_BUCKET:
				name = "Lavaeimer";
			case LEASH:
				name = "Leine";
			case LEATHER:
				name = "Leder";
			case LEATHER_BOOTS:
				name = "Lederschuhe";
			case LEATHER_CHESTPLATE:
				name = "Lederbrustpanzer";
			case LEATHER_HELMET:
				name = "Lederhelm";
			case LEATHER_LEGGINGS:
				name = "Lederhose";
			case LEAVES:
				name = "Blätter";
			case LEAVES_2:
				name = "Blätter";
			case LEVER:
				name = "Hebel";
			case LOG:
				name = "Holz";
			case LOG_2:
				name = "Holz";
			case LONG_GRASS:
				name = "Hohes Grass";
			case MAGMA_CREAM:
				name = "Magmacreme";
			case MAP:
				name = "Karte";
			case MELON:
				name = "Melone";
			case MELON_BLOCK:
				name = "Melone";
			case MELON_SEEDS:
				name = "Melonensamen";
			case MELON_STEM:
				name = "Melone";
			case MILK_BUCKET:
				name = "Milcheimer";
			case MINECART:
				name = "Lore";
			case MOB_SPAWNER:
				name = "Monsterbeschwörer";
			case MONSTER_EGG:
				name = "Monsterei";
			case MONSTER_EGGS:
				name = "Monstereier";
			case MOSSY_COBBLESTONE:
				name = "Vermoster Pflasterstein";
			case MUSHROOM_SOUP:
				name = "Pilzsuppe";
			case MYCEL:
				name = "Myzel";
			case NAME_TAG:
				name = "Namenschild";
			case NETHERRACK:
				name = "Netherrack";
			case NETHER_BRICK:
				name = "Netherziegelwand";
			case NETHER_BRICK_ITEM:
				name = "Netherziegelwand";
			case NETHER_BRICK_STAIRS:
				name = "Netherziegelstufen";
			case NETHER_FENCE:
				name = "Höllenzaun";
			case NETHER_STALK:
				name = "Höllenwarze";
			case NETHER_STAR:
				name = "Höllenstern";
			case NETHER_WARTS:
				name = "Höllenwarze";
			case NOTE_BLOCK:
				name = "Notenblock";;
			case OBSIDIAN:
				name = "Obsidian";
			case PACKED_ICE:
				name = "Packeis";
			case PAINTING:
				name = "Bild";
			case PAPER:
				name = "Papyrus";
			case PISTON_BASE:
				name = "Kolben";
			case PISTON_EXTENSION:
				name = "Kolben";
			case PISTON_MOVING_PIECE:
				name = "Kolben";
			case PISTON_STICKY_BASE:
				name = "Kolben";
			case POISONOUS_POTATO:
				name = "Giftige Kartoffel";
			case PORK:
				name = "Schweinefleisch";
			case PORTAL:
				name = "Portal";
			case POTATO:
				name = "Kartoffel";
			case POTATO_ITEM:
				name = "Kartoffel";
			case POTION:
				name = "Trank";
			case POWERED_MINECART:
				name = "Lore";
			case POWERED_RAIL:
				name = "Schiene";
			case PUMPKIN:
				name = "Kürbis";
			case PUMPKIN_PIE:
				name = "Kürbis";
			case PUMPKIN_SEEDS:
				name = "Kürbissamen";
			case PUMPKIN_STEM:
				name = "Kürbis";
			case QUARTZ:
				name = "Quartz";
			case QUARTZ_BLOCK:
				name = "Quartzblock";
			case QUARTZ_ORE:
				name = "Quartzerz";
			case QUARTZ_STAIRS:
				name = "Quartzstufen";
			case RAILS:
				name = "Schiene";
			case RAW_BEEF:
				name = "Rohes Fleisch";
			case RAW_CHICKEN:
				name = "Rohes Putenfleisch";
			case RAW_FISH:
				name = "Roher Fisch";
			case RECORD_10:
				name = "Schallplatte";
			case RECORD_11:
				name = "Schallplatte";
			case RECORD_12:
				name = "Schallplatte";
			case RECORD_3:
				name = "Schallplatte";
			case RECORD_4:
				name = "Schallplatte";
			case RECORD_5:
				name = "Schallplatte";
			case RECORD_6:
				name = "Schallplatte";
			case RECORD_7:
				name = "Schallplatte";
			case RECORD_8:
				name = "Schallplatte";
			case RECORD_9:
				name = "Schallplatte";
			case REDSTONE:
				name = "Redstone";
			case REDSTONE_BLOCK:
				name = "Redstoneblock";
			case REDSTONE_COMPARATOR:
				name = "Redstonevergleicher";
			case REDSTONE_COMPARATOR_OFF:
				name = "Redstonevergleicher";
			case REDSTONE_COMPARATOR_ON:
				name = "Redstonevergleicher";
			case REDSTONE_LAMP_OFF:
				name = "Redstonelampe";
			case REDSTONE_LAMP_ON:
				name = "Redstonelampe";
			case REDSTONE_ORE:
				name = "Redstoneerz";
			case REDSTONE_TORCH_OFF:
				name = "Redstonefackel";
			case REDSTONE_TORCH_ON:
				name = "Redstonefackel";
			case REDSTONE_WIRE:
				name = "Redstonekabel";
			case RED_MUSHROOM:
				name = "Fliegenpilz";
			case RED_ROSE:
				name = "Rote Rose";
			case ROTTEN_FLESH:
				name = "Verrottetes Fleisch";
			case SADDLE:
				name = "Sattel";
			case SAND:
				name = "Sand";
			case SANDSTONE:
				name = "Sandstein";
			case SANDSTONE_STAIRS:
				name = "Sandsteinstufen";
			case SAPLING:
				name = "Setzling";
			case SEEDS:
				name = "Samen";
			case SHEARS:
				name = "Scheeren";
			case SIGN:
				name = "Schild";
			case SIGN_POST:
				name = "Schild";
			case SKULL:
				name = "Totenkopf";
			case SKULL_ITEM:
				name = "Totenkopf";
			case SLIME_BALL:
				name = "Schleimball";
			case SMOOTH_BRICK:
				name = "Stein";
			case SMOOTH_STAIRS:
				name = "Steinstufen";
			case SNOW:
				name = "Schnee";
			case SNOW_BALL:
				name = "Schneeball";
			case SNOW_BLOCK:
				name = "Schneeblock";
			case SOIL:
				name = "Ackerland";
			case SOUL_SAND:
				name = "Seelensand";
			case SPECKLED_MELON:
				name = "Melone";
			case SPIDER_EYE:
				name = "Spinnenauge";
			case SPONGE:
				name = "Schwamm";
			case SPRUCE_WOOD_STAIRS:
				name = "Fichtenstufen";
			case STAINED_CLAY:
				name = "Farbiger Lehm";
			case STAINED_GLASS:
				name = "Farbiges Glas";
			case STAINED_GLASS_PANE:
				name = "Farbige Glasscheibe";
			case STATIONARY_LAVA:
				name = "Lava";
			case STATIONARY_WATER:
				name = "Wasser";
			case STEP:
				name = "Halbblock";
			case STICK:
				name = "Stock";
			case STONE:
				name = "Stein";
			case STONE_AXE:
				name = "Steinaxt";
			case STONE_BUTTON:
				name = "Steinknopf";
			case STONE_HOE:
				name = "Steinhacke";
			case STONE_PICKAXE:
				name = "Steinspitzhacke";
			case STONE_PLATE:
				name = "Steinplatte";
			case STONE_SPADE:
				name = "Steinschaufel";
			case STONE_SWORD:
				name = "Steinschwert";
			case STORAGE_MINECART:
				name = "Lore";
			case STRING:
				name = "Faden";
			case SUGAR:
				name = "Zucker";
			case SUGAR_CANE:
				name = "Zucker";
			case SUGAR_CANE_BLOCK:
				name = "Zucker";
			case SULPHUR:
				name = "Schwefel";
			case THIN_GLASS:
				name = "Glasscheibe";
			case TNT:
				name = "TnT";
			case TORCH:
				name = "Fackel";
			case TRAPPED_CHEST:
				name = "Kistenfalle";
			case TRAP_DOOR:
				name = "Falltür";
			case TRIPWIRE:
				name = "Draht";
			case TRIPWIRE_HOOK:
				name = "Haken";
			case VINE:
				name = "Laine";
			case WALL_SIGN:
				name = "Schild";
			case WATCH:
				name = "Uhr";
			case WATER:
				name = "Wasser";
			case WATER_BUCKET:
				name = "Wassereimer";
			case WATER_LILY:
				name = "Seerose";
			case WEB:
				name = "Spinnenweben";
			case WHEAT:
				name = "Getreide";
			case WOOD:
				name = "Holz";
			case WOODEN_DOOR:
				name = "Holztür";
			case WOOD_AXE:
				name = "Holzaxt";
			case WOOD_BUTTON:
				name = "Holzknopf";
			case WOOD_DOOR:
				name = "Holztür";
			case WOOD_DOUBLE_STEP:
				name = "Doppelter Halbholzblock";
			case WOOD_HOE:
				name = "Holzhacke";
			case WOOD_PICKAXE:
				name = "Holzspitzhacke";
			case WOOD_PLATE:
				name = "Holzplatte";
			case WOOD_SPADE:
				name = "Holzschaufel";
			case WOOD_STAIRS:
				name = "Holzstufen";
			case WOOD_STEP:
				name = "Holz Halbblock";
			case WOOD_SWORD:
				name = "Holzschwert";
			case WOOL:
				name = "Wolle";
			case WORKBENCH:
				name = "Werkbank";
			case WRITTEN_BOOK:
				name = "Beschriebenes Buch";
			case YELLOW_FLOWER:
				name = "Gelbe Blume";
			default:
				break;
			}
		}
		
		return name;
	}
	
	public static String getSubject(String str) {
		String s = "";
		if(str.startsWith("kill:")) {
			QuestProperties qp = QuestProperties.getByString(str);
			if(qp == QuestProperties.MONSTER) {
				String type = getMobName(str);
				s = "§3Töte " + type + ": §7" + getAmount(str);
			}
			else if(qp == QuestProperties.PLAYER) {
				Object obj = getPlayer(str);
				if(obj instanceof String) {
					s = "§3Töte den Spieler " + obj + ": §7" + getAmount(str);
				}
				else {
					s = "§3Töte den Spieler " + Bukkit.getOfflinePlayer(UUID.fromString(obj + "")).getName() + ": §7" + getAmount(str);
				}
			}
			else if(qp == QuestProperties.RACE) {
				String type = getRace(str);
				s = "§3Töte §7" + getAmount(str) + " §3Spieler der Rasse §8" + type;
			}
		}
		else if(str.startsWith("collect:")) {
			String type = getItemName(str);
			s = "§3Finde " + type + ": §7" + getAmount(str);
		}
		else if(str.startsWith("click:") || str.startsWith("move:")) {
			
			s = "§3Klick & Move müssen noch überarbeitet werden";
		}
		return s;
	}
	
	public static ItemStack getItem(String s) {
		Bukkit.broadcastMessage(s);
		String[] data = split(s.split(",")[0]);
		if(data.length > 1) {
			String type = data[1];
			String sub = null;
			if(data[1].contains(";")) {
				type = data[1].split(";")[0];
				sub = data[1].split(";")[1];
			}
			ItemStack is = new ItemStack(Material.valueOf(type.toUpperCase()));
			if(sub != null) {
				try {
					is.setDurability(Short.parseShort(sub));
				}
				catch(NumberFormatException ex) {
					System.out.print("[AhoriaNPCs] Fehler bei Questanweisung - Item subId ohne Angabe:");
					ex.printStackTrace();
				}
			}
			if(s.contains(",")) {
				String[] nData = s.split(",");
				if(data.length > 1) {
					try {
						is.setAmount(Integer.parseInt(nData[1]));
					}
					catch(NumberFormatException ex) {
						System.out.print("[AhoriaNPCs] Fehler bei Questanweisung - Item subId ohne Angabe:");
						ex.printStackTrace();
					}
					if(nData.length > 2) {
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(nData[2].replace("&", "§"));
						if(nData.length > 3) {
							im.setLore(Arrays.asList(nData[3].replace("&", "§").split(";")));
						}
						is.setItemMeta(im);
					}
				}
			}
			return is;
		}
		return null;
	}
	
	public static EntityType getEntityType(String s) {
		String[] data = split(s);
		if(data.length > 1) {
			if(data[1].contains(",")) {
				return EntityType.valueOf(data[1].split(",")[0].toUpperCase());
			}
			return EntityType.valueOf(data[1].toUpperCase());
		}
		return null;
	}
	
	public static UUID getUniqueId(String s) {
		String[] data = split(s);
		if(data.length > 1) {
			String info = data[1];
			try {
				return UUID.fromString(info);
			}
			catch(IllegalArgumentException ex) {
				System.out.print("[AhoriaNPCs] Fehler bei Questanweisung - UniqueId ohne Angabe:");
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public static Object getPlayer(String s) {
		String[] data = split(s);
		if(data.length > 1) {
			String info = data[1];
			if(info.contains(",")) {
				try {
					return UUID.fromString(info.split(",")[0]);
				}
				catch(IllegalArgumentException ex) {
					return info.split(",")[0];
				}
			}
			else {
				try {
					return UUID.fromString(info);
				}
				catch(IllegalArgumentException ex) {
					return info;
				}
			}
		}
		return null;
	}
	
	public static Location getLocation(String s) {
		String[] data = split(s);
		if(data.length >= 5) {
			World w = Bukkit.getWorld(data[1]);
			if(w != null) {
				return new Location(w, Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]));
			}
		}
		return null;
	}
	
	public static Double getRadius(String s) {
		String[] data = split(s);
		if(data.length > 5) {
			return Double.parseDouble(data[5]);
		}
		return null;
	}
	
	public static String getMonsterDisplayName(String s) {
		String[] data = split(s);
		if(data.length > 1) {
			String dt = data[1].replace("&", "§");
			if(dt.contains(",")) {
				if(dt.split(",").length > 2) {
					return dt.split(",")[2];
				}
			}
		}
		return null;
	}
	
	public static int getAmount(String s) {
		return getAmount(getByString(s), s);
	}	
	
	public static int getAmount(QuestProperties qp, String s) {
		if(qp == QuestProperties.LEVEL || qp == QuestProperties.XP) {
			return getLevel(s);
		}
		else if(qp == QuestProperties.MONSTER || qp == QuestProperties.PLAYER || qp == QuestProperties.ID || qp == QuestProperties.RACE) {
			String data[] = split(s);
			if(data.length > 1) {
				String[] info = data[1].split(",");
				if(info.length > 1) {
					try {
						return Integer.parseInt(info[1]);
					}
					catch(NumberFormatException ex) {
						System.out.print("[AhoriaNPCs] Fehler bei Questanweisung - Mobanzahl ohne Angabe:");
						ex.printStackTrace();
					}
				}
			}
		}
		else if(qp == QuestProperties.ITEM) {
			return getItem(s).getAmount();
		}
		return 1;
	}
	
	public String getShortcut() {
		return shortcut;
	}
	
	public static QuestProperties getByString(String str) {
		str = str.replace("kill:", "").replace("collect:", "").replace("move:", "").replace("click:", "");
		for(QuestProperties qp : values()) {
			if(str.contains(qp.getShortcut())) {
				return qp;
			}
		}
		return null;
	}
	
	public static String[] split(String str) {
		return str.split(getByString(str).getShortcut());
	}
	
}