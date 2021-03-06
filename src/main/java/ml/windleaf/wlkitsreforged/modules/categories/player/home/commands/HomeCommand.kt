package ml.windleaf.wlkitsreforged.modules.categories.player.home.commands

import ml.windleaf.wlkitsreforged.modules.CommandInfo
import ml.windleaf.wlkitsreforged.modules.annotations.MustPlayer
import ml.windleaf.wlkitsreforged.modules.annotations.Permission
import ml.windleaf.wlkitsreforged.modules.commanding.ModuleCommand
import ml.windleaf.wlkitsreforged.modules.categories.player.Home
import ml.windleaf.wlkitsreforged.utils.Util
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandInfo(cmd = "home", description = "Back to home", belongTo = Home::class)
class HomeCommand : ModuleCommand {
    @MustPlayer
    @Permission("wlkits.cmd.home")
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        val player = sender as Player
        if (Home.homes.contains(Util.getUUID(player)!!)) {
            val locationString = Home.homes[Util.getUUID(player)!!]!! as String
            val array = locationString.split("\\s+".toRegex()).toTypedArray()
            val world = Util.getWorldByName(array[0])
            val x = array[1].toDouble()
            val y = array[2].toDouble()
            val z = array[3].toDouble()
            if (array.size == 6) {
                val yaw = array[4].toFloat()
                val pitch = array[5].toFloat()
                player.teleport(Location(world, x, y, z, yaw, pitch))
            } else player.teleport(Location(world, x, y, z))
            Util.send(player, Util.getModuleMsg("Home", "back-home"))
        } else Util.send(player, Util.getModuleMsg("Home", "no-home"))
    }
}