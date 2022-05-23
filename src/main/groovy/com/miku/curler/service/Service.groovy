package com.miku.curler.service

import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import groovy.sql.Sql
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

    def ppotsWithIds = ["ppotNizh" : 2537954299099485894,
                        "ppotHakas" : 2537954299099485894,
                        "ppotKir" : 2537954299099485894,
                        "ppotKurg" : 2537954299099485894,
                        "ppotAltKr" : 2537954299099485894,
                        "ppotSam" : 2537954299099485894,
                        "ppotOmsk" : 2537954299099485894,
                        "ppotEvr" : 2537954299099485894,
                        "ppotIrk" : 2537954299099485894,
                        "ppotIng" : 2537954299099485894,
                        "ppotKam" : 2537954299099485894,
                        "ppotPrim" : 2537954299099485894,
                        "ppotKrasnodar" : 2537954299099485894,
                        "ppotAstr" : 2537954299099485894,
                        "ppotZPDOKG" : 2537954299099485894,
                        "ppotMoscow" : 2537954299099485894,
                        "ppotMf" : 2537954299099485894,
                        "ppotCrimea" : 2537954299099485894,
                        "ppotTat" : 2537954299099485894,
                        "ppotSverd" : 2537954299099485894,
                        "ppotNovos" : 2537954299099485894,
                        "ppotChelyab" : 2537954299099485894,
                        "ppotTumen" : 2537954299099485894,
                        "ppotKrasn" : 2537954299099485894,
                        "ppotPerm" : 2537954299099485894,
                        "ppotMos" : 2537954299099485894,
                        "ppotTyva" : 2537954299099485894,
                        "ppotTomsk" : 2537954299099485894,
                        "ppotSarat" : 2537954299099485894,
                        "ppotHabar" : 2537954299099485894,
                        "ppotVolg" : 2537954299099485894,
                        "ppotDag" : 2537954299099485894,
                        "ppotKemer" : 2537954299099485894,
                        "ppotRost" : 2537954299099485894,
                        "ppotNen" : 2537954299099485894,
                        "ppotSahal" : 2537954299099485894,
                        "ppotYNAO" : 2537954299099485894,
                        "ppotZab" : 2537954299099485894,
                        "ppotRost2" : 2537954299099485894,
                        "ppotSahYak" : 2537954299099485894,
                        "ppotUdmur" : 2537954299099485894,
                        "ppotAmur" : 2537954299099485894,
                        "ppotChuk" : 2537954299099485894,
                        "ppotBash" : 2537954299099485894,
                        "ppotBur" : 2537954299099485894,
                        "ppotKalm" : 2537954299099485894,
                        "ppotPiter" : 2537954299099485894,
                        "ppotHMAO" : 2537954299099485894,
                        "ppotMagad" : 2537954299099485894,
                        "ppotStavr" : 2537954299099485894,
                        "ppotUROGO" : 2537954299099485894,
                        "ppotResAltay" : 2537954299099485894,
                        "ppotOren" : 2537954299099485894
    ]

    boolean jar = Service.class.getResource('Service.class').toString().contains("jar")

    def set = {post -> {
        post.setRequestProperty("Content-Type", "application/json")
        post.doOutput = true
        post.requestMethod = 'POST'
    }}

    def spam(String json) {
        def data = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def parsed = JsonSlurper.newInstance().parseText(json)
        for (def ppot: ppots) {
            def message = (parsed.message as String).replaceAll('ppot', "\"${ppot}\"").replaceAll("'", "\"")
            def post = (new URL("http://localhost:${data.app.ext}/${parsed.path}").openConnection() as HttpURLConnection)
            set.call(post)
            post.getOutputStream().write(message.getBytes("UTF-8"))
            def response = post.getResponseCode()
            if (response == 200) {
                log.info("${ppot}: " + post.getInputStream().getText())
            }
            Thread.sleep(parsed.period as Long * 1000)
        }
    }

    def send(String json) {

        def data = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def parsed = new JsonSlurper().parseText(json)
        def message = """{"startDate": ${parsed.from}, "finishDate": ${parsed.to}}"""

        for (def ppot: ppots) {
            def post = (new URL("http://localhost:${data.app.ext}/migration/parent-case-link/${ppot}/${data.app.system}/${data.app.small}/start").openConnection() as HttpURLConnection)
            set.call(post)
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
        log.info("Found rows: ${all}.")
        def count = 0
        def sql = Sql.newInstance(params.db.url, params.db.user, params.db.pw, params.db.driver)
        data.each {person -> {
            def date = (person.date as String).split("\\.")
            def cons = person.mname == 'НЕТ' ? "" : "\n                AND pmd.PATRONYMIC_NAME_CYR = '${person.mname}'"
            def query = """
                SELECT pmd.CITIZENSHIP_COUNTRY_SID as v
                FROM CBDUIGDATA.PET_CITIZENSHIP cit
                INNER JOIN CBDUIGDATA.PERSON_DOCUMENT p_doc ON p_doc.SID = cit.PID
                INNER JOIN CBDUIGDATA.PERSON_MAIN_DATA pmd ON pmd.SID = p_doc.PID
                WHERE pmd.LAST_NAME_CYR = '${person.lname}' 
                AND pmd.FIRST_NAME_CYR = '${person.fname}'${cons} 
                AND pmd.BIRTH_DAY = ${date[0] as Long} 
                AND pmd.BIRTH_MONTH =${date[1] as Long} 
                AND pmd.BIRTH_YEAR =${date[2] as Long}
            """
            sql.rows(query as String).each {writer.write("${person.lname}: ${it.v}\n")}
            log.debug(query)
            log.info("Querying DB: ${count + 1} / ${all}.")
            count = count + 1
        }}
        writer.flush()
        writer.close()
        sql.close()
    }

    def files(String file, boolean csv) {
        def lines, reader
        if (csv) {
            file = new JsonSlurper().parseText(file).file
            reader = new BufferedReader(new FileReader(jar ? file : "src/main/resources/${file}"))
        }
        lines = csv ? reader.readLines() : file.split("\n")
        def json = new ArrayList()
        if (lines.size() == 0) {
            log.info("Blank file.")
        } else {
            def separator = csv ? lines[0].replaceAll("[\\sA-я0-9\\\\/]", "").substring(0, 1) : ","
            lines.each { line ->
                {
                    def data = line.split(separator)
                    if (data.length == 4) {
                        json.add([
                                "lname": data[0],
                                "fname": data[1],
                                "mname": data[2],
                                "date" : data[3].replaceAll("[^0-9.]", ".")
                        ])
                    }
                }
            }
            def a = new JsonGenerator.Options().disableUnicodeEscaping().build().toJson(json)
            "grab from zbduig"(a)
        }
    }

    def "to csv"(String file) {
        file = new JsonSlurper().parseText(file).file
        def reader = new BufferedReader(new FileReader(jar ? file : "src/main/resources/${file}"))
        def lines = reader.readLines()
        def body = ""
        lines.each {line -> {
                                    body = body + line
                                    .replaceAll("[^A-я0-9/.\\-]", " ")
                                    .replaceAll("( )+", " ")
                                    .replaceAll("[\\s]", ",") + "\n"
                            }
        }
        files(body, false)
    }

    def void leha() {
        def slurper = new JsonSlurper()
        def params = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def sql = Sql.newInstance(params.db.leha.url, params.db.leha.user, params.db.leha.pw, params.db.leha.driver)
        def minimal = sql.firstRow("select core_person_document_id as c from core_person_document order by core_person_document_id" as String).c as Long
        def maximal = sql.firstRow("select core_person_document_id as c from core_person_document order by core_person_document_id desc" as String).c as Long
        def threshold = 5000

        for (; minimal < maximal; minimal = minimal + threshold) {
            def count = 0
            def max = minimal + threshold > maximal ? maximal : minimal + threshold
            log.info("Querying ids: ${minimal} - ${max}")
            sql.rows("select core_person_document_id as id, citizenship_id as c from core_person_document where core_person_document_id between ${minimal} and ${max}" as String).each {
                def countryNsi = (new URL("${params.db.leha.nsi}${it.c}").openConnection() as HttpURLConnection)
                countryNsi.requestMethod = 'GET'
                countryNsi.doOutput = true
                if (countryNsi.getResponseCode() == 200) {
                    def inner = slurper.parseText(countryNsi.getInputStream().getText())
                    def oldID = it.c, newID = ""
                    if (inner.catalogCode != "citizenships") {
                        def message = """{
                                        "AND": {
                                            "filters": [
                                                {
                                                    "key": "fullName",
                                                    "values": [
                                                        "${inner.attributeSet.name}"
                                                    ],
                                                    "type": "EXACT"
                                                }
                                            ]
                                        }
                                    }"""
                        def citNsi = (new URL("${params.db.leha.nsiCit}").openConnection() as HttpURLConnection)
                        set.call(citNsi)
                        citNsi.getOutputStream().write(message.getBytes("UTF-8"))
                        if (citNsi.getResponseCode() == 200) {
                            //sql.executeUpdate("update core_person_document set citizenship_id = ${slurper.parseText(citNsi.getInputStream().getText()).content.recordId}")
                            newID = slurper.parseText(citNsi.getInputStream().getText()).content.recordId
                        } else {
                            citNsi.getOutputStream().flush()
                            def shortM = """{
                                        "AND": {
                                            "filters": [
                                                {
                                                    "key": "shortName",
                                                    "values": [
                                                        "${inner.attributeSet.shortName}"
                                                    ],
                                                    "type": "EXACT"
                                                }
                                            ]
                                        }
                                    } """
                            citNsi.getOutputStream().write(shortM.getBytes("UTF-8"))
                            if (citNsi.getResponseCode() == 200) {
                                //sql.executeUpdate("update core_person_document set citizenship_id = ${slurper.parseText(citNsi.getInputStream().getText()).content.recordId}")
                                newID = slurper.parseText(citNsi.getInputStream().getText()).content.recordId
                            }
                        }
                        log.info "CPD #${it.id}: ${oldID} -> ${newID}!"
                    } else {
                        log.info "CPD #${it.id}: ${inner.catalogCode} is already citizenships!"
                    }
                    log.info("Done ${count + 1} / ${max}")
                    count = count + 1
                } else {
                    log.info("Couldn't find ${it.c} value in NSI, CPD #${it.id}")
                }
            }
        }
        log.info "Done!"
    }

    def void "leha 2"() {
        def slurper = new JsonSlurper()
        def params = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def sql = Sql.newInstance(params.db.leha.url, params.db.leha.user, params.db.leha.pw, params.db.leha.driver)
        sql.rows("select distinct citizenship_id as c from core_person_document" as String).each {
            log.info("ID: $it.c")
            def old = it.c
            def countryNsi = (new URL("${params.db.leha.nsi}${it.c}").openConnection() as HttpURLConnection)
            countryNsi.requestMethod = 'GET'
            countryNsi.doOutput = true
            if (countryNsi.getResponseCode() == 200) {
                def inner = slurper.parseText(countryNsi.getInputStream().getText())
                def newID = ""
                if (inner.catalogCode != "citizenships") {
                    def message = """{
                                        "AND": {
                                            "filters": [
                                                {
                                                    "key": "fullName",
                                                    "values": [
                                                        "${inner.attributeSet.name}"
                                                    ],
                                                    "type": "EXACT"
                                                }
                                            ]
                                        }
                                    }"""
                    def citNsi = (new URL("${params.db.leha.nsiCit}").openConnection() as HttpURLConnection)
                    set.call(citNsi)
                    citNsi.getOutputStream().write(message.getBytes("UTF-8"))
                    if (citNsi.getResponseCode() == 200) {
                        //sql.executeUpdate("update core_person_document set citizenship_id = ${slurper.parseText(citNsi.getInputStream().getText()).content.recordId} where citizenship_id = ${old}")
                        newID = slurper.parseText(citNsi.getInputStream().getText()).content.recordId
                    } else {
                        citNsi.getOutputStream().flush()
                        def shortM = """{
                                        "AND": {
                                            "filters": [
                                                {
                                                    "key": "shortName",
                                                    "values": [
                                                        "${inner.attributeSet.shortName}"
                                                    ],
                                                    "type": "EXACT"
                                                }
                                            ]
                                        }
                                    } """
                        citNsi.getOutputStream().write(shortM.getBytes("UTF-8"))
                        if (citNsi.getResponseCode() == 200) {
                            //sql.executeUpdate("update core_person_document set citizenship_id = ${slurper.parseText(citNsi.getInputStream().getText()).content.recordId} where citizenship_id = ${old}")
                            newID = slurper.parseText(citNsi.getInputStream().getText()).content.recordId
                        }
                    }
                    log.info "${old} -> ${newID}!"
                } else {
                    log.info "${it.c} is already in citizenships!"
                }
            } else {
                log.info("Couldn't find ${it.c} value in NSI.")
            }
        }
        log.info "Done!"
    }

    def "find these cases"(String json) {
        def params = new YamlSlurper().parse(jar ? 'application.yml' as File : "src/main/resources/application.yml" as File)
        def sql = Sql.newInstance(params.sanya.db.url, params.sanya.db.user, params.sanya.db.pw, params.sanya.db.driver)
        ppotsWithIds.each { ppot ->
            log.info("Processing ${ppot.key}.")
            def outer = sql.rows("select source_id as s from to_remigration_ppot where source_system_name = '${ppot.key}'" as String)
            def count = outer.size()
            log.info("Found ${count} rows.")
            def i = 0
            outer.each {
                sql.rows("select case_type_id as ct from core_case where source_case_id like '%${"-" + (it.s as String)}' and source_id = ${ppot.value}" as String).each { row ->
                    sql.executeUpdate("update to_remigration_ppot set case_type_id = ${row.ct} where source_id = ${it.c} and source_system_name = '${ppot.key}'" as String)
                }
                log.info("Done in ${ppot.key}: ${i + 1} / ${count}")
                i = i + 1
            }
            log.info("Done with ${ppot.key}")
        }
    }
}
