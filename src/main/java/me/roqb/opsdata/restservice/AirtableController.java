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

	// Type Twins
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

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com"})
	@GetMapping("/tenCoins")
	public Object opsRecords(
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
		return airtableService.getAirtableCoins(maxRecords, tenCoins, includeCommunity);
	}

	@CrossOrigin(origins = {"http://localhost:4200", "http://app.subjectivepersonality.com"})
	@GetMapping("/name")
	public Object opsRecords(
			@RequestParam(value = "maxRecords", defaultValue = "5") String maxRecords,
			@RequestParam(value = "name", defaultValue = "") String nameString,
			@RequestParam(value = "cm", defaultValue = "false") boolean includeCommunity) {
		return airtableService.getAirtableRecordsByName(maxRecords, nameString, includeCommunity);
	}
}
