package fr.magrigri;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class enableCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            topBar topbar = new topBar();
            topbar.setDisabledRemove(player.getUniqueId().toString());
            return true;
        }

        return false;
    }
}
