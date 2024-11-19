package com.llm.agents.core.chain;

/**
 * @Author Devin
 * @Date 2024/11/19 22:49
 * @Description:
 **/
public enum ChainStatus {
    READY(0), // 未开始执行
    RUNNING(1), // 已开始执行，执行中...
    ERROR(10), //发生错误
    FINISHED_NORMAL(20), //正常结束
    FINISHED_ABNORMAL(21), //错误结束
    ;

    final int value;

    ChainStatus(int value) {
        this.value = value;
    }
}
