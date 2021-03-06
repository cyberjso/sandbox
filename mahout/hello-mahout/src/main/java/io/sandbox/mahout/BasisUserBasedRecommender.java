package io.sandbox.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BasisUserBasedRecommender {
    private String DATA_SOURCE = "/user-choices.properties";

    public List<Recommendation> recommend(Integer userToRecommend, Integer recommendationsSize) {
        try {

            DataModel datamodel = new FileDataModel(new File(this.getClass().getResource(DATA_SOURCE).getFile()));

            UserSimilarity user = new PearsonCorrelationSimilarity(datamodel);

            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, user, datamodel);

            Recommender recommender = new GenericUserBasedRecommender(datamodel, neighborhood,  user);

            List<RecommendedItem> recommendations = recommender.recommend(userToRecommend, recommendationsSize);

            return  recommendations.stream().map(
                    item -> new Recommendation(String.valueOf(item.getItemID()), String.valueOf(item.getValue())))
                        .collect(Collectors.toList());

        } catch (IOException|TasteException e) {
            throw new RecommendationException(e);
        }
    }

}

