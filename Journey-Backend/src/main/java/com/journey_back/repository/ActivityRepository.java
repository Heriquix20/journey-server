package com.journey_back.repository;

import com.journey_back.model.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityModel, Integer> {

    List<ActivityModel> findByTripId(Integer tripId);
}
