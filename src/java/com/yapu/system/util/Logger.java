package com.yapu.system.util;
/**
 *	
 *  @author :	wangf
 *  @date:   	2009-3-10  上午12:26:46
 *  @version :
 *  @memo:		日志处理类
 */
public class Logger {
	/**
	 * 声明log4j对象Logger
	 */
	private org.apache.log4j.Logger log;
	/**
	 * 构造函数
	 * @param name   参数为类的名称
	 */
	public Logger(String name){
		log = org.apache.log4j.Logger.getLogger(name);
	}
	/**
	 * 构造函数
	 * @param clazz   参数为类实例
	 */
	public Logger(Class clazz){
		log = org.apache.log4j.Logger.getLogger(clazz);
	}
	
	public void trace(Object object) {
		log.debug(object);
	}

	public void trace(Object object, Throwable throwable) {
		throwable.printStackTrace();
		log.debug(object, throwable);
	}

	/**
	 * 调试信息
	 * @param object
	 */
	public void debug(Object object) {
		log.debug(object);
	}

	public void debug(Object object, Throwable throwable) {
		throwable.printStackTrace();
		log.debug(object, throwable);
	}

	/**
	 * 信息
	 * @param object
	 */
	public void info(Object object) {
		log.info(object);
	}

	public void info(Object object, Throwable throwable) {
		throwable.printStackTrace();
		log.info(object, throwable);
	}

	/**
	 * 注意
	 * @param object
	 */
	public void warn(Object object) {
		log.warn(object);
	}

	public void warn(Object object, Throwable throwable) {
		throwable.printStackTrace();
		log.warn(object, throwable);

	}

	/**
	 * 错误
	 * @param object
	 */
	public void error(Object object) {
		log.error(object);
	}

	public void error(Object object, Throwable throwable) {
		throwable.printStackTrace();
		log.error(object, throwable);
	}

	public void fatal(Object object) {
		log.fatal(object);
	}

	public void fatal(Object object, Throwable throwable) {
		throwable.printStackTrace();
		log.fatal(object, throwable);
	}
	
	
	
	
}

