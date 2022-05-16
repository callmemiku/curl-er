package com.miku.curler.service

import groovy.json.JsonSlurper
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

    def analyze(String json) {
        def data = new JsonSlurper().parseText(json)
        def writer = new BufferedWriter(new FileWriter('./result.csv'))
        data.each {person -> {
            def date = (person.date as String).split("\\.")
            def query = """
                SELECT *
                FROM CBDUIGDATA.PET_CITIZENSHIP cit
                INNER JOIN CBDUIGDATA.PERSON_DOCUMENT p_doc ON p_doc.SID = cit.PID
                INNER JOIN CBDUIGDATA.PERSON_MAIN_DATA pmd ON pmd.SID = p_doc.PID
                WHERE pmd.LAST_NAME_CYR = '${person.lname}' 
                AND pmd.FIRST_NAME_CYR = '${person.fname}' 
                AND pmd.PATRONYMIC_NAME_CYR = '${person.mname}' 
                AND pmd.BIRTH_DAY = ${date[0] as Long} 
                AND pmd.BIRTH_MONTH =${date[1] as Long} 
                AND pmd.BIRTH_YEAR =${date[2] as Long};
            """

        }}
    }

}
