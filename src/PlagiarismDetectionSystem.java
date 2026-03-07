
import java.util.*;

class document{
    String id;
    String content;
    public document(String id,String content){
        this.id=id;
        this.content=content;
    }
}

public class PlagiarismDetectionSystem {

    private Map<String, Set<String>> index = new HashMap<>();  //ngrams
    private Map<String, Integer> DocNgramCount = new HashMap<>();  //count storage


    //convertes string to lowecase and normalized it
    public List<String> tokenize(String text) {
        text = text.toLowerCase().replaceAll("[^a-z0-9 ]", " ");
        return Arrays.asList(text.split("\\s+"));
    }


    // converts to n grams
    public List<String> generateNGrams(List<String> words, int n) {
        List<String> ngrams = new ArrayList<>();

        for (int count = 0; count <= words.size() - n; count++) {
            String gram = String.join(" ", words.subList(count, count + n));
            ngrams.add(gram);
        }
        return ngrams;
    }


    //doing indexing
    public void indexing(document doc, int n) {
        List<String> words = tokenize(doc.content);
        List<String> ngrams=generateNGrams(words,n);
        DocNgramCount.put(doc.id,ngrams.size());

        for(String gram : ngrams){
            index.putIfAbsent(gram,new HashSet<>());
            index.get(gram).add(doc.id);
        }
    }

    public Map<String,Integer> analyzeDoc(String text,int n){
        List<String> words=tokenize(text);
        List<String> ngrams=generateNGrams(words,n);

        Map<String,Integer> matchcount=new HashMap<>();

        for(String gram :ngrams){
            if(index.containsKey(gram)){
                for(String id : index.get(gram)){
                    matchcount.put(id, matchcount.getOrDefault(id,0)+1);
                }
            }
        }
        return matchcount;
    }

    public void detect(String text,int n){
        List<String> word=tokenize(text);
        List<String> nGRAM=generateNGrams(word,n);
        Map<String,Integer> output=analyzeDoc(text,n);
        printResults(output,nGRAM.size());
    }

    public double calculateSimilarity(int match,int total){
        return (match*100.0)/total;
    }

    public void printResults(Map<String, Integer> matches, int queryNgrams) {

        matches.entrySet().stream().sorted((a, b) -> b.getValue() - a.getValue()).forEach(entry -> {

                    double similarity =
                            calculateSimilarity(entry.getValue(),Math.min(queryNgrams, DocNgramCount.get(entry.getKey())));

                    System.out.println("--------------------------------");

                    System.out.println("Document: " + entry.getKey());
                    System.out.println("Matching n-grams: " + entry.getValue());
                    System.out.printf("Similarity: %.2f%%\n", similarity);

                    if (similarity > 50) {
                        System.out.println("PLAGIARISM DETECTED");
                    }
                });
    }

    public static void main (String[] args) {
        int n = 5;
        PlagiarismDetectionSystem detector = new PlagiarismDetectionSystem();
        document d1 = new document("E1", "Machine learning is a field of artificial intelligence that focuses on training algorithms to learn from data.");
        document d2 = new document("E2", "Machine learning is a field of artificial intelligence that focuses on training algorithms to learn from data and improve predictions.");
        detector.indexing(d1,n);
        detector.indexing(d2,n);

        String newDoc = "Machine learning is a field of artificial intelligence that focuses on training algorithms to learn from data.";

        detector.detect(newDoc,n);
    }
}

