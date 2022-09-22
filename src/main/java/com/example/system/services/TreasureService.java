package com.example.system.services;

import com.example.system.dto.geocoder.GeocoderResult;
import com.example.system.dto.treasure.CreateTreasureDto;
import com.example.system.dto.treasure.EditTreasureDto;
import com.example.system.dto.treasure.CreateTreasureFindingDto;
import com.example.system.dto.treasure.UserTreasuresDto;
import com.example.system.entity.Treasure;
import com.example.system.entity.TreasureFinding;
import com.example.system.entity.User;
import com.example.system.enums.MESSAGE_BROKER_REWARD_TYPE;
import com.example.system.exceptions.badrequest.TreasureNotFoundException;
import com.example.system.exceptions.badrequest.WrongTreasureException;
import com.example.system.exceptions.badrequest.WrongUserException;
import com.example.system.repository.TreasureFindingRepository;
import com.example.system.repository.TreasureRepository;
import com.example.system.services.image.ImageService;
import com.example.system.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class TreasureService {
    //#region services and repositories
    private final UserService userService;
    private final IIntegrationService integrationService;
    private final TreasureFindingService treasureFindingService;
    private final ISocialService socialService;
    private final TreasureRepository treasureRepository;
    private final TreasureFindingRepository treasureFindingRepository;
    private final ImageService imageService;
    private final IGeocoderService geocoderService;
    //#endregion

    public UserTreasuresDto getTreasuresForUser(Integer uid) {
        User user = userService.getUserById(uid);
        List<Treasure> foundTreasures = getTreasuresFoundByUser(user);
        List<Treasure> createdTreasures = treasureRepository.findTreasuresByAuthorId(user.getId());
        return new UserTreasuresDto(foundTreasures, createdTreasures);
    }

    public List<Treasure> getAllTreasures() {
        return treasureRepository.findAll();
    }

    public Treasure createTreasure(CreateTreasureDto createTreasureDto) {
        return createTreasure(userService.getCurrentUser(), createTreasureDto);
    }

    @Transactional
    public Treasure createTreasure(User user, CreateTreasureDto createTreasureDto) {
        double latitude = createTreasureDto.getLatitude(), longitude = createTreasureDto.getLongitude();
        List<String> textBlocks = createTreasureDto.getTextBlocks();
        List<String> imageUrls = createTreasureDto.getImageUrls();
        // TODO: DTO image URL validation when receiving requestBody https://www.baeldung.com/spring-dynamic-dto-validation
        imageService.checkForInvalidImageURLs(imageUrls);
        Treasure treasure = new Treasure(user, latitude, longitude, imageUrls, textBlocks);
        GeocoderResult reverseResult = this.geocoderService.reverseRequest(treasure.getLatitude(), treasure.getLongitude());
        treasure.setGeocoderResultData(reverseResult);
        treasure = treasureRepository.saveAndFlush(treasure);
        integrationService.rewardUser(user, treasure, MESSAGE_BROKER_REWARD_TYPE.CREATED_TREASURE);
        return treasure;
    }

    @Transactional
    public Treasure findTreasure(Integer id, CreateTreasureFindingDto createTreasureFindingDto) {
        return findTreasure(id, userService.getCurrentUser(), createTreasureFindingDto);
    }

    @Transactional
    public Treasure findTreasure(Integer id, User user, CreateTreasureFindingDto createTreasureFindingDto) {
        // TODO: DTO image URL validation when receiving requestBody https://www.baeldung.com/spring-dynamic-dto-validation
        imageService.checkForInvalidImageURLs(createTreasureFindingDto.imageUrl);

        Treasure treasure = getTreasureById(id);

        List<Treasure> foundTreasures = getTreasuresFoundByUser(user);
        if(foundTreasures.stream().anyMatch((t)->t.getId() == treasure.getId()))
            throw new WrongTreasureException("User can't find the same treasure multiple times");

        if(user.getId() == treasure.getAuthor().getId())
            throw new WrongUserException("User that created the treasure can't find it");

        TreasureFinding newTreasureFinding = treasureFindingService.createTreasureFinding(createTreasureFindingDto.imageUrl, user, treasure);
        treasure.getUserFindings().add(newTreasureFinding);
        return treasureRepository.saveAndFlush(treasure);
    }

    public Treasure editTreasure(Integer id, EditTreasureDto editTreasureDto) {
        // TODO: DTO image URL validation when receiving requestBody https://www.baeldung.com/spring-dynamic-dto-validation
        imageService.checkForInvalidImageURLs(editTreasureDto.getImageUrls());

        Treasure treasure = getTreasureById(id);
        User curUser = userService.getCurrentUser();
        if(treasure.getAuthor().getId() != curUser.getId())
            throw new WrongUserException("Treasure "+treasure.getId()+" wasn't created by "+curUser.getUsername());

        treasure.setImageUrls(editTreasureDto.getImageUrls());
        treasure.setTextBlocks(editTreasureDto.getTextBlocks());
        return treasureRepository.saveAndFlush(treasure);
    }

    public Treasure getTreasureById(Integer id) {
        return treasureRepository.findById(id).orElseThrow(()->new TreasureNotFoundException(id));
    }

    public List<Treasure> getTreasuresFoundByUser(User user) {
        return treasureFindingRepository
                .findByUser(user).stream()
                .map(TreasureFinding::getTreasure).collect(Collectors.toList());
    }

    public Treasure likeTreasure(Integer id) {
        Treasure treasure = getTreasureById(id);
        User currentUser = userService.getCurrentUser();
        boolean isLiked = treasure.getLikedIds().contains(currentUser.getId());
        return socialService.likeEntity(currentUser.getId(), treasure, treasureRepository, !isLiked);
    }

    public Treasure saveTreasure(Treasure treasure) {
        return treasureRepository.saveAndFlush(treasure);
    }
}
