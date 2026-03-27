package backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class CarRequestDto {
    private String carName;
    private String carModel;
    private Integer year;
    private Integer totalRunKm;
    private Double price;
    private Integer owner;
    private List<MultipartFile> images;
}