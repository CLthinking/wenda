package com.uestc.nowcoder.wenda.service;

import com.uestc.nowcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CLthinking
 * @date 2019/7/22 上午 10:14
 */
@Service
public class FollowService {
    @Autowired
    JedisService jedisService;

    // 一个人关注一个实体， userId表示一个人， entityType与entityId确定一个实体
    // 需要做两件事：1、在实体的关注列表followerKey中增加userId，表示哪些人关注了。
    // 2、在用户的被关注列表中followeeKey增加关注的实体，相当于一个用户关注了那些问题
    public boolean follow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date  date = new Date();

        Jedis jedis = jedisService.getJedis();
        Transaction tx = jedisService.multi(jedis);
        // 一个实体被那些用户关注
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        // 一个用户关注了那些实体
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = jedisService.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    public boolean unfollow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date  date = new Date();

        Jedis jedis = jedisService.getJedis();
        // 启动一个事务
        Transaction tx = jedisService.multi(jedis);
        // 取消关注一个问题，从实体中删除用户
        tx.zrem(followerKey, String.valueOf(userId));
        // 是用户关注列表中删除该实体
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = jedisService.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    // 获取粉丝
    public List<Integer> getFollowers (int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisService.zrevrange(followerKey, 0, count));
    }
    private List<Integer> getIdsFromSet(Set<String> idset) {
        return idset.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<Integer> getFollowers (int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisService.zrevrange(followerKey, offset, count));
    }

    // 获取关注的问题
    public List<Integer> getFollowees (int entityType, int entityId, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return getIdsFromSet(jedisService.zrevrange(followeeKey, 0, count));
    }

    public List<Integer> getFollowees (int entityType, int entityId, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return getIdsFromSet(jedisService.zrevrange(followeeKey, offset, count));
    }

    public long getFolloweeCount (int entityId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityId, entityType);
        return jedisService.zcard(followeeKey);
    }
    // 获取粉丝数
    public long getFollowerCount (int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisService.zcard(followerKey);
    }

    // 是否相互关注
    public boolean isFollower (int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisService.zscore(followerKey, String.valueOf(userId)) != 0;
    }
}
