package com.avalith.challenge.controller;

import com.avalith.challenge.domain.Area;
import com.avalith.challenge.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("area")
@Api(value = "Area Management System")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @ApiOperation(value = "Search all areas", response = List.class)
    @GetMapping("findAll")
    public List<Area> findAll() {
        return areaService.findAll();
    }

    @ApiOperation(value = "Search for an area by id", response = Area.class)
    @GetMapping("findById/{id}")
    public ResponseEntity<Area> findById(@ApiParam(required = true) @PathVariable Integer id) {
        return ResponseEntity.of(areaService.findById(id));
    }

    @ApiOperation(value = "Save an area", response = Area.class)
    @PostMapping("save")
    public ResponseEntity<Area> save(@RequestBody Area area) {
        return ResponseEntity.of(areaService.save(area));
    }
}
