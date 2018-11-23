package fr.magrigri;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

class mention {

    mention(){

    }

    void sendMention(AsyncPlayerChatEvent e){
        if( main.getPlugin().getConfig().getString("mentionEnable").equalsIgnoreCase("true")
                && ( e.getPlayer().hasPermission(main.getPlugin().getConfig().getString("mentionPermission")) ||
                main.getPlugin().getConfig().getString("mentionPermission").equalsIgnoreCase("ALL")
        )

        ){
            for (Player item : e.getRecipients()) {
                if(e.getMessage().contains(main.getPlugin().getConfig().getString("mentionTag") + item.getName())) {
                    item.playSound(item.getLocation(), Sound.valueOf(main.getPlugin().getConfig().getString("mentionPlaysound")), 1f, 1f);

                    if(main.getPlugin().getConfig().getString("mentionSendMessage").equalsIgnoreCase("true")){
                        String message =  main.getPlugin().getConfig().getString("MentionMessage").replace("{mentionedPlayer}", item.getName()).replace("{player}", e.getPlayer().getName());
                        String colored = ChatColor.translateAlternateColorCodes('&', message);
                        item.sendMessage(colored);
                    }

                }
            }
        }
    }

}
