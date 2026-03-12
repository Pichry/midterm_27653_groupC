package com.smartsalon.service;

import com.smartsalon.dto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataPopulationService - Populates Rwanda location data on application startup.
 * 
 * This service runs once when the application starts and ensures that the
 * complete Rwanda administrative hierarchy is available in the database.
 */
@Component
public class DataPopulationService implements CommandLineRunner {

    private final LocationService locationService;

    public DataPopulationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🏗️  Starting Rwanda location data population...");
        
        // Check if data already exists
        if (!locationService.getAllProvinces().isEmpty()) {
            System.out.println("✅ Location data already exists. Skipping population.");
            return;
        }
        
        try {
            populateProvinces();
            populateDistricts();
            populateSectors();
            populateCells();
            populateVillages();
            
            System.out.println("✅ Rwanda location data population completed successfully!");
        } catch (Exception e) {
            System.out.println("⚠️  Error during data population: " + e.getMessage());
        }
    }

    private void populateProvinces() {
        String[][] provinces = {
            {"KIG", "Kigali City"},
            {"NOR", "Northern Province"},
            {"SOU", "Southern Province"},
            {"EAS", "Eastern Province"},
            {"WES", "Western Province"}
        };

        for (String[] province : provinces) {
            try {
                locationService.saveProvince(new ProvinceRequest(province[0], province[1]));
                System.out.println("✓ Created province: " + province[1]);
            } catch (IllegalArgumentException e) {
                // Province already exists
            }
        }
    }

    private void populateDistricts() {
        // Get province IDs first
        var provinces = locationService.getAllProvinces();
        var kigali = provinces.stream().filter(p -> p.getCode().equals("KIG")).findFirst().orElse(null);
        var northern = provinces.stream().filter(p -> p.getCode().equals("NOR")).findFirst().orElse(null);
        var eastern = provinces.stream().filter(p -> p.getCode().equals("EAS")).findFirst().orElse(null);

        if (kigali == null) return;

        String[][] districts = {
            // Kigali City
            {"Gasabo", kigali.getId().toString()},
            {"Kicukiro", kigali.getId().toString()},
            {"Nyarugenge", kigali.getId().toString()},
            
            // Northern Province
            {"Burera", northern != null ? northern.getId().toString() : ""},
            {"Gakenke", northern != null ? northern.getId().toString() : ""},
            {"Gicumbi", northern != null ? northern.getId().toString() : ""},
            {"Musanze", northern != null ? northern.getId().toString() : ""},
            {"Rulindo", northern != null ? northern.getId().toString() : ""},
            
            // Eastern Province
            {"Bugesera", eastern != null ? eastern.getId().toString() : ""},
            {"Gatsibo", eastern != null ? eastern.getId().toString() : ""},
            {"Kayonza", eastern != null ? eastern.getId().toString() : ""},
            {"Kirehe", eastern != null ? eastern.getId().toString() : ""},
            {"Ngoma", eastern != null ? eastern.getId().toString() : ""},
            {"Nyagatare", eastern != null ? eastern.getId().toString() : ""},
            {"Rwamagana", eastern != null ? eastern.getId().toString() : ""}
        };

        for (String[] district : districts) {
            if (!district[1].isEmpty()) {
                try {
                    locationService.saveDistrict(new DistrictRequest(district[0], Long.parseLong(district[1])));
                    System.out.println("✓ Created district: " + district[0]);
                } catch (Exception e) {
                    // District already exists
                }
            }
        }
    }

    private void populateSectors() {
        var districts = locationService.getAllProvinces().stream()
            .flatMap(p -> locationService.getDistrictsByProvince(p.getId()).stream())
            .toList();
        
        var gasabo = districts.stream().filter(d -> d.getName().equals("Gasabo")).findFirst().orElse(null);
        var nyarugenge = districts.stream().filter(d -> d.getName().equals("Nyarugenge")).findFirst().orElse(null);

        if (gasabo != null) {
            String[][] gasaboSectors = {
                {"Bumbogo", "BUM"},
                {"Gatsata", "GAT"},
                {"Gikomero", "GIK"},
                {"Gisozi", "GIS"},
                {"Jabana", "JAB"},
                {"Jali", "JAL"},
                {"Kacyiru", "KAC"},
                {"Kimihurura", "KIM"},
                {"Kinyinya", "KIN"},
                {"Ndera", "NDE"},
                {"Nduba", "NDB"},
                {"Remera", "REM"},
                {"Rusororo", "RUS"},
                {"Rutunga", "RUT"}
            };

            for (String[] sector : gasaboSectors) {
                try {
                    locationService.saveSector(new SectorRequest(sector[0], gasabo.getId()));
                    System.out.println("✓ Created sector: " + sector[0]);
                } catch (Exception e) {
                    // Sector already exists
                }
            }
        }

        if (nyarugenge != null) {
            String[][] nyarugengeSectors = {
                {"Gitega", "GIT"},
                {"Kanyinya", "KNY"},
                {"Kigali", "KGL"},
                {"Kimisagara", "KMS"},
                {"Mageragere", "MAG"},
                {"Muhima", "MUH"},
                {"Nyakabanda", "NYK"},
                {"Nyamirambo", "NYM"},
                {"Rwezamenyo", "RWE"}
            };

            for (String[] sector : nyarugengeSectors) {
                try {
                    locationService.saveSector(new SectorRequest(sector[0], nyarugenge.getId()));
                    System.out.println("✓ Created sector: " + sector[0]);
                } catch (Exception e) {
                    // Sector already exists
                }
            }
        }
    }

    private void populateCells() {
        var allDistricts = locationService.getAllProvinces().stream()
            .flatMap(p -> locationService.getDistrictsByProvince(p.getId()).stream())
            .toList();
        
        var gasabo = allDistricts.stream().filter(d -> d.getName().equals("Gasabo")).findFirst().orElse(null);
        if (gasabo == null) return;

        var gasaboSectors = locationService.getSectorsByDistrict(gasabo.getId());
        var kacyiru = gasaboSectors.stream().filter(s -> s.getName().equals("Kacyiru")).findFirst().orElse(null);
        var remera = gasaboSectors.stream().filter(s -> s.getName().equals("Remera")).findFirst().orElse(null);

        if (kacyiru != null) {
            String[][] kacyiruCells = {
                {"Kamatamu", "KMT"},
                {"Kibagabaga", "KBG"},
                {"Kimihurura", "KMH"},
                {"Nyakabanda", "NYK"}
            };

            for (String[] cell : kacyiruCells) {
                try {
                    locationService.saveCell(new CellRequest(cell[0], kacyiru.getId()));
                    System.out.println("✓ Created cell: " + cell[0]);
                } catch (Exception e) {
                    // Cell already exists
                }
            }
        }

        if (remera != null) {
            String[][] remeraCells = {
                {"Gishushu", "GSH"},
                {"Kabuga", "KBG"},
                {"Kimironko", "KMR"},
                {"Rukiri", "RKR"}
            };

            for (String[] cell : remeraCells) {
                try {
                    locationService.saveCell(new CellRequest(cell[0], remera.getId()));
                    System.out.println("✓ Created cell: " + cell[0]);
                } catch (Exception e) {
                    // Cell already exists
                }
            }
        }
    }

    private void populateVillages() {
        var allDistricts = locationService.getAllProvinces().stream()
            .flatMap(p -> locationService.getDistrictsByProvince(p.getId()).stream())
            .toList();
        
        var gasabo = allDistricts.stream().filter(d -> d.getName().equals("Gasabo")).findFirst().orElse(null);
        if (gasabo == null) return;

        var gasaboSectors = locationService.getSectorsByDistrict(gasabo.getId());
        var kacyiru = gasaboSectors.stream().filter(s -> s.getName().equals("Kacyiru")).findFirst().orElse(null);
        var remera = gasaboSectors.stream().filter(s -> s.getName().equals("Remera")).findFirst().orElse(null);

        if (kacyiru != null) {
            var kacyiruCells = locationService.getCellsBySector(kacyiru.getId());
            var kimihurura = kacyiruCells.stream().filter(c -> c.getName().equals("Kimihurura")).findFirst().orElse(null);

            if (kimihurura != null) {
                String[] kimihururaVillages = {"Nyarutarama", "Kibagabaga", "Kimihurura"};

                for (String village : kimihururaVillages) {
                    try {
                        locationService.saveVillage(new VillageRequest(village, kimihurura.getId()));
                        System.out.println("✓ Created village: " + village);
                    } catch (Exception e) {
                        // Village already exists
                    }
                }
            }
        }

        if (remera != null) {
            var remeraCells = locationService.getCellsBySector(remera.getId());
            var kabuga = remeraCells.stream().filter(c -> c.getName().equals("Kabuga")).findFirst().orElse(null);
            var gishushu = remeraCells.stream().filter(c -> c.getName().equals("Gishushu")).findFirst().orElse(null);

            if (kabuga != null) {
                String[] kabugaVillages = {"Nyamirama", "Kabuga", "Rusororo"};

                for (String village : kabugaVillages) {
                    try {
                        locationService.saveVillage(new VillageRequest(village, kabuga.getId()));
                        System.out.println("✓ Created village: " + village);
                    } catch (Exception e) {
                        // Village already exists
                    }
                }
            }

            if (gishushu != null) {
                String[] gishushuVillages = {"Gishushu", "Rwandex", "Kacyiru"};

                for (String village : gishushuVillages) {
                    try {
                        locationService.saveVillage(new VillageRequest(village, gishushu.getId()));
                        System.out.println("✓ Created village: " + village);
                    } catch (Exception e) {
                        // Village already exists
                    }
                }
            }
        }
    }
}