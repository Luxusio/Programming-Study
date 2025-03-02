import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import java.util.*
import kotlin.math.abs

fun main() {
    val dataString = getData()
    val mapper = ObjectMapper()
    mapper.registerModules(kotlinModule())

    val data = mapper.readValue(dataString, Data::class.java)
    val towns = data.towns
    val schools = data.schools

    val distances = Array(towns.size) { IntArray(schools.size) }
    val townSchools = PriorityQueue<TownSchool>()
    for (i in towns.indices) {
        for (j in schools.indices) {
            townSchools.add(TownSchool(towns[i], schools[j]))
        }
    }

    // distance가 가장 적은 학교-마을 조합을 찾아서 그 순서대로 빼기 수행
    println(townSchools)

    var score = 0
    townSchools.forEach { townSchool ->
        val town = townSchool.town
        val school = townSchool.school
        val distance = townSchool.distance

        val minusValues = Math.min(school.capacity, town.students)
        school.capacity -= minusValues
        town.students -= minusValues
        score += distance * minusValues
    }

    println("MIN SCORE: $score")
}

data class Data(
    val towns: List<Town>,
    val schools: List<School>
)

data class Town(
    val name: String,
    var students: Int,
    val position: Position
)

data class Position(
    val x: Int,
    val y: Int
)

data class School(
    val name: String,
    val town_students: MutableList<String> = mutableListOf(),
    val position: Position,
    var capacity: Int
)

data class TownSchool(
    val town: Town,
    val school: School
) : Comparable<TownSchool> {
    // root 굳이 안해도 되니까 안할거임
    val distance = abs(town.position.x - school.position.x) +
            abs(town.position.y - school.position.y)

    override fun compareTo(other: TownSchool): Int {
        return distance.compareTo(other.distance)
    }

}

