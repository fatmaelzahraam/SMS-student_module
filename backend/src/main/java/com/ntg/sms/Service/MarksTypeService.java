package com.ntg.sms.Service;


import com.ntg.sms.Entities.Dtos.Request.MarksTypeRequest;
import com.ntg.sms.Entities.Dtos.Response.MarksTypeResponse;
import com.ntg.sms.Entities.MarksType;
import com.ntg.sms.Mapper.MarksTypeMapper;
import com.ntg.sms.Repositories.MarksTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarksTypeService {

    private final MarksTypeRepository marksTypeRepository;
    private final MarksTypeMapper marksTypeMapper;

    // ─── READ ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<MarksTypeResponse> getAllTypes() {
        return marksTypeRepository.findAll()
                .stream()
                .map(marksTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MarksTypeResponse getTypeById(Long id) {
        return marksTypeMapper.toResponse(findTypeOrThrow(id));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public MarksTypeResponse createType(MarksTypeRequest request) {
        if (marksTypeRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("MarksType with id " + request.getId() + " already exists.");
        }
        if (marksTypeRepository.existsByType(request.getType())) {
            throw new IllegalArgumentException("MarksType '" + request.getType() + "' already exists.");
        }

        MarksType saved = marksTypeRepository.save(marksTypeMapper.toEntity(request));
        return marksTypeMapper.toResponse(saved);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public MarksTypeResponse updateType(Long id, MarksTypeRequest request) {
        MarksType marksType = findTypeOrThrow(id);

        if (!marksType.getType().equals(request.getType())
                && marksTypeRepository.existsByType(request.getType())) {
            throw new IllegalArgumentException("MarksType '" + request.getType() + "' already exists.");
        }

        marksTypeMapper.updateEntity(marksType, request);
        return marksTypeMapper.toResponse(marksTypeRepository.save(marksType));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void deleteType(Long id) {
        if (!marksTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("MarksType not found with id: " + id);
        }
        marksTypeRepository.deleteById(id);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private MarksType findTypeOrThrow(Long id) {
        return marksTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MarksType not found with id: " + id));
    }
}
