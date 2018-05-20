package com.waffa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waffa.constant.Status;
import com.waffa.entity.Booking;
import com.waffa.entity.User;
import com.waffa.exceptions.InternalServerErrorException;
import com.waffa.model.BookingModel;
import com.waffa.respository.BookingRepository;
import com.waffa.respository.UserRepository;
import com.waffa.utils.AppConstants;
import com.waffa.utils.DateUtil;;

@Service("bookingService")
public class BookingServiceImp implements BookingService {

	private final static Logger logger = LoggerFactory.getLogger(BookingServiceImp.class);

	@Autowired
	public BookingRepository bookingRepository;

	@Autowired
	public UserRepository userRepository;

	@Override
	public List<BookingModel> getAllActiveBookingPerUser(int userId) {
		try {
			User user = userRepository.findById(userId);
			List<Booking> bookingList = bookingRepository.findAllBookingByUserAndIsActive(user, Status.Y);
			List<BookingModel> bookingModelList = new ArrayList<BookingModel>();
			for (Booking book : bookingList) {
				BookingModel bookingMdl = new BookingModel();
				bookingMdl.setBookingId(book.getId());
				bookingMdl.setUserName(user.getUsername());
				bookingMdl.setBookingStartTime(book.getStartTime());
				bookingMdl.setBookingEndTime(book.getEndTime());
				bookingMdl.setBookingStartDate(
			    DateUtil.convertDatetoString(book.getStartDate(), AppConstants.DATE_FORMAT_DDMMYYYY));
				bookingModelList.add(bookingMdl);
			}

			return bookingModelList;
		} catch (Exception e) {
			throw new InternalServerErrorException("error while fetching Data from data base ");
		}
	}

	public boolean validEndDate(Date startDate, Date endDate) {
		boolean before = endDate.before(startDate);
		if (before) {
			return false;
		}
		return true;
	}

	@Override
	public boolean cancleBooking(BookingModel bookingMdl) {
		try {
			Booking book = bookingRepository.findOneById(bookingMdl.getBookingId());
			book.setIsCancled(Status.Y);
			book.setIsActive(Status.N);
			book.setIsDone(Status.N);
			book.setIsPending(Status.N);
			bookingRepository.save(book);
			return true;
		} catch (Exception e) {
			throw new InternalServerErrorException("some thing went wrong while trying to update entity");
		}
	}

	@Override
	public List<BookingModel> getAllDoneBooking(int userId) {

		try {
			User user = userRepository.findById(userId);
			List<Booking> bookingList = bookingRepository.findAllBookingByUserAndIsDone(user, Status.Y);
			List<BookingModel> bookingModelList = new ArrayList<BookingModel>();
			for (Booking book : bookingList) {
				BookingModel bookingMdl = new BookingModel();
				bookingMdl.setBookingId(book.getId());
				bookingMdl.setUserName(user.getUsername());
				bookingMdl.setBookingStartTime(book.getStartTime());
				bookingMdl.setBookingEndTime(book.getEndTime());
				bookingMdl.setBookingStartDate(
						DateUtil.convertDatetoString(book.getStartDate(), AppConstants.DATE_FORMAT_DDMMYYYY));
				bookingModelList.add(bookingMdl);
			}

			return bookingModelList;
		} catch (Exception e) {
			throw new InternalServerErrorException("error while fetching Data from data base ");
		}
	}

	@Override
	public int numberOfCancledBookingPerUser(int userId) {
		User user = userRepository.findById(userId);
		List<Booking> cancledBooking = bookingRepository.findAllBookingByUserAndIsCancled(user, Status.Y);
		int numberOfCancledBooking = cancledBooking.size();

		return numberOfCancledBooking;
	}

	//To do time validation 
	public boolean isTimeValid(String time,String time2)
	{
		return true;
	}
	@Override
	public Booking BookNewAppoitment(BookingModel bookingMdl, int userId) {
		User user = userRepository.findById(userId);
		Booking Appointment = new Booking();
		Appointment.setIsActive(Status.N);
		Appointment.setIsCancled(Status.N);
		Appointment.setIsDone(Status.N);
		Appointment.setIsPending(Status.Y);
		Appointment.setStartDate(DateUtil.convertStringToDate(bookingMdl.getBookingStartDate()));
		Appointment.setStartTime(bookingMdl.getBookingStartTime());
		Appointment.setEndTime(bookingMdl.getBookingEndTime());
		Appointment.setUser(user);
		bookingRepository.saveAndFlush(Appointment);
		return Appointment;
	}

	@Override
	public List<BookingModel> getAllPendingAppointment() {
		try {
			List<Booking> bookingList = bookingRepository.findAllByIsPending(Status.Y);
			

			List<BookingModel> bookingModelList = new ArrayList<BookingModel>();
			for (Booking book : bookingList) {
				BookingModel bookingMdl = new BookingModel();
				User user = book.getUser();
				bookingMdl.setUserName(user.getUsername());
				bookingMdl.setBookingId(book.getId());
				bookingMdl.setBookingStartTime(book.getStartTime());
				bookingMdl.setBookingEndTime(book.getEndTime());
				bookingMdl.setBookingStartDate(
				DateUtil.convertDatetoString(book.getStartDate(), AppConstants.DATE_FORMAT_DDMMYYYY));
				bookingModelList.add(bookingMdl);
			}

			return bookingModelList;
		} catch (Exception e) {
			throw new InternalServerErrorException("error while fetching Data from data base ");
		}
	}


}