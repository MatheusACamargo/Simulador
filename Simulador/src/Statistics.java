import java.util.Arrays;

public class Statistics {
    Double[] data;
    int size;

    public Statistics(Double[] data) {
        this.data = data;
        Arrays.sort(data);
        size = data.length;
    }

    public Double[] getData() {
        return data;
    }

    double getMean() {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    double getVariance() {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

    double getStdDev() {
        return Math.sqrt(getVariance());
    }

    double getMin() {
        return data[0];
    }

    double get1stQuartile() {
        int quart = (int) (size*0.25);
        return data[quart];
    }
    
    double getMedian() {
        int med = size / 2;
        //se há dois elementos no meio
        if(med%2==0){
            return (data[med] + data[med + 1]) / 2;
        }else{
            return data[med];
        }
    }

    double get3thQuartile() {
        int quart = (int) (size*0.75);
        return data[quart];
    }

    double getMax() {
        return data[size - 1];
    }
    
    
}
