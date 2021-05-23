package top.safun.miaosha.redis;

public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();
}
