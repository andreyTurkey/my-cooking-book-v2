package com.example.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.application.model.PhotoLink;
import com.example.application.repository.PhotoLinkRepository;

import java.util.List;

@Component
public class PhotoLinkService {

    private final PhotoLinkRepository photoLinkRepository;

    public PhotoLinkService(@Autowired PhotoLinkRepository photoLinkRepository) {
        this.photoLinkRepository = photoLinkRepository;
    }

    @Transactional
    public void deleteRecipePhotoLink(Long recipeId) {
        photoLinkRepository.removeAllByRecipeId(recipeId);
    }

    public List<PhotoLink> saveAll(List<PhotoLink> photoLinks) {
        return photoLinkRepository.saveAll(photoLinks);
    }
}
