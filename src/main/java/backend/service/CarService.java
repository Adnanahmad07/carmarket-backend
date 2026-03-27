package backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import backend.dto.CarRequestDto;
import backend.entity.Car;
import backend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final Cloudinary cloudinary;

    public Car addCar(CarRequestDto dto) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        if (dto.getImages() != null) {
            for (MultipartFile image : dto.getImages()) {
                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.asMap("folder", "carmarket")
                );
                imageUrls.add((String) uploadResult.get("secure_url"));
            }
        }

        Car car = new Car();
        car.setCarName(dto.getCarName());
        car.setCarModel(dto.getCarModel());
        car.setYear(dto.getYear());
        car.setTotalRunKm(dto.getTotalRunKm());
        car.setPrice(dto.getPrice());
        car.setOwner(dto.getOwner());
        car.setImageUrls(imageUrls);

        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car markSoldOut(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        car.setSoldOut(true);
        return carRepository.save(car);
    }
}