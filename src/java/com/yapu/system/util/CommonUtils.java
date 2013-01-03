package com.yapu.system.util;

import com.yapu.archive.entity.SysDoc;
import com.yapu.elfinder.DirFileInfor;
import com.yapu.system.util.id.UUIDHexGenerator;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class CommonUtils {
	public CommonUtils() {
	}

	private final static Logger log = new Logger(CommonUtils.class);
    private static final String DATE_STYLE = "yyyy-MM-dd";
    private static final String TIME_STYLE = "yyyy-MM-dd HH:mm:ss";

    public static String getMACAddress() throws Exception {
		InputStream in = null;
		String message = null;
		try {
			java.lang.Process proc = Runtime.getRuntime().exec("ipconfig /all");
			in = proc.getInputStream();
			byte[] data = new byte[2048];
			in.read(data);
			String netdata = new String(data);
			return procAll(netdata);
		} catch (IOException e) {
			e.printStackTrace();
			message = e.getMessage();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		throw new Exception("异常错误：" + message + "，导致无法获取MAC地址");
	}

	private static String procAll(String str) {
		return procStringEnd(procFirstMac(procAddress(str)));
	}

	private static String procAddress(String str) {
		int indexof = str.indexOf("Physical Address");
		if (indexof > 0) {
			return str.substring(indexof, str.length());
		}
		return str;
	}

	private static String procFirstMac(String str) {
		int indexof = str.indexOf(":");
		if (indexof > 0) {
			return str.substring(indexof + 1, str.length()).trim();
		}
		return str;
	}

	private static String procStringEnd(String str) {
		int indexof = str.indexOf("\r");
		if (indexof > 0) {
			return str.substring(0, indexof).trim();
		}
		return str;
	}

	/**
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
	public static void parameter2Attribute(HttpServletRequest request) {
		Enumeration enu = request.getParameterNames();
		if (enu != null) {
			String key = null;
			String value = null;
			StringBuffer strBuf = new StringBuffer("Parameter contents:");
			int i = 0;
			while (enu.hasMoreElements()) {
				key = (String) enu.nextElement();
				value = request.getParameter(key);
				strBuf.append("\n  " + (++i) + ") name=" + key + "; value=" + value);
				if (value != null) {
					strBuf.append(";type=" + value.getClass().getName());
				}
				request.setAttribute(key, value);
			}
			logger.info(strBuf.toString());
		}
	}

	private static Logger logger = new Logger("BaseUtil");

	/**
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            String
	 * @return Object
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String name) {
		logger.info("Getting " + name + " from session.");
		Object obj = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			obj = session.getAttribute(name);
		}
		return obj;

	}

	public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
		logger.info("Setting " + name + " of type " + value.getClass().getName() + " on session.");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(name, value);
		}
	}

	public static void removeSessionAttribute(HttpServletRequest request, String name) {
		logger.info("Removing " + name + " from session.");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(name);
		}
	}

	/**
	 * 
	 * @param req
	 *            HttpServletRequest
	 */
	public static void printRequestAttributeContents(HttpServletRequest req) {
		Enumeration enu = req.getAttributeNames();
		StringBuffer strBuf = new StringBuffer("Attribute contents:");
		int i = 0;
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			Object obj = req.getAttribute(name);
			strBuf.append("\n  " + (++i) + ") name=" + name + "; value=" + obj);
			if (obj != null) {
				strBuf.append(";type=" + obj.getClass().getName());
			}
		}
		logger.info(strBuf.toString());
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	public static String filterString(String temp) {
		if (temp == null) {
			return null;
		}
		return temp.replaceAll("'", "''");
	}

	/**
	 * 
	 * @param req
	 *            HttpServletRequest
	 */
	public static void printSessionContents(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			Enumeration enu = session.getAttributeNames();
			StringBuffer strBuf = new StringBuffer("Session contents:");
			int i = 0;
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				Object obj = session.getAttribute(name);
				strBuf.append("\n  " + (++i) + ") name=" + name + "; value=" + obj);
				if (obj != null) {
					strBuf.append(";type=" + obj.getClass().getName());
				}
			}
			logger.info(strBuf.toString());
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    public static String replaceNull2Space(String s) {
        if (s == null)
            return "";
        if (s.trim().toUpperCase().equals("NULL"))
            return "";
        return s.trim();
    }
	public static void closeStream(Closeable stream) {
		if (stream != null)
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) {
		if (value != null && value.trim().length() > 0) {
			Pattern pattern = Pattern.compile("^[+-]?\\d*[.]?\\d*$");
			return pattern.matcher(value).matches();
		}
		return false;
	}

    public static boolean  isPdfPrintType(String filename){
        boolean flag = false;
        filename = filename.toLowerCase();
        if(filename.endsWith(".doc")) flag = true;
        if(filename.endsWith(".docx")) flag = true;
        if(filename.endsWith(".ppt")) flag = true;
        if(filename.endsWith(".pptx")) flag = true;
        if(filename.endsWith(".xls")) flag = true;
        if(filename.endsWith(".xlsx")) flag = true;
       return  flag;
    }

    /**
     * 日期型时间转换为字符串
     * @param dt
     * @return
     */
    public static String Time2String(Date dt) {
        if (dt == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_STYLE);
        try {
            return sdf.format(dt);
        } catch (Exception ex) {
            log.error("==ComUtil:Time2String==：" + ex);
            return "";
        }
    }
    /**
     *	获取应用服务器系统日期、时间
     *	@return	日期+时间字符串。格式“yyyy-mm-dd hh:mm:ss”
     */
    public static  String getTimeStamp() {
        return Time2String(new Date());
    }

    /**
     * 转换文件大小单位 将字节转换为K M G
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static String getId() {
        UUIDHexGenerator uuid = new UUIDHexGenerator();
        return (String) uuid.generate();
    }
    public static DirFileInfor copyDoc2Dfi(SysDoc doc, DirFileInfor dfi){
        dfi.setDirs(0);
        if(doc.getDoctype().equals("1"))  dfi.setDirs(1);
        dfi.setMime(doc.getMime());
        dfi.setHash(doc.getDocid());
        dfi.setLocked(Integer.valueOf(doc.getLocked()));
        dfi.setName(doc.getDocnewname());
        dfi.setRead(Integer.valueOf(doc.getMread()));
        dfi.setSize(Integer.valueOf(doc.getDoclength()));
        dfi.setTs(doc.getMtime());
        dfi.setVolumeid(doc.getDocserverid());
        dfi.setWrite(Integer.valueOf(doc.getMwrite()));
        dfi.setPhash(doc.getParentid());
        dfi.setVolumeid(doc.getDocserverid());
        dfi.setDate(doc.getCreatetime());
        return dfi;
    }
    public static  boolean filesContains(List<DirFileInfor> files,String docId){
        for(DirFileInfor file:files){
            if (file.getHash().equals(docId))
                return true;
        }
       return false;
    }
    public static String getMime(File file) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        Collection<MimeType> mimes = MimeUtil.getMimeTypes(file);
        if (!mimes.isEmpty()) {
            return mimes.iterator().next().toString();
        }
        return new MimetypesFileTypeMap().getContentType(file);
    }

    public static void main(String[] args) {

    }
}