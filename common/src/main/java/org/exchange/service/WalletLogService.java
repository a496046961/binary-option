package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exchange.model.Wallet;
import org.exchange.model.WalletLog;

public interface WalletLogService extends IService<WalletLog> {

    /**
     * 记录钱包变更日志
     * @param oldWallet 变更前的钱包
     * @param newWallet 变更后的钱包
     * @param changeType 变更类型
     * @param businessId 业务ID
     * @param operator 操作人
     * @param ipAddress IP地址
     * @param remark 备注
     */
    void recordWalletChange(Wallet oldWallet, Wallet newWallet,
                            String changeType, String businessId,
                            String operator, String ipAddress, String remark);

}
