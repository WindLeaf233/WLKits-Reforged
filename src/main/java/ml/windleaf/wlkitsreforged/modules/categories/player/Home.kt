package ml.windleaf.wlkitsreforged.modules.categories.player

import ml.windleaf.wlkitsreforged.modules.ModuleInfo
import ml.windleaf.wlkitsreforged.modules.Module
import ml.windleaf.wlkitsreforged.core.enums.LoadType
import ml.windleaf.wlkitsreforged.core.saving.JsonData
import ml.windleaf.wlkitsreforged.modules.categories.Category
import ml.windleaf.wlkitsreforged.utils.Util
import kotlin.properties.Delegates

@ModuleInfo(category = Category.PLAYER, description = "Allows players to set and delete homes", type = LoadType.ON_STARTUP)
class Home : Module {
    private var enabled = false
    override fun getEnabled() = enabled
    companion object {
        lateinit var homes: JsonData
        var enabled by Delegates.notNull<Boolean>()
    }

    override fun setEnabled(target: Boolean) {
        enabled = target
    }

    override fun load() {
        enabled = Util.isEnabled(getName())
        homes = JsonData("homes")
        Companion.enabled = enabled
    }
}