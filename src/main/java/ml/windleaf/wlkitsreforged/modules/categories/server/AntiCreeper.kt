package ml.windleaf.wlkitsreforged.modules.categories.server

import ml.windleaf.wlkitsreforged.core.enums.LoadType
import ml.windleaf.wlkitsreforged.modules.Module
import ml.windleaf.wlkitsreforged.modules.ModuleInfo
import ml.windleaf.wlkitsreforged.core.WLKits
import ml.windleaf.wlkitsreforged.modules.categories.Category
import ml.windleaf.wlkitsreforged.utils.Util
import org.bukkit.entity.Creeper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import kotlin.properties.Delegates

@ModuleInfo(category = Category.SERVER, description = "Prevents creeper explosions", type = LoadType.ON_STARTUP)
class AntiCreeper : Module, Listener {
    private var enabled = false
    override fun getEnabled() = enabled
    private var notice by Delegates.notNull<Boolean>()

    override fun setEnabled(target: Boolean) {
        enabled = target
    }

    override fun load() {
        enabled = Util.isEnabled(getName())
        notice = Util.getModuleConfig(getName(), "notice") as Boolean
    }

    @EventHandler
    fun event(e: EntityExplodeEvent) {
        if (enabled && e.entity is Creeper) {
            e.isCancelled = true
            if (notice) {
                val loc = e.location
                WLKits.log("&aCreeper exploded at &3X:${loc.blockX}&f, &aY:${loc.blockY}&f, &cZ:${loc.blockZ}&f.")
            }
        }
    }
}