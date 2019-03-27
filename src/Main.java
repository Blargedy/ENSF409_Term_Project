import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Reading data from file");
        String filename = "input.txt";
        BinSearchTree studentData = readFile(filename);
    }

    public static BinSearchTree readFile(String filename){
        File file;
        BufferedReader br;
        BinSearchTree BST = new BinSearchTree();
        try{
            file = new File(filename);
            br = new BufferedReader(new FileReader(file));
                try {
                    String line;
                    while ((line = br.readLine()) != null){
                        String[] splitLine = line.split("\\s+");
                        BST.insert(splitLine[1], splitLine[2], splitLine[3], splitLine[4]);
                    }

                } catch (IOException e) {
                    System.out.println("IO exception");
                    e.printStackTrace();
                }

        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            e.printStackTrace();
        }
        return BST;
    }
}
