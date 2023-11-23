package com.nasida.core.connector;

/**
 * @author yanasida
 * @date 2023/11/23 16:26
 * 时间毫秒值
 */
public abstract class HeartCheckConnector implements Connector {
    private String nodeIp;
    private Long registerTime;
    private Long lastHbTime;
    private Long expiredTime;

    public boolean isExpired() {
        return System.currentTimeMillis() > expiredTime;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public Long getLastHbTime() {
        return lastHbTime;
    }

    public void setLastHbTime(Long lastHbTime) {
        this.lastHbTime = lastHbTime;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
