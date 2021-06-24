package mx.diosito.adventcalendar.listeners;

import mx.diosito.adventcalendar.database.Methods;
import mx.diosito.adventcalendar.inventory.InventoryCalendar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class ClicksEvents implements Listener {
    private Methods methods = new Methods();

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        if (!InventoryCalendar.users.containsKey(p.getUniqueId())) return;
        InventoryCalendar inv = InventoryCalendar.users.get(p.getUniqueId());
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if (event.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.DARK_BLUE + "Calendario de adviento")) {
            if (redeem(p, event.getCurrentItem().getItemMeta().getDisplayName())) {
                p.sendMessage(ChatColor.GREEN + "Has reclamado el " + event.getCurrentItem().getItemMeta().getDisplayName());
            } else {
                p.sendMessage(ChatColor.RED + "Ya has reclamado el " + event.getCurrentItem().getItemMeta().getDisplayName());
            }
            p.closeInventory();
        }
    }


    public boolean redeem(Player player, String day) {
        int redeem;
        if (day.length() > 5) {
            redeem = Integer.parseInt(day.substring(4));
        } else {
            redeem = Integer.parseInt(day.substring(4, 5));
        }
        if (methods.isClaimed(player.getUniqueId(), redeem)) {
            return false;
        } else {
            methods.claimDay(player.getUniqueId(), redeem);
            return true;
        }
    }
}
