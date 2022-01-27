package ml.windleaf.wlkitsreforged.plugins

import ml.windleaf.wlkitsreforged.core.LoadType
import ml.windleaf.wlkitsreforged.core.PermissionType
import ml.windleaf.wlkitsreforged.core.Plugin
import ml.windleaf.wlkitsreforged.core.WLKits
import ml.windleaf.wlkitsreforged.utils.Util
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBedLeaveEvent

class SkipNight : Plugin, Listener {
    private var enabled = false
    override fun getName() = "SkipNight"
    override fun getEnabled() = enabled
    override fun getType() = LoadType.ON_STARTUP
    private var onBed = ArrayList<Player>()

    override fun setEnabled(target: Boolean) {
        enabled = target
    }

    override fun load() {
        enabled = Util.isEnabled(name)
    }

    override fun unload() = Unit
    override fun registers() = Util.registerEvent(this)

    @EventHandler
    fun onPlayerBedEnterEvent(e: PlayerBedEnterEvent) {
        if (enabled
            && (e.player.world.time >= 12010 || e.player.world.isThundering)
            && Util.hasPermission(e.player, "skipnight", PermissionType.ACTION)
        ) {
            if (!onBed.contains(e.player)) onBed.add(e.player)
            val percent = Util.getPluginConfig(name, "percent") as Int
            if (percent < 0 || percent > 100)
                WLKits.log("&cError config &6plugins.skipnight.percent&c!")
            else {
                if ((onBed.size / Bukkit.getOnlinePlayers().size) >= (percent / 100)) {
                    Util.broadcastPlayers(Util.getPluginMsg(name, "msg-ok"))
                    val world: World = e.player.world
                    world.time = 100
                } else {
                    var fakePlayers = 0
                    while ((fakePlayers / Bukkit.getOnlinePlayers().size) < (percent / 100)) fakePlayers += 1
                    Util.broadcastPlayers(Util.insert(Util.getPluginMsg(name, "msg-need"), "onBed" to onBed.size.toString(), "needPlayers" to fakePlayers.toString()))
                }
            }
        }
    }

    @EventHandler
    fun onPlayerBedLeaveEvent(e: PlayerBedLeaveEvent) {
        if (enabled && Util.hasPermission(e.player, "skipnight", PermissionType.ACTION)) onBed.remove(e.player)
    }
}