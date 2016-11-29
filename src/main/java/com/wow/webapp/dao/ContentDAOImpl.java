package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wow.webapp.domain.account.UserModel;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.ApiReturnVitalModel;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.domain.pojo.Vitals;
import com.wow.webapp.domain.pojo.VitalsModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.entitymodel.Vital;
import com.wow.webapp.util.Responses;
import com.wow.webapp.util.Utils;

public class ContentDAOImpl implements ContentDAO{

	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(ContentDAOImpl.class);

	public ContentDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void save(Doctor d) {
		// TODO Auto-generated method stub
		System.out.println("enter into save bookings");
		Session session = this.getSession();
		session.saveOrUpdate(d.getUser());
		session.saveOrUpdate(d);
		
		
	}
	
	@Transactional
	public void save(Clinic c) {
		logger.debug("save clinic start" );
		Session session = this.getSession();
        //session.persist(c);
		session.saveOrUpdate(c);
		logger.debug("save clinic end" );
	}
	
	@Transactional
	public void save(Slot s){
		Session session = this.getSession();
		session.saveOrUpdate(s);
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClinicModel> getClinics(String speciality, String location){
		logger.debug("getClinic start");
		List<ClinicModel> list = new ArrayList<ClinicModel>();
		Session session = this.getSession();
		List<Clinic> clinics = session.createQuery("from Clinic where type=:paramType")
				.setParameter("paramType", "clinic")
				.list();
		for(  Clinic c : clinics){
			logger.debug(c.getName() + " " + c.getSpecialities() + " " + c.getAddresses() );
			boolean specialityMatch = false;
			if(c.getSpecialities() != null ){
				for(String spec : c.getSpecialities().split(",")){
					logger.debug(spec);
					if(spec.toLowerCase().equals(speciality.toLowerCase())) {
						specialityMatch = true; break;
					}
				}
			}
			logger.debug("Speciality Match : " + specialityMatch);
			if(!specialityMatch) continue;
			boolean addressMatch = false;
			for( ClinicAddress addr : c.getAddresses()){
				if(addr.getCity().toLowerCase().equals(location.toLowerCase())){
					addressMatch = true; break;
				}
			}
			logger.debug("addressMatch Match : " + addressMatch);
			if(!addressMatch) continue;
			ClinicModel model = new ClinicModel();
			model.setClinicName(c.getName());
			model.setClinicDesc(c.getDescription());
			
			model.setClinicAddress(c.getAddresses().toString());
			model.setClinicPhones(c.getPhoneNos().toString());
			list.add(model);
		}
		logger.debug("getClinics end");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DoctorModel> getDoctorsInfo(String speciality, String location,String type){
		logger.debug("getDoctorsInfo start");
		Utils utils=new Utils();
		List<DoctorModel> list = new ArrayList<DoctorModel>();
		Session session = this.getSession();
		List<Clinic> clinics = session.createQuery("from Clinic where type=:paramType")
				.setParameter("paramType", type)
				.list();
		for(  Clinic c : clinics){
			boolean addressMatch = false;
			for( ClinicAddress addr : c.getAddresses()){
				if(addr.getCity().toLowerCase().equals(location.toLowerCase())){
					addressMatch = true; break;
				}
			}
			logger.debug("addressMatch Match : " + addressMatch);
			if(!addressMatch) continue;
			List<Slot> slots = session.createQuery("from Slot where clinic=:paramType group by doctor,clinic")
					.setParameter("paramType", c)
					.list();
			if(slots.size() == 0) continue;
			for(Slot s : slots){
				if(speciality != null && speciality.trim().length() > 0)
					if(!s.getDoctor().getSpeciality().equalsIgnoreCase(speciality)) continue;
				
				DoctorModel doctorModel = new DoctorModel();
				Doctor d = s.getDoctor();
				doctorModel.setId(d.getId());
				doctorModel.setName(d.getUser().getUserProfile().getName());
				doctorModel.setMobile(d.getUser().getUsername());
				doctorModel.setSpeciality(s.getDoctor().getSpeciality());
				
				/*Clinic Info */ 
				
				ClinicModel clinicModel = new ClinicModel();
				clinicModel.setId(c.getId());
				clinicModel.setClinicName(c.getName());
				clinicModel.setClinicDesc(c.getDescription());
				clinicModel.setClinicAddress(c.getAddresses().toString());
				clinicModel.setClinicPhones(c.getPhoneNos().toString());
				doctorModel.setClinic(clinicModel);
				
				list.add(doctorModel);
				
			}
			
		}
		logger.debug("getClinics end");
		return list;
	}
	
	@Transactional
	public Doctor findDoctorByMobile(String mobile) throws Exception {
		logger.debug("findDoctorByMobile start " + mobile);
		Session session=this.getSession();
		User user=new User(mobile);
		List<Doctor> doctorList = session.createQuery("from Doctor where user=:user")
				.setParameter("user", user)
				.list();
		if(doctorList == null || doctorList.size() <= 0){
		logger.info("username is not availble in doctor table");
			return null;
		}
		return doctorList.get(0);
	}
	
	/*private Doctor findDoctorByMobile(Session session ,String mobile) throws Exception{
		@SuppressWarnings("unchecked")
		
		List<Doctor> doctorList = session.createQuery("from Doctor where User=:user")
				.setParameter("user", user)
				.list();
		if(doctorList == null || doctorList.size() <= 0){
//			logger.debug("Not Found : " + username);
//			throw new Exception("Not Found : " + username);
			return null;
		}
		return doctorList.get(0);
	}*/
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Slot> findSlotsByClinicAndDoctor(Doctor d,Clinic c,String date){
		Session session = this.getSession();
		logger.debug("Before");
		String slotDate="s.time like '%"+date+"%'";
		List<Slot> slots = session.createQuery("from Slot s where s.enabled=true and s.clinic=:clinic and s.doctor=:doctor and "+slotDate)
				.setParameter("clinic", c).setParameter("doctor", d).list();
		logger.debug("after");
		return slots;
	}

	@Transactional
	public Doctor getDoctorById(Integer id) {
		try
		{
			Session session = this.getSession();
			logger.debug("Before");
			List<Doctor> doctor = session.createQuery("from Doctor where id=:id").setParameter("id", id).list();
			logger.debug("after");
			if(doctor == null || doctor.size() <= 0) return null;
			return doctor.get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while getting doctor by id:::"+e.toString());
		}
		return null;
	}
	
	@Transactional
	public Doctor getDoctorByUser(User user) {
		try
		{
			Session session = this.getSession();
			logger.debug("Before");
			List<Doctor> doctor = session.createQuery("from Doctor where user=:user").setParameter("user", user).list();
			logger.debug("after");
			if(doctor == null || doctor.size() <= 0) return null;
			return doctor.get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while getting doctor by user:::"+e.toString());
		}
		return null;
	}

	@Transactional
	public List<String> findSlotsByStartAndEndTimes(Date startTime,
			Date endTime,Doctor d) {
		List<String> list=null;
		Utils utils=new Utils();
		try
		{
			Session session=this.getSession();
			list=new ArrayList<String>();
			
			List<Slot> slots=session.createQuery("from Slot WHERE doctor=:doctor and time BETWEEN :startDate AND :endDate").setParameter("doctor", d).setParameter("startDate", startTime).setParameter("endDate", endTime).list();
			if(slots!=null && slots.size()>0)
			{
				for(Slot slot:slots)
				{
					String time=utils.convertDateToUTCFormat(slot.getTime());
					list.add(time);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception while getting slots information using start and endtimes:::"+e.toString());
		}
		return list;
	}
	
	@Transactional
	public List<BookingModel> findBookingsOnClinic(Clinic clinic) {
		// TODO Auto-generated method stub
		logger.debug("In get bookings on clinic");
		Session session = this.getSession();
		List<BookingModel> bookingModel=null;
		List<Slot> bookings=session.createQuery("from Slot b where b.clinic=? and b.user is not null order by booked_time desc").setParameter(0,clinic).list();
		bookingModel=getBookings(bookings);
		logger.debug("SLOT"+bookingModel);
		return bookingModel;
	}
	@Transactional
	public List<BookingModel> findBookingsOnDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		logger.debug("In get bookings on doctor");
		Session session = this.getSession();
		List<BookingModel> bookingModel=null;
		List<Slot> bookings=session.createQuery("from Slot b where b.doctor=? and b.user is not null order by booked_time desc").setParameter(0,doctor).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}
	@Transactional
	public List<BookingModel> findBookingsOnClinic(Clinic clinic, String date) {
		Session session = this.getSession();
		logger.info("enter into findbookings with date");
		List<BookingModel> bookingModel=null;
		String dateAdded="b.booked_time like '%"+date+"%'";
		List<Slot> bookings=session.createQuery("from Slot b where b.clinic=:clinic  and user is not null and "+dateAdded+"").setParameter("clinic",clinic).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}
	
	@Transactional
	public List<BookingModel> findBookingsOnUser(String userName) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		User user=new User(userName);
		List<BookingModel> bookingModel=null;
		List<Slot> bookings=session.createQuery("from Slot b where b.user=? order by booked_time desc").setParameter(0,user).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}
	
	private List<BookingModel> getBookings(List<Slot> bookings)
	{
		List<BookingModel> bookingModel=new ArrayList<BookingModel>();
		if(bookings!=null&&bookings.size()>0)
		{
			for(Slot b:bookings)
			{
				BookingModel bm=new BookingModel();
				String time=new Utils().convertDateToUTCFormat(b.getTime());
				logger.info("booking time in utc format:::"+time);
				bm.setSlotTime(time);
				bm.setSlotId(b.getId());
				
				/* userinfo */
				UserModel userModel=new UserModel();
				User u=b.getUser();
				userModel.setUsername(u.getUsername());
				userModel.setName(u.getUserProfile().getName());
				bm.setUser(userModel);
				
				/* clinic info */
				ClinicModel clinicModel=new ClinicModel();
				Clinic c=b.getClinic();
				clinicModel.setId(c.getId());
				clinicModel.setClinicName(c.getName());
				clinicModel.setClinicDesc(c.getDescription());
				clinicModel.setClinicAddress(c.getAddresses().toString());
				clinicModel.setClinicPhones(c.getPhoneNos().toString());
				bm.setClinic(clinicModel);
				
				/* doctor info */
				DoctorModel doctorModel=new DoctorModel();
				doctorModel.setId(b.getDoctor().getId());
				doctorModel.setName(b.getDoctor().getUser().getUserProfile().getName());
				doctorModel.setMobile(b.getDoctor().getUser().getUsername());
				doctorModel.setSpeciality(b.getDoctor().getSpeciality());
				bm.setDoctor(doctorModel);
				
				bookingModel.add(bm);
			}
		}
		return bookingModel;
	}
	
	@Autowired
	private UserDAO userDao;
	
	@Transactional
	public List<DoctorModel> getDoctors(String speciality,String location) {
		
		List<DoctorModel> doctorModel=new ArrayList<DoctorModel>();
		Session session=this.getSession();
		List<Doctor> doctors = new ArrayList<Doctor>();
		if(speciality == null || location == null || location.isEmpty() || speciality.isEmpty()){
			doctors=session.createQuery("from Doctor").list();
		}
		else{
			doctors=session.createQuery("from Doctor where speciality=:speciality")
					.setParameter("speciality", speciality).list();
		}
		if(doctors!=null && doctors.size()>0)
		{
			for(Doctor d:doctors)
			{
				//doctor model
				DoctorModel model=new DoctorModel();
				model.setId(d.getId());
				model.setName(d.getUser().getUserProfile().getName());
				model.setMobile(d.getUser().getUsername());
				model.setSpeciality(d.getSpeciality());
				
				//clinic model
				ClinicModel clinicModel=new ClinicModel();
				Clinic clinic= userDao.getClinicByUserName("9999999999");
				clinicModel.setId(clinic.getId());
				clinicModel.setClinicName(clinic.getName());
				clinicModel.setClinicPhones(clinic.getPhoneNos().toString());
				clinicModel.setClinicAddress(clinic.getAddresses().toString());
				clinicModel.setClinicDesc(clinic.getDescription());
				
				//add doctorModel
				model.setClinic(clinicModel);
				doctorModel.add(model);
				
			}
		}
		return doctorModel;
		
	}
	
	@Transactional
	public List<DoctorModel> getDoctors() {
		
		List<DoctorModel> doctorModel=new ArrayList<DoctorModel>();
		Session session=this.getSession();
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors=session.createQuery("from Doctor").list();
		
		if(doctors!=null && doctors.size()>0)
		{
			for(Doctor d:doctors)
			{
				//doctor model
				DoctorModel model=new DoctorModel();
				model.setId(d.getId());
				model.setName(d.getUser().getUserProfile().getName());
				model.setMobile(d.getUser().getUsername());
				model.setSpeciality(d.getSpeciality());
				
				//clinic model
				ClinicModel clinicModel=new ClinicModel();
				Clinic clinic= userDao.getClinicByUserName("9999999999");
				clinicModel.setId(clinic.getId());
				clinicModel.setClinicName(clinic.getName());
				clinicModel.setClinicPhones(clinic.getPhoneNos().toString());
				clinicModel.setClinicAddress(clinic.getAddresses().toString());
				clinicModel.setClinicDesc(clinic.getDescription());
				
				//add doctorModel
				model.setClinic(clinicModel);
				doctorModel.add(model);
				
			}
		}
		return doctorModel;
		
	}

	@Transactional
	public boolean save(VitalsModel vitalsModel) {
	
		Session session=this.getSession();
		try
		{
			Integer id = Integer.parseInt(vitalsModel.getBookingId());
			Vitals[] vitals = vitalsModel.getVitals();
			for(Vitals v:vitals)
			{
				logger.info("\t property::"+v.getProperty()+" value::"+v.getValue());
				if(v.getProperty()!=null && !v.getProperty().isEmpty() && v.getValue()!=null && !v.getValue().isEmpty())
				{
					
					Vital vital = new Vital();
					Slot booking = new Slot();
					booking.setId(id);
					List<Vital> checkVital=session.createQuery("from Vital where property=:property  and bookingId=:bookingId")
							.setParameter("property", v.getProperty()).setParameter("bookingId", booking).list();
					if(checkVital == null || checkVital.isEmpty()){
						vital.setBookingId(booking);
						vital.setProperty(v.getProperty());
						vital.setValue(v.getValue());
						vital.setInserted_on(new Date());
						session.save(vital);
					}
					
					
				}
				
			}
			return true;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while saving vitalsModel:::"+e.toString());
		}
		return false;
		
	}

	@Transactional
	public ApiReturnModel getVitalsOnBookingId(String bookingId) {
		
		ApiReturnModel returnModel = null;
		Session session = this.getSession();
		try
		{
			Slot s= new Slot();
			s.setId(Integer.parseInt(bookingId));
			List<Vital> vitalsList=session.createQuery("from Vital where bookingId=:bookingId")
					.setParameter("bookingId", s).list();
			if(vitalsList!=null && !vitalsList.isEmpty())
			{
				Slot slot = vitalsList.get(0).getBookingId();
				
				Vitals[] vitalsArray = new Vitals[vitalsList.size()];
				int i=0;
				for(Vital v:vitalsList)
				{
					Vitals vitals = new Vitals();
					vitals.setProperty(v.getProperty());
					vitals.setValue(v.getValue());
					
					vitalsArray[i]=vitals;
					i++;
				}
			
				UserModel userModel = new UserModel();
				User u = slot.getUser();
				userModel.setUsername(u.getUsername());
				userModel.setName(u.getUserProfile().getName());
				
				
				DoctorModel doctorModel = new DoctorModel();
				Doctor d = slot.getDoctor();
				doctorModel.setId(d.getId());
				doctorModel.setMobile(d.getUser().getUsername());
				doctorModel.setName(d.getUser().getUserProfile().getName());
				doctorModel.setSpeciality(d.getSpeciality());
				
				VitalsModel vitalsModel = new VitalsModel();
				vitalsModel.setBookingId(String.valueOf(slot.getId()));
				vitalsModel.setVitals(vitalsArray);
				
				
				returnModel = new ApiReturnVitalModel(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG,vitalsModel,doctorModel,userModel);
			}
			else
			{
				returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, "no vitals defined for bookingId");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("exception occurs while getting getVitalsOnBookingId::"+e.toString());
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		return returnModel;
	}

	
}
