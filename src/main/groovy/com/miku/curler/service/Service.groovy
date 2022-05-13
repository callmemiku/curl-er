package com.miku.curler.service

import groovy.util.logging.Slf4j
import groovy.yaml.YamlSlurper
import org.springframework.stereotype.Component

@Component
@Slf4j
class Service {

    def ppots = ["ppotKurg",
                 "ppotNizh",
                 "ppotAltKr",
                 "ppotAmur",
                 "ppotEvr",
                 "ppotZab",
                 "ppotIrk",
                 "ppotKam",
                 "ppotKemer",
                 "ppotMagad",
                 "ppotNen",
                 "ppotPrim",
                 "ppotResAltay",
                 "ppotCrimea",
                 "ppotTat",
                 "ppotRost",
                 "ppotSahal",
                 "ppotSverd",
                 "ppotTomsk",
                 "ppotTumen",
                 "ppotUdmur",
                 "ppotNovos",
                 "ppotKrasn",
                 "ppotKrasnodar",
                 "ppotMos",
                 "ppotMoscow",
                 "ppotStavr",
                 "ppotSarat",
                 "ppotOmsk",
                 "ppotOren",
                 "ppotPerm",
                 "ppotBash",
                 "ppotBur",
                 "ppotDag",
                 "ppotHabar",
                 "ppotHMAO",
                 "ppotChuk",
                 "ppotYNAO",
                 "ppotKir",
                 "ppotKalm",
                 "ppotTyva",
                 "ppotSahYak",
                 "ppotHakas",
                 "ppotSam",
                 "ppotPiter",
                 "ppotMf",
                 "ppotChelyab",
                 "ppotZPDOKG",
                 "ppotRost2",
                 "ppotIng",
                 "ppotAstr",
                 "ppotVolg",
                 "ppotUROGO"
    ]

    def message = '{"startDate": 1637020800000, "finishDate": 1649980800000}'


    def send() {
        boolean jar = Service.class.getResource('Service.class').toString().contains("jar")
        def data = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        for (def ppot: ppots) {
            def post = (new URL("http://localhost:${data.app.ext}/migration/parent-case-link/${ppot}/${data.app.system}/${data.app.small}/start").openConnection() as HttpURLConnection)
            post.setRequestProperty("Content-Type", "application/json")
            post.doOutput = true
            post.requestMethod = 'POST'
            post.getOutputStream().write(message.getBytes("UTF-8"))
            def response = post.getResponseCode()
            if (response == 200) {
                log.info("${ppot}: " + post.getInputStream().getText())
            }
            Thread.sleep(60 * 1000)
        }
    }

}
