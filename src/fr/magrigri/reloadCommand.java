package fr.magrigri;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            main.getPlugin().reloadConfig();
            commandSender.sendMessage("Over2Chat reloaded");
            System.out.print("Over2chat reloaded");
            return true;

        }else{

            main.getPlugin().reloadConfig();
            System.out.print("Over2chat reloaded");

        }

        return true;
    }
}
