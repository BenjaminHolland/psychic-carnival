package land.generic.event

import land.generic.calendar.v1.CreateEventRequest
import land.generic.calendar.v1.CreateEventResponse
import land.generic.calendar.v1.EventService

class EventServiceImpl: EventService {
    override suspend fun createEvent(message: CreateEventRequest): CreateEventResponse {
        TODO()
    }
}