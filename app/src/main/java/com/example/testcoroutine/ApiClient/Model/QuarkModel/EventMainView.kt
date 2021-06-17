package com.example.testcoroutine.ApiClient.Model.QuarkModel

import com.example.testcoroutine.ApiClient.Model.IResultContainer
import com.google.gson.*
import java.lang.reflect.Type

data class EventMainView(override val status: Int,
                         override val msg: String?,
                    override val data: List<EventMainView.ItemContainer?>?)
    : IResultContainer<List<EventMainView.ItemContainer?>> {

    enum class EventType {
        joinedEvents, ongoingEvents, upcomingEvents, finishedEvents
    }

    interface ItemContainer {
        val type: EventType
    }

    data class RankingEventContainer(override val type: EventType,
                                     val data: List<String>,
                                     val title: String
    ) : ItemContainer


    data class PaginationRankingEventContainer(override val type: EventType,
                                               val data: List<Int>,
                                               val title: String) : ItemContainer

    class EventTypeAdapter : JsonDeserializer<ItemContainer>, JsonSerializer<ItemContainer> {

        @Throws(JsonParseException::class)
        override fun deserialize(elem: JsonElement, interfaceType: Type, context: JsonDeserializationContext): ItemContainer? {
            val member = elem as JsonObject
            val typeString = get(member, "type")
            val actualType = typeForName(typeString)
            return if (actualType != null) {
                context.deserialize(elem, actualType)
            } else null
        }

        private fun typeForName(typeElem: JsonElement): Type? {
            val typeString = typeElem.asString
            if (typeString == EventType.joinedEvents.name
                || typeString == EventType.ongoingEvents.name
                || typeString == EventType.upcomingEvents.name) {
                return RankingEventContainer::class.java
            } else if (typeString == EventType.finishedEvents.name) {
                return PaginationRankingEventContainer::class.java
            }
            return null
        }

        private operator fun get(wrapper: JsonObject, memberName: String): JsonElement {

            return wrapper.get(memberName) ?: throw JsonParseException(
                "no '$memberName' member found in json file.")
        }

        override fun serialize(src: ItemContainer, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
            if (src is RankingEventContainer) {
                return context.serialize(src, RankingEventContainer::class.java)
            }

            return if (src is PaginationRankingEventContainer) {
                context.serialize(src, PaginationRankingEventContainer::class.java)
            } else null
        }
    }
}