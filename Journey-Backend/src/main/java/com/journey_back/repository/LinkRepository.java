package com.journey_back.repository;

import com.journey_back.model.LinkModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkModel, Integer> {

    List<LinkModel> findByTripId(Integer tripId);
}
