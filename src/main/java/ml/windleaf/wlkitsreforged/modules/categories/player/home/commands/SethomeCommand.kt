package ml.windleaf.wlkitsreforged.modules.categories.player.home.commands

import ml.windleaf.wlkitsreforged.modules.CommandInfo
import ml.windleaf.wlkitsreforged.modules.annotations.MustPlayer
import ml.windleaf.wlkitsreforged.modules.annotations.Permission
import ml.windleaf.wlkitsreforged.modules.commanding.ModuleCommand
import ml.windleaf.wlkitsreforged.modules.categories.player.Home
import ml.windleaf.wlkitsreforged.utils.Util
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandInfo(cmd = "sethome", description = "Set home", belongTo = Home::class)
class SethomeCommand : ModuleCommand {
    @MustPlayer
    @Permission("wlkits.cmd.home")
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        val player = sender as Player
        if (Home.homes.contains(Util.getUUID(player)!!)) Util.send(player, Util.getModuleMsg("Home", "redo"))
        else {
            val sb = StringBuilder()
            val location = player.location
            sb.append(location.world?.name)
                .append(" ").append(location.x)
                .append(" ").append(location.y)
                .append(" ").append(location.z)
            if (Util.getModuleConfig("Home", "set-more") as Boolean)
                sb.append(" ").append(location.yaw).append(" ").append(location.pitch)
            Home.homes[Util.getUUID(player)!!] = sb.toString()
            Home.homes.saveData()
            Util.send(player, Util.getModuleMsg("Home", "success"))
        }
    }
}