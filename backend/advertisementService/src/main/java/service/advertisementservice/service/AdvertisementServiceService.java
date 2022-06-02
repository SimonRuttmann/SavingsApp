package service.advertisementservice.service;

import model.AtomicIntegerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RedisDatabaseService;
import service.advertisementservice.model.AdvertisementDto;

//Service returns nothing in string if an error occured
@Service
public class AdvertisementServiceService {

    @Autowired
    RedisDatabaseService redisDatabaseService;

    public AdvertisementDto fetchData(){
        var dto = new AdvertisementDto();

        dto.setDiagram1(redisDatabaseService.getSingleSetValue(AtomicIntegerModel.SENDMESSAGES).toString());
        dto.setDiagram2(redisDatabaseService.getSingleSetValue(AtomicIntegerModel.REGISTEREDITEMS).toString());
        dto.setDiagram3(redisDatabaseService.getSingleSetValue(AtomicIntegerModel.COUNTUSERS).toString());

        return dto;
    }

}
