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
            if(main.getPlugin().getConfig().getString("lang.topbarenabled").isEmpty()){
                commandSender.sendMessage("Top bar has been disabled");
            }else{
                commandSender.sendMessage(main.getPlugin().getConfig().getString("lang.topbarenabled"));
            }

            return true;
        }

        return false;
    }
}
