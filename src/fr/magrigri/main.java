package fr.magrigri;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


public final class main extends JavaPlugin implements Listener{

    private static main plugin;

    @Override
    public void onEnable() {
        System.out.println("[Over2Chat] - enabled");
        plugin = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    static main getPlugin(){
        return plugin;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){

        Player p = e.getPlayer();

        if(getConfig().getString("topbarEnabled").equalsIgnoreCase("true")){
            topBar topBar = new topBar();
            topBar.sendTopBar(e);
        }

        preffix preffix = new preffix();
        e.setFormat(preffix.getFullMessage(p));

        mention mention = new mention();
        mention.sendMention(e);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(getConfig().getString("OverWritePreffix").equalsIgnoreCase("true")) {
            preffix preffix = new preffix();

            e.getPlayer().setDisplayName(preffix.getPreffix(e.getPlayer()) + e.getPlayer().getName());
            preffix.getKey(e.getPlayer());
            if(getConfig().getString("tabListEnabled").equalsIgnoreCase("true")) {
                Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();
                Team team = scoreboard.getTeam(preffix.getKey(e.getPlayer()));
                if (team == null) {
                    team = scoreboard.registerNewTeam(preffix.getKey(e.getPlayer()));
                }

                team.setPrefix(preffix.getPreffix(e.getPlayer()));
                team.addEntry(e.getPlayer().getName());
            }

        }

    }

}
