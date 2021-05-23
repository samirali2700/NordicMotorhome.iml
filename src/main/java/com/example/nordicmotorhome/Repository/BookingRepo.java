package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepo {
    @Autowired
    JdbcTemplate template;

    public void addBooking(Booking booking){
        String sql = "INSERT INTO booking (renter_ID,motorhome_ID,start_kM,pickup_date,return_date,pickup_location,kmToPickup,dropoff_location,kmToDropoff,extras) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        template.update(sql, booking.getRenter_ID(),booking.getMotorhome_ID(),booking.getStart_km(),booking.getPickup_date(),booking.getReturn_date(),booking.getPickup_location(),booking.getKmToPickup(), booking.getDropoff_location(),booking.getKmToDropoff(),booking.getExtras());
    }
    public void updateBooking(Booking b){
        String sql = "UPDATE booking SET pickup_date = ?, return_date = ?, pickup_location = ?, kmToPickup = ?, dropoff_location = ?, kmToDropoff = ?, extras = ? WHERE booking_ID = ?;";
        template.update(sql, b.getPickup_date(),b.getReturn_date(),b.getPickup_location(),b.getKmToPickup(),b.getDropoff_location(),b.getKmToDropoff(),b.getExtras(),b.getBooking_ID());
    }
    public void deleteBooking(int id){
        String sql = "DELETE FROM booking WHERE booking_ID = ?";
        template.update(sql,id);
    }
    public List<Booking> fetchAll(){
        String sql = "SELECT     season_ID, booking.* from booking\n" +
                "left join renter on booking.renter_ID = renter.renter_ID\n" +
                "left join motorhome on booking.motorhome_ID = motorhome.motorhome_ID\n" +
                "left join season on MONTH(booking.pickup_date) <= season.season_to and MONTH(booking.pickup_date) >= season.season_from \n" +
                ";";//May be set as joined select booking - invoice
        RowMapper<Booking> list = new BeanPropertyRowMapper<>(Booking.class);

        return template.query(sql,list);
    }


    public Booking fetchById(int id){
        String sql = "SELECT * FROM booking WHERE booking_ID = ?";
        RowMapper<Booking> list = new BeanPropertyRowMapper<>(Booking.class);

        return template.queryForObject(sql,list,id);
    }
    public Booking fetchByRenterID(int id ){
        String sql = "SELECT * FROM booking WHERE renter_ID = ?";
        RowMapper<Booking> list = new BeanPropertyRowMapper<>(Booking.class);
        return template.queryForObject(sql,list,id);
    }
    public int bookingCount(){
        String sql = "SELECT count(*) FROM booking";
        return template.queryForObject(sql,Integer.class);
    }


    //Invoice


}