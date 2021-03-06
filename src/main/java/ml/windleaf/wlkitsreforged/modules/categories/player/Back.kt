package ml.windleaf.wlkitsreforged.modules.categories.player

import ml.windleaf.wlkitsreforged.modules.CommandInfo
import ml.windleaf.wlkitsreforged.core.enums.LoadType
import ml.windleaf.wlkitsreforged.modules.Module
import ml.windleaf.wlkitsreforged.modules.ModuleInfo
import ml.windleaf.wlkitsreforged.modules.annotations.MustPlayer
import ml.windleaf.wlkitsreforged.modules.annotations.Permission
import ml.windleaf.wlkitsreforged.modules.categories.Category
import ml.windleaf.wlkitsreforged.modules.commanding.ModuleCommand
import ml.windleaf.wlkitsreforged.utils.Util
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

@CommandInfo(cmd = "back", description = "Teleport to your last location", belongTo = Back::class)
@ModuleInfo(category = Category.PLAYER, description = "Teleport to your last location", type = LoadType.ON_STARTUP)
class Back : Module, Listener, ModuleCommand {
    private var enabled = false
    override fun getEnabled() = enabled
    private var tpLogs = HashMap<Player, Location>()

    override fun setEnabled(target: Boolean) {
        enabled = target
    }

    override fun load() {
        enabled = Util.isEnabled(getName())
    }

    @EventHandler
    fun onPlayerTeleportEvent(e: PlayerTeleportEvent) {
        if (enabled) tpLogs[e.player] = e.from
    }

    @MustPlayer
    @Permission("wlkits.cmd.back")
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        sender as Player
        if (tpLogs.containsKey(sender)) {
            sender.teleport(tpLogs[sender]!!)
            Util.send(sender, Util.getModuleMsg(getName(), "success"))
        } else Util.send(sender, Util.getModuleMsg(getName(), "fail"))
    }
}