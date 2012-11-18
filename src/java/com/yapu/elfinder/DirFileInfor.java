package com.yapu.elfinder;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-7
 * Time: 上午12:17
 * To change this template use File | Settings | File Templates.
 */

/**
 * 当前工作目录信息
 */
public class DirFileInfor {
    private int dirs; //1
    private String hash;// "l1_XA"
    private String phash; //父节点hash
    private int locked; //1
    private String  mime; //"directory"
    private String name;// "files"  当前目录或文件的名称
    private int read;// 1
    private int size;// 0
    private Long ts;// 1346942638
    private String volumeid;// "l1_"
    private int write;// 1
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    /*   "name"   : "Images",                   // (String) name of file/dir. Required
"hash"   : "l0_SW1hZ2Vz",        // (String) hash of current file/dir path, first symbol must be letter, symbols before _underline_ - volume id, Required.
"phash"  : "l0_Lw",               // (String) hash of parent directory. Required except roots dirs.
"mime"   : "directory",          // (String) mime type. Required.
"ts"     : 1334163643,           // (Number) file modification time in unix timestamp. Required.
"date"   : "30 Jan 2010 14:25",  // (String) last modification time (mime). Depricated but yet supported. Use ts instead.
"size"   : 12345,                // (Number) file size in bytes
"childs" : 1,                    // (Number) Only for directories. Marks if directory has child directories inside it. 0 (or not set) - no, 1 - yes. Do not need to calculate amount.
"read"   : 1,                    // (Number) is readable
"write"  : 1,                    // (Number) is writable
"locked" : 0,                    // (Number) is file locked. If locked that object cannot be deleted and renamed
"tmb"    : 'bac0d45b625f8d4633435ffbd52ca495.png' // (String) Only for images. Thumbnail file name, if file do not have thumbnail yet, but it can be generated than it must have value "1"
"alias"  : "files/images",       // (String) For symlinks only. Symlink target path.
"thash"  : "l1_c2NhbnMy",        // (String) For symlinks only. Symlink target hash.
"dim"    : "640x480"             // (String) For images - file dimensions. Optionally.
"volumeid" : "l1_"               // (String) Volume id. For root dir only.*/

    public int getDirs() {
        return dirs;
    }

    public void setDirs(int dirs) {
        this.dirs = dirs;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getMime() {
        return mime;
    }

    public String getPhash() {
        return phash;
    }

    public void setPhash(String phash) {
        this.phash = phash;
    }
    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getVolumeid() {
        return volumeid;
    }

    public void setVolumeid(String volumeid) {
        this.volumeid = volumeid;
    }

    public int getWrite() {
        return write;
    }

    public void setWrite(int write) {
        this.write = write;
    }
}
