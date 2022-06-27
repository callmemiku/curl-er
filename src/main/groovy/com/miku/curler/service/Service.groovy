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

    String genClass() {
        String table = htmlTable()
        HashMap classesMap = []
        classesMap.put("Блок \"Общие данные дела\"", new Tuple("CommonCaseInfo", new ArrayList<>()))
        classesMap.put("Блок Связанные дела\"", new Tuple("LinkedCasesInfo", new ArrayList<>()))
        classesMap.put("Блок \"Данные дела\"", new Tuple("CaseInfo", new ArrayList<>()))
        classesMap.put("Блок Сведения о браке\"", new Tuple("MaritalInfo", new ArrayList<>()))
        classesMap.put("Блок \"Дети\"", new Tuple("KidsInfo", new ArrayList<>()))
        classesMap.put("Блок \"Процедуры дела\"", new Tuple("CaseProceduresInfo", new ArrayList<>()))
        classesMap.put("Блок \"Входящие данные заявителя\"", new Tuple("IncomingApplicantInfo", new ArrayList<>()))
        classesMap.put("Блок \"Исходящие данные заявителя\"", new Tuple("OutgoingApplicantInfo", new ArrayList<>()))
        classesMap.put("Блок \"Внешнее взаимодействие (запросы)\"", new Tuple("ExternalRequestsInfo", new ArrayList<>()))
        classesMap.put("Блок \"Внешнее взаимодействие (выгрузки)\"", new Tuple("ExternalExportsInfo", new ArrayList<>()))
        classesMap.put("Блок \"Семья\"", new Tuple("FamilyInfo", new ArrayList<>()))
        classesMap.put("Блок \"Платежные документы\"", new Tuple("PaymentDocumentsInfo", new ArrayList<>()))
        classesMap.put("Блок \"Сведения о начислениях\"", new Tuple("PaymentsInfo", new ArrayList<>()))
        classesMap.put("Блок \"Электронные копии документов\"", new Tuple("DocumentsDigitalCopyInfo", new ArrayList<>()))
        def lastBlock = null;
        def strings = table.split("\n")
        def arr = []
        def lastField = new Field()
        def countPerField = 0
        strings.each {
            if (lastBlock != null) {
                if (it.contains("</tr>")) {
                }
                lastBlock[1].add(lastField)
                if (it.contains("<tr>")) {
                    lastField = new Field()
                }
            } else {
                def block = it.substring (
                        it.indexOf("strong>") + 7,
                        it.indexOf("</strong>")
                )
                if (block.contains("Блок")) {
                    lastBlock = classesMap.get(block)
                } else if (lastBlock != null) {
                    arr[countPerField] = it.substring(
                            it.indexOf("d\">") + 3,
                            it.indexOf("</td>")
                    )
                    countPerField = countPerField + 1
                }
            }
        }

        println classesMap
    }

    class Field {
        String jsonProperty
        String fieldName
        String table
        String field
        String action
        String commentary
    }

    String htmlTable() {
        return '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Общие данные дела"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Идентификатор дела</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">coreCaseId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">core_case_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Номер дела</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseNo</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_no</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип дела</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseTypeId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Статус дела</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseStatus</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">person_status_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Состояние дела</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseStatusId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_status_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата приема</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">receptionDate</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">reception_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата создания</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createDttm</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">create_dttm</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Подразделение</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">departmentCode</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">departament_code</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Подразделение</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">departmentId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">departament_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Пользователь</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createUser</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">create_user</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Источник данных</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createSupplierId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">source_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Категория поставщика</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">receptionSupplierId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">reception_supplier_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок Связанные дела"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Идентификатор дела</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">coreCaseId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">core_case_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Номер дела</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseNo</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_no</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>ФИО ФЛ/ Наименование ЮЛ</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">fio</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd"><br></td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd"><br></td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Документ</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">documentInfo</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd"><br></td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">docBasisTypeId</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Тип дела</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseTypeId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Статус дела</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><p>personStatusId</p></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">person_status_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Данные дела"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Причина выдачи паспорта</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">caseReasonId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_reason_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дополнительная причина выдачи</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">additApplicationReasonRefId</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">rfp_issuance_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">addit_application_reason_ref_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Место обращения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">applicationPlaceRefId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">rfp_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">application_place_ref_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок Сведения о браке"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Сведения о браке</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">maritalStatusRefId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">rfp_issuance_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">marital_status_ref_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата события</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span>spouseEventDt</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">marital_info</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">spouse_first_dt</td>\n' +
                '    <td class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="confluenceTd">Супруг (а) имя</td>\n' +
                '    <td class="confluenceTd">spouseFirstName</td>\n' +
                '    <td class="confluenceTd">marital_info</td>\n' +
                '    <td class="confluenceTd">spouse_first_name</td>\n' +
                '    <td class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="confluenceTd">Супруг (а) фамилия</td>\n' +
                '    <td class="confluenceTd">spouseLastName</td>\n' +
                '    <td class="confluenceTd">marital_info</td>\n' +
                '    <td class="confluenceTd">spouse_last_name</td>\n' +
                '    <td class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="confluenceTd">Супруг (а) отчество</td>\n' +
                '    <td class="confluenceTd">spouseMiddleName</td>\n' +
                '    <td class="confluenceTd">marital_info</td>\n' +
                '    <td class="confluenceTd">spouse_middle_name</td>\n' +
                '    <td class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="confluenceTd">Супруг (а) дата дня рождения</td>\n' +
                '    <td class="confluenceTd">spouseBirthDt</td>\n' +
                '    <td class="confluenceTd">marital_info</td>\n' +
                '    <td class="confluenceTd">spouse_birth_dt</td>\n' +
                '    <td class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="confluenceTd">Супруг (а) дата брака</td>\n' +
                '    <td class="confluenceTd">spouseEventDt</td>\n' +
                '    <td class="confluenceTd">marital_info</td>\n' +
                '    <td class="confluenceTd">spouse_event_dt</td>\n' +
                '    <td class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Дети"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>ФИО</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">fio</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">last_name + first_name + middle_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Фамилия</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">lastName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">last_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Имя</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">firstName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">first_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Отчество</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">middleName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">middle_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Пол</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">genderId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">gender_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><span>Дата рождения</span></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthDate</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birthday_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Процедуры дела"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Наименование операции</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">coreProcedureId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_procedure</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">operation_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата и время операции</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">operationDttm</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_procedure</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">operation_dttm</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Наименование статуса</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">status</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_procedure</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">status_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата статуса</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">statusDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_procedure</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">status_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">ФИО пользователя</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createUser</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_procedure</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">create_user</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Входящие данные заявителя"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип документа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">typeId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><p><label>Серия</label></p></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">seriesCode</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">series_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><p><label>Номер</label></p></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">docNo</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">doc_no</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Кем выдан</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">issuePlaceDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">issue_place_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Код органа&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">departmentCode</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">authority_code</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Статус документа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">personStatusId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">status_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата выдачи</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">issueDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">issue_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата окончания</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">validToDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">valid_to_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Фамилия</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">lastName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">last_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Имя</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">firstName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">first_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Отчество</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">middleName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">middle_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата рождения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthdayDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birthday_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Пол</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">genderId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">gender_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Гражданство&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">citizenshipId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">citizenship_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Страна рождения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthCountryId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_country_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Регион</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceRegionDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_region_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Район</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceAreaDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_area_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Город</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceCityDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_city_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Населенный пункт</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceLocalityDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_locality_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Место рождения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birthday_place_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Исходящие данные заявителя"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип документа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">typeId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><p><label>Серия</label></p></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">seriesCode</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">series_code</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd"><p><label>Номер</label></p></td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">docNo</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">doc_no</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Кем выдан</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">authorityDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd"><p>authority_desc</p>\n' +
                '        <p>authority_organ_id</p></td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Код органа&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">departmentCode</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">authority_code</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Статус документа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">personStatusId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">status_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата выдачи</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">issueDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">issued_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата окончания</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">validToDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">valid_to_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Фамилия</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">lastName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">last_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Имя</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">firstName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">first_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Отчество</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">middleName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">middle_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата рождения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthdayDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Пол</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">genderId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">gender_cval</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Гражданство&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">citizenshipId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">citizenship_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Страна рождения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthCountryId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_country_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Регион</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceRegionDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_region_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Район</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceAreaDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_area_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Город</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceCityDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_city_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Населенный пункт</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceLocalityDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">birth_place_locality_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Место рождения</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">birthPlaceDesc</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">doc_birth_place_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" title="Цвет фона: Серый" colspan="6"\n' +
                '        data-highlight-colour="grey"><strong>Блок "Внешнее взаимодействие (запросы)"</strong><span><strong><span\n' +
                '            class="url-scheme">http://</span><span class="url-host">172.24.14.130:38081</span></strong><span class="url-filename"><strong>/passport-rf-backend/ext-interaction/2824766720864947329] </strong><br></span></span>\n' +
                '    </td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Ведомство</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">system: name</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">system_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="confluenceTd">ФИО</td>\n' +
                '    <td class="confluenceTd"><span class="treeLabel stringLabel" title="">fio</span></td>\n' +
                '    <td class="confluenceTd">core_person_document</td>\n' +
                '    <td class="confluenceTd">last_name + first_name + middle_name</td>\n' +
                '    <td class="confluenceTd">Просмотр</td>\n' +
                '    <td class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Фамилия</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><br title="">lastName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">last_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Имя</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">firstName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">first_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Отчество&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">middleName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_person_document</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">middle_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">interactionType</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">interaction_type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата создания запроса</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">create_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата отправки запроса</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">requestDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">request_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Ответ</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">responseCval</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">response_cval</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата ответа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">responseDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">response_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата обработки ответа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">checkDt</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">external_interaction</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">check_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" title="Цвет фона: Серый" colspan="6"\n' +
                '        data-highlight-colour="grey"><strong>Блок "Внешнее взаимодействие (выгрузки)"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Идентификатор</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">exportHistorySrcId</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">export_history_src</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">export_history_src_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата события (системная)</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">createDttm</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">export_history_src</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">create_dttm</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата события логическая</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">eventDt</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">export_history_src</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">event_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата завершения выгрузки</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">finishDt</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">export_history_src</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">finish_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип выгрузки</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">typeCval</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">export_history_src</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">type_cval</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Статус</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">statusCval</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">export_history_src</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">status_cval</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Семья"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Мать</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">motherFioName</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">rfp_issuance_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">father_fio_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Отец</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><span class="treeLabel stringLabel">fatherFioName</span></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">rfp_issuance_case</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">mother_fio_name</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Платежные документы"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Льготная категория</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd"><br></td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Реестр платежных документов</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd"><br></td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Назначение платежа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">purposeName</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd"><p>purpose_name</p>\n' +
                '        <p>supplier_bill_uin</p></td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">УИП</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">paymentKey</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">payment_key</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">КБК</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">kbk</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">kbk</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата платежа</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">paymentDttm</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">payment_dttm</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Сумма</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">amountRubCoin</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">amount_rub_coin</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Оплачен</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">statusBool</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">status_bool</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Статус квитирования (ГИС ГМП)</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">billStatusInd</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">payment</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">bill_status_ind</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Сведения о начислениях"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">УИН</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">supplierBillUin</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">charge</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">supplier_bill_uin</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Сумма</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">amountRubCoin</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">charge</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">amount_rub_coin</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип плательщика&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">payerKindMnemonic</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">charge</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">payer_kind_code</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип документа удостоверяющего личность</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd"><p>idDocument" : {</p> <p>"typeMnemonic</p></td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">charge</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">payer_doc_type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td class="highlight-grey confluenceTd" style="width: 99.8724%;" colspan="6" data-highlight-colour="grey"><strong>Блок "Электронные копии документов"</strong></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Тип</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">Name</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_uploaded_application</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">case_type_id</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Номер документа&nbsp;</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">recordId</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_uploaded_application</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">application_no</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Дата добавления</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createDttm</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_uploaded_application</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">reception_dt</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Описание</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">description</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_file_storage</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">file_desc</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Пользователь</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">createUser</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_uploaded_application</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">processed_user</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Электронная почта</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">email</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_contact_info</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">email</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Телефон</td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">phone</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">core_contact_info</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">phone</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр + редактирование</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n' +
                '    <td style="width: 39.6684%;" class="confluenceTd">Согласие гражданина на осуществление оценки качества предоставления государственной услуги </td>\n' +
                '    <td style="width: 12.1811%;" class="confluenceTd">surveyAgreementBool</td>\n' +
                '    <td style="width: 11.4158%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 16.3903%;" class="confluenceTd">???</td>\n' +
                '    <td style="width: 12.6913%;" class="confluenceTd">Просмотр</td>\n' +
                '    <td style="width: 7.52551%;" class="confluenceTd"><br></td>\n' +
                '</tr>\n' +
                '<tr>\n'
    }
}








