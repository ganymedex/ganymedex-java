# ganymedex-java

http://blog.csdn.net/win7system/article/details/51160386 redisTemplate 批量操作
https://github.com/Jenyow/xiaozan-parent/blob/d5aa4f465782020473075c6d34c3fc0f1eb4d336/xiaozan-parent/xiaozan-common/src/test/java/com/xiaozan/common/redis/RedisTest.java
package com.xiaozan.common.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.xiaozan.common.redis.RedisUtil;
import com.xiaozan.common.util.ValidateUtil;

public class RedisTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test2() {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
/*
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				String str1 = "str1";
				String str2 = "str2";
				connection.sAdd("SMOOTH".getBytes(), serializer.serialize(str1));
				connection.sAdd("SMOOTH".getBytes(), serializer.serialize(str2));
				return null;
			}
		});
*/
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("SMOOTH");
				connection.sMembers(key);
				return null;
			}
		});
		
		for (Object object : list) {
			@SuppressWarnings("unchecked")
			Set<String> set2 = (Set<String>) object;
			for (String str : set2){
				System.out.println(str);
			}
			
		}
	}
	
	@Test
	public void test4() {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("SMOOTH");
				connection.hGet(key, "aa".getBytes());
				return null;
			}
		});
		
		String str = (String) list.get(0);
		System.out.println(str);
	}
	
	@Test
	public void test5() {
		String str = "1,2";
		Set<String> set = new HashSet<>(Arrays.asList(str.split(",")));
		System.out.println(set.toString());
	}
	
	@Test
	public void test3() throws Exception {
		List<Object> list = getTimeList("111");
		List<String> list2 = (List<String>) list.get(0);
		Set<String> times = new HashSet<>(list2);
		System.out.println(ValidateUtil.isNotEmpty(times));
		for (String string : times) {
			System.out.println(string);
		}
	}
	
	/**
	 * 
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	private List<Object> getTimeList(String policyId) throws Exception {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
		return redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.lRange(serializer.serialize(policyId), 0, -1);
				connection.del(serializer.serialize(policyId));
				return null;
			}
		});
	}
	
/*
	@Test
	public void test1() {
		Jedis jedis = new Jedis("localhost");
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}
*/
	/*
	@Test
	public void test2() {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				List<Map<String, Object>> list = new ArrayList<>();
				Map<String, Object> map1 = new HashMap<>();
				map1.put("hkey1", "value1");
				map1.put("hkey2", "value2");
				list.add(map1);
				for (Map<String, Object> map : list) {
					for (String mapkey : map.keySet()) {  
						byte[] key = serializer.serialize("hash");
						byte[] field = serializer.serialize(mapkey);
						byte[] value = serializer.serialize(map.get(mapkey).toString());
						connection.hSet(key, field, value);
					}  
				}
				return null;
			}
		});
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("hash");
				connection.hGetAll(key);
				return null;
			}
		});
		
		for (Object object : list) {
			System.out.println(object.toString());
		}
	}
	
	@Test
	public void test3() {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringBuffer key = new StringBuffer();
				key.append("SMOOTH");
				key.append(":");
				key.append("4733887");
				connection.sMembers(key.toString().getBytes());
				return null;
			}
		});
		
		System.out.println(list.get(0).toString());
		Set<String> sets = (Set<String>) list.get(0);
		for (String set: sets) {
			System.out.println(set);
		}
			
	}
	@Test
	public void test4() {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.sIsMember("SMOOTH".getBytes(), "4733887".getBytes());
				return null;
			}
		});
		
		System.out.println(list.get(0).toString());
		boolean is = (boolean) list.get(0);
		System.out.println(is);
			
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMembers() {
		Set<String> sets = (Set<String>) members().get(0);
		if (sets == null || sets.isEmpty()) {
			System.out.println("set is null");
		} else {
			System.out.println("set is not null");
			for (String b : sets) {
				System.out.println(b);
			}
			
		}
	
	}
	
	public List<Object> members() {
		RedisTemplate<String, String> redisTemplate = RedisUtil.redisTemplate();
		return redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.sMembers(serializer.serialize("set1"));
				return null;
			}
		});
	}
