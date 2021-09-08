package me.roqb.opsdata.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirtableController {

	@Autowired
	private AirtableService airtableService;

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com"})
	@GetMapping("/airtable")
	public Object airtable() {
		return airtableService.getAirtable();
	}

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com"})
	@GetMapping("/opsRecords")
	public Object opsRecords(
			@RequestParam(value = "maxRecords", defaultValue = "5") String maxRecords,
			@RequestParam(value = "s1", defaultValue = "Fe") String saviorOne,
			@RequestParam(value = "s2", defaultValue = "Se") String saviorTwo,
			@RequestParam(value = "as", defaultValue = "PC/S(B)") String animalStack,
			@RequestParam(value = "cm", defaultValue = "false") boolean includeCommunity) {
		return airtableService.getAirtableRecords(maxRecords, saviorOne, saviorTwo, animalStack, includeCommunity);
	}
}
