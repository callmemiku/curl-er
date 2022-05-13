package com.miku.curler.controller

import com.miku.curler.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @Autowired
    Service service

    @PostMapping("/send")
    def send() {
        service.send()
        return "Процесс пуска рестов запущен!"
    }

}
