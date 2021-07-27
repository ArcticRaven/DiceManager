package dev.arcticgaming.utils;

import dev.arcticgaming.Main;

public class tools {
    Main plugin = Main.getPlugin(Main.class);

    public void log(String log) {
        plugin.getLogger().info(log);
    }

    public boolean isInteger(String checkThis){
        try{
            Integer.parseInt(checkThis);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
