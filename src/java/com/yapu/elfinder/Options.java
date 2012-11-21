package com.yapu.elfinder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-7
 * Time: 上午12:43
 * To change this template use File | Settings | File Templates.
 */
public class Options {
   private String  path;//"files",
    private String url;//":"\/elFinder\/php\/..\/files\/",
    private String  tmbUrl;//":"\/elFinder\/php\/..\/files\/.tmb\/",
    private List disabled ;//:[],
    private String separator;//:"\\",
    private int copyOverwrite;//:1,
    private Archiver archivers; //:{  "create":[],   "extract":[]     }


/*    "path"          : "files/folder42",                                 // (String) Current folder path
            "url"           : "http://localhost/elfinder/files/folder42/",      // (String) Current folder URL
            "tmbURL"        : "http://localhost/elfinder/files/folder42/.tmb/", // (String) Thumbnails folder URL
            "separator"     : "/",                                              // (String) Разделитель пути для текущего тома
            "disabled"      : [],                                               // (Array) List of commands not allowed (disabled) on this volume
            "copyOverwrite" : 1,                                                // (Number) Разрешена или нет перезапись файлов с одинаковыми именами на текущем томе
            "archivers"     : {                                                 // (Object) Настройки архиваторов
        "create"  : [
        0 : "application/x-tar",
                1 : "application/x-gzip"
        ],                                                   // (Array)  Список mime типов архивов, которые могут быть созданы
        "extract" : [
        0 : "application/x-tar",
                1 : "application/x-gzip"
        ]                                                    // (Array)  Список mime типов архивов, которые могут быть распакованы
    }*/

    public List getDisabled() {
        return disabled;
    }

    public void setDisabled(List disabled) {
        this.disabled = disabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTmbUrl() {
        return tmbUrl;
    }

    public void setTmbUrl(String tmbUrl) {
        this.tmbUrl = tmbUrl;
    }



    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public int getCopyOverwrite() {
        return copyOverwrite;
    }

    public void setCopyOverwrite(int copyOverwrite) {
        this.copyOverwrite = copyOverwrite;
    }

    public Archiver getArchivers() {
        return archivers;
    }

    public void setArchivers(Archiver archivers) {
        this.archivers = archivers;
    }
}
