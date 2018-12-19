package com.yologlu.mongodbbeginning.Repo;

import com.yologlu.mongodbbeginning.Document.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends MongoRepository<Hotel,String>, QuerydslPredicateExecutor<Hotel> {

    Optional<Hotel> findById(String id);
}
