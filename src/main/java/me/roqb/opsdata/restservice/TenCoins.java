package me.roqb.opsdata.restservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenCoins {
    private String primaryHumanNeed;
    private String observerHumanNeed;
    private String deciderHumanNeed;
    private String observerLetter;
    private String deciderLetter;
    private String infoAnimal;
    private String energyAnimal;
    private String animalDominance; // info/energy
    private String sensoryModality;
    private String deModality;

    public String getPrimaryHumanNeed() {
        String need = null;
        if (this.primaryHumanNeed == null)
            need = null;
        if (this.primaryHumanNeed.startsWith("O")) {
            need = "Single Observer / Double Decider";
        } else if (this.primaryHumanNeed.startsWith("D")) {
            need = "Single Decider / Double Observer";
        }
        return need;
    }
    public String getObserverLetter() {
        String letter = null;
        if (this.observerLetter == null)
            letter = null;
        if (this.observerLetter.startsWith("S")) {
            letter = "Sensory";
        } else if (this.observerLetter.startsWith("I") || this.observerLetter.startsWith("N")) {
            letter = "Intuition";
        }
        return letter;
    }
    public String getDeciderLetter() {
        String letter = null;
        if (this.deciderLetter == null)
            letter = null;
        if (this.deciderLetter.startsWith("F")) {
            letter = "Feeling";
        } else if (this.deciderLetter.startsWith("T")) {
            letter = "Thinking";
        }
        return letter;
    }
    public String getInfoAnimal() {
        String animal = null;
        if (this.infoAnimal == null)
            animal = null;
        if (this.infoAnimal.startsWith("B")) {
            animal = "Blast";
        } else if (this.infoAnimal.startsWith("C")) {
            animal = "Consume";
        }
        return animal;
    }
    public String getEnergyAnimal() {
        String animal = null;
        if (this.energyAnimal == null)
            animal = null;
        if (this.energyAnimal.startsWith("P")) {
            animal = "Play";
        } else if (this.energyAnimal.startsWith("S")) {
            animal = "Sleep";
        }
        return animal;
    }
    public String getAnimalDominance() {
        String animal = null;
        if (this.animalDominance == null)
            animal = null;
        if (this.animalDominance.startsWith("I")) {
            animal = "Info";
        } else if (this.animalDominance.startsWith("E")) {
            animal = "Energy";
        }
        return animal;
    }
    public String getDeModality() {
        return this.getFullModality(this.deModality);
    }
    public String getSensoryModality() {
        return this.getFullModality(this.sensoryModality);
    }
    private String getFullModality(String modality) {
        String mod = null;
        if (modality == null)
            mod = null;
        if (modality.startsWith("F")) {
            mod = "Feminine";
        } else if (modality.startsWith("M")) {
            mod = "Masculine";
        }
        return mod;
    }
}
