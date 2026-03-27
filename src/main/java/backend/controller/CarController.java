package backend.controller;

import backend.dto.CarRequestDto;
import backend.entity.Car;
import backend.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Car> addCar(
            @RequestParam("carName") String carName,
            @RequestParam("carModel") String carModel,
            @RequestParam("year") Integer year,
            @RequestParam("totalRunKm") Integer totalRunKm,
            @RequestParam("price") Double price,
            @RequestParam("owner") Integer owner,
            @RequestParam("images") List<MultipartFile> images
    ) throws IOException {

        CarRequestDto dto = new CarRequestDto();
        dto.setCarName(carName);
        dto.setCarModel(carModel);
        dto.setYear(year);
        dto.setTotalRunKm(totalRunKm);
        dto.setPrice(price);
        dto.setOwner(owner);
        dto.setImages(images);

        return ResponseEntity.ok(carService.addCar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/soldout")
    public ResponseEntity<Car> markSoldOut(@PathVariable Long id) {
        return ResponseEntity.ok(carService.markSoldOut(id));
    }

}