package events;

import java.util.ArrayList;
import java.util.List;

import main.AhoriaNPCs;
import net.minecraft.server.v1_7_R4.EntityVillager;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MerchantRecipe;
import net.minecraft.server.v1_7_R4.MerchantRecipeList;
import npc.AhoriaNPC;
import npc.EditorPage;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import quest.Quest;
import util.ItemSerialization;

public class InventoryClose implements Listener {

	private AhoriaNPCs main;
	
	public InventoryClose(AhoriaNPCs an) {
		main = an;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player p = (Player)e.getPlayer();
		if(main.stocks.containsKey(p.getUniqueId()) && e.getInventory().getTitle().equalsIgnoreCase("Lager")) {
			FileConfiguration cfg = main.getStock(p);
			cfg.set("inventory", ItemSerialization.toBase64(main.stocks.get(p.getUniqueId())));
			main.saveStock(p, cfg);;
			p.playSound(p.getTargetBlock(null, 5).getLocation(), Sound.CHEST_CLOSE, 0.2F, 1.0F);
		}
		if(e.getInventory().getHolder() instanceof AhoriaNPC) {
			AhoriaNPC npc = (AhoriaNPC) e.getInventory().getHolder();
			EditorPage ep = EditorPage.getByInventory(e.getInventory());
			if(ep == EditorPage.QUESTS) {
				List<String> quests = new ArrayList<String>();
				for(org.bukkit.inventory.ItemStack is : e.getInventory().getContents()) {
					if(is != null) {
						Quest q = main.getQuestByBook(is);
						if(q != null) {
							if(!quests.contains(q.getFileName())) {
								quests.add(q.getFileName());
							}
						}
					}
				}
				npc.getConfig().set("quests", quests);
				npc.saveConfig();
				p.playSound(npc.getBukkitEntity().getEyeLocation(), Sound.VILLAGER_YES, 0.2F, 1F);
			}
			if(npc.isTrader() && EditorPage.getByInventory(e.getInventory()) != null && EditorPage.getByInventory(e.getInventory()) == EditorPage.SHOP) {
				EntityVillager enpc = npc.getEntityVillager();
				Inventory shop = npc.getEditor(EditorPage.SHOP);
				MerchantRecipeList recipes = new MerchantRecipeList();
				for(int i = 0; i < 7; i++) {
					if(shop.getItem(i) != null) {
						ItemStack product = CraftItemStack.asNMSCopy(shop.getItem(i));
						if(product != null) {
							ItemStack price1 = CraftItemStack.asNMSCopy(shop.getItem(i+9));
							ItemStack price2 = CraftItemStack.asNMSCopy(shop.getItem(i+18));
							if(price1 != null && price2 == null) {
								MerchantRecipe recipe = new MerchantRecipe(price1, product);
								recipe.a(999999999);
								recipes.add(recipe);
							}
							else if(price1 == null && price2 != null) {
								MerchantRecipe recipe = new MerchantRecipe(price2, product);
								recipe.a(999999999);
								recipes.add(recipe);
							}
							else if(price1 != null && price2 != null) {
								MerchantRecipe recipe = new MerchantRecipe(price1, price2, product);
								recipe.a(999999999);
								recipes.add(recipe);
							}
						}
					}
				}
				enpc.getOffers(null).clear();
				for(Object mr : recipes) {
					enpc.getOffers(null).add(mr);
				}
				p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 0.1F, 1F);
			}
		}
	}
	
}