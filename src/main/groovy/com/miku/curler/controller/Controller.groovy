package com.miku.curler.controller

import com.miku.curler.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @Autowired
    Service service

    @PostMapping("/send")
    def send(@RequestBody String json) {
        service.send(json)
        return "Процесс пуска рестов запущен!"
    }

    @PostMapping("/find-json")
    def json(@RequestBody String json) {
        return service."grab from zbduig"(json)
    }

    @PostMapping("/find-file")
    def file(@RequestBody String file) {
        return service.file(file)
    }
}
