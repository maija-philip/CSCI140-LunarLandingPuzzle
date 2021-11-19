package puzzles.water;

import solver.Configuration;

import java.util.ArrayList;
import java.util.Objects;

public class WaterConfiguration implements Configuration<ArrayList<Integer>> {

    private int amount;
    private ArrayList<Integer> buckets;

    public WaterConfiguration(int amount, ArrayList<Integer> buckets) {
        this.amount = amount;
        this.buckets = buckets;
    }

    @Override
    public boolean isSolution(ArrayList<Integer> integers) {
        boolean result = false;

        for (Integer i: integers) {
            if (i == amount) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getNeighbors(ArrayList<Integer> integers) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        //System.out.println("integers: " + integers);



        for (int i = 0; i < integers.size(); i++) {
            ArrayList<Integer> options = new ArrayList<>(integers);

            int MaxI = buckets.get(i);
            int currentI = integers.get(i);

            /* fill up */
            options.set(i, MaxI);
            result.add(options);

            options = new ArrayList<>(integers);

            /* dump out */
            options.set(i, 0);
            result.add(options);

            //System.out.println("integers size: " + integers.size());



            /* fill other bucket */
            for (int j = 0; j < integers.size(); j++) {
                if ( i != j) {

                    int MaxJ = buckets.get(j);
                    int currentJ = integers.get(j);


                    ArrayList<Integer> options2 = new ArrayList<>(integers);
                    int spaceInJ = MaxJ-currentJ;

                    if (currentI > spaceInJ) {
                        options2.set(i, currentI - spaceInJ);
                        options2.set(j, MaxJ);
                        result.add(options2);
                    }
                    else {
                        options2.set(i, 0);
                        options2.set(j, currentJ + currentI);
                        result.add(options2);
                    }

                }
            }




        }
        //System.out.println(result);
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterConfiguration that = (WaterConfiguration) o;
        return amount == that.amount && Objects.equals(buckets, that.buckets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, buckets);
    }
}
