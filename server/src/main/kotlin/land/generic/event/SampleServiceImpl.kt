package land.generic.event

import land.generic.calendar.v1.ClientGreeting
import land.generic.calendar.v1.SampleService
import land.generic.calendar.v1.ServerGreeting

class SampleServiceImpl : SampleService {
    override suspend fun greeting(name: ClientGreeting): ServerGreeting {
        return object:ServerGreeting {
            override val content: String
                get() =  "Hello, ${name.name}!" }
    }
}
