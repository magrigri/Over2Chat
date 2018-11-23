package fr.magrigri;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class preffix {

    public preffix(){

    }

    public String getKey(Player p) {

        String[] preffix = new String[3];
        ConfigurationSection sec = main.getPlugin().getConfig().getConfigurationSection("preffix");
        for(String key : sec.getKeys(false)){

            if(main.getPlugin().getConfig().getString("preffix." + key + ".permission") != null) {
                if (p.hasPermission(main.getPlugin().getConfig().getString("preffix." + key + ".permission"))) {

                    String keyCorrect;
                    if(key.length() > 5){
                        System.out.println("WARNING [Over2Chat] - The prefix id '" + key + "' is longer than 5 characters, this may create some errors and break the plugin");
                        keyCorrect = key.substring(0, 5);
                    }else{
                        keyCorrect = key;
                    }

                    if (main.getPlugin().getConfig().getInt("preffix." + key + ".priority") == 0) {
                        preffix[0] = keyCorrect;
                    }
                    if (main.getPlugin().getConfig().getInt("preffix." + key + ".priority") == 1) {
                        preffix[1] = keyCorrect;
                    }
                    if (main.getPlugin().getConfig().getInt("preffix." + key + ".priority") == 2) {
                        preffix[2] = keyCorrect;
                    }
                }
            }

            if(p.getName().equalsIgnoreCase(key)){
                String keyCorrect;
                int keyHash;
                if(key.length() > 15){
                    keyHash = key.hashCode();
                    keyCorrect = (""+keyHash).substring(0, 15);
                }else{
                    keyCorrect = key;
                }
                preffix[0] = keyCorrect;
                preffix[1] = "";
                preffix[2] = "";
                break;
            }else if(p.isOp()){
                preffix[0] = "defaultOperator";
                preffix[1] = "";
                preffix[2] = "";
            }


        }

        if(preffix[0] == null){
            preffix[0] = "";
        }
        if(preffix[1] == null){
            preffix[1] = "";
        }
        if(preffix[2] == null){
            preffix[2] = "";
        }

        return preffix[0] + preffix[1] + preffix[2];


    }

    public String getPreffix(Player p) {

        String[] preffix = new String[3];
        ConfigurationSection sec = main.getPlugin().getConfig().getConfigurationSection("preffix");
        for(String key : sec.getKeys(false)){

            if(main.getPlugin().getConfig().getString("preffix." + key + ".permission") != null) {
                if (p.hasPermission(main.getPlugin().getConfig().getString("preffix." + key + ".permission"))) {
                    if (main.getPlugin().getConfig().getInt("preffix." + key + ".priority") == 0) {
                        preffix[0] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix." + key + ".stringToDisplay"));
                    }
                    if (main.getPlugin().getConfig().getInt("preffix." + key + ".priority") == 1) {
                        preffix[1] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix." + key + ".stringToDisplay"));
                    }
                    if (main.getPlugin().getConfig().getInt("preffix." + key + ".priority") == 2) {
                        preffix[2] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix." + key + ".stringToDisplay"));
                    }
                }
            }

            if(p.getName().equalsIgnoreCase(key)){

                preffix[0] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix." + key + ".prefix0"));
                preffix[1] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix." + key + ".prefix1"));
                preffix[2] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix." + key + ".prefix2"));
                break;
            }else if(p.isOp()){
                preffix[0] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix.defaultOperator.prefix0"));
                preffix[1] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix.defaultOperator.prefix1"));
                preffix[2] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("preffix.defaultOperator.prefix2"));
            }


        }

        if(preffix[0] == null){
            preffix[0] = "";
        }
        if(preffix[1] == null){
            preffix[1] = "";
        }
        if(preffix[2] == null){
            preffix[2] = "";
        }


        return preffix[0] + preffix[1] + preffix[2];


    }

    public String getFullMessage(Player p){

        String separator;
        if(main.getPlugin().getConfig().getString("MessageSeparator") == null){
            separator = ": ";
        }
        else{
            separator = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("MessageSeparator"));
        }

        if(main.getPlugin().getConfig().getString("OverWritePreffix").isEmpty()
                || main.getPlugin().getConfig().getString("OverWritePreffix") == null
                || main.getPlugin().getConfig().getString("OverWritePreffix").equalsIgnoreCase("true")) {

            p.setDisplayName(getPreffix(p) + p.getName());

            String fullPreffix = "%s" + separator + "%s";

            return fullPreffix;

        }else{

            String fullPreffix = getPreffix(p) + "%s" + separator + "%s";
            return fullPreffix;
        }



    }

}


