package top.safun.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {
    @Override
    public int exipreSeconds() {
        return 0;
    }

    @Override
    public String getPrefix() {
        return null;
    }
}
