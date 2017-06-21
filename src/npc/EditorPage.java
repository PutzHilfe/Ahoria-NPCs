package npc;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum EditorPage {

	MAIN(0, "NPC-Editor", new ItemStack[] { setName(new ItemStack(Material.BOOK_AND_QUILL), "§2Quests"), setName(new ItemStack(Material.DIAMOND), "§2Shop"), setName(new ItemStack(Material.CHEST), "§2Lager"), null, setName(new ItemStack(Material.GHAST_TEAR), "§7UUID anzeigen"), null, setName(new ItemStack(Material.RED_ROSE), "§2Aussehen"), setName(new ItemStack(Material.NAME_TAG), "§7Umbenennen"), setName(new ItemStack(Material.WOOL, 1, (short)14), "§cLöschen")}, 9),
	DELETE(1, "NPC löschen", new ItemStack[] { null, null, setName(new ItemStack(Material.WOOL, 1, (short)5), "§2Ja"), null, null, null, setName(new ItemStack(Material.WOOL, 1, (short)14), "§cNein"), null, null}, 9),
	SHOP(2, "NPC-Shop", new ItemStack[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, setName(new ItemStack(Material.WOOL, 1, (short)5), "§2Aktivieren"), setName(new ItemStack(Material.WOOL, 1, (short)14), "§cDeaktivieren")}, 27),
	STOCK(3, "NPC-Lager", new ItemStack[] { null, null, setName(new ItemStack(Material.WOOL, 1, (short)5), "§2Aktivieren"), null, null, null, setName(new ItemStack(Material.WOOL, 1, (short)14), "§cDeaktivieren"), null, null}, 9),
	LOOK(4, "Aussehen ändern", new ItemStack[] { setName(new ItemStack(Material.IRON_HOE), "§2Farmer"), setName(new ItemStack(Material.BOOK), "§2Bibliothekar"), setName(new ItemStack(Material.FEATHER), "§2Priester"), setName(new ItemStack(Material.ANVIL), "§2Schmied"), setName(new ItemStack(Material.COOKED_CHICKEN), "§2Metzger"), null, null, setName(new ItemStack(Material.NAME_TAG), "§2Namen anzeigen"), setName(new ItemStack(Material.NAME_TAG), "§cNamen ausblenden")}, 5),
	QUESTS(5, "Quests", new ItemStack[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}, 54);
	
	private int id;
	private String name;
	private ItemStack[] items;
	private int size;

	EditorPage(int id, String name, ItemStack[] items, int size) {
		this.id = id;
		this.name = name;
		this.items = items;
		this.size = size;
	}
	
	public int getId() {
		return id;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public static EditorPage getByInventory(Inventory inv) {
		for(EditorPage ep : values()) {
			String[] data = inv.getName().split(":");
			if(data.length == 2) {
				if(ep.getName().equals(data[0])) {
					return ep;
				}
			}
		}
		return null;
	}
	
	public static ItemStack setName(ItemStack is, String name) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack setLore(ItemStack is, String lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(Arrays.asList(lore.split(";")));
		is.setItemMeta(im);
		return is;
	}
	
}