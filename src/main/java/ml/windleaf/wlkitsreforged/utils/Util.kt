package ml.windleaf.wlkitsreforged.utils

import ml.windleaf.wlkitsreforged.core.WLKits
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.io.File

class Util {
    companion object {
        fun translateColorCode(s: String?) = s?.replace("&", "§")
        fun withPrefix() = "&b${WLKits.name} » &r"
        fun getPath() = System.getProperty("user.dir") + File.separator
        fun registerEvent(listener: Listener) = WLKits.instance.server.pluginManager.registerEvents(listener, WLKits.instance)
        fun registerCommand(cmd: String, executor: CommandExecutor) = WLKits.instance.getCommand(cmd)?.setExecutor(executor)
        fun getPluginConfig(pluginName: String, root: String) = WLKits.instance.config.get("plugins.${pluginName.lowercase()}.$root")
        fun getPluginMsg(pluginName: String, root: String) = WLKits.message.getString("${pluginName.lowercase()}.$root")
        fun getPluginMsgAs(pluginName: String, root: String) = WLKits.message.get("${pluginName.lowercase()}.$root")
        fun send(p: CommandSender, s: String?) = p.sendMessage(translateColorCode(withPrefix() + s)!!)
        fun isEnabled(pluginName: String) = getPluginConfig(pluginName, "enabled") as Boolean
        fun disabled(p: CommandSender) = send(p, getPluginMsg("main", "disabled"))
        fun getUUID(p: Player) = p.uniqueId.toString()
        fun invalidArgs(p: CommandSender) = send(p, getPluginMsg("main", "invalid-args"))

        fun mustPlayer(p: CommandSender): Boolean {
            return if (p is Player && p !is ConsoleCommandSender) true
            else {
                send(p, getPluginMsg("main", "must-player"))
                false
            }
        }

        fun insert(string: String?, insertMap: Map<String, String>): String? {
            var s = string
            for (i in insertMap.keys) s = s?.replace("{$i}", insertMap[i]!!)
            return s
        }

        fun broadcastPlayers(string: String?) {
            for (player in Bukkit.getOnlinePlayers()) send(player, string)
        }

        fun sendHelp(sender: CommandSender, helps: Map<String, String>) {
            if (sender is Player) for (i in helps.keys) send(sender, "&8» &6$i &f- &a${helps[i]}".replace("|", "&2|&6"))
            else for (i in helps.keys) send(sender, "&6$i &f- &a${helps[i]}".replace("|", "&2|&6"))
        }
    }
}