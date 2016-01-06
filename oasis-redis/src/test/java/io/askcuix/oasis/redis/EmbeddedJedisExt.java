package io.askcuix.oasis.redis;

import com.lordofthejars.nosqlunit.redis.embedded.EmbeddedJedis;

import java.util.List;
import java.util.Set;

/**
 * Extension for EmbeddedJedis to handle some unimplemented jedis method.
 * 
 * @author Chris
 *
 */
public class EmbeddedJedisExt extends EmbeddedJedis {

    public void close() {
        // do nothing
    }

    @Override
    public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return null;
    }

    @Override
    public Long pexpire(byte[] key, long milliseconds) {
        return null;
    }

    @Override
    public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
        return null;
    }

    @Override
    public Double incrByFloat(byte[] key, double value) {
        return null;
    }

    @Override
    public Double hincrByFloat(byte[] key, byte[] field, double value) {
        return null;
    }

    @Override
    public Set<byte[]> spop(byte[] key, long count) {
        return null;
    }

    @Override
    public List<byte[]> srandmember(byte[] key, int count) {
        return null;
    }

    @Override
    public Long zlexcount(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    @Override
    public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Long pfadd(byte[] key, byte[]... elements) {
        return null;
    }

    @Override
    public long pfcount(byte[] key) {
        return 0;
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        return null;
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        return null;
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        return null;
    }

    @Override
    public Double incrByFloat(String key, double value) {
        return null;
    }

    @Override
    public Set<String> spop(String key, long count) {
        return null;
    }

    @Override
    public List<String> srandmember(String key, int count) {
        return null;
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return null;
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        return null;
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        return null;
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        return null;
    }

    @Override
    public Long pfadd(String key, String... elements) {
        return null;
    }

    @Override
    public long pfcount(String key) {
        return 0;
    }
}
