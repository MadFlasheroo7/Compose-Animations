package pro.jayeshseth.animations.core.model

import kotlinx.coroutines.flow.Flow

interface EasterEggRepository {
    val hasEasterEggEgged: Flow<Boolean>
    suspend fun markTriggered()
}
