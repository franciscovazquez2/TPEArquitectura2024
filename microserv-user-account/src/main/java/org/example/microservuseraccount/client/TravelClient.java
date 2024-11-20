package org.example.microservuseraccount.client;

import org.example.microservuseraccount.dto.TravelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microserv-travel")
public interface TravelClient {
    @GetMapping(value="api/travel/startTravel/{id_user}/{id_scooter}")
    TravelDto startTravel(@PathVariable(value = "id_user")Long id_user, @PathVariable(value="id_scooter")Long id_scooter);

}
