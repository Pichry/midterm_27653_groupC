package com.smartsalon.service;

import com.smartsalon.dto.*;
import com.smartsalon.model.*;
	import com.smartsalon.repository.*;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.domain.Page;
	import org.springframework.data.domain.PageRequest;
	import org.springframework.data.domain.Pageable;
	import org.springframework.data.domain.Sort;
	import org.springframework.lang.NonNull;
	import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private ProvinceRepository provinceRepository;
    
    @Autowired
    private DistrictRepository districtRepository;
    
    @Autowired
    private SectorRepository sectorRepository;
    
    @Autowired
    private CellRepository cellRepository;
    
    @Autowired
    private VillageRepository villageRepository;

    // ==================== PROVINCE OPERATIONS ====================
    
    public Province createProvince(ProvinceRequest request) {
        Province province = new Province();
        province.setCode(request.getCode());
        province.setName(request.getName());
        return provinceRepository.save(province);
    }

    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

	    public Page<Province> getAllProvincesPaginated(@NonNull Pageable pageable) {
	        return provinceRepository.findAll(pageable);
	    }

    public Page<Province> getAllProvincesPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return provinceRepository.findAll(pageable);
    }

	    public Optional<Province> getProvinceByIdOptional(@NonNull Long id) {
	        return provinceRepository.findById(id);
	    }

	    public Province updateProvince(@NonNull Long id, ProvinceRequest request) {
	        Province province = provinceRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Province not found with id: " + id));
        
        province.setCode(request.getCode());
        province.setName(request.getName());
        return provinceRepository.save(province);
    }

	    public void deleteProvince(@NonNull Long id) {
	        provinceRepository.deleteById(id);
	    }

    // ==================== DISTRICT OPERATIONS ====================
    
	    public District createDistrict(DistrictRequest request) {
	        Long provinceId = request.getProvinceId();
	        if (provinceId == null) {
	            throw new IllegalArgumentException("Province id is required");
	        }

	        Province province = provinceRepository.findById(provinceId)
	                .orElseThrow(() -> new RuntimeException("Province not found with id: " + provinceId));
        
        District district = new District();
        district.setName(request.getName());
        district.setProvince(province);
        return districtRepository.save(district);
    }

	    public List<District> getDistrictsByProvince(@NonNull Long provinceId) {
	        return districtRepository.findByProvinceId(provinceId);
	    }

	    public Page<District> getDistrictsByProvincePaginated(@NonNull Long provinceId, @NonNull Pageable pageable) {
	        return districtRepository.findByProvinceId(provinceId, pageable);
	    }

	    public Page<District> getAllDistrictsPaginated(@NonNull Pageable pageable) {
	        return districtRepository.findAll(pageable);
	    }

    public Page<District> getAllDistrictsPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return districtRepository.findAll(pageable);
    }

	    public Optional<District> getDistrictByIdOptional(@NonNull Long id) {
	        return districtRepository.findById(id);
	    }

	    public District updateDistrict(@NonNull Long id, DistrictRequest request) {
	        District district = districtRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("District not found with id: " + id));

	        Long provinceId = request.getProvinceId();
	        if (provinceId == null) {
	            throw new IllegalArgumentException("Province id is required");
	        }

	        Province province = provinceRepository.findById(provinceId)
	                .orElseThrow(() -> new RuntimeException("Province not found with id: " + provinceId));
        
        district.setName(request.getName());
        district.setProvince(province);
        return districtRepository.save(district);
    }

	    public void deleteDistrict(@NonNull Long id) {
	        districtRepository.deleteById(id);
	    }

    // ==================== SECTOR OPERATIONS ====================
    
	    public Sector createSector(SectorRequest request) {
	        Long districtId = request.getDistrictId();
	        if (districtId == null) {
	            throw new IllegalArgumentException("District id is required");
	        }

	        District district = districtRepository.findById(districtId)
	                .orElseThrow(() -> new RuntimeException("District not found with id: " + districtId));
        
        Sector sector = new Sector();
        sector.setName(request.getName());
        sector.setDistrict(district);
        return sectorRepository.save(sector);
    }

	    public List<Sector> getSectorsByDistrict(@NonNull Long districtId) {
	        return sectorRepository.findByDistrictId(districtId);
	    }

	    public Page<Sector> getSectorsByDistrictPaginated(@NonNull Long districtId, @NonNull Pageable pageable) {
	        return sectorRepository.findByDistrictId(districtId, pageable);
	    }

	    public Page<Sector> getAllSectorsPaginated(@NonNull Pageable pageable) {
	        return sectorRepository.findAll(pageable);
	    }

	    public Optional<Sector> getSectorByIdOptional(@NonNull Long id) {
	        return sectorRepository.findById(id);
	    }

	    public Sector updateSector(@NonNull Long id, SectorRequest request) {
	        Sector sector = sectorRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Sector not found with id: " + id));

	        Long districtId = request.getDistrictId();
	        if (districtId == null) {
	            throw new IllegalArgumentException("District id is required");
	        }

	        District district = districtRepository.findById(districtId)
	                .orElseThrow(() -> new RuntimeException("District not found"));
        
        sector.setName(request.getName());
        sector.setDistrict(district);
        return sectorRepository.save(sector);
    }

	    public void deleteSector(@NonNull Long id) {
	        sectorRepository.deleteById(id);
	    }

    // ==================== CELL OPERATIONS ====================
    
	    public Cell createCell(CellRequest request) {
	        Long sectorId = request.getSectorId();
	        if (sectorId == null) {
	            throw new IllegalArgumentException("Sector id is required");
	        }

	        Sector sector = sectorRepository.findById(sectorId)
	                .orElseThrow(() -> new RuntimeException("Sector not found with id: " + sectorId));
        
        Cell cell = new Cell();
        cell.setName(request.getName());
        cell.setSector(sector);
        return cellRepository.save(cell);
    }

	    public List<Cell> getCellsBySector(@NonNull Long sectorId) {
	        return cellRepository.findBySectorId(sectorId);
	    }

	    public Page<Cell> getCellsBySectorPaginated(@NonNull Long sectorId, @NonNull Pageable pageable) {
	        return cellRepository.findBySectorId(sectorId, pageable);
	    }

	    public Page<Cell> getAllCellsPaginated(@NonNull Pageable pageable) {
	        return cellRepository.findAll(pageable);
	    }

	    public Optional<Cell> getCellByIdOptional(@NonNull Long id) {
	        return cellRepository.findById(id);
	    }

	    public Cell updateCell(@NonNull Long id, CellRequest request) {
	        Cell cell = cellRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Cell not found with id: " + id));

	        Long sectorId = request.getSectorId();
	        if (sectorId == null) {
	            throw new IllegalArgumentException("Sector id is required");
	        }

	        Sector sector = sectorRepository.findById(sectorId)
	                .orElseThrow(() -> new RuntimeException("Sector not found"));
        
        cell.setName(request.getName());
        cell.setSector(sector);
        return cellRepository.save(cell);
    }

	    public void deleteCell(@NonNull Long id) {
	        cellRepository.deleteById(id);
	    }

    // ==================== VILLAGE OPERATIONS ====================
    
	    public Village createVillage(VillageRequest request) {
	        Long cellId = request.getCellId();
	        if (cellId == null) {
	            throw new IllegalArgumentException("Cell id is required");
	        }

	        Cell cell = cellRepository.findById(cellId)
	                .orElseThrow(() -> new RuntimeException("Cell not found with id: " + cellId));
        
        Village village = new Village();
        village.setName(request.getName());
        village.setCell(cell);
        return villageRepository.save(village);
    }

	    public List<Village> getVillagesByCell(@NonNull Long cellId) {
	        return villageRepository.findByCellId(cellId);
	    }

	    public Page<Village> getVillagesByCellPaginated(@NonNull Long cellId, @NonNull Pageable pageable) {
	        return villageRepository.findByCellId(cellId, pageable);
	    }

	    public Page<Village> getAllVillagesPaginated(@NonNull Pageable pageable) {
	        return villageRepository.findAll(pageable);
	    }

	    public Optional<Village> getVillageByIdOptional(@NonNull Long id) {
	        return villageRepository.findById(id);
	    }

	    public Village getVillageWithFullHierarchy(@NonNull Long id) {
	        return villageRepository.findByIdWithFullHierarchy(id);
	    }

	    public Village updateVillage(@NonNull Long id, VillageRequest request) {
	        Village village = villageRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Village not found with id: " + id));

	        Long cellId = request.getCellId();
	        if (cellId == null) {
	            throw new IllegalArgumentException("Cell id is required");
	        }

	        Cell cell = cellRepository.findById(cellId)
	                .orElseThrow(() -> new RuntimeException("Cell not found"));
        
        village.setName(request.getName());
        village.setCell(cell);
        return villageRepository.save(village);
    }

	    public void deleteVillage(@NonNull Long id) {
	        villageRepository.deleteById(id);
	    }

    // ==================== SEARCH OPERATIONS ====================
    
    public List<Village> searchVillagesByName(String name) {
        return villageRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Village> getVillagesByProvince(String provinceName) {
        return villageRepository.findByProvinceName(provinceName);
    }

    public List<Village> getVillagesByDistrict(String districtName) {
        return villageRepository.findByDistrictName(districtName);
    }

    public List<Village> getVillagesBySector(String sectorName) {
        return villageRepository.findBySectorName(sectorName);
    }

    public List<Village> getVillagesByCell(String cellName) {
        return villageRepository.findByCellName(cellName);
    }

    // ==================== BACKWARD COMPATIBILITY METHODS ====================
    
    public Province saveProvince(ProvinceRequest request) {
        return createProvince(request);
    }

    public District saveDistrict(DistrictRequest request) {
        return createDistrict(request);
    }

    public Sector saveSector(SectorRequest request) {
        return createSector(request);
    }

    public Cell saveCell(CellRequest request) {
        return createCell(request);
    }

    public Village saveVillage(VillageRequest request) {
        return createVillage(request);
    }

	    public Province getProvinceById(@NonNull Long id) {
	        return getProvinceByIdOptional(id)
	                .orElseThrow(() -> new RuntimeException("Province not found with id: " + id));
	    }

	    public District getDistrictById(@NonNull Long id) {
	        return getDistrictByIdOptional(id)
	                .orElseThrow(() -> new RuntimeException("District not found with id: " + id));
	    }

	    public Sector getSectorById(@NonNull Long id) {
	        return getSectorByIdOptional(id)
	                .orElseThrow(() -> new RuntimeException("Sector not found with id: " + id));
	    }

	    public Cell getCellById(@NonNull Long id) {
	        return getCellByIdOptional(id)
	                .orElseThrow(() -> new RuntimeException("Cell not found with id: " + id));
	    }

	    public Village getVillageById(@NonNull Long id) {
	        return getVillageByIdOptional(id)
	                .orElseThrow(() -> new RuntimeException("Village not found with id: " + id));
	    }

    public Village getVillageByCodeOrName(String identifier) {
        return villageRepository.findByCodeOrName(identifier)
                .orElseThrow(() -> new RuntimeException("Village not found with identifier: " + identifier));
    }
}
