package ru.enwulf.grab;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import ru.enwulf.grab.items.impl.GrabbingItem;
import ru.enwulf.grab.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandListener implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("grab.use") || sender instanceof ConsoleCommandSender) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(CC.translate("&8&o# github.com/enwulf/Grab"));
            player.sendMessage(CC.translate("&e&l - Help"));
            player.sendMessage(CC.translate("/grab reload &7 - &2Reload config"));
            player.sendMessage(CC.translate("/grab item &7 - &2Give item for grabbing"));
            return true;
        }


        switch (args[0].toLowerCase()) {
            case "reload" -> {
                Grab.get().loadConfig();
                Grab.get().initializeMap();
                sender.sendMessage(CC.translate("&aGrab successfully reloaded!"));
            }
            case "item" -> {
                GrabbingItem grabbingItem = new GrabbingItem();
                player.getInventory().addItem(grabbingItem.getItemStack());
            }
            default -> player.sendMessage(CC.translate("&cUnknown subcommand. Use /grab for help."));
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("grab.use") || sender instanceof ConsoleCommandSender || args == null || args.length == 0) {
            return Collections.emptyList();
        }

        var completions = Arrays.asList(
                "reload",
                "item"
        );

        return completions.stream()
                .filter(c -> c.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
    }
}