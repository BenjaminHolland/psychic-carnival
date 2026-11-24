package land.generic.event

import com.google.protobuf.kotlin.Timestamp
import com.google.protobuf.kotlin.invoke
import land.generic.calendar.v1.AuditAction
import land.generic.calendar.v1.CreateEventRequest
import land.generic.calendar.v1.CreateEventResponse
import land.generic.calendar.v1.CreationEvent
import land.generic.calendar.v1.DeleteEventRequest
import land.generic.calendar.v1.DeleteEventResponse
import land.generic.calendar.v1.DeletionEvent
import land.generic.calendar.v1.Event

import land.generic.calendar.v1.EventService
import land.generic.calendar.v1.invoke
import kotlin.time.Clock

import kotlin.uuid.Uuid

class EventServiceImpl: EventService {
    override suspend fun createEvent(message: CreateEventRequest): CreateEventResponse {
        val at = Clock.System.now()

        val next = Event {
            id = Uuid.random().toString()
            start = message.start
            end = message.end
            title = message.title
            description = message.description
            tags = message.tags
        }
        Data.events.add(next)
        val audit = CreationEvent{
            eventId = next.id
            `when` = Timestamp{
                seconds = at.epochSeconds
                nanos = at.nanosecondsOfSecond
            }
        }
        Data.audit.add(AuditAction{
            action = AuditAction.Action.Created(value = audit)
        })
        return CreateEventResponse{
            event = next
        }
    }

    override suspend fun deleteEvent(message: DeleteEventRequest): DeleteEventResponse {
        val at = Clock.System.now()
        val result = Data.events.removeIf { it.id ==message.id }
        if(result) {
            Data.audit.add(AuditAction {
                action = AuditAction.Action.Deleted(value = DeletionEvent{
                    eventId = message.id
                    `when` = Timestamp{
                        seconds = at.epochSeconds
                        nanos = at.nanosecondsOfSecond
                    }
                })
            })
        }
        return DeleteEventResponse{
            success = result
        }
    }
}


object Data{
    val events:MutableList<Event> = mutableListOf()
    val audit: MutableList<AuditAction> = mutableListOf()
}