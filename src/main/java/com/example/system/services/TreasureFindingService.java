package com.example.system.services;

import com.example.system.entity.Treasure;
import com.example.system.entity.TreasureFinding;
import com.example.system.entity.User;
import com.example.system.entity.UserImage;
import com.example.system.exceptions.badrequest.TreasureFindingNotFoundException;
import com.example.system.repository.TreasureFindingRepository;
import com.example.system.services.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
@RequiredArgsConstructor
public class TreasureFindingService {
    private final ImageService imageService;
    private final TreasureFindingRepository treasureFindingRepository;

    public TreasureFinding createTreasureFinding(String imageUrl, User user, Treasure foundTreasure){
        UserImage image = imageService.getImageByUrl(imageUrl);
        return treasureFindingRepository.save(new TreasureFinding(image,user, foundTreasure));
    }

    public List<TreasureFinding> getAllFindings(){
        return treasureFindingRepository.findAll();
    }

    public TreasureFinding getFindingById(int findingId){
        return treasureFindingRepository.findById(findingId).orElseThrow(()-> new TreasureFindingNotFoundException(findingId));
    }

    public List<TreasureFinding> getUnreviewedFindings(){
        return treasureFindingRepository.findByVerifiedAndDeclined(false, false);
    }
}
