package mx.diosito.adventcalendar.commands;

import mx.diosito.adventcalendar.inventory.InventoryCalendar;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class AdventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("advent")) {
                if (args.length == 0)
                    openInventory(player);
                else
                    player.sendMessage(ChatColor.RED + "/advent");
            }
        }
        return true;
    }

    public void openInventory(Player player) {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (int i = 1; i < 26; i++) {
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwningPlayer(player);
            skullMeta.setDisplayName("Día " + i);
            itemStack.setItemMeta(skullMeta);
            list.add(itemStack);
        }
        new InventoryCalendar(list, ChatColor.DARK_BLUE + "Calendario de adviento", player);
    }
}
