package fr.magrigri;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class topBar {

    private static String lastPname = "empty";
    private static ArrayList<String> disabled = new ArrayList<>();


    void getDisabledFromConfig(){
        List<String> sec = main.getPlugin().getConfig().getStringList("disabled");
        for(String key : sec) {

            setDisabled(key);


        }


    }

    void setDisabledToConfig(){
        if(disabled.isEmpty()){
            return;
        }
        System.out.print("[Over2Chat] Writing disabled uuid to config file in disabled section..");
        main.getPlugin().getConfig().set("disabled", disabled);
        main.getPlugin().saveConfig();
    }


    void setDisabled(String playername){
        if(!disabled.contains(playername)){
            disabled.add(playername);
        }

    }

    void setDisabledRemove(String playername){
        if(disabled.contains(playername)) {
            disabled.remove(playername);
        }
    }

    private String placeHolder(String str, Player p){

        String colored = ChatColor.translateAlternateColorCodes('&', str);
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(main.getPlugin().getConfig().getString("timeFormat"));
        return colored.replace("{date}", format.format(now)).replace("{name}", p.getName());
    }

    TextComponent getTopBar(Player p, Player item){

        TextComponent barMessage = new TextComponent("");

        ConfigurationSection sec = main.getPlugin().getConfig().getConfigurationSection("topbar");
        for(String key : sec.getKeys(false)) {

            String toDisplay = placeHolder(main.getPlugin().getConfig().getString("topbar." + key + ".toDisplay"), p);
            String onHover = placeHolder(main.getPlugin().getConfig().getString("topbar." + key + ".onHover"), p);
            String onClickEvent = main.getPlugin().getConfig().getString("topbar." + key + ".onClickEvent");
            String onClickValue = placeHolder(main.getPlugin().getConfig().getString("topbar." + key + ".onClickValue"), p);
            String permission = main.getPlugin().getConfig().getString("topbar." + key + ".permission");

            if(item.hasPermission(permission) || permission.equalsIgnoreCase("ALL")){

                TextComponent textComponent = new TextComponent(toDisplay);

                if(!onHover.isEmpty()){
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(onHover).create()));
                }

                if(onClickEvent.equalsIgnoreCase("run")){
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, onClickValue));
                }
                else if(onClickEvent.equalsIgnoreCase("suggest")){
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, onClickValue));
                }
                else if (onClickEvent.equalsIgnoreCase("openurl")){
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, onClickValue));
                }

                barMessage.addExtra(textComponent);
            }

        }

        return barMessage;
    }

    void sendTopBar(AsyncPlayerChatEvent e){

        if(!main.getPlugin().getConfig().getString("groupTheMessages").equalsIgnoreCase("true")){
            for (Player item : e.getRecipients()) {
                if(!disabled.contains(item.getUniqueId().toString())) {
                    item.spigot().sendMessage(getTopBar(e.getPlayer(), item));
                }
            }
        }else {
            if (lastPname.equalsIgnoreCase(e.getPlayer().getName())) {
                lastPname = e.getPlayer().getName();
            } else {
                lastPname = e.getPlayer().getName();
                for (Player item : e.getRecipients()) {
                    if(!disabled.contains(item.getUniqueId().toString())) {
                        item.spigot().sendMessage(getTopBar(e.getPlayer(), item));
                    }
                }
            }
        }

    }

}
