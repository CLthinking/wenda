package com.uestc.nowcoder.wenda.service;

import com.uestc.nowcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CLthinking
 * @date 2019/7/20 下午 10:38
 */
@Service
public class LikeService {
    @Autowired
    JedisService jedisService;

    // 一个人喜欢某个实体，这里使用事务应该更好
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisService.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDiSLikeKey(entityType, entityId);
        jedisService.srem(disLikeKey, String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    // 一个人不喜欢某个实体
    public long disLike(int userId, int entityType, int entityId) {
        String diSLikeKey = RedisKeyUtil.getDiSLikeKey(entityType, entityId);
        jedisService.sadd(diSLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisService.srem(likeKey, String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    /**
     * 一个人对某一个实体的喜欢状态
     * @param userId 用户Id
     * @param entityType 实体类型
     * @param enrityId 实体Id
     * @return 喜欢返回1，不喜欢返回-1，否则返回0
     */
    public int getLikeStatus(int userId, int entityType, int enrityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, enrityId);
        if (jedisService.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDiSLikeKey(entityType, enrityId);
        return jedisService.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    // 一个实体被喜欢的数量
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisService.scard(likeKey);
    }

}
