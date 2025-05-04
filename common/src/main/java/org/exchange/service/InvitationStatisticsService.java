package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exchange.model.InvitationStatistics;

public interface InvitationStatisticsService extends IService<InvitationStatistics> {

    void init(Long userId);

    void updateCount(long userid);

}
