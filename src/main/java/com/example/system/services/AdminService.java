package com.example.system.services;

import com.example.system.entity.TreasureFinding;
import com.example.system.enums.MESSAGE_BROKER_REWARD_TYPE;
import com.example.system.exceptions.badrequest.TreasureFindingNotFoundException;
import com.example.system.repository.TreasureFindingRepository;
import com.example.system.services.interfaces.IIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final TreasureFindingRepository treasureFindingRepository;
    private final TreasureFindingService treasureFindingService;
    private final IIntegrationService integrationService;

    @Transactional
    public TreasureFinding verifyOrDeclineFinding(int id, boolean isVerify) {
        TreasureFinding finding = treasureFindingService.getFindingById(id);
        if (finding == null) throw new TreasureFindingNotFoundException(id);
        if (isVerify) finding.verify(); else finding.decline();
        treasureFindingRepository.saveAndFlush(finding);
        if (isVerify) integrationService.rewardUser(finding.getUser(), finding.getTreasure(), MESSAGE_BROKER_REWARD_TYPE.FOUND_TREASURE);
        return finding;
    }

    public List<TreasureFinding> getUnverifiedFindings() {
        return treasureFindingService.getUnreviewedFindings();
    }
}
