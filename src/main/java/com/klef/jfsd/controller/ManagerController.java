package com.klef.jfsd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.klef.jfsd.model.Event;
import com.klef.jfsd.model.Manager;
import com.klef.jfsd.model.Student;
import com.klef.jfsd.service.EventService;
import com.klef.jfsd.service.ManagerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ManagerController {
	
	 @Autowired
	 private ManagerService managerService;
	 
	 @Autowired
	    private EventService eventService;
	
	
	@GetMapping("managerhome")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("managerhome");
		return mv;
	}
	
	@GetMapping("managerlogin")
	public ModelAndView studentlogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("managerlogin");
		return mv;
	}
	
	@PostMapping("checkmanagerlogin")
	public ModelAndView checkmanagerlogin(HttpServletRequest request) {
	    ModelAndView mv = new ModelAndView();
	    
	    String memail = request.getParameter("memail");
	    String mpwd = request.getParameter("mpwd");
	    
	    // Verify login credentials
	    Manager manager = managerService.checkmanagerlogin(memail, mpwd);
	    
	    if(manager != null) {
	        // Set the student object in the session upon successful login
	        HttpSession session = request.getSession();
	        session.setAttribute("manager", manager);
	        
	        mv.setViewName("managerhome");
	    } else {
	        mv.setViewName("managerloginfail");
	        mv.addObject("message", "Login Failed");
	    }
	    
	    return mv;
	}
	
	@GetMapping("managerprofile")
	public ModelAndView managerprofile() 
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("managerprofile");
		return mv;
	}
	
	@GetMapping("updatemanager")
	public ModelAndView updatemanager() 
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("updatemanager");
		return mv;
	}
	
	
	
	@PostMapping({"update", "updatemanager"})
	public ModelAndView update(HttpServletRequest request, HttpSession session) {
	

	    int id = Integer.parseInt(request.getParameter("mid"));
	    String name = request.getParameter("mname");
	    String gender = request.getParameter("mgender");
	    String dob = request.getParameter("mdob");
	    String club = request.getParameter("mclub");
	    String location = request.getParameter("mlocation");
	    String password = request.getParameter("mpwd");
	    String contact = request.getParameter("mcontact");

	    Manager m = new Manager();
	    m.setId(id);
	    m.setName(name);
	    m.setGender(gender);
	    m.setDateOfBirth(dob);
	    m.setClub(club);
	    m.setLocation(location);
	    m.setPassword(password);
	    m.setContact(contact);

	    // Update the student in the database
	    String msg = managerService.updatemanager(m);

	    // Update the session attribute with the new student details
	    session.setAttribute("manager", m);

	    // Redirect to success page
	    ModelAndView mv = new ModelAndView("updatesuccess");
	    mv.addObject("message", msg);

	    return mv;
	}
	
	// Display all manager events
	@GetMapping("/managerEvents")
	public ModelAndView showManagerEvents(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    Manager manager = (Manager) session.getAttribute("manager");

	    if (manager == null) {
	        return new ModelAndView("redirect:/managerlogin");
	    }

	    List<Event> managerEvents = eventService.getEventsByManager(manager.getId());
	    ModelAndView modelAndView = new ModelAndView("managerEvents");
	    modelAndView.addObject("eventList", managerEvents);
	    return modelAndView;
	}

	// Display students registered for an event
	@GetMapping("/viewRegisteredStudents/{eventId}")
	public ModelAndView viewRegisteredStudents(@PathVariable int eventId) {
	    List<Student> registeredStudents = eventService.getStudentsForEvent(eventId);

	    ModelAndView modelAndView = new ModelAndView("viewRegisteredStudents");
	    modelAndView.addObject("registeredStudents", registeredStudents);
	    return modelAndView;
	}
	
	@GetMapping("/postAttendance/{eventId}")
	public ModelAndView postAttendance(@PathVariable int eventId) {
	    // Fetch registered students for the event
	    List<Student> registeredStudents = eventService.getStudentsForEvent(eventId);
	    Event event = eventService.getEventById(eventId);

	    ModelAndView modelAndView = new ModelAndView("postAttendance");
	    modelAndView.addObject("registeredStudents", registeredStudents);
	    modelAndView.addObject("event", event);
	    return modelAndView;
	}
	
	@PostMapping("/submitAttendance")
	public ModelAndView submitAttendance(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    Manager manager = (Manager) session.getAttribute("manager");

	    if (manager == null) {
	        return new ModelAndView("redirect:/managerlogin");
	    }

	    int eventId;
	    try {
	        eventId = Integer.parseInt(request.getParameter("eventId"));
	    } catch (NumberFormatException e) {
	        // Log the error and return a meaningful response to the user
	        return new ModelAndView("redirect:/managerEvents").addObject("error", "Invalid Event ID");
	    }

	    Event event = eventService.getEventById(eventId);

	    // Loop through each parameter and store attendance status
	    request.getParameterMap().forEach((key, value) -> {
	        if (key.startsWith("status_")) {
	            try {
	                int studentId = Integer.parseInt(key.split("_")[1]);
	                String status = value[0];

	                // Store the attendance status in the database
	                eventService.postAttendance(manager.getId(), event, studentId, status);
	            } catch (NumberFormatException | NullPointerException e) {
	                // Log the error and continue with the next iteration
	                // Optionally, you can return an error message or redirect to a specific page
	            }
	        }
	    });

	    // Redirect to the manager events page after successfully storing attendance
	    return new ModelAndView("redirect:/managerEvents");
	}

	
	@GetMapping("managerlogout")
	public ModelAndView managerlogout(HttpServletRequest request) 
	{
		HttpSession session = request.getSession();
		
		session.removeAttribute("manager");
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("managerlogin");
		return mv;
	}
}