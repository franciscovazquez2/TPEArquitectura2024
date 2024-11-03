package org.example.microservtravel.repository;
import org.example.microservtravel.entity.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelRepository extends MongoRepository<Travel,Long> {
}
