package ml.windleaf.wlkitsreforged.modules.categories.player

import ml.windleaf.wlkitsreforged.modules.ModuleInfo
import ml.windleaf.wlkitsreforged.core.enums.LoadType
import ml.windleaf.wlkitsreforged.modules.Module
import ml.windleaf.wlkitsreforged.modules.categories.Category
import ml.windleaf.wlkitsreforged.utils.Util
import org.bukkit.entity.Player
import kotlin.properties.Delegates

@ModuleInfo(category = Category.PLAYER, description = "Sends teleport requests and teleports", type = LoadType.ON_STARTUP)
class Tpa : Module {
    private var enabled = false
    override fun getEnabled() = enabled
    companion object {
        var tpaLogs = HashMap<Player, Player>()
        var enabled by Delegates.notNull<Boolean>()
    }

    override fun setEnabled(target: Boolean) {
        enabled = target
    }

    override fun load() {
        enabled = Util.isEnabled(getName())
        Companion.enabled = enabled
    }
}