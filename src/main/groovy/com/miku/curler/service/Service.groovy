package com.miku.curler.service

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer
import groovy.json.JsonGenerator
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.sql.Sql
import groovy.util.logging.Slf4j
import groovy.yaml.YamlSlurper
import org.springframework.stereotype.Component

import java.lang.reflect.ParameterizedType

@Component
@Slf4j
class Service {

    boolean jar = Service.class.getResource('Service.class').toString().contains("jar")

    def send(String json) {

        def data = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def parsed = JsonSlurper.newInstance().parseText(json)
        def message = """{"startDate": ${parsed.from}, "finishDate": ${parsed.to}}"""

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
                     "ppotUROGO"]

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

    def "grab from zbduig"(String json) {

        def params = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def data = new JsonSlurper().parseText(json)
        def writer = new BufferedWriter(new FileWriter('result.csv'))
        def all = data.findAll().size()
        def count = 0
        def sql = Sql.newInstance(params.db.url, params.db.user, params.db.pw, params.db.driver)
        data.each {person -> {
            def date = (person.date as String).split("\\.")
            def query = """
                SELECT pmd.CITIZENSHIP_COUNTRY_SID as v
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
            sql.rows(query as String).each {writer.write(it.v)}
            log.info("Querying DB: ${count} / ${all}.")
            count = count + 1
        }}
        writer.flush()
        writer.close()
    }

    def file(String file) {
        file = new JsonSlurper().parseText(file).file
        def reader = new BufferedReader(new FileReader(jar ? file : "src/main/resources/${file}"))
        def lines = reader.readLines()
        def json = new ArrayList()
        lines.each {line -> {
            def data = line.split(",")
            if (data.length == 4) {
                json.add(["lname" : data[0], "fname" : data[1], "mname" : data[2], "date" : data[3].replaceAll("[^0-9.]", ".")])
            }
        }}

        "grab from zbduig"(new JsonGenerator.Options().disableUnicodeEscaping().build().toJson(json))
    }

}
