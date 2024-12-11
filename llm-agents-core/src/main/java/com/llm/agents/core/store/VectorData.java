package com.llm.agents.core.store;
import com.llm.agents.core.bean.MetaData;

import java.util.Arrays;

/**
 * @Author Devin
 * @Date 2024/12/11 22:55
 * @Description:
 **/
public class VectorData extends MetaData {

    private double[] vector;

    public double[] getVector(){
        return vector;
    }

    public void setVector(double[] vector){
        this.vector = vector;
    }

    @Override
    public String toString() {
        return "VectorData{" +
                "metadataMap=" + metaDataMap +
                ", vector=" + Arrays.toString(vector) +
                '}';
    }

}
