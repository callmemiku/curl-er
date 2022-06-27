package com.miku.curler.controller


import com.miku.curler.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

@Async
@RestController
class Controller {

    @Autowired
    Service service

    @Deprecated
    @PostMapping("/send")
    def send(@RequestBody String json) {
        service.send(json)
        return "Parent-case-link started @ ${LocalDateTime.now()}!"
    }

    @Async
    @PostMapping("/spam-to-app")
    def "reg address domigration"(@RequestBody String json) {
        service.spam(json)
        return "Spam started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/find-json")
    def json(@RequestBody String json) {
        service."grab from zbduig"(json)
        return "ZBD UIG querying started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/find-file")
    def file(@RequestBody String file) {
        service.files(file, true)
        return "ZBD UIG querying started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/find-file-not-csv")
    def "non csv"(@RequestBody String file) {
        service."to csv"(file)
        return "ZBD UIG querying started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/leha")
    def leha(@RequestBody String json) {
        service.leha()
        return "LEHA PRIKOLING started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/leha-2")
    def "leha 2"(@RequestBody String json) {
        service."leha 2"()
        return "LEHA PRIKOLING started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/update-case-types")
    def up(@RequestBody String json) {
        service."find these cases"(json)
        return "Case types update started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/test")
    def nothing() {
        service.genClass()
    }
}
