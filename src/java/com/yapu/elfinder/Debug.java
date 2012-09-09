package com.yapu.elfinder;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-7
 * Time: 上午1:13
 * To change this template use File | Settings | File Templates.
 */
public class Debug {
    private String  connector;//:"php",
    private String  phpver;//:"5.2.5",
    private int time;//:0.28054189682007,
    private String memory;//":"1951Kb \/ 1909Kb \/ 128M",
    private String upload; //:"",
    private List<Volume> volumes;//
    private List<Map<Integer,String>> mountErrors;//:[]
   /* "connector":"php",                     // (String) Тип коннектора
            "phpver"   : "5.3.6",                  // (String) Версия php
            "time"     : 0.0749430656433,          // (Number) Время выполнения скрипта
            "memory"   : "3348Kb / 2507Kb / 128M", // (String) Используемая/пиковая/доступная память
            "volumes"  : [                         // (Array)  Отладка по томам
    {
        "id"         : "l1_",              // (String) ID тома
            "driver"     : "localfilesystem",  // (String) Тип драйвера (имя класса)
            "mimeDetect" : "internal",         // (String) Метод определения mime типов
            "imgLib"     : "gd"                // (String) Библиотека для работы с изображениями
    }

    ],
            "mountErrors" : [                      // (Array) List of errors for not mounted volumes
            0 : "Root folder has not read and write permissions."
            ]*/
}
