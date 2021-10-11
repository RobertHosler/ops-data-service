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

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com", "http://sp-app-qa.herokuapp.com"})
	@GetMapping("/airtable")
	public Object airtable() {
		return airtableService.getAirtable();
	}

	// Type Twins
	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com", "http://sp-app-qa.herokuapp.com"})
	@GetMapping("/opsRecords")
	public Object opsRecords(
			@RequestParam(value = "maxRecords", defaultValue = "5") String maxRecords,
			@RequestParam(value = "s1", defaultValue = "Fe") String saviorOne,
			@RequestParam(value = "s2", defaultValue = "Se") String saviorTwo,
			@RequestParam(value = "as", defaultValue = "PC/S(B)") String animalStack,
			@RequestParam(value = "cm", defaultValue = "false") boolean includeCommunity) {
		return airtableService.getAirtableRecords(maxRecords, saviorOne, saviorTwo, animalStack, includeCommunity);
	}

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com", "http://sp-app-qa.herokuapp.com"})
	@GetMapping("/tenCoins")
	public Object tenCoins(
			@RequestParam(value = "maxRecords", defaultValue = "5") String maxRecords,
			@RequestParam(value = "hn1", defaultValue = "") String needOne, // Observer vs Decider
			@RequestParam(value = "ohn", defaultValue = "") String observerNeed,
			@RequestParam(value = "dhn", defaultValue = "") String deciderNeed,
			@RequestParam(value = "dl", defaultValue = "") String deciderLetter,
			@RequestParam(value = "ol", defaultValue = "") String observerLetter,
			@RequestParam(value = "ia", defaultValue = "") String infoAnimal,
			@RequestParam(value = "ea", defaultValue = "") String energyAnimal,
			@RequestParam(value = "dom", defaultValue = "") String dominance,
			@RequestParam(value = "smod", defaultValue = "") String sensoryModality,
			@RequestParam(value = "demod", defaultValue = "") String deModality,
			@RequestParam(value = "sex", defaultValue = "") String sex,
			@RequestParam(value = "cm", defaultValue = "false") boolean includeCommunity) {
		TenCoins tenCoins = new TenCoins();
		tenCoins.setPrimaryHumanNeed(needOne);
		tenCoins.setObserverHumanNeed(observerNeed);
		tenCoins.setDeciderHumanNeed(deciderNeed);
		tenCoins.setDeciderLetter(deciderLetter);
		tenCoins.setObserverLetter(observerLetter);
		tenCoins.setInfoAnimal(infoAnimal);
		tenCoins.setEnergyAnimal(energyAnimal);
		tenCoins.setAnimalDominance(dominance);
		tenCoins.setSensoryModality(sensoryModality);
		tenCoins.setDeModality(deModality);
		tenCoins.setSex(sex);
		return airtableService.getAirtableCoins(maxRecords, tenCoins, includeCommunity);
	}

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com", "http://sp-app-qa.herokuapp.com"})
	@GetMapping("/name")
	public Object opsRecordName(
			@RequestParam(value = "maxRecords", defaultValue = "5") String maxRecords,
			@RequestParam(value = "name", defaultValue = "") String nameString,
			@RequestParam(value = "cm", defaultValue = "false") boolean includeCommunity) {
		return airtableService.getAirtableRecordsByName(maxRecords, nameString, includeCommunity);
	}

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com", "http://sp-app-qa.herokuapp.com"})
	@GetMapping("/type")
	public Object opsRecordType(
			@RequestParam(value = "maxRecords", defaultValue = "5") String maxRecords,
			@RequestParam(value = "type", defaultValue = "") String typeString,
			@RequestParam(value = "cm", defaultValue = "false") boolean includeCommunity) {
		return airtableService.getAirtableRecordsByType(maxRecords, typeString, includeCommunity);
	}
}
