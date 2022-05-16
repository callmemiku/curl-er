package com.miku.curler.controller

import com.miku.curler.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDate
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

    @PostMapping("/find-json")
    def json(@RequestBody String json) {
        service."grab from zbduig"(json)
        return "ZBD UIG querying started @ ${LocalDateTime.now()}!"
    }

    @PostMapping("/find-file")
    def file(@RequestBody String file) {
        service.file(file)
        return "ZBD UIG querying started @ ${LocalDateTime.now()}!"
    }
}
