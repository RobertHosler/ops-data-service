# Objective Personality System Data Service
Created to pull data from the Objective Personality Database in association with [my angular web app](https://github.com/RobertHosler/ops-type-points/). 

Currently the Community Members from the database are excluded from the results by default (though there is a boolean for this).  They can also be added to the inclusions list below to be readded to the results.

[Inclusions List](src/main/resources/communityInclusions.txt)

## endpoints

### Name String
/name?name=Dan

### Type String
/type?type=FF-Fe/Se

### Ten Coins
/tenCoins?cm=false&maxRecords=10&hn1=O&ohn=Oe&dhn=De&dl=F&ol=S&ia=Consume&ea=Play&dom=Energy&smod=F&demod=F

### Type Twins
Pass in an OPS Type without modality
* s1 - First Savior
* s2 - Second Savior
* as - Animal Stack

/opsRecords?s1=Fe&s2=Se&as=PC/S(B)&cm=false&maxRecords=10