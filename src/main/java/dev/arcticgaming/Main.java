package dev.arcticgaming;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CommandManager cmgr = new CommandManager();
        this.getCommand(cmgr.cmd1).setExecutor(cmgr);

        getLogger().info("Dice have been rolled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Dice have been packed!");
    }

    public void getLogger(String log) {
    }
}
