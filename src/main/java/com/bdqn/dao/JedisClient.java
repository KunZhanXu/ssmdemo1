package com.bdqn.dao;

public interface JedisClient {
	/**
	 * 新增
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key, String value);

	/**
	 * 查询
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 判断是否存在这个key
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 给key设置时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);

}