package com.team254.lib.trajectory.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.Trajectory;

/**
 *
 * @author Jared341
 */
public class TextFileDeserializer implements IPathDeserializer {

  public Path deserialize(String serialized) {
    StringTokenizer tokenizer = new StringTokenizer(serialized, "\n");
    System.out.println("Parsing path string...");
    System.out.println("String has " + serialized.length() + " chars");
    System.out.println("Found " + tokenizer.countTokens() + " tokens");
    
    String name = tokenizer.nextToken();
    int num_elements = Integer.parseInt(tokenizer.nextToken());
    
    Trajectory left = new Trajectory(num_elements);
    for (int i = 0; i < num_elements; ++i) {
      Trajectory.Segment segment = new Trajectory.Segment();
      StringTokenizer line_tokenizer = new StringTokenizer(
              tokenizer.nextToken(), " ");
      
            segment.pos = Double.parseDouble(line_tokenizer.nextToken());
            segment.vel = Double.parseDouble(line_tokenizer.nextToken());
            segment.acc = Double.parseDouble(line_tokenizer.nextToken());
            segment.jerk = Double.parseDouble(line_tokenizer.nextToken());
            segment.heading = Double.parseDouble(line_tokenizer.nextToken());
            segment.dt = Double.parseDouble(line_tokenizer.nextToken());
            segment.x = Double.parseDouble(line_tokenizer.nextToken());
            segment.y = Double.parseDouble(line_tokenizer.nextToken());
      
      left.setSegment(i, segment);
    }
    Trajectory right = new Trajectory(num_elements);
    for (int i = 0; i < num_elements; ++i) {
      Trajectory.Segment segment = new Trajectory.Segment();
      StringTokenizer line_tokenizer = new StringTokenizer(
              tokenizer.nextToken(), " ");
      
            segment.pos = Double.parseDouble(line_tokenizer.nextToken());
            segment.vel = Double.parseDouble(line_tokenizer.nextToken());
            segment.acc = Double.parseDouble(line_tokenizer.nextToken());
            segment.jerk = Double.parseDouble(line_tokenizer.nextToken());
            segment.heading = Double.parseDouble(line_tokenizer.nextToken());
            segment.dt = Double.parseDouble(line_tokenizer.nextToken());
            segment.x = Double.parseDouble(line_tokenizer.nextToken());
            segment.y = Double.parseDouble(line_tokenizer.nextToken());
      
      right.setSegment(i, segment);
    }
    
    System.out.println("...finished parsing path from string.");
    return new Path(name, new Trajectory.Pair(left, right));
  }

    public Path deserializeFromFile(String aFilename)
    {
        File the_file = new File(aFilename);

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(the_file));

            StringBuilder fileContents = new StringBuilder();

            String line;

            while ((line = br.readLine()) != null)
            {
                fileContents.append(line + "\n");
            }

            br.close();

            return deserialize(fileContents.toString());
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not find the path file.  It should be at : '" + the_file.getAbsolutePath() + "'");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
  
}
