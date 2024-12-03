package com.journey_back.service;

import com.journey_back.infra.exception.ValidationError;
import com.journey_back.model.LinkModel;
import com.journey_back.repository.LinkRepository;
import com.journey_back.repository.UserRepository;
import com.journey_back.request.LinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksService {

    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private TripService tripService;
    @Autowired
    private UserRepository userRepository;

    // Construtor
    public LinksService(LinkRepository repository) {
        this.linkRepository = repository;
    }

    // Listar Links
    public List<LinkModel> getLinks(Integer tripId) {
        return linkRepository.findByTripId(tripId);
    }

    // Inserir Links
    public LinkModel registerLink(LinkRequest linkRequest, Integer tripId) {
        LinkModel link = new LinkModel();
        link.setUrl(linkRequest.url());
        link.setTitle(linkRequest.title());
        link.setTripId(tripId);

        linkRepository.save(link);

        return link;
    }

    // Atualizar Links
    public LinkModel updateLink(Integer id, LinkRequest linkRequest) {
       var link = linkRepository.findById(id);

       if (link.isPresent()) {
           LinkModel linkBefore = link.get();
           linkBefore.setTitle(linkRequest.title());
           linkBefore.setUrl(linkRequest.url());
           LinkModel newLink = linkBefore;
           linkRepository.save(newLink);
           return newLink;
       } else {
           throw new ValidationError("Link nao cadastrado");
       }
    }

    // Deletar Links
    public boolean deleteLink(Integer id) {
        var link = this.linkRepository.findById(id);

        if (link.isPresent()) {
            linkRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
