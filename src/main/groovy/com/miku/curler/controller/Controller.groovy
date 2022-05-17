package com.miku.curler.controller

import com.miku.curler.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

@RestController
class Controller {
    @Autowired
    Service service

    @PostMapping("/send")
    def send(@RequestBody String json) {
        service.send(json)
        return "Parent-case-link started @ ${LocalDateTime.now()}!"
    }

    @Async
    @PostMapping("/spam-to-app")
    def "reg address domigration"(@RequestBody String json) {
        service."reg address domigration"(json)
        return "Reg address domigration started @ ${LocalDateTime.now()}!"
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
}
