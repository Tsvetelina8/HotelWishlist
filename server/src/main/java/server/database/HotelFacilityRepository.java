package server.database;

import commons.HotelFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelFacilityRepository extends JpaRepository<HotelFacility, String> {}
