import edu.duke.*;

public class Part3 {
StorageResource genes = new StorageResource();
StorageResource getGenes = new StorageResource();
FileResource fr = new FileResource("brca1line.fa");
String dna = fr.asString();
public void processGenes(StorageResource sr){
    int count = 0;
    int cgCount = 0;
    String currLongest = "";
    for(String s : sr.data()){
        if(s.length() > 60){
            System.out.println(s);
            count++;
        }
    }
    System.out.println(count);
    for(String s : sr.data()){
        if(cgRatio(s) > .35){
            System.out.println(s);
            cgCount++;
        }
    }
    System.out.println(cgCount);
    
    for(String s : sr.data()){
        if (s.length() > currLongest.length()){
         currLongest = s;   
        }
    }
    System.out.println(currLongest);
}
public float cgRatio(String dna){
    int cgCount = 0;
    for(int i = 0; i < dna.length(); i++){
     if(dna.charAt(i) == 'c' || dna.charAt(i) == 'g' ){
         cgCount++;
        }
    }
   float cgRatio = (float)cgCount / dna.length();
   return cgRatio;
}
public int findStopCodon(String dna, int startIndex,String stopCodon){
    int endIndex = dna.indexOf(stopCodon, startIndex + 3);
    while(endIndex != -1){
        if((startIndex - endIndex) % 3 == 0){
            return endIndex;   
        }
        else{
            endIndex = dna.indexOf(stopCodon, endIndex + 1);   
        }
    }
    return dna.length();
}
public String findGene(String dna){
    int startIndex = dna.indexOf("atg");
    if(startIndex == -1){
     return "";   
    }
    int taaIndex = findStopCodon(dna, startIndex, "taa");
    int tagIndex = findStopCodon(dna, startIndex, "tag");
    int tgaIndex = findStopCodon(dna, startIndex, "tga");
    int temp = Math.min(taaIndex, tagIndex);
    int minIndex = Math.min(temp, tgaIndex);
    if(minIndex == dna.length()){
     return "";   
    }
    return dna.substring(startIndex, minIndex + 3);
    
}

public StorageResource getAllGenes(String dna){
    String currentGene = findGene(dna);
    int countGenes = 0;
    while(true){
        if(currentGene.isEmpty()){
         break;   
        }
        
        genes.add(currentGene);
        currentGene = findGene((dna.substring(dna.lastIndexOf(currentGene) + 1, dna.length())));
        countGenes++;
    }
    System.out.println(countGenes);
    return genes;
}
public void test(){
 getGenes = getAllGenes(dna); 
 processGenes(getGenes);
}
}
