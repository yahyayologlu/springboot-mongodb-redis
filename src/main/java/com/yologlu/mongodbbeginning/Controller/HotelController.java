package com.yologlu.mongodbbeginning.Controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yologlu.mongodbbeginning.Document.Hotel;
import com.yologlu.mongodbbeginning.Document.QHotel;
import com.yologlu.mongodbbeginning.Repo.HotelRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/all")
    @Cacheable(value = "hotel")
    public List<Hotel> getAll(){
        return this.hotelRepository.findAll();
    }

    @PutMapping
    @CachePut(value = "hotel")
    public void insert(@RequestBody Hotel hotel){
        this.hotelRepository.insert(hotel);
    }

    @PostMapping
    @CachePut(value = "hotel", key = "#hotel.id")
    public void update(@RequestBody Hotel hotel){
        this.hotelRepository.save(hotel);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "hotel", key = "#id")
    public Hotel getById(@PathVariable("id") String id){
        return this.hotelRepository.findById(id).orElse(null);
    }

    @GetMapping("/price/{maxPrice}")
    @Cacheable(value = "hotel", key = "#maxPrice")
    public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice){
        // create a query class (QHotel)
        QHotel qHotel = QHotel.hotel;
        // using the query class we can create the filters
        BooleanExpression filter = qHotel.pricePerNight.lt(maxPrice);

        return (List<Hotel>) this.hotelRepository.findAll(filter);
    }

    @GetMapping("/address/{city}")
    @Cacheable(value = "hotel", key = "#city")
    public List<Hotel> getByCity(@PathVariable("city") String city){
        // create a query class (QHotel)
        QHotel qHotel = QHotel.hotel;
        // using the query class we can create the filters
        BooleanExpression filterByCity = qHotel.address.city.eq(city);

        return (List<Hotel>) this.hotelRepository.findAll(filterByCity);
    }

    @GetMapping("/country/{country}")
    @Cacheable(value = "hotel", key = "#country")
    public List<Hotel> getByCountry(@PathVariable("country") String country){
        // create a query class (QHotel)
        QHotel qHotel = QHotel.hotel;

        // using the query class we can create the filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        // we can then pass the filters to the findAll() method
        return (List<Hotel>) this.hotelRepository.findAll(filterByCountry);
    }

    @GetMapping("/recommended")
    @Cacheable(value = "hotel")
    public List<Hotel> getRecommended(){
        final int maxPrice = 200;
        final int minRating = 7;

        // create a query class (QHotel)
        QHotel qHotel = QHotel.hotel;

        // using the query class we can create the filters
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);

        // we can then pass the filters to the findAll() method
        return (List<Hotel>) this.hotelRepository.findAll(filterByPrice.and(filterByRating));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "hotel", key = "#id")
    public void delete(@PathVariable("id") String id) {
        this.hotelRepository.deleteById(id);
    }

    /*
    {
        "id": "5c003ed641ef0d0953b55abc",
        "name": "Marriot",
        "pricePerNight": 130,
        "address": {
            "city": "Paris",
            "country": "France"
        },
        "reviews": [
            {
                "userName": "Jhon",
                "rating": 8,
                "approved": false
            },
            {
                "userName": "Mary",
                "rating": 7,
                "approved": true
            }
        ]
    }
    */

}
