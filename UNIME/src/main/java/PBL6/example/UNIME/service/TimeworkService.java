package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.response.TimeworkResponse;
import PBL6.example.UNIME.entity.Timework;

import java.time.LocalTime;
import java.util.List;

public interface TimeworkService {

    List<TimeworkResponse> getAllTimeworks();

}
