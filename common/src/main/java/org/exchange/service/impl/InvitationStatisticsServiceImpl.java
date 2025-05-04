package org.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exchange.mapper.InvitationStatisticsMapper;
import org.exchange.model.InvitationStatistics;
import org.exchange.service.InvitationStatisticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvitationStatisticsServiceImpl extends ServiceImpl<InvitationStatisticsMapper, InvitationStatistics> implements InvitationStatisticsService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InvitationStatisticsServiceImpl.class);

    @Override
    public void init(Long userId) {
        InvitationStatistics invitationStatistics = new InvitationStatistics();
        invitationStatistics.setUserId(userId);
        invitationStatistics.setCreateDate(LocalDateTime.now());
        this.save(invitationStatistics);
    }

    @Override
    public void updateCount(long userid) {
        InvitationStatistics statistics = this.lambdaQuery().eq(InvitationStatistics::getUserId, userid).one();
        statistics.setCount(statistics.getCount() + 1);
        this.updateById(statistics);
    }
}
