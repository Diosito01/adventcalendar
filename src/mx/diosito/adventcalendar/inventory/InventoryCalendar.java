package mx.diosito.adventcalendar.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InventoryCalendar {
    public ArrayList<Inventory> pages = new ArrayList<>();
    public UUID id;
    public int currpage = 0;
    public static HashMap<UUID, InventoryCalendar> users = new HashMap<>();

    //Running this will open a paged inventory for the specified player, with the items in the arraylist specified.
    public InventoryCalendar(ArrayList<ItemStack> items, String name, Player p) {
        this.id = UUID.randomUUID();
        //create new blank page
        Inventory page = getBlankPage(name);
        //According to the items in the arraylist, add items to the ScrollerInventory
        for (int i = 0; i < items.size(); i++) {
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            if (page.firstEmpty() == 46) {
                pages.add(page);
                page = getBlankPage(name);
                page.addItem(items.get(i));
            } else {
                //Add the item to the current page as per normal
                //25
                page.setItem(4, items.get(24));
                //22-24
                page.setItem(14, items.get(23));
                page.setItem(13, items.get(22));
                page.setItem(12, items.get(21));
                //17-21
                page.setItem(24, items.get(20));
                page.setItem(23, items.get(19));
                page.setItem(22, items.get(18));
                page.setItem(21, items.get(17));
                page.setItem(20, items.get(16));
                //10-16
                page.setItem(34, items.get(15));
                page.setItem(33, items.get(14));
                page.setItem(32, items.get(13));
                page.setItem(31, items.get(12));
                page.setItem(30, items.get(11));
                page.setItem(29, items.get(10));
                page.setItem(28, items.get(9));
                //1-9
                page.setItem(44, items.get(8));
                page.setItem(43, items.get(7));
                page.setItem(42, items.get(6));
                page.setItem(41, items.get(5));
                page.setItem(40, items.get(4));
                page.setItem(39, items.get(3));
                page.setItem(38, items.get(2));
                page.setItem(37, items.get(1));
                page.setItem(36, items.get(0));
            }
        }
        pages.add(page);
        //open page 0 for the specified player
        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }


    public static String nextPageName = ChatColor.AQUA + "Página siguiente";
    public static String previousPageName = ChatColor.AQUA + "Página anterior";

    //This creates a blank page with the next and prev buttons
    private Inventory getBlankPage(String name) {
        Inventory page = Bukkit.createInventory(null, 54, name);

        ItemStack nextpage = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE);
        ItemMeta meta = nextpage.getItemMeta();
        meta.setDisplayName(nextPageName);
        nextpage.setItemMeta(meta);

        ItemStack prevpage = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE);
        meta = prevpage.getItemMeta();
        meta.setDisplayName(previousPageName);
        prevpage.setItemMeta(meta);


        //page.setItem(53, nextpage);
        //page.setItem(45, prevpage);
        return page;
    }
}
