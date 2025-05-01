package org.exchange.constant;

public enum ChangeTypeEnum {

    ADD_BALANCE,// 添加可用
    SUBTRACT_BALANCE, //减少可用
    ADD_FREEZE_BALANCE, // 增加冻结
    SUBTRACT_FREEZE_BALANCE, // 减少冻结
    ADD_LOCK_BALANCE, // 增加锁仓
    SUBTRACT_LOCK_BALANCE, // 减少锁仓
    ADD_BALANCE_SUBTRACT_FREEZE_BALANCE, // 增加可用，减少冻结
    SUBTRACT_BALANCE_ADD_FREEZE_BALANCE, // 减少可用，增加冻结
    ADD_LOCK_BALANCE_SUBTRACT_FREEZE_BALANCE,// 增加锁仓，减少冻结
    SUBTRACT_LOCK_BALANCE_ADD_FREEZE_BALANCE, // 减少锁仓，增加冻结
    ADD_BALANCE_SUBTRACT_LOCK_BALANCE, // 增加可用，减少锁仓
    SUBTRACT_BALANCE_ADD_LOCK_BALANCE, // 减少可用，增加锁仓
    ;


}
