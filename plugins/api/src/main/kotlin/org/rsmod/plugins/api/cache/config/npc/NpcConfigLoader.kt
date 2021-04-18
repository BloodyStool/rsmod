package org.rsmod.plugins.api.cache.config.npc

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.rsmod.game.model.npc.type.NpcTypeBuilder
import org.rsmod.plugins.api.cache.config.ConfigFileLoader

private const val ID_KEY = "id"
private const val INHERIT_KEY = "inherit"
private const val PACK_KEY = "pack"
private const val FILE_KEY = "data_file"

class NpcConfigLoader(override val mapper: ObjectMapper) : ConfigFileLoader<NpcConfig> {

    override fun JsonNode.toType(): NpcConfig {
        val builder = mapper.convertValue<NpcTypeBuilder>(this)
        val id = this[ID_KEY].asInt()
        val inherit = if (has(INHERIT_KEY)) this[INHERIT_KEY].asInt() else id
        val pack = if (has(PACK_KEY)) this[PACK_KEY].asBoolean() else true
        val dataFile = if (has(FILE_KEY)) this[FILE_KEY].asText() else null
        return NpcConfig(
            id = id,
            inherit = if (inherit > 0) inherit else null,
            dataFile = dataFile,
            pack = pack,
            builder = builder
        )
    }
}
