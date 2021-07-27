package dev.arcticgaming;

import dev.arcticgaming.utils.tools;
import dev.arcticgaming.values.ArrayManager;
import dev.arcticgaming.values.ModifierManager;
import dev.arcticgaming.values.RollsManager;
import dev.arcticgaming.values.SumManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    public String cmd1 = "roll";
    tools tools = new tools();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if (cmd.getName().equalsIgnoreCase(cmd1)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                //bring in the management classes
                ModifierManager manageModifier = new ModifierManager();
                SumManager manageSum = new SumManager();
                RollsManager manageRolls = new RollsManager();
                ArrayManager manageArray = new ArrayManager();

                //check if modifier is present
                if (args.length < 3) {
                    manageModifier.setModifier(0);
                    tools.log("No modifier, setting to 0");
                }
                if (args.length == 3) {
                    boolean checkMod = new tools().isInteger(args[2]);
                    if (checkMod) {
                        manageModifier.setModifier(Integer.parseInt(args[2]));
                        //player.sendMessage("Debug - Modifier passed validation - its value is " + manageModifier.getModifier());
                        tools.log("Modifier found, value = " + manageModifier.getModifier());
                    } else {
                        manageModifier.setModifier(0);
                        //player.sendMessage("Debug - Modifier failed validation - its being set to 0 for safety");
                        tools.log("Modifier failed validation - value is " + args[2]);
                    }
                }
                //check if arguments are missing
                if (args.length < 2) {
                    player.sendMessage(ChatColor.AQUA + "Dice Tower » " + ChatColor.GOLD + "Not enough arguments - use /roll <#ofDice> d<#ofFaces> <modifier>");
                    tools.log("Roll attempted - too few arguments");
                } else {
                    //command structure now assumed correct, verify supplied arguments are valid
                    boolean numDice = new tools().isInteger(args[0]);
                    boolean numFace = new tools().isInteger(args[1].substring(1));

                    //if integers pass then we move on
                    if (numDice && numFace) {
                        tools.log("Argument validation passed");
                        //parse the total dice as the remaining rolls
                        manageRolls.setRollsRemaining(Integer.parseInt(args[0]),0);
                        //ensures that Sum is zeroed before calculating a sum - potential bug?
                        manageSum.setSum(0,0);
                        //parse the faces requested as an integer
                        int validFace = Integer.parseInt(args[1].substring(1));

                        //begin using managers to figure stuff out!
                        //use our set dice remaining to control the loops
                        while (manageRolls.getRollsRemaining() > 0) {
                            // update rolls remaining to prevent infinite loops
                            manageRolls.setRollsRemaining(manageRolls.getRollsRemaining(),-1);
                            //make a roll and store its value as an integer
                            int lastRoll = manageRolls.makeRolls(validFace);

                            //using the returned roll value - add that to the roll list
                            manageArray.addRoll(lastRoll);
                            //using the returned roll value - add that roll to the previous sum
                            manageSum.setSum(manageSum.getSum(),lastRoll);

                            //player.sendMessage("Debug - verify last roll method: it was " + manageRolls.getLastRollValue() + "\nDebug - verify remaining rolls is working: you have " + manageRolls.getRollsRemaining() + " rolls remaining");
                        }

                        //loops are done, lets supply our values
                        player.sendMessage(ChatColor.AQUA + "Dice Tower » "+ ChatColor.WHITE + "Rolls were: "+ manageArray.getRollList());
                        if (args.length == 3) {
                            player.sendMessage(ChatColor.AQUA + "Dice Tower » "+ ChatColor.WHITE +"Result Modifier: " + manageModifier.getModifier() + "\n" + ChatColor.AQUA + "Dice Tower » " + ChatColor.WHITE + "Roll Sum: " + (manageSum.getSum() + manageModifier.getModifier()));
                        } else {
                            player.sendMessage(ChatColor.AQUA + "Dice Tower » " + ChatColor.WHITE + "Result Sum: " + manageSum.getSum());
                        }
                    } else {
                        //supplied arguments failed - send error :(
                        player.sendMessage(ChatColor.AQUA + "Dice Tower » " + ChatColor.GOLD + "Invalid arguments - use /roll <#ofDice> d<#ofFaces> <modifier>");
                    }
                }
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage(ChatColor.AQUA + "Dice »" + ChatColor.GOLD + " Error! Invalid command. Valid commands are /roll");
                tools.log("Player attempted invalid command?");

            }
        }
        return true;
    }
}
