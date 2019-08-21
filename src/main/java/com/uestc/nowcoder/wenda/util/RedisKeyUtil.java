package com.uestc.nowcoder.wenda.util;

/**
 * @author CLthinking
 * @date 2019/7/20 下午 10:49
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    // 粉丝
    private static String BIZ_FOLLOWER = "FOLLOWER";
    // 关注对象
    private static String BIZ_FOLLOWEE = "FOLLOWEE";
    // timeline
    private static String BIZ_TIMELINE = "TIMELINE";

    public static String getLikeKey (int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDiSLikeKey (int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    // 一个实体的粉丝，实体可以是人，或者问题，问题的关注者或者人的粉丝，
    // 一个实体由entityType与entityId确定
    public static String getFollowerKey (int entityType, int entityId) {
        return BIZ_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }
    // 一个实体关注了那些东西，：比如一个人关注了那些东西，一个人可以关注问题，也可以关注人
    // 人由userId唯一确定，entityType表示该人关注的实体类型
    public static String getFolloweeKey (int userId, int entityType) {
        return BIZ_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // timeline的redis key
    public static String getTimelineKey(int userId) {
        return BIZ_TIMELINE + SPLIT + userId;
    }

}
