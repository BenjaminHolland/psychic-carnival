package land.generic

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarEntry
import java.util.jar.JarFile


fun Application.configureRouting() {
    routing {
        get("/") {
            SimpleResourceDumper.main(arrayOf())
            val x = this::class.java.getResourceAsStream("openapi/openapi.yaml")?.reader()?.readText()
            log.info(x)
            call.respondText("Hello, World!")
        }
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

object SimpleResourceDumper {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val classpath = System.getProperty("java.class.path")
        for (entry in classpath.split(File.pathSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            val file: File = File(entry)
            if (file.isDirectory()) {
                val root: Path = file.toPath()
                Files.walk(root).use { stream ->
                    stream.filter(Files::isRegularFile)
                        .map(root::relativize)
                        .filter({ p -> !p.toString().endsWith(".class") })
                        .forEach(System.out::println)
                }
            } else if (file.isFile() && entry.endsWith(".jar")) {
                JarFile(file).use { jar ->
                    jar.stream()
                        .filter { e: JarEntry? -> !e!!.isDirectory() && !e.getName().endsWith(".class") }
                        .forEach { e: JarEntry? -> println(e!!.getName()) }
                }
            }
        }
    }
}