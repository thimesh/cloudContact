package com.blog.main.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.main.dao.ContactDao;
import com.blog.main.dao.UserDao;
import com.blog.main.model.Contact;
import com.blog.main.model.User;
import com.blog.main.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
public class UserController {
	public static final String EXTERNAL_FILE_PATH ="/Users/himeshtulsiani/Desktop/java/smartMessage/src/main/webapp/images";
	public static final String EXTERNAL_CSV_FILE_PATH ="/Users/himeshtulsiani/Desktop/java/smartMessage/src/main/webapp/files";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao dao;
	
	@Autowired
	private ContactDao contactDao;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		//log.info(principal.getName());
		User user = dao.findByEmailId(principal.getName());
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/dashboard")
	public String dashboard( Model model, Principal principal ) {
		User user = dao.getUserByUsername(principal.getName());
		user.setOnline(true);
		dao.save(user);
		return "dashboard";
	}
	
	
	
	@GetMapping("/contact/{page}")
	public String contacts(@PathVariable("page") int page,Principal principal, Model model) throws ParseException {
		String username = principal.getName();
		User user = dao.findByEmailId(username);
		// here will show recent contact and all contacts
		log.info("Pagination Implemented");
		// will fetch from db top 5 recent contacts
		Date date = new Date();
		PageRequest pageable = PageRequest.of(page, 5);

		// here will show all contacts

		Page<Contact> listContacts = null;
		if(user != null) {
			listContacts = contactDao.findContactsByUser(user.getuId(), pageable);

		}


		List<Contact> recentContacts = contactDao.getContactsByTime(user.getuId(), format.format(date));
		List<Contact> recentContactsLastName = new ArrayList<>();
		int count = 0;
		for (Contact contact : recentContacts) {
			if(count ==3) {
				break;
			}
			recentContactsLastName.add(contact);
			count+=1;
		}
		

		//String n = String.valueOf(false);
		model.addAttribute("username", user.getName());
		model.addAttribute("contacts", listContacts);
		model.addAttribute("recentContacts", recentContactsLastName);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpages", listContacts.getTotalPages());
		log.info("ff");
		return "contact";
	}
	
	@GetMapping("/addcontact")
	public String addContacts(HttpServletRequest request, Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("result", "");
		return "addcontact";
	}
	
	
	@PostMapping("/addcontact1")
	public String addContact(Model model, @ModelAttribute Contact contact, BindingResult bindingResult, Principal principal,
			@RequestParam("image") MultipartFile file) throws ParseException {
	//	, @RequestParam("image") MultipartFile file
		log.info(contact.toString());
		
		String d = new Date().toGMTString();
		String d1 = new Date().toLocaleString();
		contact.setTime(formatter.format(new Date()));
		//Do file proccess
		
		try {
			if(!file.isEmpty()) {
				String fileName = contact.getPhoneNumber()+file.getOriginalFilename();
				
				contact.setImage(fileName);
				InputStream is = file.getInputStream();
				byte b[] = new byte[is.available()];
				is.read(b);
				
				// write
				FileOutputStream fos = new FileOutputStream(EXTERNAL_FILE_PATH+"/"+fileName);
				fos.write(b);
				fos.close();
				
				
			}else {
				model.addAttribute("title", "Provide photo");
				return "";
			}
		}catch (Exception e) {
			e.printStackTrace();		}
		
		log.info(file.getName());
		
		User user = dao.findByEmailId(principal.getName());
		contact.setUser(user);
		user.getContact().add(contact);
		String result = "";
		try {
			
			dao.save(user);
		}catch (Exception e) {
			result = "Failed";
			e.printStackTrace();
		}
		result = "Success";
		
		model.addAttribute("result", result);
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "addcontact";
	}
	
	@GetMapping("/show/{id}")
	public String showSingleContact(@PathVariable("id") int id, Model model,Principal principal){
		Optional<Contact> contact =null;
		log.info("id :: "+id);
		
		contact = contactDao.findById(id);
		Contact con = null;
		if(contact.isPresent()) {
			con = contact.get();
		}
		User user = dao.findByEmailId(principal.getName());
		if(user.getuId()== con.getUser().getuId()) {
			model.addAttribute("contact", con);
		}
		// set image in contact for display
		
		
		return "showcontact";
	}
	
	
	@GetMapping("/settings")
	public String settings() {
		
		return "setting";
	}
	
	@GetMapping("/chatwindow")
	public String chatWindow(Principal principal, Model model) {
		User user = dao.findByEmailId(principal.getName());
		model.addAttribute("user", user);
		
		
		return "chatwindow";
	}
	
	@GetMapping("/profile")
	public String profile(Principal principal, Model model) {
		User user = dao.findByEmailId(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("username", user.getName());
		
		return "profile";
	}
	
	@GetMapping("/contactupload")
	public String contactupload(Principal principal, Model model) {
		User user = dao.findByEmailId(principal.getName());
		model.addAttribute("user", user);
		
		
		return "contactupload";
	}
	
	
	
}
