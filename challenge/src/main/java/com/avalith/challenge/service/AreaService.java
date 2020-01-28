package com.avalith.challenge.service;

import com.avalith.challenge.domain.Area;
import com.avalith.challenge.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public Optional<Area> save(Area area) {
        return Optional.of(areaRepository.save(area));
    }

    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    public Optional<Area> findById(Integer id) {
        return areaRepository.findById(id);
    }
}