fun getData(): String {
    return """
        {
        "towns": [
        {
        "name": "t_1",
        "students": 969,
        "position": {
        "x": 129,
        "y": 903
        }
        },
        {
        "name": "t_2",
        "students": 1474,
        "position": {
        "x": 378,
        "y": 246
        }
        },
        {
        "name": "t_3",
        "students": 1361,
        "position": {
        "x": 656,
        "y": 276
        }
        },
        {
        "name": "t_4",
        "students": 1017,
        "position": {
        "x": 292,
        "y": 293
        }
        },
        {
        "name": "t_5",
        "students": 1061,
        "position": {
        "x": 462,
        "y": 840
        }
        },
        {
        "name": "t_6",
        "students": 1134,
        "position": {
        "x": 7,
        "y": 352
        }
        },
        {
        "name": "t_7",
        "students": 1195,
        "position": {
        "x": 564,
        "y": 168
        }
        },
        {
        "name": "t_8",
        "students": 1051,
        "position": {
        "x": 745,
        "y": 478
        }
        },
        {
        "name": "t_9",
        "students": 1279,
        "position": {
        "x": 993,
        "y": 271
        }
        },
        {
        "name": "t_10",
        "students": 854,
        "position": {
        "x": 698,
        "y": 603
        }
        },
        {
        "name": "t_11",
        "students": 1135,
        "position": {
        "x": 315,
        "y": 896
        }
        },
        {
        "name": "t_12",
        "students": 1375,
        "position": {
        "x": 90,
        "y": 839
        }
        },
        {
        "name": "t_13",
        "students": 965,
        "position": {
        "x": 177,
        "y": 345
        }
        },
        {
        "name": "t_14",
        "students": 1229,
        "position": {
        "x": 814,
        "y": 331
        }
        },
        {
        "name": "t_15",
        "students": 1239,
        "position": {
        "x": 572,
        "y": 932
        }
        },
        {
        "name": "t_16",
        "students": 815,
        "position": {
        "x": 530,
        "y": 167
        }
        },
        {
        "name": "t_17",
        "students": 1312,
        "position": {
        "x": 847,
        "y": 681
        }
        },
        {
        "name": "t_18",
        "students": 1093,
        "position": {
        "x": 25,
        "y": 714
        }
        },
        {
        "name": "t_19",
        "students": 917,
        "position": {
        "x": 195,
        "y": 950
        }
        },
        {
        "name": "t_20",
        "students": 1063,
        "position": {
        "x": 385,
        "y": 2
        }
        },
        {
        "name": "t_21",
        "students": 970,
        "position": {
        "x": 888,
        "y": 35
        }
        },
        {
        "name": "t_22",
        "students": 926,
        "position": {
        "x": 408,
        "y": 70
        }
        },
        {
        "name": "t_23",
        "students": 931,
        "position": {
        "x": 475,
        "y": 524
        }
        },
        {
        "name": "t_24",
        "students": 877,
        "position": {
        "x": 165,
        "y": 537
        }
        },
        {
        "name": "t_25",
        "students": 1357,
        "position": {
        "x": 942,
        "y": 645
        }
        },
        {
        "name": "t_26",
        "students": 1108,
        "position": {
        "x": 414,
        "y": 891
        }
        },
        {
        "name": "t_27",
        "students": 1035,
        "position": {
        "x": 500,
        "y": 539
        }
        },
        {
        "name": "t_28",
        "students": 1189,
        "position": {
        "x": 310,
        "y": 185
        }
        },
        {
        "name": "t_29",
        "students": 1059,
        "position": {
        "x": 899,
        "y": 777
        }
        },
        {
        "name": "t_30",
        "students": 915,
        "position": {
        "x": 121,
        "y": 895
        }
        },
        {
        "name": "t_31",
        "students": 922,
        "position": {
        "x": 465,
        "y": 886
        }
        },
        {
        "name": "t_32",
        "students": 953,
        "position": {
        "x": 779,
        "y": 895
        }
        },
        {
        "name": "t_33",
        "students": 1240,
        "position": {
        "x": 736,
        "y": 776
        }
        },
        {
        "name": "t_34",
        "students": 460,
        "position": {
        "x": 704,
        "y": 497
        }
        },
        {
        "name": "t_35",
        "students": 801,
        "position": {
        "x": 264,
        "y": 10
        }
        },
        {
        "name": "t_36",
        "students": 1326,
        "position": {
        "x": 917,
        "y": 608
        }
        },
        {
        "name": "t_37",
        "students": 1037,
        "position": {
        "x": 142,
        "y": 371
        }
        },
        {
        "name": "t_38",
        "students": 1120,
        "position": {
        "x": 285,
        "y": 827
        }
        },
        {
        "name": "t_39",
        "students": 952,
        "position": {
        "x": 948,
        "y": 949
        }
        },
        {
        "name": "t_40",
        "students": 1091,
        "position": {
        "x": 326,
        "y": 540
        }
        },
        {
        "name": "t_41",
        "students": 846,
        "position": {
        "x": 14,
        "y": 884
        }
        },
        {
        "name": "t_42",
        "students": 887,
        "position": {
        "x": 358,
        "y": 446
        }
        },
        {
        "name": "t_43",
        "students": 1285,
        "position": {
        "x": 209,
        "y": 221
        }
        },
        {
        "name": "t_44",
        "students": 891,
        "position": {
        "x": 489,
        "y": 148
        }
        },
        {
        "name": "t_45",
        "students": 850,
        "position": {
        "x": 751,
        "y": 750
        }
        },
        {
        "name": "t_46",
        "students": 907,
        "position": {
        "x": 362,
        "y": 764
        }
        },
        {
        "name": "t_47",
        "students": 1022,
        "position": {
        "x": 600,
        "y": 701
        }
        },
        {
        "name": "t_48",
        "students": 1025,
        "position": {
        "x": 408,
        "y": 576
        }
        },
        {
        "name": "t_49",
        "students": 973,
        "position": {
        "x": 602,
        "y": 464
        }
        },
        {
        "name": "t_50",
        "students": 1087,
        "position": {
        "x": 49,
        "y": 333
        }
        },
        {
        "name": "t_51",
        "students": 866,
        "position": {
        "x": 518,
        "y": 33
        }
        },
        {
        "name": "t_52",
        "students": 1155,
        "position": {
        "x": 558,
        "y": 700
        }
        },
        {
        "name": "t_53",
        "students": 1048,
        "position": {
        "x": 116,
        "y": 412
        }
        },
        {
        "name": "t_54",
        "students": 1339,
        "position": {
        "x": 638,
        "y": 998
        }
        },
        {
        "name": "t_55",
        "students": 928,
        "position": {
        "x": 363,
        "y": 493
        }
        },
        {
        "name": "t_56",
        "students": 977,
        "position": {
        "x": 446,
        "y": 760
        }
        },
        {
        "name": "t_57",
        "students": 670,
        "position": {
        "x": 890,
        "y": 702
        }
        },
        {
        "name": "t_58",
        "students": 1089,
        "position": {
        "x": 783,
        "y": 847
        }
        },
        {
        "name": "t_59",
        "students": 1208,
        "position": {
        "x": 46,
        "y": 861
        }
        },
        {
        "name": "t_60",
        "students": 1201,
        "position": {
        "x": 287,
        "y": 799
        }
        },
        {
        "name": "t_61",
        "students": 879,
        "position": {
        "x": 668,
        "y": 98
        }
        },
        {
        "name": "t_62",
        "students": 775,
        "position": {
        "x": 358,
        "y": 523
        }
        },
        {
        "name": "t_63",
        "students": 908,
        "position": {
        "x": 5,
        "y": 247
        }
        },
        {
        "name": "t_64",
        "students": 808,
        "position": {
        "x": 856,
        "y": 375
        }
        },
        {
        "name": "t_65",
        "students": 1020,
        "position": {
        "x": 784,
        "y": 860
        }
        },
        {
        "name": "t_66",
        "students": 1062,
        "position": {
        "x": 493,
        "y": 999
        }
        },
        {
        "name": "t_67",
        "students": 1006,
        "position": {
        "x": 762,
        "y": 937
        }
        },
        {
        "name": "t_68",
        "students": 1247,
        "position": {
        "x": 673,
        "y": 400
        }
        },
        {
        "name": "t_69",
        "students": 501,
        "position": {
        "x": 980,
        "y": 263
        }
        },
        {
        "name": "t_70",
        "students": 880,
        "position": {
        "x": 121,
        "y": 489
        }
        },
        {
        "name": "t_71",
        "students": 1235,
        "position": {
        "x": 32,
        "y": 643
        }
        },
        {
        "name": "t_72",
        "students": 895,
        "position": {
        "x": 892,
        "y": 429
        }
        },
        {
        "name": "t_73",
        "students": 1143,
        "position": {
        "x": 917,
        "y": 960
        }
        },
        {
        "name": "t_74",
        "students": 1040,
        "position": {
        "x": 763,
        "y": 310
        }
        },
        {
        "name": "t_75",
        "students": 1211,
        "position": {
        "x": 411,
        "y": 88
        }
        },
        {
        "name": "t_76",
        "students": 909,
        "position": {
        "x": 79,
        "y": 630
        }
        },
        {
        "name": "t_77",
        "students": 798,
        "position": {
        "x": 93,
        "y": 437
        }
        },
        {
        "name": "t_78",
        "students": 837,
        "position": {
        "x": 956,
        "y": 646
        }
        },
        {
        "name": "t_79",
        "students": 1098,
        "position": {
        "x": 515,
        "y": 791
        }
        },
        {
        "name": "t_80",
        "students": 1074,
        "position": {
        "x": 661,
        "y": 208
        }
        },
        {
        "name": "t_81",
        "students": 1073,
        "position": {
        "x": 857,
        "y": 69
        }
        },
        {
        "name": "t_82",
        "students": 885,
        "position": {
        "x": 474,
        "y": 391
        }
        },
        {
        "name": "t_83",
        "students": 878,
        "position": {
        "x": 298,
        "y": 417
        }
        },
        {
        "name": "t_84",
        "students": 731,
        "position": {
        "x": 727,
        "y": 68
        }
        },
        {
        "name": "t_85",
        "students": 838,
        "position": {
        "x": 949,
        "y": 919
        }
        },
        {
        "name": "t_86",
        "students": 920,
        "position": {
        "x": 59,
        "y": 368
        }
        },
        {
        "name": "t_87",
        "students": 1177,
        "position": {
        "x": 5,
        "y": 517
        }
        },
        {
        "name": "t_88",
        "students": 673,
        "position": {
        "x": 559,
        "y": 580
        }
        },
        {
        "name": "t_89",
        "students": 987,
        "position": {
        "x": 229,
        "y": 558
        }
        },
        {
        "name": "t_90",
        "students": 984,
        "position": {
        "x": 557,
        "y": 841
        }
        },
        {
        "name": "t_91",
        "students": 808,
        "position": {
        "x": 451,
        "y": 238
        }
        },
        {
        "name": "t_92",
        "students": 940,
        "position": {
        "x": 837,
        "y": 298
        }
        },
        {
        "name": "t_93",
        "students": 706,
        "position": {
        "x": 319,
        "y": 867
        }
        },
        {
        "name": "t_94",
        "students": 921,
        "position": {
        "x": 566,
        "y": 245
        }
        },
        {
        "name": "t_95",
        "students": 727,
        "position": {
        "x": 554,
        "y": 512
        }
        },
        {
        "name": "t_96",
        "students": 728,
        "position": {
        "x": 57,
        "y": 107
        }
        },
        {
        "name": "t_97",
        "students": 734,
        "position": {
        "x": 409,
        "y": 219
        }
        },
        {
        "name": "t_98",
        "students": 788,
        "position": {
        "x": 297,
        "y": 381
        }
        },
        {
        "name": "t_99",
        "students": 1142,
        "position": {
        "x": 831,
        "y": 700
        }
        },
        {
        "name": "t_100",
        "students": 973,
        "position": {
        "x": 647,
        "y": 12
        }
        }
        ],
        "schools": [
        {
        "name": "s_1",
        "town_students": [],
        "position": {
        "x": 338,
        "y": 878
        },
        "capacity": 3285
        },
        {
        "name": "s_2",
        "town_students": [],
        "position": {
        "x": 984,
        "y": 856
        },
        "capacity": 3118
        },
        {
        "name": "s_3",
        "town_students": [],
        "position": {
        "x": 589,
        "y": 637
        },
        "capacity": 4593
        },
        {
        "name": "s_4",
        "town_students": [],
        "position": {
        "x": 517,
        "y": 313
        },
        "capacity": 3708
        },
        {
        "name": "s_5",
        "town_students": [],
        "position": {
        "x": 770,
        "y": 617
        },
        "capacity": 3777
        },
        {
        "name": "s_6",
        "town_students": [],
        "position": {
        "x": 473,
        "y": 848
        },
        "capacity": 4080
        },
        {
        "name": "s_7",
        "town_students": [],
        "position": {
        "x": 203,
        "y": 790
        },
        "capacity": 3771
        },
        {
        "name": "s_8",
        "town_students": [],
        "position": {
        "x": 63,
        "y": 698
        },
        "capacity": 4041
        },
        {
        "name": "s_9",
        "town_students": [],
        "position": {
        "x": 977,
        "y": 505
        },
        "capacity": 4215
        },
        {
        "name": "s_10",
        "town_students": [],
        "position": {
        "x": 518,
        "y": 130
        },
        "capacity": 3907
        },
        {
        "name": "s_11",
        "town_students": [],
        "position": {
        "x": 363,
        "y": 336
        },
        "capacity": 3487
        },
        {
        "name": "s_12",
        "town_students": [],
        "position": {
        "x": 683,
        "y": 85
        },
        "capacity": 3103
        },
        {
        "name": "s_13",
        "town_students": [],
        "position": {
        "x": 997,
        "y": 494
        },
        "capacity": 2927
        },
        {
        "name": "s_14",
        "town_students": [],
        "position": {
        "x": 581,
        "y": 796
        },
        "capacity": 3702
        },
        {
        "name": "s_15",
        "town_students": [],
        "position": {
        "x": 346,
        "y": 787
        },
        "capacity": 2273
        },
        {
        "name": "s_16",
        "town_students": [],
        "position": {
        "x": 408,
        "y": 851
        },
        "capacity": 3167
        },
        {
        "name": "s_17",
        "town_students": [],
        "position": {
        "x": 205,
        "y": 914
        },
        "capacity": 3128
        },
        {
        "name": "s_18",
        "town_students": [],
        "position": {
        "x": 945,
        "y": 40
        },
        "capacity": 2398
        },
        {
        "name": "s_19",
        "town_students": [],
        "position": {
        "x": 683,
        "y": 712
        },
        "capacity": 3010
        },
        {
        "name": "s_20",
        "town_students": [],
        "position": {
        "x": 58,
        "y": 296
        },
        "capacity": 3663
        },
        {
        "name": "s_21",
        "town_students": [],
        "position": {
        "x": 756,
        "y": 307
        },
        "capacity": 3400
        },
        {
        "name": "s_22",
        "town_students": [],
        "position": {
        "x": 775,
        "y": 917
        },
        "capacity": 2937
        },
        {
        "name": "s_23",
        "town_students": [],
        "position": {
        "x": 664,
        "y": 610
        },
        "capacity": 3563
        },
        {
        "name": "s_24",
        "town_students": [],
        "position": {
        "x": 255,
        "y": 405
        },
        "capacity": 3128
        },
        {
        "name": "s_25",
        "town_students": [],
        "position": {
        "x": 967,
        "y": 565
        },
        "capacity": 3538
        },
        {
        "name": "s_26",
        "town_students": [],
        "position": {
        "x": 338,
        "y": 847
        },
        "capacity": 2845
        },
        {
        "name": "s_27",
        "town_students": [],
        "position": {
        "x": 744,
        "y": 804
        },
        "capacity": 2977
        },
        {
        "name": "s_28",
        "town_students": [],
        "position": {
        "x": 939,
        "y": 329
        },
        "capacity": 2666
        },
        {
        "name": "s_29",
        "town_students": [],
        "position": {
        "x": 413,
        "y": 799
        },
        "capacity": 2279
        },
        {
        "name": "s_30",
        "town_students": [],
        "position": {
        "x": 484,
        "y": 639
        },
        "capacity": 3314
        }
        ]
        }
    """.trimIndent()
}

fun parse(data: String): Pair<List<Town>, List<School>> {
    val towns = mutableListOf<Town>()
    val schools = mutableListOf<School>()

    val nestingStack = mutableListOf<Char>()
    val iterator = data.chars().iterator()
    while (iterator.hasNext()) {
        val c = iterator.next().toChar()
        when (c) {
            '{' -> {
                nestingStack.add('{')
            }

            '}' -> {
                nestingStack.removeLast()
            }

            '[' -> {
                nestingStack.add('[')
            }

            ']' -> {
                nestingStack.removeLast()
            }

            '"' -> {
                // start key
                val key = getStringValue(iterator)
            }
        }
    }


    return Pair(towns, schools)
}

fun getStringValue(iterator: Iterator<Int>): String {
    val value = StringBuilder()
    var isEscape = false
    while (iterator.hasNext()) {
        val keyChar = iterator.next().toChar()

        if (isEscape) {
            value.append(keyChar)
            isEscape = false
            continue
        }
        if (keyChar == '\\') {
            isEscape = true
            continue
        }
        if (keyChar == '"') {
            break
        }
        value.append(keyChar)
    }
    return value.toString()
}

